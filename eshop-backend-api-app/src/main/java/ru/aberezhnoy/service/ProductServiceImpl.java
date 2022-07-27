package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.CategoryDto;
import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.persist.ProductRepository;
import ru.aberezhnoy.persist.ProductSpecification;
import ru.aberezhnoy.persist.model.Picture;
import ru.aberezhnoy.persist.model.Product;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<Long> brandId, Optional<String> namePattern,
                                    Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
        }
        if (brandId.isPresent() && brandId.get() != -1) {
            spec = spec.and(ProductSpecification.byBrand(brandId.get()));
        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecification.byName(namePattern.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                .map(this::toProductDto);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(this::toProductDto);
    }

    private ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(),
                product.getName(),
                new BrandDto(product.getBrand().getId(), product.getBrand().getName()),
                product.getDescription(),
                product.getPrice(),
                new CategoryDto(product.getCategory().getId(), product.getCategory().getName()),
                product.getPictures().stream().map(Picture::getId).collect(Collectors.toList()));
    }
}
