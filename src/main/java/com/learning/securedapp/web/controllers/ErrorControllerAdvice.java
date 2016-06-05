package com.learning.securedapp.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    private static final String viewPrefix = "error/";

    @ExceptionHandler(Throwable.class)
    public ModelAndView exception(Throwable throwable) {
        log.error("Exception during execution of SpringMail application", throwable);
        String errorMessage = throwable != null ? throwable.getMessage() : "Unknown error";
        ModelAndView mav = new ModelAndView();
        mav.getModel().put("errorMessage", errorMessage);
        mav.setViewName(viewPrefix + "accessDenied");
        return mav;
    }
}
