package com.metric.eticaret.order.service;


import com.metric.eticaret.exception.domain.OrderNotFoundException;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order save(Order order){
        if (order != null && order.getId() != null) {
            orderRepository.findById(order.getId()).orElseThrow(() ->
                    new OrderNotFoundException("order not found"));

        }
        order.setOrderDate(new Date().getTime());
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> retrieveAllOrders() {
        return orderRepository.findAll();
    }

}
