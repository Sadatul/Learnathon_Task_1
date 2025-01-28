package com.district12.backend.controllers;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.services.abstractions.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationControllerV1 {

    private final NotificationService notificationService;

    // PUT /notification/order/complete/1
    @PutMapping("/order/complete/{orderId}")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)") // hasAuthority('SCOPE_ADMIN')
    public ResponseEntity<OrderResponse> shipOneReadyOrderForAdmin(@PathVariable Long orderId) {
        OrderResponse completedOrderResponse = notificationService.completeOrder(orderId);
        return ResponseEntity.ok(completedOrderResponse);
    }

}
