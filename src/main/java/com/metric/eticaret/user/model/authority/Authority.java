package com.metric.eticaret.user.model.authority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metric.eticaret.user.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "authority_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("authorities")
    private List<Role> roles;

    public Authority(String name) {
        this.name = name;
    }
}
