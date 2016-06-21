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
    
	@GetMapping("/myAccount")
	public String myAccount(Model model){
        String userName = getCurrentUser();
        User user =securityService.getUserByUserName(userName);
        return "redirect:users/"+user.getId();
    }
}
