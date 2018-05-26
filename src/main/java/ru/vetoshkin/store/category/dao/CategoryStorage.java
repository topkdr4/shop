package ru.vetoshkin.store.category.dao;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.category.Category;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
public class CategoryStorage {
    private static final Map<Integer, Category> categories = new ConcurrentHashMap<>();


    public static Stream<Category> stream() {
        return categories.values().stream();
    }


    public static void save(Category category) {
        categories.put(category.getId(), category);
    }


    public static Category get(Integer id) {
        return categories.get(id);
    }


    public static void remove(Integer id) {
        categories.remove(id);
    }
}
