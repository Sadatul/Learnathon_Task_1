package com.district12.backend.specifications;

import com.district12.backend.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> withPrice(Integer startPrice, Integer endPrice) {
        return (root, query, cb) -> {
            if (startPrice != null && endPrice != null) {
                return cb.between(root.get("price"), startPrice, endPrice);
            }
            else if(startPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), startPrice);
            }
            else if(endPrice != null) {
                return cb.lessThanOrEqualTo(root.get("price"), endPrice);
            }
            return null;
        };
    }

    public static Specification<Product> withStock(Boolean inStock) {
        return (root, query, cb) -> {
            if (inStock != null) {
                if (!inStock) {
                    return cb.equal(root.get("stock"), 0);
                }
                return cb.greaterThan(root.get("stock"), 0);
            }
            return null;
        };
    }

    public static Specification<Product> withCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId != null) {
                return cb.equal(root.get("category").get("id"), categoryId);
            }
            return null;
        };
    }
}
