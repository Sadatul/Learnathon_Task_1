package com.district12.backend.controllers;

import com.district12.backend.dtos.CategoryResponse;
import com.district12.backend.dtos.ProductRequest;
import com.district12.backend.dtos.ProductResponse;
import com.district12.backend.entities.Product;
import com.district12.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        ProductResponse response = new ProductResponse(product.getName(), product.getDescription(), product.getPrice(), product.getStock(),
                new CategoryResponse(product.getCategory().getName(), product.getCategory().getDescription()));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/add")
//    @PreAuthorize("hasAuthority(SCOPE_ADMIN)")
    public ResponseEntity<Void> addProduct(@RequestBody ProductRequest request) {
        Long id = productService.addProduct(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.categoryId()
                ).getId();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/update/{productId}")
//    @PreAuthorize("hasAuthority(SCOPE_ADMIN)")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductRequest request, @PathVariable Long productId) {
        productService.updateProduct(
                productId,
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.categoryId()
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority(SCOPE_ADMIN")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
