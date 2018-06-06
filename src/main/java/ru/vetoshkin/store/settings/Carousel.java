package ru.vetoshkin.store.settings;

import lombok.Getter;
import lombok.Setter;
import org.postgresql.PGConnection;
import org.postgresql.jdbc.PgArray;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.product.dao.ProductService;
import ru.vetoshkin.store.util.HikariPool;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;





/**
 * Ветошкин А.В.
 * РИС-16-бзу
 */
@Service
@Initialize
public class Carousel {
    private static final Map<Integer, CarouselItem> images = new ConcurrentHashMap<>();
    private static final byte[] EMPTY_IMAGE = new byte[0];
    private static String classes = "";


    private Carousel() {

    }


    /**
     * Инициализация настроек
     */
    public static void init() throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call settings.get_carousel()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            StringBuilder builder = new StringBuilder();
            int index = 1;
            while (set.next()) {
                CarouselItem item = new CarouselItem();
                item.setIndex(index++);
                item.setUrl(set.getString(1));

                try {
                    byte[] data = ProductService.loadImage(item.getUrl());
                    item.setSource(data);
                } catch (IOException e) {
                    item.setSource(EMPTY_IMAGE);
                }

                images.put(item.getIndex(), item);
                appendClass(builder, item);
            }

            classes = builder.toString();
        }
    }



    public static List<String> getUrls() {
        return images.values().stream()
                .map(CarouselItem::getUrl)
                .collect(Collectors.toList());
    }



    public static String getClasses() {
        return classes;
    }



    private static void appendClass(StringBuilder builder, CarouselItem item) {
        builder.append(".carousel-").append(item.getIndex()).append("{")
                .append("background-image: url(\"").append("/carousel/").append(item.getIndex()).append("\");")
                .append("background-repeat: no-repeat;")
                .append("background-position: center center;")
                .append("background-size: cover;")
                .append("}\n");
    }


    public static void save(List<String> url) {
        images.clear();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < url.size(); i++) {
            CarouselItem item = new CarouselItem();
            item.setIndex(i + 1);
            item.setUrl(url.get(i));

            try {
                byte[] data = ProductService.loadImage(item.getUrl());
                item.setSource(data);
            } catch (IOException e) {
                item.setSource(EMPTY_IMAGE);
            }

            images.put(item.getIndex(), item);
            appendClass(builder, item);
        }

        try (Connection connection = HikariPool.getSource().getConnection()) {
            CallableStatement statement = connection.prepareCall("{call settings.save_carousel(?)}");
            statement.setArray(1, connection.createArrayOf("varchar", url.toArray()));
            statement.execute();
        } catch (Exception e) {
            //
        }
        classes = builder.toString();
    }


    public static byte[] getImage(int imageID) {
        return images.get(imageID).getSource();
    }



    @Getter
    @Setter
    private static class CarouselItem {
        private int index;
        private String url;
        private byte[] source;


        @Override
        public String toString() {
            return url;
        }
    }

}
