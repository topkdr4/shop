package ru.vetoshkin.store.product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFound extends Exception {

    public ProductNotFound(String productID) {
        super("Product with \'" + productID + "\' not found");
    }
}
