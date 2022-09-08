package com.metric.eticaret.order.controller;


import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.repository.OrderRepository;
import com.metric.eticaret.order.service.OrderService;
import com.metric.eticaret.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<Order> save(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<Order> retrieveAllOrders(){
        return orderService.retrieveAllOrders();
    }
}
