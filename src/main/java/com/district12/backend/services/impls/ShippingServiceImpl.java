package com.district12.backend.services.impls;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.repositories.OrderRepository;
import com.district12.backend.services.abstractions.ShippingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getAllReadyOrdersForAdmin() {
        return orderRepository.getAllReadyOrders();
    }

    @Override
    @Transactional
    public List<OrderResponse> shipAndFetchReadyOrders() {
        int numberOfShippedOrders = orderRepository.shipAllReadyOrders();
        if(numberOfShippedOrders == 0)
            throw new RuntimeException("No orders were shipped");
        return orderRepository.getAllShippedOrders();
    }

    @Override
    @Transactional
    public OrderResponse shipAndFetchOneReadyOrderForAdmin(Long orderId) {
        int numberOfShippedOrders = orderRepository.shipOneReadyOrderForAdmin(orderId);
        if (numberOfShippedOrders  == 0)
            throw new RuntimeException("No order was shipped");
        return orderRepository.getOrderResponseById(orderId);
    }

}
