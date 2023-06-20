package ru.aberezhnoy.persist;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import ru.aberezhnoy.persist.model.Brand;
import ru.aberezhnoy.persist.model.User;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    List<Brand> findAll(@Nullable Specification<Brand> prefix);
}
