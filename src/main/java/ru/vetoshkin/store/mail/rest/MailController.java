package ru.vetoshkin.store.mail.rest;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.mail.dao.MailService;
import ru.vetoshkin.store.mail.dao.TemplatesStorage;
import ru.vetoshkin.store.mail.dto.TemplateRequest;
import ru.vetoshkin.store.mail.dto.TemplateResponse;





/**
 * Ветошкин А.В. РИС-16бзу
 * */

@RestController
@RequestMapping(value = "/template")
public class MailController {


    /**
     * Получить значение шаблона
     */
    @RequestMapping(value = "/get/{key}", method = RequestMethod.POST)
    public static SimpleResponse<TemplateResponse> get(@PathVariable(name = "key") String key) {
        return new SimpleResponse<>(TemplatesStorage.getTemplate(key).transfer());
    }



    /**
     * Сохранение шаблона
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody TemplateRequest template) {
        TemplatesStorage.saveTemplate(template.transfer());
    }



    /**
     * Удаление шаблона
     */
    @RequestMapping(value = "/remove/{key}", method = RequestMethod.POST)
    public void remove(@PathVariable(name = "key") String key) {
        TemplatesStorage.removeTemplate(key);
    }



    /**
     * Запусть рассылку
     */
    @RequestMapping(value = "/send/{key}", method = RequestMethod.POST)
    public void send(@PathVariable(name = "key") String key) {
        MailService.sendAll(TemplatesStorage.getTemplate(key));
    }

}
