package ru.vetoshkin.store.settings;

import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;

/**
 * Ветошкин А.В.
 * РИС-16-бзу
 */
@Getter
@Setter
public class SettingsRequest implements DataTransferObject<Settings> {
    /**
     * Наименование магазина
     */
    private String title;

    /**
     * Почтовый хост
     */
    private String host;

    /**
     * Почтовый порт
     */
    private int port;

    /**
     * Адрес почты
     */
    private String email;

    /**
     * Пароль от почты
     */
    private String password;


    @Override
    public Settings transfer() {
        Settings result = new Settings();
        result.setTitle(title);
        result.setHost(host);
        result.setPort(port);
        result.setEmail(email);
        result.setPassword(password);

        return result;
    }
}
