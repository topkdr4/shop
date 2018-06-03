package ru.vetoshkin.store.user;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.user.dto.UserResponse;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class User implements DataTransferObject<UserResponse> {
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
    public UserResponse transfer() {
        UserResponse result = new UserResponse();
        result.setEmail(email);
        result.setName(name);
        result.setDispatch(dispatch);

        return result;
    }
}
