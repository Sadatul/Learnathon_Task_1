package com.district12.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemUpdateRequest {

    private Long cartItemId;
    private Integer newQuantity;

}
