package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    private Long userId;
    private Long productId;
    private Integer quantity;
    private Long orderId;

    CartItemRequest(Long userId, Long productId, Integer quantity, Long orderId) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }
}

