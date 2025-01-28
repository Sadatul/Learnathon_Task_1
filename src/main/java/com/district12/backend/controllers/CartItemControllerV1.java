package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemRequest;
import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.CartItemUpdateRequest;
import com.district12.backend.entities.Product;
import com.district12.backend.entities.User;
import com.district12.backend.services.abstractions.CartItemService;
import com.district12.backend.services.ProductService;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartItemControllerV1 {

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    // GET /cart/items/user/101
    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems() {
        List<CartItemResponse> cartItems = cartItemService.getCartItemsForUser(SecurityUtils.getOwnerID());
        return ResponseEntity.ok(cartItems);
    }

    // POST /cart/item/add
    @PostMapping(path = "/items")
    public ResponseEntity<Object> createCartItemForUser(
            @Valid @RequestBody CartItemRequest cartItemRequest) {

            User user = userService.getUserById(SecurityUtils.getOwnerID());
            Product cartItemProduct = productService.findById(cartItemRequest.getProductId());
            CartItemResponse savedCartItemResponse = cartItemService.addCartItem(
                    user, cartItemProduct, cartItemRequest.getQuantity());

            URI savedCartItemUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}").buildAndExpand(savedCartItemResponse.getCartItemId()).toUri();
            return ResponseEntity.created(savedCartItemUri).body(savedCartItemResponse);

    }

    // PUT /cart/item/quantity/2
    @PatchMapping(path = "/items/{cartItemId}/quantity")
    public ResponseEntity<Object> updateCartItemQuantityForUser(
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateRequest cartItemRequest) {

            CartItemResponse updatedCartItemResponse = cartItemService.updateCartItemQuantity(
                    SecurityUtils.getOwnerID(),
                    cartItemId, cartItemRequest.getNewQuantity());

            return ResponseEntity.ok(updatedCartItemResponse);

    }

    // DELETE /cart/item/delete/2
    @DeleteMapping(path = "/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItemForUser(
            @PathVariable Long cartItemId) {

            cartItemService.deleteCartItem(SecurityUtils.getOwnerID(), cartItemId);
            return ResponseEntity.noContent().build();
    }

}
