package ru.vetoshkin.store.util;
import ru.vetoshkin.store.core.AuthFilter;
import ru.vetoshkin.store.user.User;
import ru.vetoshkin.store.user.dao.UserStorage;

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


    public static User getUser(HttpServletRequest request, String cookieName) throws Exception {
        Cookie cookie = getCookie(request, AuthFilter.cookieName);
        if (cookie == null)
            throw new Exception("user not found");

        User user = UserStorage.getUser(cookie.getValue());

        if (user == null)
            throw new Exception("user not found");

        return user;
    }

}
