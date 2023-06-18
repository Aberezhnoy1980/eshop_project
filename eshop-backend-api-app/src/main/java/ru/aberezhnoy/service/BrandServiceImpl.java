package ru.aberezhnoy.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.dto.BrandDto;
import ru.aberezhnoy.persist.BrandRepository;
import ru.aberezhnoy.persist.BrandSpecification;
import ru.aberezhnoy.persist.model.Brand;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDto> findAll(Optional<String> prefix) {
        Specification<Brand> spec = Specification.where(null);
        if (prefix.isPresent()) {
            spec = spec.and(BrandSpecification.brandPrefix(prefix.get()));
        }
        return brandRepository
                .findAll(spec)
                .stream()
                .map(BrandDto::new)
                .collect(Collectors.toList());
    }

}
