package com.district12.backend.services;

import com.district12.backend.dtos.JwtTokenResponse;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {
    JwtTokenResponse generateToken(Authentication authentication);
    JwtTokenResponse generateToken(String refreshToken);
}
