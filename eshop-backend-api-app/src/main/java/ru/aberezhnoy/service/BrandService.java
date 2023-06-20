package ru.aberezhnoy.service;

import org.springframework.stereotype.Service;
import ru.aberezhnoy.dto.BrandDto;

import java.util.List;
import java.util.Optional;

@Service
public interface BrandService {

    List<BrandDto> findAll(Optional<String> prefix);
}
