package ru.vetoshkin.store.settings.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.settings.BestProduct;
import ru.vetoshkin.store.settings.Carousel;
import ru.vetoshkin.store.settings.Settings;
import ru.vetoshkin.store.settings.SettingsRequest;

import java.sql.SQLException;
import java.util.List;





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
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody SettingsRequest settings) throws SQLException {
        Settings.save(settings.transfer());
    }



    /**
     * Сохранить настройки карусели
     */
    @RequestMapping(value = "/carousel/save", method = RequestMethod.POST)
    public void saveCarousel(@RequestBody List<String> urls) {
        Carousel.save(urls);
    }


    /**
     * Сохранить натсройки лучших товаров
     */
    @RequestMapping(value = "/best_product/save", method = RequestMethod.POST)
    public void saveBest(@RequestBody List<String> codes) {
        BestProduct.save(codes);
    }
}
