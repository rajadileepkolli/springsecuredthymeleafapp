package com.learning.securedapp.web.controllers;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.CartForm;

@Controller
@RequestMapping("/cart")
public class CartController extends SecuredAppBaseController {

	@Autowired
	Cart cart;

	@ModelAttribute
	CartForm setUpForm() {
		return new CartForm();
	}

	@GetMapping()
	String viewCart(Model model) {
		model.addAttribute("orderLines", cart.getOrderLines());
		return "products/viewCart";
	}

	@PostMapping()
	String removeFromCart(@Validated CartForm cartForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", getMessage("error.notChecked"));
			return viewCart(model);
		}
		cart.remove(cartForm.getLineNo());
		return "redirect:/cart";
	}

	/**
	 * <p>getCartItemCount.</p>
	 *
	 * @param request a {@link javax.servlet.http.HttpServletRequest} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.util.Map} object.
	 */
	@GetMapping(value = "/items/count")
	@ResponseBody
	public Map<String, Object> getCartItemCount(HttpServletRequest request, Model model) {
		Cart cart = getOrCreateCart(request);
		int itemCount = cart.getOrderLines().getList().size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", itemCount);
		return map;
	}

	private Cart getOrCreateCart(HttpServletRequest request) {
		Cart cart = null;
		cart = (Cart) request.getSession().getAttribute("CART_KEY");
		if (isNull(cart)) {
			cart = new Cart();
			request.getSession().setAttribute("CART_KEY", cart);
		}
		return cart;
	}

}
