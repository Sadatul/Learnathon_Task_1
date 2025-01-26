package com.district12.backend.repositories;

import com.district12.backend.dtos.UnverifiedUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface UserVerificationRepository {
    void putUserVerificationInfo(String email, UnverifiedUser unverifiedUser) throws JsonProcessingException;

    Optional<UnverifiedUser> getUserVerificationInfoByEmail(String email) throws JsonProcessingException;

    void deleteUserVerInfoByEmail(String email);
}
