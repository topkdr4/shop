package ru.vetoshkin.store.product.dao;

import org.springframework.stereotype.Service;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 */
@Service
public class ProductStorage {



    public static Product get(String productID) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_product(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, productID);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            if (set.next()) {
                Product product = new Product();

                product.setId(set.getString(1));
                product.setTitle(set.getString(2));
                product.setDescription(set.getString(3));
                product.setCategory(set.getInt(4));
                product.setImages(Arrays.asList(
                        set.getInt(5),
                        set.getInt(6),
                        set.getInt(7)
                ));
                product.setPrice(set.getFloat(8));

                return product;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }


    public static void remove(String productID) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call public.remove_product(?)}");
            statement.setString(1, productID);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void save(Product product) {
        try (Connection connection = HikariPool.getSource().getConnection()) {

            CallableStatement statement = connection.prepareCall("{call public.save_product(?, ?, ?, ?, ?)}");
            statement.setString(1, product.getId());
            statement.setString(2, product.getTitle());
            statement.setString(3, product.getDescription());
            statement.setInt(4,    product.getCategory());
            statement.setFloat(5,  product.getPrice());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                product.setDescription(set.getString(3));
                product.setCategory(set.getInt(4));
                product.setImages(Arrays.asList(
                        set.getInt(5),
                        set.getInt(6),
                        set.getInt(7)
                ));
                product.setPrice(set.getFloat(8));

                result.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return result;
    }


    /**
     * Сохарнить ИД картинки в товар
     */
    public static void saveImage(String productID, int index, int fileId) {
        try (Connection connection = HikariPool.getSource().getConnection()) {

            CallableStatement statement = connection.prepareCall("{call public.save_image_in_product(?, ?, ?)}");
            statement.setString(1, productID);
            statement.setInt(2, index);
            statement.setInt(3, fileId);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Удалить ИД картинки и картинку
     */
    public static void removeImage(String productID, int imageIndex) throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call public.remove_image(?, ?)}");
            statement.setString(1, productID);
            statement.setInt(2, imageIndex);

            statement.execute();
        }
    }
}
