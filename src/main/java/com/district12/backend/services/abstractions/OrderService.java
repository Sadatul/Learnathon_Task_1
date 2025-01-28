package com.district12.backend.services.abstractions;

import com.district12.backend.dtos.CartItemResponse;
import com.district12.backend.dtos.OrderDetailsResponse;
import com.district12.backend.dtos.OrderResponse;
import com.district12.backend.entities.Order;
import com.district12.backend.entities.User;
import com.district12.backend.enums.PaymentMethod;

import java.util.List;

public interface OrderService {

    List<OrderResponse> getOrdersForUser(Long userId);
    List<OrderResponse> getPastOrdersForUser(Long userId);
    OrderDetailsResponse createOrderDetailsResponseForUser(
            Order order, List<CartItemResponse> cartItemResponses);
    Order createOrder(User user, PaymentMethod paymentMethod);
    Order getOrderById(Long userId, Long orderId);
    boolean cancelOrderForUser(Long userId, Long orderId);
    boolean confirmOrderForUser(Long userId, Long orderId);

}
