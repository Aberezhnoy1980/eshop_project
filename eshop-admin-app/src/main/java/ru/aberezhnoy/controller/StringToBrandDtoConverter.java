package ru.aberezhnoy.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.CategoryDto;

@Component
public class StringToBrandDtoConverter implements Converter<String, BrandDto> {

    @Override
    public BrandDto convert(String id) {
        return new BrandDto(Long.parseLong(id));
    }
}
