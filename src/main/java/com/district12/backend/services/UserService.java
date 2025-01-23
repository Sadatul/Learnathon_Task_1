package com.district12.backend.services;

import com.district12.backend.entities.User;
import com.district12.backend.repositories.UserRepository;
import com.district12.backend.repositories.UserVerificationRepository;
import lombok.RequiredArgsConstructor;
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

}
