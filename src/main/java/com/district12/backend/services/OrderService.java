package com.district12.backend.services;

import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.OrderStatus;
import com.district12.backend.enums.PaymentMethod;
import com.district12.backend.exceptions.UnauthorizedException;
import com.district12.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderResponse> getOrdersForUser(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    public List<OrderResponse> getPastOrdersForUser(Long userId) {
        return orderRepository.findPastOrdersByUserId(userId);
    }

    public Order createOrder(User user, PaymentMethod paymentMethod) {
//        Order order = new Order(user, OrderStatus.CHECKED_OUT, paymentMethod);
        Order order = new Order(user, OrderStatus.CHECKED_OUT);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long userId, Long orderId) {
        String errorMessage = "User is not authorized to retrieve the details of this order";
        return this.verifyUserId(userId, orderId, errorMessage);
    }

    public boolean cancelOrderForUser(Long userId, Long orderId) {
        String errorMessage = "User is not authorized to cancel this order";
        this.verifyUserId(userId, orderId, errorMessage);
        orderRepository.cancelOrderById(orderId);
        return true;
    }

    public boolean confirmOrderForUser(Long userId, Long orderId) {
        String errorMessage = "User is not authorized to confirm this order";
        this.verifyUserId(userId, orderId, errorMessage);
        orderRepository.confirmOrderById(orderId);
        return true;
    }

    private Order verifyUserId(Long userId, Long orderId, String errorMessage) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found for the given order id"));

        if (!existingOrder.getUser().getId().equals(userId))
            throw new UnauthorizedException(errorMessage);

        return existingOrder;
    }

}
