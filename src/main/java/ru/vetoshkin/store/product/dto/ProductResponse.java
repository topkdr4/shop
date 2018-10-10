package ru.vetoshkin.store.product.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class ProductResponse {
    /**
     * Идентификатор
     */
    private String id;

    /**
     * Название
     */
    private String title;

    /**
     * Изображения
     */
    private List<Integer> images;

    /**
     * Цена
     */
    private float price;

    /**
     * Описание
     */
    private String description;
}
