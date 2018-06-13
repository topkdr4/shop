package ru.vetoshkin.store.basket.rest;
import org.springframework.web.bind.annotation.*;
import ru.vetoshkin.store.basket.OrderHistory;
import ru.vetoshkin.store.basket.OrderStatus;
import ru.vetoshkin.store.basket.dao.OrderStorage;
import ru.vetoshkin.store.core.SimpleResponse;

import java.util.List;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    /**
     * Получить страницу
     */
    @ResponseBody
    @RequestMapping(value = "/list/{page}", method = RequestMethod.POST)
    public SimpleResponse<List<OrderHistory>> getPage(@PathVariable(name = "page") int page) throws Exception {
        return new SimpleResponse<>(OrderStorage.getPage(page));
    }


    @RequestMapping(value = "/change/{id}/{status}", method = RequestMethod.POST)
    public void change(@PathVariable(name = "id") int orderId, @PathVariable(name = "status")String status) throws Exception {
        OrderStatus orderStatus = OrderStatus.fromMap(status);
        if (orderStatus == null)
            throw new RuntimeException(new NullPointerException());

        OrderStorage.setStatus(orderId, orderStatus);
    }
}
