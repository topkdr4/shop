package ru.vetoshkin.store.core.configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.vetoshkin.store.core.Initialize;

import java.lang.reflect.Method;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Component
public class InitializeBeanPostProcessor implements BeanPostProcessor {

    @Nullable @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();

        Initialize initialize = clazz.getDeclaredAnnotation(Initialize.class);
        if (initialize == null)
            return bean;

        try {
            Method init = clazz.getMethod("init");
            init.setAccessible(true);
            init.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }
}
