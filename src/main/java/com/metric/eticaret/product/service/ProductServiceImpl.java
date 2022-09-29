package com.metric.eticaret.product.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.ShopCard;
import com.metric.eticaret.order.repository.ShopCardRepository;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ShopCardRepository shopCardRepository;

    @Transactional
    @Override
    public Product save(Product product) throws NotFoundException {
        if (product.getId() != null && product != null) {
            productRepository.findById(product.getId()).orElseThrow(() -> new NotFoundException("Product not found"));
        }
        return productRepository.save(product);
}

    @Override
    public List<Product> findAllByProductName(String productName) {
        return productRepository.findAllByProductName(productName);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProductById(Long id) throws NotFoundException {
        productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
