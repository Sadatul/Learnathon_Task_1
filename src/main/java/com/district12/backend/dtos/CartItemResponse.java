package com.district12.backend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private String productDescription;
    private Integer quantity;

}

