package ru.vetoshkin.store.user.rest;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.admin.AdminAuth;
import ru.vetoshkin.store.basket.OrderHistory;
import ru.vetoshkin.store.basket.dao.BasketStorage;
import ru.vetoshkin.store.core.AuthFilter;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.user.User;
import ru.vetoshkin.store.user.dao.UserStorage;
import ru.vetoshkin.store.user.dto.UserRequest;
import ru.vetoshkin.store.util.ServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;





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
        cookie.setHttpOnly(true);

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


    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie userCookie = ServletUtil.getCookie(request, AuthFilter.cookieName);
        if (userCookie == null)
            return;

        Cookie cookie = new Cookie("sessionId", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }


    @ResponseBody
    @RequestMapping(path = "/payment/{page}", method = RequestMethod.POST)
    public SimpleResponse<List<OrderHistory>> getPaymentHistory(@PathVariable(name = "page") int page, HttpServletRequest request) throws Exception {
        User user = ServletUtil.getUser(request, AuthFilter.cookieName);
        return new SimpleResponse<>(BasketStorage.getOrderHistory(user.getEmail(), page));
    }

}
