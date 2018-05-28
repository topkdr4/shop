package ru.vetoshkin.store.product.dto;
import lombok.Getter;
import lombok.Setter;





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
}
