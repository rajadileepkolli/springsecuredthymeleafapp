package com.learning.securedapp.web.services;

import com.learning.securedapp.exception.SecuredAppException;

public interface EmailService {

    void sendEmail(String to, String subject, String htmlContent) throws SecuredAppException;
}
