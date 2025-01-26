package com.district12.backend.services;

import com.district12.backend.entities.Order;
import com.district12.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final OrderRepository orderRepository;

    public Order completeOrder(Long orderId) {
        return orderRepository.completeOrder(orderId);
    }


}
