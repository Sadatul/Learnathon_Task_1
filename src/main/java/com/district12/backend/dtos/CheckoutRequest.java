package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {

    private List<Integer> cartItemIds;
    private String address;
    private String paymentMethod;

    public CheckoutRequest(List<Integer> cartItemIds, String address, String paymentMethod) {
        this.cartItemIds = cartItemIds;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
}

