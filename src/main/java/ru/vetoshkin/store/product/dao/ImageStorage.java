package ru.vetoshkin.store.product.dao;
import com.google.common.cache.*;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.category.Category;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ImageStorage {
    private static final LoadingCache<Integer, byte[]> images = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<Integer, byte[]>() {
                @Override
                public byte[] load(Integer key) throws Exception {
                    try (Connection connection =  HikariPool.getSource().getConnection()) {
                        connection.setAutoCommit(false);

                        CallableStatement statement = connection.prepareCall("{? = call public.get_image(?)}");
                        statement.registerOutParameter(1, Types.OTHER);
                        statement.setInt(2, key);

                        statement.execute();

                        ResultSet set = (ResultSet) statement.getObject(1);
                        if (set.next()) {
                            return set.getBytes(1);
                        }

                    }
                    return new byte[0];
                }
            });


    public static byte[] getImage(int id) throws ExecutionException {
        return images.get(id);
    }


    public static int save(byte[] image) throws SQLException {
        return saveToStorage(image);
    }


    private static int saveToStorage(byte[] image) throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            String method = "{? = call public.save_image(?)}";
            CallableStatement statement = connection.prepareCall(method);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setBytes(2, image);

            statement.execute();
            return statement.getInt(1);
        }
    }


    public static void remove(int imageIndex) {
        images.invalidate(imageIndex);
    }
}
