package com.learning.securedapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecurityService;

@Controller
public class HomeController extends SecuredAppBaseController
{
    @Autowired SecurityService securityService;
    
	@Override
	protected String getHeaderTitle() {
		return "Home";
	}
	
	@GetMapping(value={"/home"})
	public String home(Model model)
	{
		return "home";
	}
	
	@GetMapping(value={"/registration"})
    public String registration()
    {
        return "registration";
    }
	
	@GetMapping(value={"/successRegister"})
    public String successRegister()
    {
        return "successRegister";
    }
	
	@GetMapping("/login")
	public String login()
	{
	    return "login";
	}

	@GetMapping("/myAccount")
	public String myAccount(Model model){
        String userName = getCurrentUser();
        User user =securityService.getUserByUserName(userName);
        return "redirect:users/"+user.getId();
    }
}
