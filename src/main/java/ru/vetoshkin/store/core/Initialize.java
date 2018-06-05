package ru.vetoshkin.store.core;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Initialize {
    public int priority() default 0;
}
