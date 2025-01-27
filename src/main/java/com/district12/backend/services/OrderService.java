package com.district12.backend.services;

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

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    public List<Order> getPastOrdersForUser(Long userId) {
        return orderRepository.findPastOrdersByUserId(userId);
    }

    public Order createOrder(User user, PaymentMethod paymentMethod) {
        Order order = new Order(user, OrderStatus.CHECKED_OUT, paymentMethod);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long userId, Long orderId) {
        return this.verifyUserId(
                userId, orderId,
                "User is not authorized to retrieve the details of this order");
    }

    public boolean cancelOrderForUser(Long userId, Long orderId) {
        Order existingOrder = this.verifyUserId(
                userId, orderId,
                "User is not authorized to cancel this order");
        orderRepository.cancelOrderById(orderId);
        return true;
    }

    public boolean confirmOrderForUser(Long userId, Long orderId) {
        Order existingOrder = this.verifyUserId(
                userId, orderId,
                "User is not authorized to confirm this order");
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
