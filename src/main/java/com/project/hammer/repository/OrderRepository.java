package com.project.hammer.repository;

import com.project.hammer.entity.Orders;
import com.project.hammer.entity.Users;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {

    @Query(value = "SELECT * FROM orders WHERE user_id = :User",nativeQuery = true)
    List<Orders> getOrderByUser(@Param("User")Integer users);
}
