package com.metric.eticaret.order.service;

import com.metric.eticaret.order.model.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    List<Order> retrieveAllOrders();

    Order getUser(Long id);

    void deleteUser(Long id);
}
