package com.project.hammer.service;

import com.project.hammer.model.MakeOrder;
import com.project.hammer.model.ShowOrders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    String addNewOrder(MakeOrder product);

    List<ShowOrders> getMyOrders();
}
