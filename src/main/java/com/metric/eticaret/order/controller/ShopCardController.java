package com.metric.eticaret.order.controller;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.shopcard.ShopCardDTO;
import com.metric.eticaret.order.service.ShopCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop-card")
@CrossOrigin(origins = "http://localhost:4200")
public class ShopCardController {

    private final ShopCardService shopCardService;

    @PostMapping("/add-product/{productId}/user/{username}")
    public ResponseEntity<ShopCardDTO> addProductInShopCard(@PathVariable("productId") Long productId,
                                                            @PathVariable("username") String username) throws NotFoundException {
        return new ResponseEntity<>(shopCardService.addProductInShopCard(productId, username), OK);
    }

    @GetMapping("/products/{username}")
    public ResponseEntity<ShopCardDTO> retrieveShopCard(@PathVariable("username") String username) throws NotFoundException {
        return new ResponseEntity<>(shopCardService.retrieveShopCardProducts(username), OK);
    }


    @DeleteMapping("/delete-product/{productId}/user/{username}")
    public ResponseEntity<ShopCardDTO> deleteProductInShopCard(@PathVariable("productId") Long productId,
                                                               @PathVariable("username") String username) throws NotFoundException {
        return new ResponseEntity<>(shopCardService.deleteProductInShopCard(productId, username), NO_CONTENT);
    }
}
