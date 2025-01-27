package com.district12.backend.dtos;

public record ProductFilterRequestBody(Integer priceStart, Integer priceEnd, Boolean inStock, Long categoryId) {
}
