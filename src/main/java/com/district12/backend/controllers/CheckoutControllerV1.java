package com.district12.backend.controllers;

import com.district12.backend.dtos.CheckoutRequest;
import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.OrderService;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
@Slf4j
public class CheckoutControllerV1 {

    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final UserService userService;

    // POST /checkout/items
    @PostMapping(path = "/items")
    public ResponseEntity<OrderResponse> checkOutItemsForUser(
            @Valid @RequestBody CheckoutRequest checkoutRequest) {

        cartItemService.doAllCartItemsBelongToUser(
                checkoutRequest.getCartItemIds(), SecurityUtils.getOwnerID());
        cartItemService.isAnyCartItemInAnotherOrder(checkoutRequest.getCartItemIds());

        User user = userService.getUserById(SecurityUtils.getOwnerID());
        Order newOrder = orderService.createOrder(user, checkoutRequest.getPaymentMethod());
        cartItemService.updateCartItemsOrderId(checkoutRequest.getCartItemIds(), newOrder);

        OrderResponse orderResponse = new OrderResponse(
                newOrder.getId(), SecurityUtils.getOwnerID(),
                newOrder.getTimestamp(), newOrder.getStatus());
        return ResponseEntity.ok(orderResponse);

    }

}
