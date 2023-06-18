package ru.aberezhnoy.service;

import org.springframework.stereotype.Service;
import ru.aberezhnoy.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    List<CategoryDto> findAll(Optional<String> prefix);
}
