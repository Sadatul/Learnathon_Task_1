package com.district12.backend.services;

import com.district12.backend.entities.Category;
import com.district12.backend.entities.Product;
import com.district12.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for the given user id"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(String name, String description, Double price, Integer stock, Long categoryId) {
        Category category = new Category(categoryId);
        Product product = new Product(name, description, price, stock, category);
        return productRepository.save(product);
    }

    @Override
    public void updateProduct(Long productId, String name, String description, Double price, Integer stock, Long categoryId) {
        Category category = categoryService.findById(categoryId);
        Product product = findById(productId);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
