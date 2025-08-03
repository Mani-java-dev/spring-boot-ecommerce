package com.project.hammer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "card_name")
    private String cartName;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY)
    private List<CartItems> cartItems;


}
