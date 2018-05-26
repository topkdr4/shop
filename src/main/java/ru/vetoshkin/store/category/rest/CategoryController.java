package ru.vetoshkin.store.category.rest;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.category.Category;
import ru.vetoshkin.store.category.dao.CategoryStorage;
import ru.vetoshkin.store.category.dto.CategoryRequest;
import ru.vetoshkin.store.category.dto.CategoryResponse;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.product.dto.ProductRequest;

import java.util.List;
import java.util.stream.Collectors;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private static final int itemsPerPage = 20;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SimpleResponse<List<CategoryResponse>> getAll() {
        List<CategoryResponse> result = CategoryStorage.stream()
                .map(Category::transfer)
                .collect(Collectors.toList());

        return new SimpleResponse<>(result);
    }


    @ResponseBody
    @RequestMapping(value = "/list/{page}", method = RequestMethod.POST)
    public SimpleResponse<List<CategoryResponse>> getPage(@PathVariable(name = "page") int page) {
        int from = (page - 1) * itemsPerPage;
        int to   = page * itemsPerPage;

        List<CategoryResponse> result = CategoryStorage.stream()
                .map(Category::transfer)
                .collect(Collectors.toList())
                .subList(from, to);

        return new SimpleResponse<>(result);
    }


    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public void remove(@PathVariable(name = "id") int id) {
        CategoryStorage.remove(id);
    }


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody CategoryRequest category) {
        CategoryStorage.save(category.transfer());
    }

}
