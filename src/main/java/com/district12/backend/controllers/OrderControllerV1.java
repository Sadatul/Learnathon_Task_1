package com.district12.backend.controllers;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.services.CartItemService;
import com.district12.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderControllerV1 {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    // GET /order/all/101
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersForUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersForUser(userId);
        return ResponseEntity.ok(orders);
    }

    // GET /order/past/101
    @GetMapping("/past/{userId}")
    public ResponseEntity<List<Order>> getAllPastOrdersForUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getPastOrdersForUser(userId);
        return ResponseEntity.ok(orders);
    }

    // GET /order/details/101
    @GetMapping("/details/{orderId}")
    public List<CartItemResponse> getOrderDetailsForUser(@PathVariable Long orderId) {
        Order userOrder = orderService.getOrderById(orderId);
        return cartItemService.getCartItemsByOrderId(userOrder.getId());
    }

    // PUT /order/cancel/101
    @PutMapping("/cancel/{orderId}")
    public boolean cancelOrderForUser(@PathVariable Long orderId) {
        return orderService.cancelOrderForUser(orderId);
    }

    // PUT /order/confirm/101
    @PutMapping("/confirm/{orderId}")
    public boolean confirmOrderForUser(@PathVariable Long orderId) {
        return orderService.confirmOrderForUser(orderId);
    }

}
