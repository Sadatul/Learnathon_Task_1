package com.district12.backend.dtos;

import com.district12.backend.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class RefreshToken {
    private User user;
    private String token;
}
