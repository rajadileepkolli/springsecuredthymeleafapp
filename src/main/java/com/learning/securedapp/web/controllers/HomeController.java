package com.learning.securedapp.web.controllers;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecuredAppBaseService;
import com.learning.securedapp.web.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SecurityService securityService;
    private final SecuredAppBaseService securedAppBaseService;

    /**
     * myAccount.
     *
     * @param model a {@link org.springframework.ui.Model} object.
     * @return a {@link java.lang.String} object.
     */
    @GetMapping("/myAccount")
    public String myAccount(Model model) {
        String userName = securedAppBaseService.getCurrentUser();
        User user = securityService.getUserByUserName(userName);
        return "redirect:users/" + user.getId();
    }
}
