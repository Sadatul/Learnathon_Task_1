package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartItemControllerV1 {

    private final CartItemService cartItemService;
    private final ProductService productService;

    // GET /items/user/101
    @GetMapping("/items/user/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable Long userId) {
        List<CartItemResponse> cartItems = cartItemService.getCartItemsForUser(userId);
        return ResponseEntity.ok(cartItems);
    }

}
