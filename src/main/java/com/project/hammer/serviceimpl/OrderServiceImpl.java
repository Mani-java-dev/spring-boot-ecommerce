package com.project.hammer.serviceimpl;

import com.project.hammer.config.RequestedUserInfo;
import com.project.hammer.entity.Cart;
import com.project.hammer.entity.Orders;
import com.project.hammer.entity.Product;
import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.model.MakeOrder;
import com.project.hammer.model.OrderDetails;
import com.project.hammer.model.RequestUserInfo;
import com.project.hammer.model.ShowOrders;
import com.project.hammer.repository.CartRepository;
import com.project.hammer.repository.OrderRepository;
import com.project.hammer.repository.ProductRepo;
import com.project.hammer.repository.UserRepo;
import com.project.hammer.service.OrderService;
import jakarta.persistence.criteria.Order;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepo userRepo;
    @Autowired
    private final RequestedUserInfo requestUserInfo;
    private final ProductRepo productRepo;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserRepo userRepo, RequestedUserInfo requestUserInfo, ProductRepo productRepo, CartRepository cartRepository, OrderRepository orderRepository) {
        this.userRepo = userRepo;
        this.requestUserInfo = requestUserInfo;
        this.productRepo = productRepo;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public String addNewOrder(MakeOrder product) {
        if(Objects.isNull(product)){
            throw new BadRequestCustomException("check order details");
        }
        Orders order=new Orders();
        order.setOrderId(UUID.randomUUID().toString());
        order.setIsPaid(product.getIsPaid()?1:0);
        order.setUser(userRepo.getUserByUserName(requestUserInfo.getUserName()));
        order.setStatus("PLACED");
        order.setAddress(product.getAddress());
        order.setContactNumber(product.getContactNumber());
        order.setOrderedAt(Instant.now().getEpochSecond());
        order.setPayMethod(product.getPaymentMethod());
        List<Product> productList=productRepo.getProductsByIds(product.getOrderId());
        order.setProducts(productList);

        Cart cart=cartRepository.getCartByUserName(requestUserInfo.getUserName()+"_card");
        cart.getCartItems().removeIf(items->product.getOrderId().contains(items.getProductId()));
        cartRepository.save(cart);

        orderRepository.save(order);
        return "Order placed";
    }

    @Override
    public List<ShowOrders> getMyOrders() {
        List<Orders> order=orderRepository.getOrderByUser(userRepo.getUserByUserName(requestUserInfo.getUserName()).getId());
        if(Objects.isNull(order)){
            throw new BadRequestCustomException("Order not found !");
        }
        List<ShowOrders> orderDetails=order.parallelStream().map(n->{
            ShowOrders orderDetail=new ShowOrders();
            orderDetail.setOrderId(n.getOrderId());
            orderDetail.setIsPaid(n.getIsPaid()==1?"Yes":"No");
            orderDetail.setOrderedTime(n.getOrderedAt());
            orderDetail.setPaymentType(n.getPayMethod());
            List<OrderDetails> productDetails=new ArrayList<>();
            for(Product product:n.getProducts()){
                OrderDetails products=new OrderDetails();
                products.setProductName(product.getProductName());
                products.setDescription(product.getDescription());
                products.setPrice(product.getPrice());
                products.setImage(product.getImage());
                products.setCategory(product.getCategory().getCategoryName());
                products.setProductId(product.getProductId());
                productDetails.add(products);
            }
            orderDetail.setOrderProducts(productDetails);
            return orderDetail;
        }).toList();
        return orderDetails;
    }


}
