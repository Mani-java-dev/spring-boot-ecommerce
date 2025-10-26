package com.project.hammer.service;

import com.project.hammer.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CartService{
    String createCart(String product);

    Map<String,Integer> getCartForUser();

    String removeItemFromCart(String productId);
}
