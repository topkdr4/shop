package ru.vetoshkin.store.settings;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class BestProduct {
    private static final List<String> bestProducts = new CopyOnWriteArrayList<>();
    private static final Map<String, Product> bestProductEntity = new ConcurrentHashMap<>();

    private BestProduct() {

    }


    public static void init() throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call settings.get_best_product()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                bestProducts.add(set.getString(1));
            }
        }

        loadBestProduct();
    }


    private static void loadBestProduct() throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_products(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setArray(2, connection.createArrayOf("varchar", bestProducts.toArray()));

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

                bestProductEntity.put(product.getId(), product);
            }
        }
    }


    /**
     * Сохранить
     */
    public static void save(List<String> source) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            CallableStatement statement = connection.prepareCall("{call settings.save_best_product(?)}");
            statement.setArray(1, connection.createArrayOf("varchar", source.toArray()));
            statement.execute();

            bestProducts.clear();
            bestProductEntity.clear();

            bestProducts.addAll(source);
            loadBestProduct();
        } catch (Exception e) {
            e.printStackTrace();
            //
        }
    }


    public static void save(Product product) {
        if (!bestProductEntity.containsKey(product.getId()))
            return;

        bestProductEntity.put(product.getId(), product);
    }


    public static void remove(String product) {
        if (!bestProductEntity.containsKey(product))
            return;

        save(bestProductEntity.keySet().stream().filter(key -> !product.equals(key)).collect(Collectors.toList()));
    }



    /**
     * Получить все идентификаторы
     */
    public static List<String> getAll() {
        return bestProducts;
    }


    /**
     * Получить все лучшие товары
     */
    public static Map<String, Product> getALlProducts() {
        return bestProductEntity;
    }

}
