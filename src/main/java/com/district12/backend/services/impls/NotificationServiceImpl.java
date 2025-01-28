package com.district12.backend.services.impls;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.repositories.OrderRepository;
import com.district12.backend.services.abstractions.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponse completeOrder(Long orderId) {
        int numberOfCompletedOrder = orderRepository.completeOrder(orderId);
        if(numberOfCompletedOrder == 0)
            throw new RuntimeException("Order was not completed");
        return orderRepository.getOrderResponseById(orderId);
    }

}
