package com.project.hammer.service;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    String addNewOrder(String product);
}
