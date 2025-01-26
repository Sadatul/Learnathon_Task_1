package com.district12.backend.repositories;

import com.district12.backend.dtos.UnverifiedUser;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("!prod")
public class UserVerificationRepositoryInMemoryImpl implements UserVerificationRepository {

    private Map<String, UnverifiedUser> userVerificationInfo;

    @PostConstruct
    public void init() {
        userVerificationInfo = new HashMap<>();
    }

    @Override
    public void putUserVerificationInfo(String email, UnverifiedUser unverifiedUser) {
        userVerificationInfo.put(email, unverifiedUser);
    }

    @Override
    public Optional<UnverifiedUser> getUserVerificationInfoByEmail(String email) {
        if (userVerificationInfo.containsKey(email)) {
            return Optional.of(userVerificationInfo.get(email));
        }
        return Optional.empty();
    }

    @Override
    public void deleteUserVerInfoByEmail(String email) {
        userVerificationInfo.remove(email);
    }
}
