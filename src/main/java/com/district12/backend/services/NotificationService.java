package com.district12.backend.services;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse completeOrder(Long orderId) {
        int numberOfCompletedOrder = orderRepository.completeOrder(orderId);
        if(numberOfCompletedOrder == 0)
            throw new RuntimeException("Order was not completed");
        return orderRepository.getOrderResponseById(orderId);
    }


}
