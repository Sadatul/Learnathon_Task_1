package com.district12.backend.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {

    private String productName;
    private String productDescription;
    private Integer quantity;

}

