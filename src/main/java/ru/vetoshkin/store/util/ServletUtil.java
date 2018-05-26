package ru.vetoshkin.store.util;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ServletUtil {

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase(cookieName)) {
                return cookie;
            }
        }

        return null;
    }

}
