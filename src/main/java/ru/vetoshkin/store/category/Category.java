package ru.vetoshkin.store.category;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.category.dto.CategoryResponse;
import ru.vetoshkin.store.core.DataTransferObject;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class Category implements DataTransferObject<CategoryResponse> {
    /**
     * Идентификатор
     */
    private Integer id;

    /**
     * Наименование
     */
    private String title;


    @Override
    public CategoryResponse transfer() {
        return null;
    }
}
