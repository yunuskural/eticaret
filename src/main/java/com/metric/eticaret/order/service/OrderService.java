package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order) throws NotFoundException;

    List<Order> retrieveAllOrders();

    Order getUser(Long id) throws NotFoundException;

    void deleteUser(Long id);
}
