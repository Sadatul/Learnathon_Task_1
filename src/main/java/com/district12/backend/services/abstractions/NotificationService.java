package com.district12.backend.services.abstractions;

import com.district12.backend.dtos.OrderResponse;
import jakarta.transaction.Transactional;

public interface NotificationService {

    @Transactional
    OrderResponse completeOrder(Long orderId);

}
