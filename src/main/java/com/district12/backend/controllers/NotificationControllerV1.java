package com.district12.backend.controllers;

import com.district12.backend.entities.Order;
import com.district12.backend.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    // PUT /notification/order/complete/101
    @PutMapping("/order/complete/{orderId}")
    public ResponseEntity<Order> shipOneReadyOrderForAdmin(@PathVariable Long orderId) {
        Order completedOrder = notificationService.completeOrder(orderId);
        return ResponseEntity.ok(completedOrder);
    }

}
