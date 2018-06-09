package ru.vetoshkin.store.basket.rest;
import com.google.common.hash.Hashing;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.basket.Basket;
import ru.vetoshkin.store.basket.dao.BasketStorage;
import ru.vetoshkin.store.core.AuthFilter;
import ru.vetoshkin.store.core.SimpleResponse;
import ru.vetoshkin.store.user.User;
import ru.vetoshkin.store.user.dao.UserStorage;
import ru.vetoshkin.store.util.ServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/basket")
public class BasketController {


    /**
     * Сохранение корзины
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SimpleResponse<Basket> save(@RequestBody Basket basket, HttpServletRequest request) throws Exception {
        Cookie cookie = ServletUtil.getCookie(request, AuthFilter.cookieName);
        if (cookie == null)
            throw new Exception("user not found");

        User user = UserStorage.getUser(cookie.getValue());

        if (user == null)
            throw new Exception("user not found");

        BasketStorage.save(cookie.getValue(), basket);

        return new SimpleResponse<>(BasketStorage.getBasket(cookie.getValue()));
    }


    /**
     * Получить карзину
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public SimpleResponse<Basket> get(HttpServletRequest request) throws Exception {
        Cookie cookie = ServletUtil.getCookie(request, AuthFilter.cookieName);
        if (cookie == null)
            throw new Exception("user not found");

        User user = UserStorage.getUser(cookie.getValue());

        if (user == null)
            throw new Exception("user not found");

        return new SimpleResponse<>(BasketStorage.getBasket(cookie.getValue()));
    }


    /**
     * Оплата товара
     */
    @ResponseBody
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public SimpleResponse<Integer> buy(HttpServletRequest request) throws Exception {
        Cookie cookie = ServletUtil.getCookie(request, AuthFilter.cookieName);
        if (cookie == null)
            throw new Exception("user not found");

        String sessionId = cookie.getValue();
        User user = UserStorage.getUser(sessionId);

        if (user == null)
            throw new Exception("user not found");


        Basket basket = BasketStorage.getBasket(sessionId);
        BasketStorage.clear(sessionId);
        int orderId = BasketStorage.buy(basket, user.getEmail());
        return new SimpleResponse<>(orderId);
    }
}
