package com.district12.backend.dtos;

public record JwtTokenResponse(
        String token,
        String refreshToken,
        Long id
) {

}
