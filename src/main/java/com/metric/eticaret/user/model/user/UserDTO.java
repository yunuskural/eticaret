package com.metric.eticaret.user.model.user;

import com.metric.eticaret.order.model.order.OrderDTO;
import com.metric.eticaret.product.model.product.ProductDTO;
import com.metric.eticaret.user.model.role.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String address;
    private String name;
    private String email;
    private String profileImageUrl;
    private Long lastLoginDate;
    private Long joinDate;
    private Set<RoleDTO> roles;
    private List<OrderDTO> orders;
    private List<ProductDTO> products;
    private Boolean status;
    private Boolean nonlocked;
}
