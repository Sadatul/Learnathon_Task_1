package com.district12.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailsResponse {

    private List<CartItemResponse> cartItems;
    private OrderResponse orderResponse;

}
