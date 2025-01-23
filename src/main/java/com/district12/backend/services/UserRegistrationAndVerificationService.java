package com.district12.backend.services;

import com.district12.backend.dtos.RegistrationRequest;
import com.district12.backend.dtos.UnverifiedUser;
import com.district12.backend.entities.User;
import com.district12.backend.exceptions.BadRequestRuntimeException;
import com.district12.backend.exceptions.TimedOutException;
import com.district12.backend.repositories.UserVerificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationAndVerificationService {
    @Value("${verification.email.message}")
    private String verificationEmailMessage;

    @Value("${verification.email.subject}")
    private String verificationEmailSubject;

    @Value("${verification.email.timeout}")
    private int optExpiration;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserVerificationRepository userVerRepo;

    public void cacheDetails(RegistrationRequest request, String otp) throws JsonProcessingException {
        UnverifiedUser unverifiedUser = new UnverifiedUser(
                new User(request.email(),
                request.fullName(),
                passwordEncoder.encode(request.password()),
                request.role(),
                request.phoneNumber())
                , otp);

        userVerRepo.putUserVerificationInfo(request.email(), unverifiedUser);
    }

    public void sendVerificationEmail(String email, String otp) {
        emailService.sendSimpleEmail(email, verificationEmailSubject,
                String.format(verificationEmailMessage, otp, (optExpiration / 60)));
    }

    public User verifyUser(String email, String otp) throws JsonProcessingException {
        Optional<UnverifiedUser> unverifiedUser = userVerRepo.getUserVerificationInfoByEmail(email);

        if (unverifiedUser.isEmpty()) {
            throw new TimedOutException("Your opt has timed out");
        }

        if(!unverifiedUser.get().getOtp().equals(otp)){
            throw new BadRequestRuntimeException("Your OTP doesn't match. Please try again");
        }
        return unverifiedUser.get().getUser();
    }

}