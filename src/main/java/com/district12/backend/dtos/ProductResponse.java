package com.district12.backend.dtos;

public record ProductResponse(
        String name,
        String description,
        Double price,
        Integer stock,
        CategoryResponse categoryResponse
) {
}
