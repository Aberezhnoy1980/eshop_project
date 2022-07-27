package ru.aberezhnoy.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.aberezhnoy.persist.model.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> byCategory(Long categoryId) {
        return (root, query, builder) -> builder.equal(root.get("category").get("id"), categoryId);
    }
    public static Specification<Product> byBrand(Long brandId) {
        return (root, query, builder) -> builder.equal(root.get("brand").get("id"), brandId);
    }

    public static Specification<Product> byName(String pattern) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + pattern + "%");
    }

    public static Specification<Product> minPrice(BigDecimal minPrice) {
        return (root, query, builder) -> builder.ge(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice) {
        return (root, query, builder) -> builder.le(root.get("price"), maxPrice);
    }
}
