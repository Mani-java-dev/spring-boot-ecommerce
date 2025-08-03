package com.project.hammer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="product_name")
    private String productName;

    @Column(name = "product_price")
    private Integer price;

    @Column(name="product_description")
    private String description;

    @Column(name="is_deleted")
    private Integer isDeleted;

    @Column(name="is_active")
    private Integer isActive;

    @Column(name = "image")
    private String image;

    @ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "products")
    private List<Orders> ordersList;

}
