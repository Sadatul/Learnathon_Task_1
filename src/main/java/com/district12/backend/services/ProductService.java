package com.district12.backend.services;

import com.district12.backend.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProductService {
    Product findById(Long id);
    Page<Product> getAllProducts(Specification<Product> specification, Pageable pageable);
    Product addProduct(String name, String description, Double price, Integer stock, Long categoryId);
    void updateProduct(Long productId, String name, String description, Double price, Integer stock, Long categoryId);
    void deleteById(Long id);
    Specification<Product> getProductFilterSpecification(Integer priceStart, Integer priceEnd, Boolean inStock, Long categoryId);
}
