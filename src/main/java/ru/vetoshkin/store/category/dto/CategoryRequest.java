package ru.vetoshkin.store.category.dto;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.category.Category;
import ru.vetoshkin.store.core.DataTransferObject;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class CategoryRequest implements DataTransferObject<Category> {
    /**
     * Идентификатор
     */
    private Integer id;

    /**
     * Наименование
     */
    private String title;


    @Override
    public Category transfer() {
        Category result = new Category();
        result.setId(id);
        result.setTitle(title);

        return result;
    }
}
