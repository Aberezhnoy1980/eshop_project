package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aberezhnoy.persist.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
