package ru.aberezhnoy.service;

import org.springframework.data.domain.Page;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.BrandListParams;
import ru.aberezhnoy.controller.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<BrandDto> findAll();

    Page<BrandDto> findWithFilter(BrandListParams brandListParams);

    Optional<BrandDto> findById(Long id);

    void save(BrandDto brandDto);

    void deleteById(Long id);
}
