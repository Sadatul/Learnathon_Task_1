package com.district12.backend.controllers;

import com.district12.backend.dtos.JwtTokenResponse;
import com.district12.backend.dtos.LogInRequest;
import com.district12.backend.dtos.RegistrationRequest;
import com.district12.backend.dtos.UserVerificationRequest;
import com.district12.backend.entities.User;
import com.district12.backend.repositories.UserRepository;
import com.district12.backend.services.JwtTokenService;
import com.district12.backend.services.UserRegistrationAndVerificationService;
import com.district12.backend.services.UserService;
import com.district12.backend.utils.CodeGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthControllerV1 {

    private final UserRepository userRepository;
    private final UserRegistrationAndVerificationService userRegVerService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<JwtTokenResponse> login(@Valid @RequestBody LogInRequest req){
        var authenticationToken = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenResponse = jwtTokenService.generateToken(authentication);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest req){
        if(userRepository.existsByEmail(req.email())){
            return ResponseEntity.badRequest().build();
        }

        String otp = CodeGenerator.generateOtp();
        try {
            userRegVerService.cacheDetails(req, otp);
            userRegVerService.sendVerificationEmail(req.email(), otp);
        }
        catch (JsonProcessingException e) {
            log.error("Error occurred while caching user details: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while caching user details");
        }
        catch (Exception e) {
            log.error("Error occurred while sending email: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while caching user details");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyOpt(@Valid @RequestBody UserVerificationRequest request) throws JsonProcessingException {
        User user = userRegVerService.verifyUser(request.email(), request.otp());
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

}
