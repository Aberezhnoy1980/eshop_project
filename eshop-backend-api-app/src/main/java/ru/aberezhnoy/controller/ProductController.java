package ru.aberezhnoy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aberezhnoy.aspect.TrackTime;
import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.service.ProductService;

import java.util.Optional;

@Tag(name = "Product", description = "Service to get product information")
@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @TrackTime
    @GetMapping("/all")
    public Page<ProductDto> findAll(@RequestParam("categoryId") Optional<Long> categoryId,
                                    @RequestParam("brandId") Optional<Long> brandId,
                                    @RequestParam("namePattern") Optional<String> namePattern,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("sortField") Optional<String> sortField) {
        return productService.findAll(
                categoryId,
                brandId,
                namePattern,
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id"));
    }
}
