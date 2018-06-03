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

    @Override
    public UserResponse transfer() {
        UserResponse result = new UserResponse();
        result.setEmail(email);
        result.setName(name);

        return result;
    }
}
