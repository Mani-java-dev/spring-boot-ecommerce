package com.project.hammer.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "ordered_at")
    private Long orderedAt;

    @Column(name = "ordered_status")
    private String status;

    @Column(name = "payment_method")
    private String payMethod;

    @Column(name = "is_paid")
    private Integer isPaid;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "Product_mapped_orders",
            joinColumns = @JoinColumn(name = "prduct_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Product> products;
}
