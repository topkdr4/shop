package ru.vetoshkin.store.basket;
import lombok.Getter;
import lombok.Setter;

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
}
