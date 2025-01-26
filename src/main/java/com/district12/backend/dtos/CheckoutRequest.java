package com.district12.backend.dtos;

import com.district12.backend.entities.Address;
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
    private Address address;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public CheckoutRequest(Long userId, List<Long> cartItemIds, Address address, PaymentMethod paymentMethod) {
        this.userId = userId;
        this.cartItemIds = cartItemIds;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
}

