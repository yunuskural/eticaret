package com.metric.eticaret.product.service;

import com.metric.eticaret.product.model.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> findAllProducts();

    void deleteUser(Long id);

    Product getUser(Long id);
}
