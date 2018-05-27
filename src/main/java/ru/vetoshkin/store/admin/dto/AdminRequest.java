package ru.vetoshkin.store.admin.dto;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.admin.Admin;
import ru.vetoshkin.store.core.DataTransferObject;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class AdminRequest implements DataTransferObject<Admin> {
    private String login;
    private String password;


    @Override
    public Admin transfer() {
        Admin admin = new Admin();
        admin.setLogin(login);
        admin.setPassword(password);
        
        return admin;
    }
}
