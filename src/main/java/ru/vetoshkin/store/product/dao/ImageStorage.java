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
    private static final LoadingCache<String, byte[]> images = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, byte[]>() {
                @Override
                public byte[] load(String key) throws Exception {
                    try (Connection connection =  HikariPool.getSource().getConnection()) {
                        connection.setAutoCommit(false);

                        CallableStatement statement = connection.prepareCall("{? = call public.get_image(?)}");
                        statement.registerOutParameter(1, Types.OTHER);
                        statement.setInt(2, Integer.parseInt(key));

                        statement.execute();

                        ResultSet set = (ResultSet) statement.getObject(1);
                        if (set.next()) {
                            return set.getBytes(1);
                        }

                    }
                    return new byte[0];
                }
            });


    public static byte[] getImage(String id) throws ExecutionException {
        return images.get(id);
    }


    public static void save(byte[] image) throws SQLException {
        saveToStorage(image);
    }


    private static void saveToStorage(byte[] image) throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            String method = "{? = call public.save_image(?)}";
            CallableStatement statement = connection.prepareCall(method);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setBytes(2, image);

            statement.execute();
            int id = statement.getInt(1);
            System.out.println(id);
        }
    }

}
