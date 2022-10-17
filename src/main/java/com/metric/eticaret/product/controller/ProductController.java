package com.metric.eticaret.product.controller;


import com.metric.eticaret.authentication.config.HttpResponseService;
import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.product.model.product.Product;
import com.metric.eticaret.product.model.product.ProductDTO;
import com.metric.eticaret.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;
    private final HttpResponseService httpResponseService;


    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody ProductDTO newProduct) throws NotFoundException {
        ProductDTO product = productService.save(newProduct);
        return httpResponseService.response(product, "Successfully created", CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<HttpResponse> retrieveAllProducts() {
        List<ProductDTO> products = productService.findAllProducts();
        return httpResponseService.response(products, "Products Successfullly loaded", OK);

    }

    @GetMapping("/products/{productName}")
    public ResponseEntity<List<ProductDTO>> findAllByProductName(@PathVariable("productName") String productName) {
        List<ProductDTO> products = productService.findAllByProductName(productName);
        return new ResponseEntity<>(products, OK);

    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") Long id) throws NotFoundException {
        productService.deleteProductById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> findProductById(@PathVariable("id") Long id) throws NotFoundException {
        Product product = productService.getProductById(id);
        return httpResponseService.response(product, "Successfull", OK);
    }
}
