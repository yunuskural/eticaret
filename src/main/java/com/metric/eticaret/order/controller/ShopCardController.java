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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop-card")
@CrossOrigin(origins = "http://localhost:4200")
public class ShopCardController {

    private final ShopCardService shopCardService;
    private final HttpResponseService httpResponseService;

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<HttpResponse> addProductToShopCard(@PathVariable("productId") Long productId) throws NotFoundException {
        ShopCard shopCard = shopCardService.addProductToShopCard(productId);
        return httpResponseService.response(shopCard, "Successfully added", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<HttpResponse> retrieveShopCard() throws NotFoundException {
        return httpResponseService.response(shopCardService.retrieveShopCard(), "Successfully returned shop-card products", HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<ShopCard> deleteProductInShopCard(@PathVariable("productId") Long productId) throws NotFoundException {
        return new ResponseEntity<>(shopCardService.deleteProductInShopCard(productId), HttpStatus.NO_CONTENT);
    }
}
