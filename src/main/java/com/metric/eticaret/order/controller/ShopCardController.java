package com.metric.eticaret.order.controller;


import com.metric.eticaret.authentication.config.HttpResponseService;
import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.ShopCard;
import com.metric.eticaret.order.service.ShopCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop-card")
public class ShopCardController {

    private final ShopCardService shopCardService;
    private final HttpResponseService httpResponseService;

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> addProductToShopCard(ShopCard shopCard) throws NotFoundException {
        shopCardService.save(shopCard);
        return httpResponseService.response(null,"Successfully added", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ShopCard>> retrieveAllBasket(){
        return new ResponseEntity<>(shopCardService.retrieveAllBasket(),HttpStatus.OK);
    }
}
