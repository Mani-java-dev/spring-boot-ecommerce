package com.project.hammer.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeOrder {

    @NonNull
    private String paymentMethod;

    @NonNull
    private Boolean isPaid;

    @NonNull
    private String address;

    @NonNull
    private String contactNumber;

    @NonNull
    private List<String> orderId;
}
