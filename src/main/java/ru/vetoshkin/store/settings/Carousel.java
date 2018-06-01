package ru.vetoshkin.store.settings;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Ветошкин А.В.
 * РИС-16-бзу
 */
@Service
@Initialize
public class Carousel {
    private static final List<String> URLS = new CopyOnWriteArrayList<>();
    private static final Map<Integer, byte[]> images = new ConcurrentHashMap<>();

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
            while (set.next()) {
                URLS.add(set.getString(1));
            }
        }
    }

    @Getter
    private static class Holder {
        private final int index;
        private final String url;

        private Holder(int index, String url) {
            this.index = index;
            this.url = url;
        }
    }
}
