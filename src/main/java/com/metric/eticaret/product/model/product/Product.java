package com.metric.eticaret.product.model.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metric.eticaret.order.model.order.Order;
import com.metric.eticaret.order.model.shopcard.ShopCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
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

    @Column(name = "image")
    private String image;

    @Column(name = "brand")
    private String brand;

    @Column(name = "currency_code")
    private String currencyCode;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ShopCard shopCard;

    @Column(name = "shopcard_quantity")
    private Integer shopCardQuantity;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order> orders;

}
