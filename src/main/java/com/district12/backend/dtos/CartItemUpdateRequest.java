package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateRequest {

    private Long productId;
    private Integer newQuantity;

    public CartItemUpdateRequest(Long productId, Integer newQuantity) {
        this.productId = productId;
        this.newQuantity = newQuantity;
    }
}
