package com.project.hammer.repository;

import com.project.hammer.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT * FROM cart WHERE card_name = :cardName", nativeQuery = true)
    Cart getCartByUserName(@Param("cardName") String usernamePlusCard);
}
