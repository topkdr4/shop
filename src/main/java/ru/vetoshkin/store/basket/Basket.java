package ru.vetoshkin.store.basket;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class Basket {
    protected List<BasketItem> items;
    protected double sum;


    public static List<String> getProductsId(Basket basket) {
        return basket.items.stream().map(BasketItem::getId).collect(Collectors.toList());
    }


    public static List<Integer> getProductsCount(Basket basket) {
        return basket.items.stream().map(BasketItem::getCount).collect(Collectors.toList());
    }
}
