package ru.vetoshkin.store.core;
import lombok.Getter;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
public class SimpleResponse<T> {
    private final T result;


    public SimpleResponse(T result) {
        this.result = result;
    }
}
