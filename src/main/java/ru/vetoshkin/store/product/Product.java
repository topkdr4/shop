package ru.vetoshkin.store.product;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.product.dto.ProductResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
@XmlRootElement
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
    private int category;

    /**
     * Изображения
     */
    private List<Integer> images;



    @Override
    public ProductResponse transfer() {
        ProductResponse result = new ProductResponse();
        result.setId(id);
        result.setTitle(title);
        
        return result;
    }
}
