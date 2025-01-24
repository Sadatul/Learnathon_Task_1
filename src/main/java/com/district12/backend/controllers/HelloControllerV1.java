package com.district12.backend.controllers;

import com.district12.backend.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hello")
@Slf4j
public class HelloControllerV1 {
    @GetMapping
    public String hello() {
        Long userId = SecurityUtils.getOwnerID();
        return "Hello, World!" + userId;
    }
}
