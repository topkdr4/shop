package ru.vetoshkin.store.product.dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.product.Product;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class ProductStorage {
    private static final Logger logger = LoggerFactory.getLogger(ProductStorage.class);

    /**
     * Инициализация
     */
    public static void init() {

    }


    public static Product getProduct(String productID) {

        return null;
    }


    public static void removeProduct(String productID) {

    }


    public static void saveProduct(Product product) {

    }


}
