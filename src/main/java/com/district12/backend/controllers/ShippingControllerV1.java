package com.district12.backend.controllers;

import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.Role;
import com.district12.backend.services.ShippingService;
import com.district12.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/ship")
@RequiredArgsConstructor
@Slf4j
public class ShippingControllerV1 {

    private final ShippingService shippingService;
    private final UserService userService;

    // GET ship/get/ready-orders
    @GetMapping("/get/ready-orders/{userId}")
    public ResponseEntity<Object> getAllReadyOrdersForAdmin(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("User is not authorized to access this resource.");
        }
        List<Order> confirmedOrders = shippingService.getAllReadyOrdersForAdmin(user);
        return ResponseEntity.ok(confirmedOrders);
    }
}
