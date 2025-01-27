package com.district12.backend.controllers;

import com.district12.backend.dtos.CategoryRequest;
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
    @PreAuthorize("hasAuthority(SCOPE_ADMIN)")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequest request) {
        Long id = categoryService.addCategory(request.name(), request.description()).getId();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @PostMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority(SCOPE_ADMIN)")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequest request, @RequestParam Long categoryId) {
        categoryService.updateCategory(categoryId, request.name(), request.description());
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority(SCOPE_ADMIN)")
    public ResponseEntity<Void> deleteCategory(@RequestParam Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

}
