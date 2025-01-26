package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateRequest {

    private Long userId;
    private Long productId;
    private Integer newQuantity;

    public CartItemUpdateRequest(Long userId, Long productId, Integer newQuantity) {
        this.userId = userId;
        this.productId = productId;
        this.newQuantity = newQuantity;
    }
}
