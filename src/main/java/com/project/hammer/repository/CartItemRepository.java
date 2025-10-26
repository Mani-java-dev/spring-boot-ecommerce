package com.project.hammer.repository;

import com.project.hammer.entity.Cart;
import com.project.hammer.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Integer> {

    @Query(value = "SELECT * FROM cart_items WHERE cart_id = :cart",nativeQuery = true)
    List<CartItems> getCartItemsByCartId(@Param("cart") Cart cart);

    @Query(value = "DELETE FROM cart_items WHERE id = :id",nativeQuery = true)
    void deleteCartItem(@Param("id") Integer id);
}
