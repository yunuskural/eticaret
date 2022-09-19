package com.metric.eticaret.product.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metric.eticaret.order.model.ShopCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

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

    @Column(name = "price")
    private BigDecimal price;

    @Size(max = 4000)
    @Column(name = "product_description", length = 4000)
    private String productDescription;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "category")
    private String category;

    @Column(name = "brand")
    private String brand;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("products")
    private ShopCard shopCard;

}
