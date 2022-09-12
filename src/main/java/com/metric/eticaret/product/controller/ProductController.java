package com.metric.eticaret.product.controller;


import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.authentication.model.HttpResponseService;
import com.metric.eticaret.exception.domain.UserNotFoundException;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final HttpResponseService httpResponseService;


    @PostMapping("/save")
    public ResponseEntity<HttpResponse> save(@RequestBody Product newProduct){
        Product product = productService.save(newProduct);
        return httpResponseService.response(product, "Successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<HttpResponse> retrieveAllUser() {
        List<Product> products = productService.findAllProducts();
        return httpResponseService.response(products, "Successfull", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('delete')")
    public ResponseEntity<HttpResponse> deleteUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        productService.deleteUser(id);
        return response(HttpStatus.NO_CONTENT, "User deleted successfully");
    }

    public ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(Locale.ROOT), message.toLowerCase(), null), httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        Product product = productService.getUser(id);
        return httpResponseService.response(product, "Successfull", HttpStatus.OK);
    }
}
