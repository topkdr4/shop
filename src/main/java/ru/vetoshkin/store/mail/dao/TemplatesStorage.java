package ru.vetoshkin.store.mail.dao;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.mail.Template;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class TemplatesStorage {
    private static final Map<String, Template> templates = new ConcurrentHashMap<>();



    public static void init() {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call settings.get_template()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                Template template = new Template();
                template.setKey(set.getString(1));
                template.setValue(set.getString(2));
                template.setDesc(set.getString(3));

                templates.put(template.getKey(), template);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Сохарнить шаблон письма
     */
    public static void saveTemplate(Template template) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call settings.save_template(?, ?, ?)}");
            statement.setString(1, template.getKey());
            statement.setString(2, template.getValue());
            statement.setString(3, template.getDesc());

            statement.execute();

            templates.put(template.getKey(), template);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Получить шаблон письма
     */
    public static Template getTemplate(String key) {
        return templates.get(key);
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

            templates.remove(key);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * ключ/описание
     */
    public static Map<String, String> getDict() {
        Map<String, String> result = new LinkedHashMap<>();
        for (Template template : templates.values()) {
            result.put(template.getKey(), template.getDesc());
        }
        return result;
    }

}
