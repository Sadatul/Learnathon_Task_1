package com.district12.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateRequest {

    private Integer productId;
    private Integer newQuantity;

    public CartItemUpdateRequest(Integer productId, Integer newQuantity) {
        this.productId = productId;
        this.newQuantity = newQuantity;
    }
}
