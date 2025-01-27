package com.district12.backend.services;

import com.district12.backend.entities.Product;

public interface ProductService {
    Product findById(Long id);
    List<Product> getAllProducts();
    Product addProduct(String name, String description, Double price, Integer stock, Long categoryId);
    void updateProduct(Long productId, String name, String description, Double price, Integer stock, Long categoryId);
    void deleteById(Long id);
}
