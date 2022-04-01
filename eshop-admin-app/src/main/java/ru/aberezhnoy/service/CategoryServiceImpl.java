package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.BrandListParams;
import ru.aberezhnoy.controller.dto.CategoryDto;
import ru.aberezhnoy.controller.dto.CategoryListParams;
import ru.aberezhnoy.persist.BrandSpecification;
import ru.aberezhnoy.persist.CategoryRepository;
import ru.aberezhnoy.persist.CategorySpecification;
import ru.aberezhnoy.persist.model.Brand;
import ru.aberezhnoy.persist.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDto> findWithFilter(CategoryListParams categoryListParams) {
        Specification<Category> spec = Specification.where(null);

        if (categoryListParams.getCategoryNameFilter() != null && !categoryListParams.getCategoryNameFilter().isBlank()) {
            spec = spec.and(CategorySpecification.categoryPrefix(categoryListParams.getCategoryNameFilter()));
        }

        return categoryRepository.findAll(spec,
                        PageRequest.of(
                                Optional.ofNullable(categoryListParams.getPage()).orElse(1) - 1,
                                Optional.ofNullable(categoryListParams.getSize()).orElse(3),
                                Sort.by(Optional.ofNullable(categoryListParams.getSortField())
                                        .filter(c -> !c.isBlank())
                                        .orElse("id"))))
                .map(category -> new CategoryDto(category.getId(), category.getName()));
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDto(category.getId(), category.getName()));
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
