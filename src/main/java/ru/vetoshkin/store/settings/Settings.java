package ru.vetoshkin.store.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.mail.MailService;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Ветошкин А.В.
 * РИС-16-бзу
 */
@Getter
@Setter
@Service
@Order(5)
@Initialize
public class Settings {
    private static final Map<String, String> gloablSettings = new ConcurrentHashMap<>();
    private String title;
    private String host;
    private int port;
    private String email;
    private String password;


    private Settings(boolean isCopy) {
        this.title = gloablSettings.get("shop.title");
        this.host  = gloablSettings.get("smtp.host");
        this.port  = Integer.parseInt(gloablSettings.get("smtp.port"));
        this.email = gloablSettings.get("smtp.user");
        this.password = gloablSettings.get("smtp.pwd");
    }


    public Settings() {}


    /**
     * Инициализация настроек
     */
    public static void init() throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call settings.get_settings()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                gloablSettings.put(set.getString(1), set.getString(2));
            }

        }

        MailService.init();
    }


    /**
     * Сохранение настроек магазина
     */
    public static void save(Settings settings) throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call settings.save_settings(?, ?, ?, ?, ?)}");

            statement.setString(1, settings.title);
            statement.setString(2, settings.host);
            statement.setString(3, String.valueOf(settings.port));
            statement.setString(4, settings.email);
            statement.setString(5, settings.password);

            statement.execute();


            gloablSettings.put("shop.title", settings.title);
            gloablSettings.put("smtp.host", settings.host);
            gloablSettings.put("smtp.port", String.valueOf(settings.port));
            gloablSettings.put("smtp.user", settings.email);
            gloablSettings.put("smtp.pwd", settings.password);
        }
    }


    public static Settings getInstance() {
        return new Settings(true);
    }


    public static String getTitle() {
        return gloablSettings.get("shop.title");
    }
}
