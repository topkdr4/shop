package ru.vetoshkin.store.basket.dao;
import ru.vetoshkin.store.basket.OrderHistory;
import ru.vetoshkin.store.basket.OrderStatus;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class OrderStorage {


    private OrderStorage() {
    }


    /**
     * Колиество заказов, подлежащих обработке
     */
    public static int getNewOrderCount() {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".get_with_state_count(?)}");
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, OrderStatus.WAITING.getStatus());

            statement.execute();

            return statement.getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Количество страниц заказов
     */
    public static int getPageCount() {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".get__all_count()}");
            statement.registerOutParameter(1, Types.INTEGER);

            statement.execute();

            int all = statement.getInt(1);
            return all / 20 + 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Страница заказов
     */
    public static List<OrderHistory> getPage(int page) {
        List<OrderHistory> result = new ArrayList<>(20);
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call \"order\".get_page(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setInt(2, page);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                result.add(OrderHistory.create(set));
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Изменить статус заказа
     */
    public static void setStatus(int orderId, OrderStatus orderStatus) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            CallableStatement statement = connection.prepareCall("{call \"order\".set_status(?, ?)}");
            statement.setInt(1, orderId);
            statement.setString(2, orderStatus.getStatus());

            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
