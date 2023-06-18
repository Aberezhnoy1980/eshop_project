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
import ru.aberezhnoy.controller.dto.BrandDto;
import ru.aberezhnoy.controller.dto.BrandListParams;
import ru.aberezhnoy.controller.dto.CategoryDto;
import ru.aberezhnoy.persist.model.Brand;
import ru.aberezhnoy.service.BrandService;
import ru.aberezhnoy.service.CategoryService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String listPage(Model model, BrandListParams brandListParams) {
        model.addAttribute("brands", brandService.findWithFilter(brandListParams));
        return "brands";
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("activePage", "Brand");
    }

    @GetMapping("/new")
    public String newBrandForm(Model model) {
        logger.info("New brand page requested");

        model.addAttribute("brand", new BrandDto());
        return "brand_form";
    }

    @GetMapping("/{id}")
    public String editBrand(@PathVariable("id") Long id, Model model) {
        logger.info("Edit brand page requested");

        model.addAttribute("brand", brandService.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found")));
        return "brand_form";
    }


    @PostMapping
    public String update(@Valid @ModelAttribute("brand") BrandDto brand, BindingResult result) {
        logger.info("Saving brand");

        if (result.hasErrors()) {
            return "brand_form";
        }
        brandService.save(brand);
        return "redirect:/brand";
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable("id") Long id) {
        logger.info("Deleting brand with id {}", id);

        brandService.deleteById(id);
        return "redirect:/brand";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
