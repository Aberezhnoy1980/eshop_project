package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.dto.CategoryDto;
import ru.aberezhnoy.persist.CategoryRepository;
import ru.aberezhnoy.persist.CategorySpecification;
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
    public List<CategoryDto> findAll(Optional<String> prefix) {
        Specification<Category> spec = Specification.where(null);
        if (prefix.isPresent()) {
            spec = spec.and(CategorySpecification.categoryPrefix(prefix.get()));
        }
        return categoryRepository
                .findAll(spec)
                .stream()
                .map(CategoryDto::new)
                .collect(Collectors
                        .toList());
    }
}
