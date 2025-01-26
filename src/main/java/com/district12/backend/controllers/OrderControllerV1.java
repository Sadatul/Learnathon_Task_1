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

    // GET /orders/user/101
    @GetMapping("/items/orders/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersForUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersForUser(userId);
        return ResponseEntity.ok(orders);
    }

    // GET /orders/past/101
    @GetMapping("/items/orders/past/{userId}")
    public ResponseEntity<List<Order>> getAllPastOrdersForUser(@PathVariable Long userId) {
        List<Order> orders = orderService.getPastOrdersForUser(userId);
        return ResponseEntity.ok(orders);
    }

    // GET /order/details/101
    @GetMapping("/items/order/details/{orderId}")
    public List<CartItemResponse> getOrderDetailsForUser(@PathVariable Long orderId) {
        Order userOrder = orderService.getOrderById(orderId);
        return cartItemService.getCartItemsByOrderId(userOrder.getId());
    }

}
