package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order, String username) throws NotFoundException;

    List<Order> retrieveAllOrders();

    Order getOrder(Long id) throws NotFoundException;

    void deleteOrderById(Long id);
}
