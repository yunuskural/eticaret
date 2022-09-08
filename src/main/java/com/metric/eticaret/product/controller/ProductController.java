package com.metric.eticaret.product.controller;


import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import com.metric.eticaret.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductServiceImpl productServiceImpl;


    @PostMapping("/save")
    public void save(@RequestBody Product product){
        productServiceImpl.save(product);
    }
}
