package com.learning.securedapp.web.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.learning.securedapp.exception.SecuredAppException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

	private static final String viewPrefix = "error/";

	@ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class })
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a non empty string");
	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView exception(Throwable throwable) {
		log.error("Exception during execution of SpringMail application", throwable);
		String errorMessage = throwable != null ? throwable.getMessage() : "Unknown error";
		ModelAndView mav = new ModelAndView();
		mav.getModel().put("errorMessage", errorMessage);
		mav.setViewName(viewPrefix + "accessDenied");
		return mav;
	}

	@ExceptionHandler({ SecuredAppException.class, Exception.class })
	public ModelAndView handleError(HttpServletRequest req, Exception exception) throws Exception {

		// Rethrow annotated exceptions or they will be processed here instead.
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
			throw exception;

		log.error("Request: " + req.getRequestURI() + " raised " + exception);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("status", 500);

		mav.setViewName("error");
		return mav;
	}
}
