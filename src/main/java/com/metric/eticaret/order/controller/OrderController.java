package com.metric.eticaret.order.controller;


import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.authentication.config.HttpResponseService;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final HttpResponseService httpResponseService;

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody Order newOrder) throws NotFoundException {
        //todo try

        Order order = orderService.save(newOrder);
        return httpResponseService.response(order, "Successfull", HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>>retrieveAllOrders(){
        return new ResponseEntity<>(orderService.retrieveAllOrders(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> deleteOrderById(@PathVariable("id") Long id){
        orderService.deleteUser(id);
        return httpResponseService.response(null,"Order deleted successfully",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserById(@PathVariable("id") Long id) throws NotFoundException {
        Order order = orderService.getUser(id);
        return httpResponseService.response(order, "Successfull", HttpStatus.CREATED);
    }
}
