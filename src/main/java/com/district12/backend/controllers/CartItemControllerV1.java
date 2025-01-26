package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemRequest;
import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.CartItemUpdateRequest;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Product;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // POST /item/add/101
    @PostMapping(path = "/item/add/{userId}")
    public ResponseEntity<CartItem> createCartItemForUser(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemRequest cartItemRequest) {

        Product cartItemProduct = productService.findById(cartItemRequest.getProductId());

        CartItem savedCartItem = cartItemService.addCartItem(userId, cartItemProduct, cartItemRequest.getQuantity());
        return ResponseEntity.ok(savedCartItem);
    }

    // PUT /item/update/quantity/101
    @PutMapping(path = "/item/update/quantity/{userId}")
    public ResponseEntity<CartItem> updateCartItemQuantityForUser(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemUpdateRequest cartItemRequest) {

        Product cartItemProduct = productService.findById(cartItemRequest.getProductId());
        CartItem updatedCartItem = cartItemService.updateCartItemQuantity(userId, cartItemProduct, cartItemRequest.getNewQuantity());
        return ResponseEntity.ok(updatedCartItem);
    }

}
