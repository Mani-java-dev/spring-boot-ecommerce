package com.project.hammer.repository;

import com.project.hammer.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT COUNT(*) FROM products WHERE product_name = :productName",nativeQuery = true)
    Integer findProductNameIsExist(@Param("productName") String productName);

    @Query(value = "SELECT * FROM products",nativeQuery = true)
    List<Product> getAllProducts();

    @Query(value = "SELECT * FROM products WHERE product_name = :productName",nativeQuery = true)
    Product findProductByProductName(@Param("productName")String productName);
}
