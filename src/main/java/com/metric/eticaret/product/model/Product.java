package com.metric.eticaret.product.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Size(max = 4000)
    @Column(name = "product_description", length = 4000)
    private String productDescription;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

}
