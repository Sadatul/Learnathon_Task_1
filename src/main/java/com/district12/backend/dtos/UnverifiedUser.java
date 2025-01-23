package com.district12.backend.dtos;

import com.district12.backend.entities.User;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UnverifiedUser implements Serializable {
    private User user;
    private String otp;
}