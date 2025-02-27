package ru.aberezhnoy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aberezhnoy.NotFoundException;
import ru.aberezhnoy.controller.dto.ProductDto;
import ru.aberezhnoy.controller.dto.ProductListParams;
import ru.aberezhnoy.service.BrandService;
import ru.aberezhnoy.service.CategoryService;
import ru.aberezhnoy.service.ProductService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final CategoryService categoryService;

    private final BrandService brandService;

    @Autowired
    public ProductController(ProductService productService,
                              CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String listPage(
            ProductListParams productListParams,
            @RequestParam("categoryId") Optional<Long> categoryId,
            @RequestParam("brandId") Optional<Long> brandId,
            @RequestParam("namePattern") Optional<String> namePattern,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("sortField") Optional<String> sortField,
            Model model
    ) {
        model.addAttribute("products", productService.findWithFilter(productListParams));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("products", productService.findAll(
                categoryId,
                brandId,
                namePattern,
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id")));
        return "products";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("activePage", "Product");
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Edit product page requested");

        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "product_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("product") ProductDto product, BindingResult result, Model model) {
        logger.info("Saving product");

        if (result.hasErrors()) {
            logger.error(result.getAllErrors().toString());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            return "product_form";
        }
        productService.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        logger.info("Deleting product with id {}", id);

        productService.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
