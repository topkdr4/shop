package ru.vetoshkin.store.mail.dto;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.mail.Template;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class TemplateRequest implements DataTransferObject<Template> {
    protected String key;
    protected String value;
    protected String desc;


    @Override
    public Template transfer() {
        Template result = new Template();
        result.setKey(key);
        result.setValue(value);
        result.setDesc(desc);

        return result;
    }
}
