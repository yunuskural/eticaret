package com.metric.eticaret.order.controller;


import com.metric.eticaret.authentication.config.HttpResponseService;
import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.order.OrderDTO;
import com.metric.eticaret.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final HttpResponseService httpResponseService;

    @PostMapping("/save/{username}")
    public ResponseEntity<HttpResponse> save(@RequestBody OrderDTO newOrder,
                                             @PathVariable("username") String username) throws NotFoundException {
        OrderDTO order = orderService.save(newOrder, username);
        return httpResponseService.response(order, "Order successfully placed. Now let's continue shopping", CREATED);
    }

    @GetMapping("/orders/{username}")
    public ResponseEntity<List<OrderDTO>> retrieveAllOrders(@PathVariable("username") String username) throws NotFoundException {
        return new ResponseEntity<>(orderService.retrieveAllOrders(username), OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getOrderById(@PathVariable("id") Long id) throws NotFoundException {
        OrderDTO order = orderService.getOrderById(id);
        return httpResponseService.response(order, "Successfull", CREATED);
    }
}
