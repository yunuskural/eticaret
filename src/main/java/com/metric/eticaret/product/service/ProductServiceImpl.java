package com.metric.eticaret.product.service;


import com.metric.eticaret.exception.domain.ProductNotFoundException;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    @Transactional
    public void save(Product product) {
        if (product.getId() != null && product != null) {
            productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        }
        productRepository.save(product);
    }
}
