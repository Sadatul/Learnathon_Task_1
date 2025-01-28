package com.district12.backend.controllers;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.services.abstractions.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ship")
@RequiredArgsConstructor
@Slf4j
public class ShippingControllerV1 {

    private final ShippingService shippingService;

    // GET ship/get/ready-orders
    @GetMapping("/get/ready-orders")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<List<OrderResponse>> getAllReadyOrdersForAdmin() {
        List<OrderResponse> confirmedOrders = shippingService.getAllReadyOrdersForAdmin();
        return ResponseEntity.ok(confirmedOrders);
    }

    // PUT ship/ready-orders/1
    @PutMapping("/ready-orders")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<List<OrderResponse>> shipAllReadyOrdersForAdmin() {
        List<OrderResponse> shippedOrders = shippingService.shipAndFetchReadyOrders();
        return ResponseEntity.ok(shippedOrders);
    }

    // PUT ship/ready-order/2
    @PutMapping("/ready-order/{orderId}")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<OrderResponse> shipOneReadyOrderForAdmin(@PathVariable Long orderId) {
        OrderResponse shippedOrder = shippingService.shipAndFetchOneReadyOrderForAdmin(orderId);
        return ResponseEntity.ok(shippedOrder);
    }
}
