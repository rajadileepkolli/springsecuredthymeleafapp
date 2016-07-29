package com.learning.securedapp.web.controllers;

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

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.web.services.CategoryService;
import com.learning.securedapp.web.validators.CategoryValidator;

@Controller
public class CategoryController extends SecuredAppBaseController {

	private static final String viewPrefix = "category/";

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryValidator categoryValidator;

	@GetMapping(value = "/categories.html")
	@Secured(value = "ROLE_CMS_ADMIN")
	public String listCategories(Model model) {
		List<Category> list = categoryService.getAllCategories();
		model.addAttribute("categories", list);
		return viewPrefix + "categories";
	}

	@GetMapping("/category/new")
	@Secured(value = "ROLE_CMS_ADMIN")
	public String newProduct(Model model) {
		model.addAttribute("category", new Category());
		return viewPrefix + "create_category";
	}

	@PostMapping(value = "/category/new")
	@Secured(value = "ROLE_CMS_ADMIN")
	public String saveCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		categoryValidator.validate(category, result);
		if (result.hasErrors()) {
			return viewPrefix + "create_category";
		}
		Category peristedCategory = categoryService.saveCategory(category);
		redirectAttributes.addFlashAttribute("success",
				"Category " + peristedCategory.getCategoryName() + " Created successfully");
		return "redirect:/categories.html";
	}

}