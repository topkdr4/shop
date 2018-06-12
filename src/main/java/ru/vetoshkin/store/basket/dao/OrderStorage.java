package ru.vetoshkin.store.basket.dao;
import ru.vetoshkin.store.basket.OrderStatus;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;





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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
