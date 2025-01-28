package com.district12.backend.services;

import com.district12.backend.entities.User;
import com.district12.backend.repositories.UserRepository;
import com.district12.backend.repositories.UserVerificationRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;

    public User saveUser(User user) {
        userVerificationRepository.deleteUserVerInfoByEmail(user.getEmail());
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    public void updateUserInfo(Long userId, String email, String phoneNumber) {
        User user = getUserById(userId);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }
}
