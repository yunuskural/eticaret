package com.metric.eticaret.product.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.product.model.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product) throws NotFoundException;

    List<Product> findAllProducts();

    void deleteUser(Long id);

    Product getUser(Long id) throws NotFoundException;
}
