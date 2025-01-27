package com.district12.backend.dtos;

public record ProductRequest(
        String name,
        String description,
        Double price,
        Integer stock,
        Long categoryId
) {
}
