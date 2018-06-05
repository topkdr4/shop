package ru.vetoshkin.store.mail;
import lombok.Getter;
import lombok.Setter;
import ru.vetoshkin.store.core.DataTransferObject;
import ru.vetoshkin.store.mail.dto.TemplateResponse;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Getter
@Setter
public class Template implements DataTransferObject<TemplateResponse> {
    protected String key;
    protected String value;
    protected String desc;


    @Override
    public TemplateResponse transfer() {
        TemplateResponse result = new TemplateResponse();
        result.setKey(key);
        result.setValue(value);
        result.setDesc(desc);

        return result;
    }
}
