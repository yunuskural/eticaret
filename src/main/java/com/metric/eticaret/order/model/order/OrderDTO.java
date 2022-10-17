package com.metric.eticaret.order.model.order;

import com.metric.eticaret.product.model.product.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private String name;
    private int orderNumber;
    private BigDecimal cargoPrice;
    private BigDecimal totalDiscount;
    private BigDecimal totalPrice;
    private BigDecimal price;
    private Long orderDate;
    private String invoiceFullAddress;
    private BigDecimal taxNumber;
    private List<ProductDTO> products;

}
