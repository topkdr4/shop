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
    @RequestMapping(value = "/list/count", method = RequestMethod.POST)
    public SimpleResponse<Long> getPageCount() {
        return new SimpleResponse<>((CategoryStorage.stream().count() / itemsPerPage) + 1);
    }


    @ResponseBody
    @RequestMapping(value = "/list/{page}", method = RequestMethod.POST)
    public SimpleResponse<List<CategoryResponse>> getPage(@PathVariable(name = "page") int page) {
        int from = (page - 1) * itemsPerPage;
        int to   = page * itemsPerPage;

        List<CategoryResponse> searchResult = CategoryStorage.stream()
                .map(Category::transfer)
                .collect(Collectors.toList());

        List<CategoryResponse> result = searchResult.subList(from, to > searchResult.size() ? searchResult.size() : to);
        return new SimpleResponse<>(result);
    }


    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public void remove(@PathVariable(name = "id") int id) {
        CategoryStorage.remove(id);
    }


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SimpleResponse<Integer> save(@RequestBody CategoryRequest category) {
        return new SimpleResponse<>(CategoryStorage.save(category.transfer()));
    }

}
