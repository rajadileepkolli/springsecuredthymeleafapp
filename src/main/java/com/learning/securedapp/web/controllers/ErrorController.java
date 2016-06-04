/**
 * 
 */
package com.learning.securedapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    private static final String viewPrefix = "error/";

    @RequestMapping("/403")
    public String accessDenied() {
        return viewPrefix + "accessDenied";
    }

    @RequestMapping("/*")
    public String error(Model model) {
        model.addAttribute("errorMessage", "No Mapping Found");
        return viewPrefix + "accessDenied";
    }

}
