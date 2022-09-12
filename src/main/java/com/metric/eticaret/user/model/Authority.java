package com.metric.eticaret.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnoreProperties("authorities")
    private List<Role> roles;

    public Authority(String name) {
        this.name=name;
    }
}
