package ru.aberezhnoy.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import ru.aberezhnoy.persist.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findAll(@Nullable Specification<Product> spec, Pageable pageable);

    List<Product> findProductByNameLike(String name);

    List<Product> findAllByNameLike(String pattern);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("select p " +
            "from Product p " +
            "where (p.name like :pattern or :pattern is null) and " +
            " (p.price >= :minPrice or :minPrice is null) and " +
            " (p.price <= :maxPrice or :maxPrice is null)")
    List<Product> findByFilter(@Param("pattern") String pattern,
                               @Param("minPrice") BigDecimal minPrice,
                               @Param("maxPrice") BigDecimal maxPrice);
}
