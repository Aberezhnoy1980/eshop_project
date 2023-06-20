package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.aberezhnoy.dto.BrandDto;
import ru.aberezhnoy.dto.CategoryDto;
import ru.aberezhnoy.dto.ProductDto;
import ru.aberezhnoy.persist.ProductRepository;
import ru.aberezhnoy.persist.ProductSpecification;
import ru.aberezhnoy.persist.model.Picture;
import ru.aberezhnoy.persist.model.Product;

import java.math.BigDecimal;
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
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> categoryPrefix,
                                    Optional<Long> brandId, Optional<String> brandPrefix,
                                    Optional<String> namePattern,
                                    Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice,
                                    Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (categoryPrefix.isPresent()) {
            spec = spec.and(ProductSpecification.byCategoryName(categoryPrefix.get()));
        }
        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
        }
        if (brandPrefix.isPresent()) {
            spec = spec.and(ProductSpecification.byBrandName(brandPrefix.get()));
        }
        if (brandId.isPresent() && brandId.get() != -1) {
            spec = spec.and(ProductSpecification.byBrand(brandId.get()));
        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecification.byName(namePattern.get()));
        }
        if (minPrice.isPresent()) {
            spec = spec.and(ProductSpecification.minPrice(minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            spec = spec.and(ProductSpecification.maxPrice(maxPrice.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                .map(this::toProductDto);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductServiceImpl.this::toProductDto);
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
