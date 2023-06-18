package ru.aberezhnoy.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.aberezhnoy.persist.model.Brand;
import ru.aberezhnoy.persist.model.User;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
}
