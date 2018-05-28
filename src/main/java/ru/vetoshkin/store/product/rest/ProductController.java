package ru.vetoshkin.store.product.rest;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vetoshkin.store.category.Category;
import ru.vetoshkin.store.category.dao.CategoryStorage;
import ru.vetoshkin.store.category.dto.CategoryResponse;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.product.ProductNotFound;
import ru.vetoshkin.store.product.dao.ImageStorage;
import ru.vetoshkin.store.product.dao.ProductStorage;
import ru.vetoshkin.store.product.dto.ProductRequest;
import ru.vetoshkin.store.product.dto.ProductResponse;
import ru.vetoshkin.store.util.HikariPool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private static final int itemsPerPage = 20;



    @ResponseBody
    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable(name = "id") String imageID) throws Exception {
        byte[] data = ImageStorage.getImage(imageID);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(new ByteArrayInputStream(data)));
    }


    @RequestMapping(value = "/image/upload/{productID}", method = RequestMethod.POST)
    public void imageUpload(
            @PathVariable(name = "productID") int productID,
            @RequestParam("image") MultipartFile[] images) throws ProductNotFound {

        Product product = ProductStorage.get(productID);

        if (product == null)
            throw new ProductNotFound(String.valueOf(productID));
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveProduct(@RequestBody ProductRequest product) {

    }


    /**
     * Загрузка xml
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST,  consumes = "multipart/form-data")
    public void uploadXml(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        String result = new String(uploadFile.getBytes(), StandardCharsets.UTF_8);
        System.out.println(result);
    }


    /**
     * Получить список товаров по категории и заданной странице
     */
    @RequestMapping(value = "/list/{category}/{page}", method = RequestMethod.POST)
    public static SimpleResponse<List<ProductResponse>> getProductOnCategory(
            @PathVariable(name = "category") int category,
            @PathVariable(name = "page") int page
    ) {
        List<ProductResponse> result = ProductStorage.getProduct(page, category)
                .stream()
                .map(Product::transfer)
                .collect(Collectors.toList());

        return new SimpleResponse<>(result);
    }


    /**
     * Получить кол-во страниц на категорию
     */
    @RequestMapping(value = "/list/{category}/count", method = RequestMethod.POST)
    public static SimpleResponse<Long> getPageCountOnCategory(
            @PathVariable(name = "category") int category
    ) throws Exception {
        long result = 0;
        long items  = 0;

        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call public.get_product_count(?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setInt(2, category);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            if (set.next()) {
                items = set.getLong(1);
            }

            result = (items / itemsPerPage) + 1;
        }

        return new SimpleResponse<>(result);
    }
}
