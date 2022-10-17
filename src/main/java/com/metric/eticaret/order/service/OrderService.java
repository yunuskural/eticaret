package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.order.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO save(OrderDTO order, String username) throws NotFoundException;

    List<OrderDTO> retrieveAllOrders(String username) throws NotFoundException;

    OrderDTO getOrderById(Long id) throws NotFoundException;

    void deleteOrderById(Long id);
}
