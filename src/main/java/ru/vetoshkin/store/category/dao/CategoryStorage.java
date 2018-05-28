package ru.vetoshkin.store.category.dao;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.admin.Admin;
import ru.vetoshkin.store.admin.AdminAuth;
import ru.vetoshkin.store.category.Category;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class CategoryStorage {
    private static final Map<Integer, Category> categories = new ConcurrentHashMap<>();


    public static void init() {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_categories()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                Category category = new Category();
                category.setId(set.getInt(1));
                category.setTitle(set.getString(2));

                categories.put(category.getId(), category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Stream<Category> stream() {
        return categories.values().stream();
    }


    public static int save(Category category) {
        try (Connection connection = HikariPool.getSource().getConnection()) {

            CallableStatement statement = connection.prepareCall("{? = call public.save_category(?, ?)}");
            statement.registerOutParameter(1, Types.INTEGER);

            if (category.getId() == null)
                 statement.setNull(2, Types.INTEGER);
            else statement.setInt(2, category.getId());

            statement.setString(3, category.getTitle());

            statement.execute();
            int id = statement.getInt(1);

            category.setId(id);
            categories.put(category.getId(), category);

            return id;
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }


    public static Category get(Integer id) {
        return categories.get(id);
    }


    public static void remove(Integer id) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call public.remove_category(?)}");
            statement.setInt(1, id);

            statement.execute();

            categories.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
