package ru.vetoshkin.store.user.dto;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.user.User;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class UserRequest implements DataTransferObject<User> {
    /**
     * Логин
     */
    protected String email;

    /**
     * Пароль
     */
    protected String password;

    /**
     * Имя
     */
    protected String name;

    /**
     * Участие в почтовой раcсылкe
     */
    protected boolean dispatch = true;


    @Override
    public User transfer() {
        User result = new User();
        result.setEmail(email);
        result.setPassword(password);
        result.setName(name);
        result.setDispatch(dispatch);

        return result;
    }
}
