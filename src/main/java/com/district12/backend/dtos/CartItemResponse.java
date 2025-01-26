package com.district12.backend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartItemResponse {
    private String productName;
    private String productDescription;
    private Integer quantity;

    public CartItemResponse(String productName, String productDescription, Integer quantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
    }
}

