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
import java.util.Optional;

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
    @PostMapping(path = "/item/add")
    public ResponseEntity<CartItem> createCartItemForUser(
            @Valid @RequestBody CartItemRequest cartItemRequest) {

        Product cartItemProduct = productService.findById(cartItemRequest.getProductId());
        CartItem savedCartItem = cartItemService.addCartItem(cartItemRequest.getUserId(), cartItemProduct, cartItemRequest.getQuantity());
        return ResponseEntity.ok(savedCartItem);
    }

    // PUT /item/update/quantity
    @PutMapping(path = "/item/update/quantity")
    public ResponseEntity<CartItem> updateCartItemQuantityForUser(
            @Valid @RequestBody CartItemUpdateRequest cartItemRequest) {

        Product cartItemProduct = productService.findById(cartItemRequest.getProductId());
        CartItem updatedCartItem = cartItemService.updateCartItemQuantity(cartItemRequest.getUserId(), cartItemProduct, cartItemRequest.getNewQuantity());
        return ResponseEntity.ok(updatedCartItem);
    }

    // DELETE /item/delete
    @DeleteMapping(path = "/item/delete")
    public void deleteCartItemForUser(
            @Valid @RequestBody CartItemUpdateRequest cartItemRequest) {

        Product cartItemProduct = productService.findById(cartItemRequest.getProductId());
        cartItemService.deleteCartItem(cartItemRequest.getUserId(), cartItemProduct);
    }

}
