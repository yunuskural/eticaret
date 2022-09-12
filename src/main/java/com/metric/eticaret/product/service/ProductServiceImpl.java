package com.metric.eticaret.product.service;


import com.metric.eticaret.exception.domain.ProductNotFoundException;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Product save(Product product) {
        if (product.getId() != null && product != null) {
            productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        }
       return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getUser(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }
}
