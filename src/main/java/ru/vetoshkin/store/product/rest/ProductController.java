package ru.vetoshkin.store.product.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.product.XmlProduct;
import ru.vetoshkin.store.product.XmlProducts;
import ru.vetoshkin.store.product.dao.ImageStorage;
import ru.vetoshkin.store.product.dao.ProductService;
import ru.vetoshkin.store.product.dao.ProductStorage;
import ru.vetoshkin.store.product.dto.ProductRequest;
import ru.vetoshkin.store.product.dto.ProductResponse;
import ru.vetoshkin.store.util.HikariPool;
import ru.vetoshkin.store.util.Json;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private static final int itemsPerPage = 20;


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
        long items = 0;

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


    @ResponseBody
    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable(name = "id") int imageID
    ) throws Exception {
        byte[] data = ImageStorage.getImage(imageID);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(new ByteArrayInputStream(data)));
    }


    @RequestMapping(value = "/image/upload/{productID}/{index}", method = RequestMethod.POST)
    public void imageUpload(
            @PathVariable(name = "productID") String productID,
            @PathVariable(name = "index")     int index,
            @RequestParam("image") MultipartFile image) throws Exception {
        int fileId = ImageStorage.save(image.getBytes());
        ProductStorage.saveImage(productID, index, fileId);
    }


    /**
     * Удаление картинки у товара
     */
    @RequestMapping(value = "/image/remove/{productID}/{imageIndex}", method = RequestMethod.POST)
    public void removeImage(@PathVariable(name = "productID")  String productID,
                            @PathVariable(name = "imageIndex") int imageIndex) throws SQLException {
        ProductStorage.removeImage(productID, imageIndex);
        ImageStorage.remove(imageIndex);
    }


    /**
     * Сохранение товара
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SimpleResponse<String> save(@RequestBody ProductRequest product) {
        ProductStorage.save(product.transfer());
        return new SimpleResponse<>(product.getId());
    }


    /**
     * Удаление товара
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public void removeProduct(@PathVariable(name = "id") String productID) {
        ProductStorage.remove(productID);
    }


    /**
     * Загрузка xml
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public void uploadXml(@RequestParam("file") MultipartFile uploadFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlProducts.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XmlProducts from = (XmlProducts) unmarshaller.unmarshal(uploadFile.getInputStream());
            ProductService.add(from.getProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
