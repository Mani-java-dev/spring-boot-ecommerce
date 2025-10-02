package com.project.hammer.repository;

import com.project.hammer.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT 1 FROM products WHERE product_name= :productName and is_deleted=0",nativeQuery = true)
    Integer findProductNameIsExist(@Param("productName") String productName);

    @Query(value = "SELECT * FROM products WHERE is_deleted=0",nativeQuery = true)
    List<Product> getAllProducts();

    @Query(value = "SELECT * FROM products WHERE product_id = :productId AND is_deleted=0",nativeQuery = true)
    Product findProductByProductName(@Param("productId")String productId);

    @Query(value = "SELECT * FROM products WHERE category_id = :id",nativeQuery = true)
    List<Product> getProductsByCategory(@Param("id")Integer categoryId);

    @Query(value ="SELECT * FROM products WHERE product_id= :productId",nativeQuery = true)
    Optional<Product> findByProductId(@Param("productId")String productId);
}
