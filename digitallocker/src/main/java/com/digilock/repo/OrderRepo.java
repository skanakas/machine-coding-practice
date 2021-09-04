package com.digilock.repo;

import java.util.HashMap;
import java.util.Map;

import com.digilock.model.Order;

public class OrderRepo {
    public static Map<String, Order> orderMap = new HashMap<>();

    public Order getOrderByOrderID(String orderId) {
        return orderMap.get(orderId);
    }
}
