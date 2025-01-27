package com.district12.backend.dtos;

public record AddressRequest(
        String name,
        String address,
        String city,
        Long zipCode
) {
}
