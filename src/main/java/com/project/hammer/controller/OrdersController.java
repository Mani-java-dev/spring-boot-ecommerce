package com.project.hammer.controller;

import com.project.hammer.constants.APIResponse;
import com.project.hammer.model.MakeOrder;
import com.project.hammer.model.ShowOrders;
import com.project.hammer.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.hammer.constants.Constant.SUCCESS;
import static com.project.hammer.constants.Constant.TRACKER;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addNewOrder(@RequestBody MakeOrder product, HttpServletRequest httpServletRequest){
        String response=orderService.addNewOrder(product);
        return ResponseEntity.ok().body(new APIResponse(SUCCESS,response,null));
    }

    @GetMapping("/myorder")
    public ResponseEntity<APIResponse> addNewOrder(HttpServletRequest httpServletRequest){
        List<ShowOrders> response=orderService.getMyOrders();
        return ResponseEntity.ok().body(new APIResponse(SUCCESS,"your orders",response));
    }

}
