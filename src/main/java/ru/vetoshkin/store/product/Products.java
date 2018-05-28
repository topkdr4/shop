package ru.vetoshkin.store.product;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {
    private List<Product> products;
}
