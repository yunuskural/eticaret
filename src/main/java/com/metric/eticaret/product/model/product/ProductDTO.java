package com.metric.eticaret.product.model.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String productName;

    private BigDecimal price;

    private String productDescription;

    private Integer stockQuantity;

    private Integer shopCardQuantity;

    private String category;

    private String image;

    private String brand;

    private String currency;

    private boolean active;
}
