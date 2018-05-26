package ru.vetoshkin.store.product.rest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.vetoshkin.store.product.ProductNotFound;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
//@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity exception(Exception e) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
