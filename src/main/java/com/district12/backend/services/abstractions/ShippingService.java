package com.district12.backend.services.abstractions;

import com.district12.backend.dtos.OrderResponse;

import java.util.List;

public interface ShippingService {

    List<OrderResponse> getAllReadyOrdersForAdmin();
    List<OrderResponse> shipAndFetchReadyOrders();
    OrderResponse shipAndFetchOneReadyOrderForAdmin(Long orderId);

}
