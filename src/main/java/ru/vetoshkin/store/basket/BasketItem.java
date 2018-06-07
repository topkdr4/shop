package ru.vetoshkin.store.basket;
import lombok.Getter;
import lombok.Setter;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class BasketItem {
    /**
     * Идентификатор
     */
    protected String id;

    /**
     * Наименование товара
     */
    protected String title;

    /**
     * Количество выбранного товара
     */
    protected int count;

    /**
     * Сумма
     */
    protected double sum;
}
