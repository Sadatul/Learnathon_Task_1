package com.district12.backend.services;

import com.district12.backend.dtos.RefreshToken;
import com.district12.backend.entities.User;
import com.district12.backend.repositories.RefreshTokenBaseRepository;
import com.district12.backend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenBaseRepository refreshTokenRepository;
    private final UserService userService;

    public String getRefreshTokenForUser(Long userId) {
        String tokenValue = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(
                userService.getUserById(userId),
                SecurityUtils.generateSHA256Hash(tokenValue)
        );
        refreshTokenRepository.saveToken(refreshToken);
        return tokenValue;
    }


    public User verifyToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(
                SecurityUtils.generateSHA256Hash(token)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")
        );
        return refreshToken.getUser();
    }
}
