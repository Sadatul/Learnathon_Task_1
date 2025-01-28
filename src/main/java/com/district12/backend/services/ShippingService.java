package com.district12.backend.services;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final OrderRepository orderRepository;

    public List<OrderResponse> getAllReadyOrdersForAdmin() {
        return orderRepository.getAllReadyOrders();
    }

    @Transactional
    public List<OrderResponse> shipAndFetchReadyOrders() {
        int numberOfShippedOrders = orderRepository.shipAllReadyOrders();
        if(numberOfShippedOrders == 0)
            throw new RuntimeException("No orders were shipped");
        return orderRepository.getAllShippedOrders();
    }

    @Transactional
    public OrderResponse shipAndFetchOneReadyOrderForAdmin(Long orderId) {
        int numberOfShippedOrders = orderRepository.shipOneReadyOrderForAdmin(orderId);
        if (numberOfShippedOrders  == 0)
            throw new RuntimeException("No order was shipped");
        return orderRepository.getOrderResponseById(orderId);
    }

}
