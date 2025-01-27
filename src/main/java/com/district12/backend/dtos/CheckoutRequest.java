package com.district12.backend.dtos;

import com.district12.backend.entities.Address;
import com.district12.backend.enums.OrderStatus;
import com.district12.backend.enums.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CheckoutRequest {

    private List<Long> cartItemIds;
    private Long addressId;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

}

