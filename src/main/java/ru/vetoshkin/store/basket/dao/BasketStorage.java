package ru.vetoshkin.store.basket.dao;
import ru.vetoshkin.store.basket.Basket;
import ru.vetoshkin.store.basket.BasketItem;
import ru.vetoshkin.store.basket.OrderHistory;
import ru.vetoshkin.store.basket.OrderStatus;
import ru.vetoshkin.store.product.dao.PriceService;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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



    /**
     * Очистить корзину
     */
    public static void clear(String sessionId) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call \"order\".clear_basket(?)}");
            statement.setString(1, sessionId);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Оплатить корзину
     */
    public static int buy(Basket basket, String email) {
        int orderId;
        Object[] productsId    = Basket.getProductsId(basket).toArray();
        Object[] productsCount = Basket.getProductsCount(basket).toArray();

        List<Float> prices = new ArrayList<>(productsCount.length);
        for (String productCode : Basket.getProductsId(basket)) {
            prices.add(PriceService.getPrice(productCode));
        }

        Object[] productsPrices = prices.toArray();

        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".buy(?, ?, ?, ?)}");
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, email);
            statement.setArray(3, connection.createArrayOf("varchar", productsId));
            statement.setArray(4, connection.createArrayOf("int4",    productsCount));
            statement.setArray(5, connection.createArrayOf("float4",  productsPrices));

            statement.execute();

            orderId = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return orderId;
    }



    /**
     * Получить кол-во заказов
     */
    public static int getOrderCount(String email) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".get_history_count(?)}");
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, email);

            statement.execute();

            return statement.getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Получить историю заказа
     */
    public static List<OrderHistory> getOrderHistory(String email, int page) {
        List<OrderHistory> result = new LinkedList<>();
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".get_history(?, ?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, email);
            statement.setInt(3, page);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);

            while (set.next()) {
                OrderHistory item = new OrderHistory();
                item.setOrderId(set.getInt(1));
                item.setTime(new Date(set.getTimestamp(2).getTime()));
                item.setPrice(set.getFloat(4));
                item.setStatus(OrderStatus.fromMap(set.getString(5)));

                result.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }
}
