package com.district12.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {
    public static Long getOwnerID(){
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.parseLong(owner);
    }
}