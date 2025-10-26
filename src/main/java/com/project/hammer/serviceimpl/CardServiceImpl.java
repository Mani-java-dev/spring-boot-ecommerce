package com.project.hammer.serviceimpl;

import ch.qos.logback.core.util.StringUtil;
import com.project.hammer.config.RequestedUserInfo;
import com.project.hammer.entity.Cart;
import com.project.hammer.entity.CartItems;
import com.project.hammer.entity.Product;
import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.repository.CartItemRepository;
import com.project.hammer.repository.CartRepository;
import com.project.hammer.repository.ProductRepo;
import com.project.hammer.service.CartService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class CardServiceImpl implements CartService {

    @Autowired
    private RequestedUserInfo requestedUserInfo;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepo productRepo;


    @Override
    @Transactional
    public String createCart(String productId) {
        if(StringUtils.isEmpty(productId)){
            throw new BadRequestCustomException("please provide productId to add");
        }
        Cart cart=cartRepository.getCartByUserName(requestedUserInfo.getUserName()+"_card");
        if(cart==null){
            Cart createNewCart=new Cart();
            createNewCart.setCartName(requestedUserInfo.getUserName()+"_card");
            createNewCart.setCartItems(new ArrayList<>());
            cart=cartRepository.save(createNewCart);
        }
        Optional<Product> product= Optional.ofNullable(productRepo.findByProductId(productId))
                .orElseThrow(()->new BadRequestCustomException("Product not found !"));

        Optional<CartItems> target = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();
        if(!target.isPresent()){
            CartItems cartItems=new CartItems();
            cartItems.setProductId(product.get().getProductId());
            cartItems.setCart(cart);
            cartItems.setQuantity(1);
            cart.getCartItems().add(cartItems);
            cartRepository.save(cart);
        }else{
            target.get().setQuantity(target.get().getQuantity()+1);
            cartItemRepository.save(target.get());
        }
        return "Product add on cart successfully";
    }

    @Override
    public Map<String,Integer> getCartForUser() {
        Cart cart=cartRepository.getCartByUserName(requestedUserInfo.getUserName()+"_card");
        if(cart==null){
            throw new BadRequestCustomException("Cart not found for user");
        }
        Map<String,Integer> response=cart.getCartItems()
                .stream()
                .collect(Collectors.toMap(CartItems::getProductId,CartItems::getQuantity));
        return response;
    }

    @Override
    public String removeItemFromCart(String productId) {
         Cart cart=cartRepository.getCartByUserName(requestedUserInfo.getUserName()+"_card");
         if(cart==null){
             throw new BadRequestCustomException("cart not found for user");
         }
         Optional<CartItems> cartItems=cart.getCartItems().stream().filter(n->n.getProductId().equals(productId)).findFirst();
         if(cartItems.isPresent()&&cartItems.get().getQuantity()>1){
               cartItems.get().setQuantity(cartItems.get().getQuantity()-1);
         }else {
               cart.getCartItems().removeIf(n -> n.getProductId().equals(productId));
         }
         cartRepository.save(cart);
        return "Item removed from cart";
    }
}
