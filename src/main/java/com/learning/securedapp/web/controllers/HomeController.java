/**
 * 
 */
package com.learning.securedapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends SecuredAppBaseController
{	
	@Override
	protected String getHeaderTitle() {
		return "Home";
	}
	
	@GetMapping(value={"/home"})
	public String home(Model model)
	{
		return "home";
	}
	
	@GetMapping("/login")
	public String login()
	{
	    return "login";
	}

}
