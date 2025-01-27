package com.district12.backend.controllers;

import com.district12.backend.dtos.CategoryResponse;
import com.district12.backend.dtos.ProductResponse;
import com.district12.backend.entities.Product;
import com.district12.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductControllerV1 {
    private final ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> response = products.stream().map(product ->
                new ProductResponse(product.getName(), product.getDescription(), product.getPrice(), product.getStock(),
                        new CategoryResponse(product.getCategory().getName(), product.getCategory().getDescription())
                        )
                ).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@RequestParam Long productId) {
        Product product = productService.findById(productId);
        ProductResponse response = new ProductResponse(product.getName(), product.getDescription(), product.getPrice(), product.getStock(),
                new CategoryResponse(product.getCategory().getName(), product.getCategory().getDescription()));

        return ResponseEntity.ok(response);
    }
}
