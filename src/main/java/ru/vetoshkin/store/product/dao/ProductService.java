package ru.vetoshkin.store.product.dao;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.product.XmlProduct;
import ru.vetoshkin.store.util.HikariPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class ProductService {
    private static final int itemsPerPage = 8;
    private static final BlockingQueue<Holder> queue = new LinkedBlockingQueue<>();
    private static final ExecutorService executors = Executors.newFixedThreadPool(10);


    public static void init() {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                try {
                    while (true) {
                        Holder holder = queue.take();
                        byte[] data = loadImage(holder.getUrl());
                        int imageId = ImageStorage.save(data);
                        ProductStorage.save(holder.getProduct());
                        ProductStorage.saveImage(holder.getProduct().getId(), holder.getIndex(), imageId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }



    public static byte[] loadImage(String url) throws IOException {
        URL image = new URL(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (InputStream stream = image.openStream()) {
            byte[] byteChunk = new byte[4096];
            int n;

            while ((n = stream.read(byteChunk)) > 0) {
                baos.write(byteChunk, 0, n);
            }
        }

        return baos.toByteArray();
    }



    public static void add(List<XmlProduct> products) {
        for (XmlProduct product : products) {
            for (int i = 0; i < product.getImages().size(); i++) {
                queue.offer(new Holder(product.transfer(), product.getImages().get(i), i + 1));
            }
        }
    }



    @Getter
    private static class Holder {
        private final Product product;
        private final String url;
        private final int    index;


        private Holder(Product product, String url, int index) {
            this.product  = product;
            this.url      = url;
            this.index    = index;
        }

    }


    public static long getPageCount(int categoryID) throws SQLException {
        long result = 0;
        long items = 0;

        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_product_count(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setInt(2, categoryID);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            if (set.next()) {
                items = set.getLong(1);
            }

            result = (items / itemsPerPage) + 1;
        }

        return result;
    }

}
