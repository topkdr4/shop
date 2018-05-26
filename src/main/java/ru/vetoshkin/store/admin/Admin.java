package ru.vetoshkin.store.admin;
import lombok.Getter;
import lombok.Setter;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class Admin {
    private Integer id;
    private String sessionId;
    private String login;
    private String password;
}
