package com.learning.securedapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.securedapp.exception.EmptyCartOrderException;
import com.learning.securedapp.exception.InvalidCartOrderException;
import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.Order;
import com.learning.securedapp.web.services.OrderService;
import com.learning.securedapp.web.services.SecurityService;

@Controller
/**
 * <p>OrderController class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@RequestMapping("order")
public class OrderController extends SecuredAppBaseController {

	@Autowired
	OrderService orderService;
	@Autowired
	SecurityService userService;
	@Autowired
	Cart cart;

	@RequestMapping(method = RequestMethod.GET, params = "confirm")
	String confirm(Model model) {
		model.addAttribute("orderLines", cart.getOrderLines());
		if (cart.isEmpty()) {
			model.addAttribute("error", "Cart Cannot be empty");
			return "products/viewCart";
		}
		model.addAttribute("account", userService.getUserByUserName(getCurrentUser()));
		model.addAttribute("signature", orderService.calcSignature(cart));
		return "order/orderConfirm";
	}

	@RequestMapping(method = RequestMethod.POST)
	String order(@RequestParam String signature, RedirectAttributes attributes) {
		Order order = orderService.purchase(userService.getUserByUserName(getCurrentUser()).getEmail(), cart,
				signature);
		attributes.addFlashAttribute(order);
		return "redirect:/order?finish";
	}

	@GetMapping(params = "finish")
	String finish() {
		return "order/orderFinish";
	}

	@ExceptionHandler({ EmptyCartOrderException.class, InvalidCartOrderException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	ModelAndView handleOrderException(RuntimeException e) {
		return new ModelAndView("order/error").addObject("error", e.getMessage());
	}
}
