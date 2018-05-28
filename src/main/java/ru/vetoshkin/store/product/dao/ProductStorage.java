package ru.vetoshkin.store.product.dao;

import org.springframework.stereotype.Service;
import ru.vetoshkin.store.category.Category;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 */
@Service
@Initialize
public class ProductStorage {

    /**
     * Инициализация
     */
    public static void init() {

    }


    public static Product get(Integer productID) {
        return null;
    }


    public static void remove(String productID) {

    }


    public static void save(Product product) {

    }


    /**
     * Получить список товаров по заданной категории и странице
     */
    public static List<Product> getProduct(int page, int category) {
        List<Product> result = new ArrayList<>();
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_product(?, ?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setInt(2, category);
            statement.setInt(3, page);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                Product product = new Product();
                product.setId(set.getString(1));
                product.setTitle(set.getString(2));

                result.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return result;
    }


}
