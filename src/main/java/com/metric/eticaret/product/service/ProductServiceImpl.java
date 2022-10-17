package com.metric.eticaret.product.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.product.model.product.Product;
import com.metric.eticaret.product.model.product.ProductDTO;
import com.metric.eticaret.product.model.product.ProductMapper;
import com.metric.eticaret.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductDTO save(ProductDTO productDTO) throws NotFoundException {
        if (productDTO.getId() != null && productDTO != null) {
            productRepository.findById(productDTO.getId()).orElseThrow(() -> new NotFoundException("Product not found"));
        }
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        productRepository.save(product);
        return ProductMapper.INSTANCE.toDTO(product);
    }

    @Override
    public List<ProductDTO> findAllByProductName(String productName) {
        List<Product> products = productRepository.findAllByProductName(productName);
        return products.stream().map(ProductMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Long id) throws NotFoundException {
        productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) throws NotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
