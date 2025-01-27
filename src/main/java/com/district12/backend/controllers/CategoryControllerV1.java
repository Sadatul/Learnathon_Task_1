package com.district12.backend.controllers;

import com.district12.backend.dtos.CategoryRequest;
import com.district12.backend.dtos.CategoryResponse;
import com.district12.backend.entities.Category;
import com.district12.backend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryControllerV1 {
    private final CategoryService categoryService;

    @PutMapping("/add")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequest request) {
        Long id = categoryService.addCategory(request.name(), request.description()).getId();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @PostMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequest request, @PathVariable Long categoryId) {
        categoryService.updateCategory(categoryId, request.name(), request.description());
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> responses = categories.stream().map(category ->
                new CategoryResponse(category.getName(), category.getDescription())
                ).toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return ResponseEntity.ok(new CategoryResponse(category.getName(), category.getDescription()));
    }

}
