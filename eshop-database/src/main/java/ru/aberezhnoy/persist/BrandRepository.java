package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aberezhnoy.persist.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
