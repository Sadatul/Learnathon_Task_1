package com.district12.backend.dtos;

import com.district12.backend.entities.User;
import com.district12.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Long userId;
    private ZonedDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
