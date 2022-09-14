package com.metric.eticaret.order.service;


import com.metric.eticaret.authentication.config.JwtTokenUtil;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.repository.OrderRepository;
import com.metric.eticaret.user.repository.UserRepository;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Order save(Order order) throws NotFoundException {

        if (order != null && order.getId() != null) {
            orderRepository.findById(order.getId()).orElseThrow(() ->
                    new NotFoundException("order not found"));

        }
        order.setOrderDate(new Date().getTime());
        //todo product logic
       // order.setUser(userRepository.findByUsername(jwtTokenUtil.getUsernameFromToken()));
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
    public Order getUser(Long id) throws NotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public void deleteUser(Long id) {
        orderRepository.deleteById(id);
    }

}
