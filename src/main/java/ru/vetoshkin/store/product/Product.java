package ru.vetoshkin.store.product;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.product.dto.ProductResponse;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class Product implements DataTransferObject<ProductResponse> {
    /**
     * Идентификатор
     */
    private String id;

    /**
     * Название
     */
    private String title;

    /**
     * Описание
     */
    private String description;

    /**
     * Категория
     */
    private String category;

    /**
     * Изображения
     */
    private List<String> images;



    @Override
    public ProductResponse transfer() {
        return null;
    }
}
