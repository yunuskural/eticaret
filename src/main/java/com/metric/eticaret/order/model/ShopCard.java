package com.metric.eticaret.order.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class ShopCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shopCard")
    @JsonIgnoreProperties("shop-card")
    private Set<Product> products;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
