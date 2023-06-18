package ru.aberezhnoy.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.aberezhnoy.persist.model.Brand;

public class BrandSpecification {

    public static Specification<Brand> brandPrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }
}
