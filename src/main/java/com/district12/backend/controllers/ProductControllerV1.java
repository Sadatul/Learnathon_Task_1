package com.district12.backend.controllers;

import com.district12.backend.dtos.*;
import com.district12.backend.entities.Product;
import com.district12.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductControllerV1 {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PagedModel<ProductResponse>> getAllProducts(
            ProductFilterRequestBody filterRequestBody,
            @Valid PaginationRequest paginationRequest,
            @RequestParam(required = false, defaultValue = "NAME") Product.SortCategory sort
            ) {
        Pageable pageable = paginationRequest.getPageable(sort);
        Specification<Product> specification = productService.getProductFilterSpecification(
                filterRequestBody.priceStart(), filterRequestBody.priceEnd(),
                filterRequestBody.inStock(), filterRequestBody.categoryId());
        Page<Product> productPage = productService.getAllProducts(specification, pageable);

        List<ProductResponse> response = productPage.getContent().stream().map(product ->
                new ProductResponse(product.getName(), product.getDescription(), product.getPrice(), product.getStock(),
                        new CategoryResponse(product.getCategory().getName(), product.getCategory().getDescription())
                        )
                ).toList();
        Page<ProductResponse> res = new PageImpl<>(response, pageable, productPage.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(res));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        ProductResponse response = new ProductResponse(product.getName(), product.getDescription(), product.getPrice(), product.getStock(),
                new CategoryResponse(product.getCategory().getName(), product.getCategory().getDescription()));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
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

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.district12.backend.enums.Role).ADMIN.value)")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
