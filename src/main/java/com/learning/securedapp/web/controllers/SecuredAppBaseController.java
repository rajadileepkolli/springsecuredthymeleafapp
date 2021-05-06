package com.learning.securedapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Abstract SecuredAppBaseController class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public abstract class SecuredAppBaseController {

    @Autowired protected MessageSource messageSource;

    /**
     * getMessage.
     *
     * @param code a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, null);
    }

    /**
     * getCurrentUser.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
