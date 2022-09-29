package com.metric.eticaret.order.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.repository.OrderRepository;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import com.metric.eticaret.user.model.User;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Order save(Order order, String username) throws NotFoundException {
        if (order != null && order.getId() != null) {
            orderRepository.findById(order.getId()).orElseThrow(() ->
                    new NotFoundException("order not found"));


        }
      /*  List<Product> products = productRepository.findAllById(order.getProducts().);
        order.getProducts().forEach(product -> {
            products.add(productRepository.findByProductName(product.getProductName()));
        });
        order.setProducts(products);*/
        order.setOrderDate(new Date().getTime());
        if (userRepository.findByUsername(username) != null) {
            order.setUser(userRepository.findByUsername(username));
        } else {
            throw new NotFoundException("user not found");
        }
        Random random = new Random();
        order.setOrderNumber(random.nextInt(100000));
        if (order.getPrice() != null) {
            BigDecimal totalPrice = order.getPrice().add(order.getPrice().multiply(order.getTaxNumber()));
            totalPrice = totalPrice.add(order.getCargoPrice().subtract((order.getTotalDiscount())));
            order.setTotalPrice(totalPrice);
        }
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> retrieveAllOrders() {
        List<Order> orders =  orderRepository.findAll();
        Hibernate.isInitialized(orders);
        return orders;
    }

    @Override
    public Order getOrder(Long id) throws NotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.deleteById(id);
        }
    }

}
