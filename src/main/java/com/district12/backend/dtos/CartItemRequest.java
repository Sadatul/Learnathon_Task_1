package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    private Long productId;
    private Integer quantity;
    private Long orderId;

    CartItemRequest(Long productId, Integer quantity, Long orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }
}

