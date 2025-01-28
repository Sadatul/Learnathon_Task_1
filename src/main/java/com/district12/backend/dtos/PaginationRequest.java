package com.district12.backend.dtos;

import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PaginationRequest(
        @Min(value = 0, message = "Negative page number is not allowed") Integer page,
        @Min(value = 0, message = "Negative page number is not allowed")Integer size,
        Sort.Direction direction
) {
    public PaginationRequest{
        page = page != null ? page : 0;
        size = size != null ? size : 10;
        direction = direction != null ? direction : Sort.Direction.ASC;
    }

    public Pageable getPageable(BaseSortCategory sortCategory) {
        return PageRequest.of(page, size,
                Sort.by(direction, sortCategory.getValue())
        );
    }
}
