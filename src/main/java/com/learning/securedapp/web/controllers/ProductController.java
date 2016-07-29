package com.learning.securedapp.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.domain.Product;
import com.learning.securedapp.web.domain.AddToCartForm;
import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.OrderLine;
import com.learning.securedapp.web.services.CategoryService;
import com.learning.securedapp.web.services.ProductService;

@Controller
public class ProductController extends SecuredAppBaseController {

	private static final String viewPrefix = "products/";

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	Cart cart;

	@ModelAttribute("categoryList")
	public List<Category> categoryList() {
		return categoryService.getAllCategories();
	}

	@ModelAttribute
	AddToCartForm addToCartForm() {
		return new AddToCartForm();
	}

	@GetMapping("product/new")
	public String newProduct(Model model) {
		model.addAttribute("product", Product.builder().build());
		return viewPrefix + "create_product";
	}

	@Secured(value = { "ROLE_ADMIN" })
	@PostMapping(value = "product")
	public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		boolean updated = true;
		if (product.getProductId().trim().length() == 0) {
			product.setProductId(null);
			updated = false;
		}
		Product persistedProduct = productService.saveProduct(product);
		if (updated) {
			redirectAttributes.addFlashAttribute("msg", persistedProduct.getProductName() + " updated successfully");
		} else {
			redirectAttributes.addFlashAttribute("success", product.getProductName() + " Created successfully");
		}

		return "redirect:/products";
	}

	// Read
	@Secured(value = { "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("product/{id}")
	public String showProduct(@PathVariable String id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return viewPrefix + "productshow";
	}

	@GetMapping(value = "products")
	public String showProducts(Model model) {
		model.addAttribute("products", productService.listAllProducts());
		return viewPrefix + "products";
	}

	// Update
	@GetMapping("product/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return viewPrefix + "create_product";
	}

	// Delete
	@GetMapping("product/delete/{id}")
	public String delete(@PathVariable String id) {
		productService.deleteProduct(id);
		return "redirect:/products";
	}

	@PostMapping(value = "/addToCart")
	String addToCart(@Validated AddToCartForm form, BindingResult result, @PageableDefault Pageable pageable,
			Model model) {
		if (result.hasErrors()) {
			// return showProducts(form.getCategoryId(), pageable, model);
			return "redirect:/products";
		}
		Product goods = productService.getProductById(form.getProductId());
		cart.add(OrderLine.builder().goods(goods).quantity(form.getQuantity()).build());
		return "redirect:/cart";
	}
}