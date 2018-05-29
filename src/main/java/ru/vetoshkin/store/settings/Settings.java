package ru.vetoshkin.store.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;

/**
 * Ветошкин А.В.
 * РИС-16-бзу
 */
@Getter
@Setter
@Service
@Initialize
public class Settings {
    private static final Settings gloablSettings = new Settings();
    private String title;
    private String host;
    private int port;
    private String email;
    private String password;


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
            if (set.next()) {
                gloablSettings.setTitle(set.getString(1));
                gloablSettings.setHost(set.getString(2));
                gloablSettings.setPort(set.getInt(3));
                gloablSettings.setEmail(set.getString(4));
                gloablSettings.setPassword(set.getString(5));
            }

        }
    }


    /**
     * Сохранение настроек магазина
     */
    public static void save(Settings settings) throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call settings.save_settings(?, ?, ?, ?, ?)}");

            statement.setString(1, settings.getTitle());
            statement.setString(2, settings.getHost());
            statement.setInt(3, settings.getPort());
            statement.setString(4, settings.getEmail());
            statement.setString(5, settings.getPassword());

            statement.execute();


            gloablSettings.setTitle(settings.getTitle());
            gloablSettings.setHost(settings.getHost());
            gloablSettings.setPort(settings.getPort());
            gloablSettings.setEmail(settings.getEmail());
            gloablSettings.setPassword(settings.getPassword());
        }
    }


    public static Settings getInstance() {
        return gloablSettings;
    }
}
