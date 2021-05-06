package com.learning.securedapp.web.controllers;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.web.services.CategoryService;
import com.learning.securedapp.web.validators.CategoryValidator;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
/**
 * CategoryController class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class CategoryController extends SecuredAppBaseController {

    private static final String viewPrefix = "category/";

    @Autowired private CategoryService categoryService;

    @Autowired private CategoryValidator categoryValidator;

    /**
     * listCategories.
     *
     * @param model a {@link org.springframework.ui.Model} object.
     * @return a {@link java.lang.String} object.
     */
    @GetMapping(value = "/categories.html")
    @Secured(value = "ROLE_CMS_ADMIN")
    public String listCategories(Model model) {
        List<Category> list = categoryService.getAllCategories();
        model.addAttribute("categories", list);
        return viewPrefix + "categories";
    }

    /**
     * newProduct.
     *
     * @param model a {@link org.springframework.ui.Model} object.
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/category/new")
    @Secured(value = "ROLE_CMS_ADMIN")
    public String newProduct(Model model) {
        model.addAttribute("category", new Category());
        return viewPrefix + "create_category";
    }

    /**
     * saveCategory.
     *
     * @param category a {@link com.learning.securedapp.domain.Category} object.
     * @param result a {@link org.springframework.validation.BindingResult} object.
     * @param model a {@link org.springframework.ui.Model} object.
     * @param redirectAttributes a {@link
     *     org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
     * @return a {@link java.lang.String} object.
     */
    @PostMapping(value = "/category/new")
    @Secured(value = "ROLE_CMS_ADMIN")
    public String saveCategory(
            @Valid @ModelAttribute("category") Category category,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        categoryValidator.validate(category, result);
        if (result.hasErrors()) {
            return viewPrefix + "create_category";
        }
        Category peristedCategory = categoryService.saveCategory(category);
        redirectAttributes.addFlashAttribute(
                "success",
                "Category " + peristedCategory.getCategoryName() + " Created successfully");
        return "redirect:/categories.html";
    }
}
