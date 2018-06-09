package ru.vetoshkin.store.basket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public enum OrderStatus {
    WAITING("WAITING"),
    PAID("PAID");

    private static Map<String, OrderStatus> map = new ConcurrentHashMap<>();

    static {
        for (OrderStatus status : OrderStatus.values()) {
            map.put(status.status, status);
        }
    }

    private final String status;


    private OrderStatus(String description) {
        this.status = description;
    }


    public String getStatus() {
        return status;
    }

    public static OrderStatus fromMap(String status) {
        return map.get(status);
    }
}
