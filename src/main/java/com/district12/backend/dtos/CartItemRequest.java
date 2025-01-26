package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    private Integer productId;
    private Integer quantity;
    private Integer orderId;

    CartItemRequest(Integer productId, Integer quantity, Integer orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }
}

