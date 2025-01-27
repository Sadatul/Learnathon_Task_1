package com.district12.backend.services;

import com.district12.backend.entities.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);
    Category addCategory(String name, String description);
    void updateCategory(Long categoryId, String name, String description);
    void deleteById(Long categoryId);
    List<Category> getAllCategories();
}
