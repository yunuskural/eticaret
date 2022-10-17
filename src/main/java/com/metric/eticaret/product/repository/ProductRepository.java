package com.metric.eticaret.product.repository;

import com.metric.eticaret.product.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductName(String productName);

    Product findByProductName(String productName);
}
