package ru.aberezhnoy.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.aberezhnoy.controller.dto.CategoryDto;

@Component
public class StringToCategoryDtoConverter implements Converter<String, CategoryDto> {

    @Override
    public CategoryDto convert(String id) {
        return new CategoryDto(Long.parseLong(id));
    }
}
