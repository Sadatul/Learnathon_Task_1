package com.district12.backend.controllers;

import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.Role;
import com.district12.backend.exceptions.UnauthorizedException;
import com.district12.backend.services.ShippingService;
import com.district12.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ship")
@RequiredArgsConstructor
@Slf4j
public class ShippingControllerV1 {

    private final ShippingService shippingService;
    private final UserService userService;

    // GET ship/get/ready-orders
    @GetMapping("/get/ready-orders/{userId}")
    public ResponseEntity<List<Order>> getAllReadyOrdersForAdmin(@PathVariable Long userId) {
        User user = userService.verifyAdminRoleById(userId);
        List<Order> confirmedOrders = shippingService.getAllReadyOrdersForAdmin(user);
        return ResponseEntity.ok(confirmedOrders);
    }

    // PUT ship/ready-orders/1
    @PutMapping("/ready-orders/{userId}")
    public ResponseEntity<List<Order>> shipAllReadyOrdersForAdmin(@PathVariable Long userId) {
        userService.verifyAdminRoleById(userId);
        List<Order> shippedOrders = shippingService.shipAllReadyOrdersForAdmin();
        return ResponseEntity.ok(shippedOrders);
    }

    // PUT ship/ready-order/2
    @PutMapping("/ready-order/{userId}/{orderId}")
    public ResponseEntity<Order> shipOneReadyOrderForAdmin(@PathVariable Long userId, @PathVariable Long orderId) {
        userService.verifyAdminRoleById(userId);
        Order shippedOrder = shippingService.shipOneReadyOrderForAdmin(orderId);
        return ResponseEntity.ok(shippedOrder);
    }
}
