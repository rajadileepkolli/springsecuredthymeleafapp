/**
 * 
 */
package com.learning.securedapp.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.securedapp.web.utils.WebUtils;

@Controller
public class ErrorController {
    private static final String viewPrefix = "error/";

    @RequestMapping("/403")
    public String accessDenied() {
        return viewPrefix + "accessDenied";
    }

    @RequestMapping("/*")
    public String error(HttpServletRequest request, Model model) {
        String requestURL = WebUtils.getURLWithContextPath(request)+request.getRequestURI();
        model.addAttribute("errorMessage", "No Mapping Found for "+ requestURL);
        return viewPrefix + "accessDenied";
    }

}
