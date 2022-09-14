package com.metric.eticaret.user.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metric.eticaret.order.model.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "last_login_date")
    private Long lastLoginDate;

    @Column(name = "join_date")
    private Long joinDate;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id") )
    @JsonIgnoreProperties("users")
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "notlocked")
    private boolean isNotLocked;
}
