package com.project.hammer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowOrders {
       private String orderId;
       private String isPaid;
       private Long orderedTime;
       private String paymentType;
       private List<OrderDetails> orderProducts;
}
