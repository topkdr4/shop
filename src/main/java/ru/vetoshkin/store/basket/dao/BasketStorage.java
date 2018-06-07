package ru.vetoshkin.store.basket.dao;
import ru.vetoshkin.store.basket.Basket;
import ru.vetoshkin.store.basket.BasketItem;
import ru.vetoshkin.store.product.dao.PriceService;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class BasketStorage {


    /**
     * Получить каризну по пользователю
     */
    public static Basket getBasket(String sessionId) {
        Basket result = new Basket();
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".get_basket(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, sessionId);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);

            List<BasketItem> basketItems = new LinkedList<>();
            double sum = 0;

            while (set.next()) {
                BasketItem item = new BasketItem();
                item.setId(set.getString(2));
                item.setCount(set.getInt(3));
                item.setTitle(PriceService.getTitle(item.getId()));

                double itemSum = (PriceService.getPrice(item.getId()) * item.getCount());
                item.setSum(itemSum);

                sum += itemSum;

                basketItems.add(item);
            }

            result.setItems(basketItems);
            result.setSum(sum);

        } catch (Exception e) {
            return result;
        }

        return result;
    }



    /**
     * Сохранить корзину
     */
    public static void save(String sessionId, Basket basket) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            Object[] productsId = Basket.getProductsId(basket).toArray();
            Object[] productsCount = Basket.getProductsCount(basket).toArray();

            if (productsId.length == 0) {
                CallableStatement statement = connection.prepareCall("{call \"order\".clear_basket(?)}");
                statement.setString(1, sessionId);

                statement.execute();
                return;
            }


            CallableStatement statement = connection.prepareCall("{call \"order\".save_basket(?, ?, ?)}");
            statement.setString(1, sessionId);

            statement.setArray(2, connection.createArrayOf("varchar", productsId));
            statement.setArray(3, connection.createArrayOf("int4", productsCount));

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
