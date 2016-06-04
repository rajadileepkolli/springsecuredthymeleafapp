package com.learning.securedapp.service.email;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.securedapp.ApplicationTests;
import com.learning.securedapp.exception.SecuredAppException;

public class EmailServiceTest extends ApplicationTests{

    @Autowired private EmailService emailService;
    
    @Test
    public final void testSendEmail() throws SecuredAppException {
        emailService.sendEmail("rajadileepkolli@gmail.com", "JCart - Test Mail", "This is a test email from JCart");
    }   
}
