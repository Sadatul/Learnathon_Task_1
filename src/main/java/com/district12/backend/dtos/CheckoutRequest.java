package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {

    private Long userId;
    private List<Long> cartItemIds;
    private String address;
    private String paymentMethod;

    public CheckoutRequest(Long userId, List<Long> cartItemIds, String address, String paymentMethod) {
        this.userId = userId;
        this.cartItemIds = cartItemIds;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
}

