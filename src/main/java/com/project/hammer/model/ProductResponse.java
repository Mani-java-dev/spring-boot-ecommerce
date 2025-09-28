package com.project.hammer.model;

import com.project.hammer.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String productId;

    private String productName;

    private Integer price;

    private String description;

    private Integer isDeleted;

    private Integer isActive;

    private String image;

    private String category;
}
