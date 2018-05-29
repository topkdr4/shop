package ru.vetoshkin.store.product.dto;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.product.Product;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class ProductRequest implements DataTransferObject<Product> {
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
    private int category;

    /**
     * Цена
     */
    private float price;


    @Override
    public Product transfer() {
        Product result = new Product();
        result.setId(id);
        result.setTitle(title);
        result.setDescription(description);
        result.setCategory(category);
        result.setPrice(price);

        return result;
    }
}
