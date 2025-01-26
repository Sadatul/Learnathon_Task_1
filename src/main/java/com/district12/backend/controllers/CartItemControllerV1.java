package com.district12.backend.controllers;

import com.district12.backend.services.CartItemService;
import com.district12.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartItemControllerV1 {

    private final CartItemService cartItemService;
    private final ProductService productService;

}
