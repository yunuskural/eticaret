package com.metric.eticaret.order.service;


import com.metric.eticaret.exception.domain.OrderNotFoundException;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order save(Order order) {
        if (order != null && order.getId() != null) {
            orderRepository.findById(order.getId()).orElseThrow(() ->
                    new OrderNotFoundException("order not found"));

        }
        order.setOrderDate(new Date().getTime());
        Random random = new Random();
        int orderNumber = random.nextInt(100000);
        order.setOrderNumber(orderNumber);
        BigDecimal totalPrice = order.getPrice().add(order.getPrice().multiply(order.getTaxNumber()));
        totalPrice = totalPrice.add(order.getCargoPrice().subtract((order.getTotalDiscount())));
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> retrieveAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getUser(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public void deleteUser(Long id) {
        orderRepository.deleteById(id);
    }

}
