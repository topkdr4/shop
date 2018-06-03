package ru.vetoshkin.store.user.dto;
import lombok.Getter;
import lombok.Setter;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class UserResponse {
    /**
     * Логин
     */
    protected String email;

    /**
     * Имя
     */
    protected String name;
}
