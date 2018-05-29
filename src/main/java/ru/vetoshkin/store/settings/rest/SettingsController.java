package ru.vetoshkin.store.settings.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.settings.Settings;
import ru.vetoshkin.store.settings.SettingsRequest;

import java.sql.SQLException;

/**
 * Ветошкин А.В.
 * РИС-16-бзу
 */
@RestController
@RequestMapping(value = "/settings")
public class SettingsController {

    /**
     * Сохранить Настройки
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SimpleResponse<Object> save(@RequestBody SettingsRequest settings) throws SQLException, JsonProcessingException {
        Settings.save(settings.transfer());
        return new SimpleResponse<>(null);
    }

}
