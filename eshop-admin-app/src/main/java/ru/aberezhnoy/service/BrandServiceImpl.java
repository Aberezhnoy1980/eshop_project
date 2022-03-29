package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.CategoryDto;
import ru.aberezhnoy.persist.BrandRepository;
import ru.aberezhnoy.persist.CategoryRepository;
import ru.aberezhnoy.persist.model.Brand;
import ru.aberezhnoy.persist.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<BrandDto> findAll(Integer page, Integer size, String sortField) {
        return brandRepository.findAll(PageRequest.of(page, size, Sort.by(sortField)))
                .map(brand -> new BrandDto(brand.getId(), brand.getName()));
    }

    @Override
    public Optional<BrandDto> findById(Long id) {
        return brandRepository.findById(id)
                .map(brand -> new BrandDto(brand.getId(), brand.getName()));
    }

    @Override
    public void save(BrandDto brandDto) {
        Brand brand = new Brand(brandDto.getId(), brandDto.getName());
        brandRepository.save(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }
}
