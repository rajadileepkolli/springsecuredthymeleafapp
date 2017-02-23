package com.learning.securedapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecurityService;

@Controller
/**
 * <p>HomeController class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class HomeController extends SecuredAppBaseController {
	@Autowired
	SecurityService securityService;

	/**
	 * <p>myAccount.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping("/myAccount")
	public String myAccount(Model model) {
		String userName = getCurrentUser();
		User user = securityService.getUserByUserName(userName);
		return "redirect:users/" + user.getId();
	}
}
