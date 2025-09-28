package com.project.hammer.repository;

import com.project.hammer.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * FROM category WHERE category_name = :catName",nativeQuery = true)
    Optional<Category> getCategoryByName(@Param("catName")String categoryName);

    @Query(value = "SELECT COUNT(*) FROM category WHERE category_name = :categoryName",nativeQuery = true)
    int isCategoryAlreadyExist(@Param("categoryName") String categoryName);

    @Query(value = "SELECT * FROM category",nativeQuery = true)
    List<Category> getAllCategory();
}
