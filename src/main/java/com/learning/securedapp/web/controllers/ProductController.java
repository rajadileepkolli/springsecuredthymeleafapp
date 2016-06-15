package com.learning.securedapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.securedapp.domain.Product;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.services.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProductController extends SecuredAppBaseController {

    private static final String viewPrefix = "products/";

    @Autowired private ProductService productService;

    @GetMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return viewPrefix + "create_product";
    }

    @Secured(value = { "ROLE_ADMIN" })
    @PostMapping(value = "/product")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/product/" + product.getProductId();
    }

    // Read
    @Secured(value = { "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("product/{id}")
    public String showProduct(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return viewPrefix + "productshow";
    }

    @GetMapping(value = "products")
    public String list(Model model) {
        model.addAttribute("products", productService.listAllProducts());
        return viewPrefix + "products";
    }

    // Update
    @GetMapping("product/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return viewPrefix + "create_product";
    }
    
    @PostMapping(value = "/product/{id}")
    public String updateProduct(@ModelAttribute("products") Product product, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) throws SecuredAppException{
        if (result.hasErrors()) {
            return viewPrefix + "productshow";
        }
        Product persistedProduct = productService.updateProduct(product);
        log.debug("Updated Product with id : {} ", persistedProduct.getProductId());
        redirectAttributes.addFlashAttribute("msg", persistedProduct.getProductId() +" updated successfully");
        return "redirect:/products";
    }

    // Delete
    @GetMapping("product/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @Override
    protected String getHeaderTitle() {
        return "Products";
    }
}
