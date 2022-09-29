package com.metric.eticaret.product.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.product.model.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product) throws NotFoundException;

    List<Product> findAllByProductName(String productName);

    List<Product> findAllProducts();

    void deleteProductById(Long id) throws NotFoundException;

    Product getProductById(Long id) throws NotFoundException;
}
