package com.district12.backend.services.impls;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.OrderDetailsResponse;
import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.OrderStatus;
import com.district12.backend.enums.PaymentMethod;
import com.district12.backend.repositories.OrderRepository;
import com.district12.backend.services.abstractions.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getOrdersForUser(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    @Override
    public List<OrderResponse> getPastOrdersForUser(Long userId) {
        return orderRepository.findPastOrdersByUserId(userId);
    }

    @Override
    public OrderDetailsResponse createOrderDetailsResponseForUser(
            Order order, List<CartItemResponse> cartItemResponses) {
        OrderResponse orderResponse = new OrderResponse(order.getId(), order.getUser().getId(),
                order.getTimestamp(), order.getStatus());
        return new OrderDetailsResponse(cartItemResponses, orderResponse);
    }

    @Override
    public Order createOrder(User user, PaymentMethod paymentMethod) {
//        Order order = new Order(user, OrderStatus.CHECKED_OUT, paymentMethod);
        Order order = new Order(user, OrderStatus.CHECKED_OUT);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long userId, Long orderId) {
        String errorMessage = "User is not authorized to retrieve the details of this order";
        return this.verifyUserId(userId, orderId, errorMessage);
    }

    @Override
    @Transactional
    public boolean cancelOrderForUser(Long userId, Long orderId) {
        String errorMessage = "User is not authorized to cancel this order";
        this.verifyUserId(userId, orderId, errorMessage);
        System.out.println("Reached here is order service");
        orderRepository.cancelOrderById(orderId);
        System.out.println("Reached here is order service after cancellation");
        return true;
    }

    @Override
    @Transactional
    public boolean confirmOrderForUser(Long userId, Long orderId) {
        String errorMessage = "User is not authorized to confirm this order";
        this.verifyUserId(userId, orderId, errorMessage);
        orderRepository.confirmOrderById(orderId);
        return true;
    }

    private Order verifyUserId(Long userId, Long orderId, String errorMessage) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for the given order id"));

        if (!existingOrder.getUser().getId().equals(userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, errorMessage);

        return existingOrder;
    }

}
