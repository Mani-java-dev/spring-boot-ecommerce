package com.project.hammer.model;

import com.project.hammer.entity.Category;
import com.project.hammer.entity.Orders;
import com.project.hammer.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {


    private String productId;

    private String productName;

    private Integer price;

    private String description;

    private String image;

    private String category;

}
