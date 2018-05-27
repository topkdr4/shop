package ru.vetoshkin.store.category.dto;
import lombok.Getter;
import lombok.Setter;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class CategoryResponse {
    /**
     * Идентификатор
     */
    private Integer id;

    /**
     * Наименование
     */
    private String title;
}
