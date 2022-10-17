package com.metric.eticaret.product.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.product.model.product.Product;
import com.metric.eticaret.product.model.product.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO save(ProductDTO product) throws NotFoundException;

    List<ProductDTO> findAllByProductName(String productName);

    List<ProductDTO> findAllProducts();

    void deleteProductById(Long id) throws NotFoundException;

    Product getProductById(Long id) throws NotFoundException;
}
