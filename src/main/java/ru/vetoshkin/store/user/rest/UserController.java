package ru.vetoshkin.store.user.rest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vetoshkin.store.admin.AdminAuth;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.user.User;
import ru.vetoshkin.store.user.dao.UserStorage;
import ru.vetoshkin.store.user.dto.UserRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/user", produces = "application/json", consumes = "application/json")
public class UserController {

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public void auth(@RequestBody UserRequest user, HttpServletResponse response) throws Exception {
        User transfer = UserStorage.exist(user.transfer());

        if (transfer == null)
            throw new Exception("Неверный логин или пароль");

        String session = UserStorage.saveSession(transfer);

        Cookie cookie = new Cookie("sessionId", session);
        cookie.setPath("/");
        cookie.setMaxAge(AdminAuth.expiry);

        response.addCookie(cookie);
    }


    @RequestMapping(path = "/registr", method = RequestMethod.POST)
    public SimpleResponse<Object> registration(@RequestBody UserRequest user) throws Exception {
        User transfer = user.transfer();
        if (UserStorage.exist(transfer) != null)
            throw new Exception("Пользователь уже существует");

        UserStorage.registration(transfer);
        return new SimpleResponse<>(null);
    }
}