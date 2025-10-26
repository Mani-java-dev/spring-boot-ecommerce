package com.project.hammer.controller;

import com.project.hammer.constants.APIResponse;
import com.project.hammer.entity.Cart;
import com.project.hammer.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.project.hammer.constants.Constant.SUCCESS;
import static com.project.hammer.constants.Constant.TRACKER;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addNewCart(String product,HttpServletRequest httpServletRequest){
        String response=cartService.createCart(product);
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(SUCCESS,response,null));
    }

    @GetMapping("/get")
    public ResponseEntity<APIResponse> getCartForUser(HttpServletRequest httpServletRequest){
        Map<String,Integer> cart=cartService.getCartForUser();
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(SUCCESS,"Cart fetched successfully",cart));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<APIResponse> removeItemFromCart(String productId,HttpServletRequest httpServletRequest){
        String response=cartService.removeItemFromCart(productId);
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(SUCCESS,response,null));
    }

}
