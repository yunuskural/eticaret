package com.metric.eticaret.order.model.shopcard;

import com.metric.eticaret.product.model.product.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopCardDTO {

    private Integer id;
    private List<ProductDTO> products;
}
