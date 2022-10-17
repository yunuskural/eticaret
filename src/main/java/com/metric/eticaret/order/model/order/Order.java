package com.metric.eticaret.order.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metric.eticaret.product.model.product.Product;
import com.metric.eticaret.user.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler", "orders"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "cargo_price")
    private BigDecimal cargoPrice;

    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "order_date")
    private Long orderDate;

    @Column(name = "invoice_full_address")
    private String invoiceFullAddress;

    @Column(name = "tax_number")
    private BigDecimal taxNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("orders")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("orders")
    private List<Product> products;

}
