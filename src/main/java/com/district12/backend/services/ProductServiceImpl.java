package com.district12.backend.services;

import com.district12.backend.entities.Category;
import com.district12.backend.entities.Product;
import com.district12.backend.repositories.ProductRepository;
import com.district12.backend.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for the given user id"));
    }

    public Page<Product> getAllProducts(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
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

    @Override
    public Specification<Product> getProductFilterSpecification(Integer priceStart, Integer priceEnd, Boolean inStock, Long categoryId) {
        return Specification.where(ProductSpecification.withPrice(priceStart, priceEnd))
                .and(ProductSpecification.withStock(inStock))
                .and(ProductSpecification.withCategory(categoryId));
    }
}
