package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemRequest;
import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.CartItemUpdateRequest;
import com.district12.backend.entities.CartItem;
import com.district12.backend.entities.Product;
import com.district12.backend.entities.User;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.ProductService;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.SecurityUtils;
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
    private final UserService userService;

    // GET /items/user/101
    @GetMapping("/items/user")
    public ResponseEntity<List<CartItemResponse>> getCartItems() {
        List<CartItemResponse> cartItems = cartItemService.getCartItemsForUser(SecurityUtils.getOwnerID());
        return ResponseEntity.ok(cartItems);
    }

    // POST /item/add
    @PostMapping(path = "/item/add")
    public ResponseEntity<CartItemResponse> createCartItemForUser(
            @Valid @RequestBody CartItemRequest cartItemRequest) {

        User user = userService.getUserById(SecurityUtils.getOwnerID());
        Product cartItemProduct = productService.findById(cartItemRequest.getProductId());
        CartItemResponse savedCartItemResponse = cartItemService.addCartItem(
                user, cartItemProduct, cartItemRequest.getQuantity());

        return ResponseEntity.ok(savedCartItemResponse);
    }

    // PUT /item/update/quantity
    @PutMapping(path = "/item/update/quantity")
    public ResponseEntity<CartItemResponse> updateCartItemQuantityForUser(
            @Valid @RequestBody CartItemUpdateRequest cartItemRequest) {

        CartItemResponse updatedCartItemResponse = cartItemService.updateCartItemQuantity(
                SecurityUtils.getOwnerID(),
                cartItemRequest.getCartItemId(), cartItemRequest.getNewQuantity());

        return ResponseEntity.ok(updatedCartItemResponse);
    }

    // DELETE /item/delete/2
    @DeleteMapping(path = "/item/delete/{cartItemId}")
    public void deleteCartItemForUser(
            @PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(SecurityUtils.getOwnerID(), cartItemId);
    }

}
