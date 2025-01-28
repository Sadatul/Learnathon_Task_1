package com.district12.backend.services.abstractions;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
