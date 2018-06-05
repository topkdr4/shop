package ru.vetoshkin.store.mail;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class TemplatesStorage {


    /**
     * Сохарнить шаблон письма
     */
    public static void saveTemplate(String key, String value) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call settings.save_template(?, ?)}");
            statement.setString(1, key);
            statement.setString(2, value);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Получить шаблон письма
     */
    public static String getTemplate(String key) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call settings.get_template(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, key);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            if (set.next())
                return set.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    /**
     * Удалить шаблон письма
     */
    public static void removeTemplate(String key) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call settings.remove_template(?)}");
            statement.setString(1, key);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
