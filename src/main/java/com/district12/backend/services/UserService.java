package com.district12.backend.services;

import com.district12.backend.entities.User;
import com.district12.backend.enums.Role;
import com.district12.backend.exceptions.UnauthorizedException;
import com.district12.backend.repositories.UserRepository;
import com.district12.backend.repositories.UserVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserVerificationRepository userVerificationRepository;

    public void saveUser(User user) {
        userVerificationRepository.deleteUserVerInfoByEmail(user.getEmail());
        userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    public void verifyAdminRoleById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        if (user.getRole() != Role.ADMIN)
            throw new UnauthorizedException("User is not authorized to access this resource.");
        // return user;
    }
}
