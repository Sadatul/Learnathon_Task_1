package com.district12.backend.controllers;

import com.district12.backend.dtos.CheckoutRequest;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.OrderService;
import com.district12.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void checkOutItemsForUser(
            @Valid @RequestBody CheckoutRequest checkoutRequest) {

        User user = userService.getUserById(checkoutRequest.getUserId());
        Order savedOrder = orderService.createOrder(user, checkoutRequest.getPaymentMethod());
        cartItemService.updateCartItemsOrderId(checkoutRequest.getCartItemIds(), savedOrder);
    }

}
