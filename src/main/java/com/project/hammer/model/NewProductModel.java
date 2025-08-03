package com.project.hammer.model;

import com.project.hammer.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductModel {
    private String productName;

    private Integer price;

    private String description;

    private String imageLink;

    private String category;

}
