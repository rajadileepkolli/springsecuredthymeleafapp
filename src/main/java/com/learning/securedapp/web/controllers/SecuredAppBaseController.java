package com.learning.securedapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class SecuredAppBaseController {
    
    @Autowired protected MessageSource messageSource;
    
    protected abstract String getHeaderTitle();

    public String getMessage(String code)
    {
        return messageSource.getMessage(code, null, null);
    }

}
