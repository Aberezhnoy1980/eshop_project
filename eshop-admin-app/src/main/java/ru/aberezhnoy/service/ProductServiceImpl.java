package ru.aberezhnoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aberezhnoy.controller.NotFoundException;
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.CategoryDto;
import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.persist.BrandRepository;
import ru.aberezhnoy.persist.CategoryRepository;
import ru.aberezhnoy.persist.ProductRepository;
import ru.aberezhnoy.persist.ProductSpecification;
import ru.aberezhnoy.persist.model.Brand;
import ru.aberezhnoy.persist.model.Category;
import ru.aberezhnoy.persist.model.Picture;
import ru.aberezhnoy.persist.model.Product;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.pictureService = pictureService;
    }


    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<Long> brandId, Optional<String> namePattern, Integer page, Integer size, String sortField) {
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
                product.getPictures().stream()
                        .map(Picture::getId)
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("category not found"));
        Brand brand = brandRepository.findById(productDto.getBrand().getId())
                .orElseThrow(() -> new RuntimeException("brand not found"));

        product.setName(productDto.getName());
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        if (productDto.getNewPicture() != null) {
            for (MultipartFile newPicture : productDto.getNewPicture()) {
                try {
                    product.getPictures().add(new Picture(null,
                            newPicture.getOriginalFilename(),
                            newPicture.getContentType(),
                            pictureService.createPicture(newPicture.getBytes()),
                            product));
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }

        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
