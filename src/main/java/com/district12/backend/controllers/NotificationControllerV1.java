package com.district12.backend.controllers;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.services.NotificationService;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.SecurityUtils;
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
    private final UserService userService;

    // PUT /notification/order/complete/1
    @PutMapping("/order/complete/{orderId}")
    public ResponseEntity<OrderResponse> shipOneReadyOrderForAdmin(@PathVariable Long orderId) {
        userService.verifyAdminRoleById(SecurityUtils.getOwnerID());
        OrderResponse completedOrderResponse = notificationService.completeOrder(orderId);
        return ResponseEntity.ok(completedOrderResponse);
    }

}
