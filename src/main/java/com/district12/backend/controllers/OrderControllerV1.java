package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.OrderDetailsResponse;
import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.services.abstractions.CartItemService;
import com.district12.backend.services.abstractions.OrderService;
import com.district12.backend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderControllerV1 {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    // GET /order/all
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForUser() {
        List<OrderResponse> orderResponses = orderService.getOrdersForUser(SecurityUtils.getOwnerID());
        return ResponseEntity.ok(orderResponses);
    }

    // GET /order/past
    @GetMapping("/past")
    public ResponseEntity<List<OrderResponse>> getAllPastOrdersForUser() {
        List<OrderResponse> orderResponses = orderService.getPastOrdersForUser(SecurityUtils.getOwnerID());
        return ResponseEntity.ok(orderResponses);
    }

    // GET /order/details/101
    @GetMapping("/details/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetailsForUser(@PathVariable Long orderId) {
        Order userOrder = orderService.getOrderById(SecurityUtils.getOwnerID(), orderId);
        List<CartItemResponse> cartItemResponses = cartItemService.getCartItemsByOrderId(userOrder.getId());

        OrderDetailsResponse orderDetailsResponse = orderService.createOrderDetailsResponseForUser(userOrder, cartItemResponses);
        return ResponseEntity.ok(orderDetailsResponse);
    }

    // PUT /order/cancel/1
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Object> cancelOrderForUser(@PathVariable Long orderId) {
        boolean isCanceled = orderService.cancelOrderForUser(SecurityUtils.getOwnerID(), orderId);
        if (isCanceled)
            return ResponseEntity.ok("Order successfully canceled.");
        else
            throw new RuntimeException("Order is already shipped and can not be cancelled anymore.");

    }

    // PUT /order/confirm/1
    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<Object> confirmOrderForUser(@PathVariable Long orderId) {
        boolean isConfirmed = orderService.confirmOrderForUser(SecurityUtils.getOwnerID(), orderId);
        if (isConfirmed)
            return ResponseEntity.ok("Order successfully canceled.");
        else
            throw new RuntimeException("Order can not be confirmed before payment.");
    }

}
