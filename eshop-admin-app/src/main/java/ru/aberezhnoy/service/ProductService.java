package ru.aberezhnoy.service;

import org.springframework.data.domain.Page;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.BrandListParams;
import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.controller.dto.ProductListParams;

import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Optional<Long> categoryId, Optional<Long> brandId, Optional<String> namePattern,
        Integer page, Integer size, String sortField);

    Page<ProductDto> findWithFilter(ProductListParams productListParams);

    Optional<ProductDto> findById(Long id);

    void save(ProductDto productDto);

    void deleteById(Long id);
}
