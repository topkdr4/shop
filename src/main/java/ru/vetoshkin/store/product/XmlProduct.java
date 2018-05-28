package ru.vetoshkin.store.product;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
@XmlRootElement
public class XmlProduct implements DataTransferObject<Product> {
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
     * Изображения
     */
    private List<String> images;


    @Override
    public Product transfer() {
        Product result = new Product();
        result.setId(id);
        result.setTitle(title);
        result.setDescription(description);
        result.setCategory(category);

        return result;
    }
}
