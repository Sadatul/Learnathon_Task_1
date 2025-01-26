package com.district12.backend.repositories;

import com.district12.backend.dtos.UnverifiedUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@ConditionalOnProperty(value = "spring.cache.type", havingValue = "none")
public class UserVerificationRepositoryInMemoryImpl implements UserVerificationRepository {

    private Map<String, UnverifiedUserWrapper> userVerificationInfo;

    @Value("${verification.email.timeout}")
    private Long verificationEmailTimeout;

    @PostConstruct
    public void init() {
        userVerificationInfo = new HashMap<>();
    }

    @Override
    public void putUserVerificationInfo(String email, UnverifiedUser unverifiedUser) {
        userVerificationInfo.put(email, new UnverifiedUserWrapper(unverifiedUser,
                Instant.now().plusSeconds(verificationEmailTimeout)));
    }

    @Override
    public Optional<UnverifiedUser> getUserVerificationInfoByEmail(String email) {
        if (userVerificationInfo.containsKey(email)) {
            UnverifiedUserWrapper userWrapper = this.userVerificationInfo.get(email);
            if (userWrapper.expiryTime().isBefore(Instant.now())) {
                this.userVerificationInfo.remove(email);
                return Optional.empty();
            }
            return Optional.of(userWrapper.unverifiedUser());
        }
        return Optional.empty();
    }

    @Override
    public void deleteUserVerInfoByEmail(String email) {
        userVerificationInfo.remove(email);
    }

    record UnverifiedUserWrapper(UnverifiedUser unverifiedUser, Instant expiryTime) {
    }
}
