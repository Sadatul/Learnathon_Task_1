package com.district12.backend.controllers;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.services.ShippingService;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.SecurityUtils;
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
    @GetMapping("/get/ready-orders")
    public ResponseEntity<List<OrderResponse>> getAllReadyOrdersForAdmin() {
        userService.verifyAdminRoleById(SecurityUtils.getOwnerID());
        List<OrderResponse> confirmedOrders = shippingService.getAllReadyOrdersForAdmin();
        return ResponseEntity.ok(confirmedOrders);
    }

    // PUT ship/ready-orders/1
    @PutMapping("/ready-orders")
    public ResponseEntity<List<OrderResponse>> shipAllReadyOrdersForAdmin() {
        userService.verifyAdminRoleById(SecurityUtils.getOwnerID());
        List<OrderResponse> shippedOrders = shippingService.shipAndFetchReadyOrders();
        return ResponseEntity.ok(shippedOrders);
    }

    // PUT ship/ready-order/2
    @PutMapping("/ready-order/{orderId}")
    public ResponseEntity<OrderResponse> shipOneReadyOrderForAdmin(@PathVariable Long orderId) {
        userService.verifyAdminRoleById(SecurityUtils.getOwnerID());
        OrderResponse shippedOrder = shippingService.shipAndFetchOneReadyOrderForAdmin(orderId);
        return ResponseEntity.ok(shippedOrder);
    }
}
