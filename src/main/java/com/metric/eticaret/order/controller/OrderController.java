package com.metric.eticaret.order.controller;


import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.authentication.model.HttpResponseService;
import com.metric.eticaret.order.model.Order;
import com.metric.eticaret.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final HttpResponseService httpResponseService;

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody Order newOrder) {
        //todo try

        Order order = orderService.save(newOrder);
        return httpResponseService.response(order, "Successfull", HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>>retrieveAllOrders(){
        return new ResponseEntity<>(orderService.retrieveAllOrders(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('delete')")
    public ResponseEntity<HttpResponse> deleteOrderById(@PathVariable("id") Long id){
        orderService.deleteUser(id);
        return response(HttpStatus.NO_CONTENT, "Order deleted successfully");
    }

    public ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(Locale.ROOT), message.toLowerCase(), null), httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserById(@PathVariable("id") Long id) {
        Order order = orderService.getUser(id);
        return httpResponseService.response(order, "Successfull", HttpStatus.CREATED);
    }
}
