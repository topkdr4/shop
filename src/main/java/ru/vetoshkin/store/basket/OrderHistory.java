package ru.vetoshkin.store.basket;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class OrderHistory {
    /**
     * номер заказа
     */
    protected int orderId;

    /**
     * Дата заказа
     */
    protected Date time;

    /**
     * Сумма заказа
     */
    protected float price;

    /**
     * Статус заказа
     */
    protected OrderStatus status;


    public static OrderHistory create(ResultSet set) throws SQLException {
        OrderHistory temp = new OrderHistory();
        temp.setOrderId(set.getInt(1));
        temp.setTime(new Date(set.getTimestamp(2).getTime()));
        temp.setPrice(set.getFloat(4));
        temp.setStatus(OrderStatus.fromMap(set.getString(5)));

        return temp;
    }
}
