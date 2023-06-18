package ru.aberezhnoy.service;

import org.springframework.data.domain.Page;
import ru.aberezhnoy.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> categoryPrefix, Optional<Long> brandId, Optional<String> brandPrefix, Optional<String> namePattern,
                             Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);
}
