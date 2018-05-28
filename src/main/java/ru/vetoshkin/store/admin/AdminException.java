package ru.vetoshkin.store.admin;
/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class AdminException extends Exception {

    public AdminException() {
        super();
    }


    public AdminException(String message) {
        super(message);
    }


    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }


    public AdminException(Throwable cause) {
        super(cause);
    }


    protected AdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
