package com.district12.backend.repositories;


import com.district12.backend.dtos.RefreshToken;

import java.util.Optional;

public interface RefreshTokenBaseRepository {
    void saveToken(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String hashedToken);
    void deleteTokenForUserId(Long userId);
}
