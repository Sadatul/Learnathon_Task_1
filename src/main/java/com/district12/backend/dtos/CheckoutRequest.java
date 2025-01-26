package com.district12.backend.dtos;

import com.district12.backend.enums.OrderStatus;
import com.district12.backend.enums.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {

    private Long userId;
    private List<Long> cartItemIds;
    private String address;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public CheckoutRequest(Long userId, List<Long> cartItemIds, String address, PaymentMethod paymentMethod) {
        this.userId = userId;
        this.cartItemIds = cartItemIds;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
}

