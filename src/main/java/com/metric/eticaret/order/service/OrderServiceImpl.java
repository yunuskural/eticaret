package com.metric.eticaret.order.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.repository.OrderRepository;
import com.metric.eticaret.product.repository.ProductRepository;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Order save(Order order, Long id) throws NotFoundException {
        if (order != null && order.getId() != null) {
            orderRepository.findById(order.getId()).orElseThrow(() ->
                    new NotFoundException("order not found"));

        }
        order.setOrderDate(new Date().getTime());
        order.setUser(userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found")));
        Random random = new Random();
        order.setOrderNumber(random.nextInt(100000));
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
    public Order getOrder(Long id) throws NotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()){
            orderRepository.deleteById(id);
        }
        //todo id kontrolu
    }

}
