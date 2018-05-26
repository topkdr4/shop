package ru.vetoshkin.store.core;
/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class SystemException extends Exception {

    public SystemException(String message) {
        super(message);
    }


    public SystemException(Throwable cause) {
        super(cause);
    }
}
