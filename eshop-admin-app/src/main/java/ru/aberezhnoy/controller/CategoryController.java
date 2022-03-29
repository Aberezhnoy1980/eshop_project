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
import ru.aberezhnoy.service.CategoryService;
import ru.aberezhnoy.controller.dto.CategoryDto;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listPage(@RequestParam("Page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam("sortField") Optional<String> sortField, Model model) {
        model.addAttribute("categories", categoryService.findAll(page.orElse(1) - 1, size.orElse(5), sortField.filter(fld -> !fld.isBlank()).orElse("id")));
        return "categories";
    }

    @ModelAttribute
    public String newCategoryForm(Model model) {
        logger.info("New category page requested");

        model.addAttribute(("category"), new CategoryDto());
        return "category_form";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        logger.info("Edit category page requested");

        model.addAttribute("category", categoryService.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found")));
        return "category_form";
    }


    @PostMapping
    public String update(@Valid @ModelAttribute("category") CategoryDto category, BindingResult result) {
        logger.info("Saving category");

        if (result.hasErrors()) {
            return "category_form";
        }
        categoryService.save(category);
        return "redirect:/category";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        logger.info("Deleting category with id {}", id);

        categoryService.deleteById(id);
        return "redirect:/category";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
