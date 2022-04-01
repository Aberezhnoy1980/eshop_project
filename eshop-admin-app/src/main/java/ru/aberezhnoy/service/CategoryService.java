package ru.aberezhnoy.service;

import org.springframework.data.domain.Page;
import ru.aberezhnoy.controller.dto.CategoryDto;
import ru.aberezhnoy.controller.dto.CategoryListParams;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();

    Page<CategoryDto> findWithFilter(CategoryListParams categoryListParams);

    Optional<CategoryDto> findById(Long id);

    void save(CategoryDto categoryDto);

    void deleteById(Long id);

}
