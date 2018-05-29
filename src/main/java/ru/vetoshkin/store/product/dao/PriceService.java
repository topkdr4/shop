package ru.vetoshkin.store.product.dao;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class PriceService {
    private static final Map<String, Float> prices = new ConcurrentHashMap<>();
    private static final Map<String, String> titles = new ConcurrentHashMap<>();


    public static void init() {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            //get_all_product
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_all_product()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                String code  = set.getString(1);
                float price  = set.getFloat(2);
                String title = set.getString(3);

                prices.put(code, price);
                titles.put(code, title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static float getPrice(String code) {
        return prices.get(code);
    }


    public static Map<String, Float> getALlPrice() {
        return prices;
    }


    public static Map<String, String> getAllTitles() {
        return titles;
    }


    public static void setPrice(String id, float price) {
        prices.put(id, price);
    }


    public static void setTitle(String id, String title) {
        titles.put(id, title);
    }
}
