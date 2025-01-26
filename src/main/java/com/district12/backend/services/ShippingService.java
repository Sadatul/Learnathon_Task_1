package com.district12.backend.services;

import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.Role;
import com.district12.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final OrderRepository orderRepository;

    public List<Order> getAllReadyOrdersForAdmin(User user) {
        return orderRepository.getAllReadyOrders();
    }

    public List<Order> shipAllReadyOrdersForAdmin() {
        return orderRepository.shipAllReadyOrders();
    }

    public Order shipOneReadyOrderForAdmin(Long orderId) {
        return orderRepository.shipOneReadyOrderForAdmin(orderId);
    }


}
