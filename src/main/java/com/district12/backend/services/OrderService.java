package com.district12.backend.services;

import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.OrderStatus;
import com.district12.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Order createOrder(User user){
        Order order = new Order(user, OrderStatus.CHECKED_OUT);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found for the given order id"));
    }

    public boolean cancelOrderForUser(Long orderId) {
        orderRepository.cancelOrderById(orderId);
        return true;
    }

    public boolean confirmOrderForUser(Long orderId) {
        orderRepository.confirmOrderById(orderId);
        return true;
    }

}
