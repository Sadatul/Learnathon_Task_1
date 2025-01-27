package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.OrderService;
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
    public List<CartItemResponse> getOrderDetailsForUser(@PathVariable Long orderId) {
        Order userOrder = orderService.getOrderById(SecurityUtils.getOwnerID(), orderId);
        return cartItemService.getCartItemsByOrderId(userOrder.getId());
    }

    // PUT /order/cancel/101
    @PutMapping("/cancel/{orderId}")
    public boolean cancelOrderForUser(@PathVariable Long orderId) {
        return orderService.cancelOrderForUser(SecurityUtils.getOwnerID(), orderId);
    }

    // PUT /order/confirm/101
    @PutMapping("/confirm/{orderId}")
    public boolean confirmOrderForUser(@PathVariable Long orderId) {
        return orderService.confirmOrderForUser(SecurityUtils.getOwnerID(), orderId);
    }

}
