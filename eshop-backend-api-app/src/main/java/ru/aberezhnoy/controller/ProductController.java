package ru.aberezhnoy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.aberezhnoy.aspect.TrackTime;
import ru.aberezhnoy.dto.BrandDto;
import ru.aberezhnoy.dto.CategoryDto;
import ru.aberezhnoy.dto.ProductDto;
import ru.aberezhnoy.exception.ResourceNotFoundException;
import ru.aberezhnoy.service.CategoryService;
import ru.aberezhnoy.service.ProductService;
import ru.aberezhnoy.service.BrandService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Tag(name = "Product", description = "Service to get product information")
@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final BrandService brandService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @TrackTime
    @GetMapping("/all")
    public Page<ProductDto> findAll(@RequestParam("categoryId") Optional<Long> categoryId,
                                    @RequestParam("categoryName") Optional<String> categoryPrefix,
                                    @RequestParam("brandId") Optional<Long> brandId,
                                    @RequestParam("brandName") Optional<String> brandPrefix,
                                    @RequestParam("namePattern") Optional<String> namePattern,
                                    @RequestParam("minPrice") Optional<BigDecimal> minPrice,
                                    @RequestParam("maxPrice") Optional<BigDecimal> maxPrice,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("sortField") Optional<String> sortField) {
        return productService.findAll(
                categoryId,
                categoryPrefix,
                brandId,
                brandPrefix,
                namePattern,
                minPrice,
                maxPrice,
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id"));
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Product with id = " + id + " not found")
        );
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories(@RequestParam("categoryPrefix") Optional<String> categoryPrefix) {
        return categoryService.findAll(categoryPrefix);
    }

    @GetMapping("/brands")
    public List<BrandDto> findAllBrands(@RequestParam("brandPrefix") Optional<String> brandPrefix) {
        return brandService.findAll(brandPrefix);
    }
}
