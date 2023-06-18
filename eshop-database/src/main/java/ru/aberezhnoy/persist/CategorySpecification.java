package ru.aberezhnoy.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.aberezhnoy.persist.model.Category;
import ru.aberezhnoy.persist.model.User;

public class CategorySpecification {

    public static Specification<Category> categoryPrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }
}
