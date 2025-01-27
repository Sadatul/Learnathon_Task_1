package com.district12.backend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CartItemRequest {

    private Long productId;
    private Integer quantity;

}

