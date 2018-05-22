package com.learning.securedapp.service.email;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.learning.securedapp.AbstractApplicationTests;
import com.learning.securedapp.exception.SecuredAppException;

public class EmailServiceTest extends AbstractApplicationTests {

    @Autowired
    private EmailService emailService;

    private GreenMail testSmtp;

    @Before
    public void testSmtpInit() {
        testSmtp = new GreenMail(ServerSetupTest.SMTPS);
        testSmtp.start();
    }

    @After
    public void cleanup() {
        testSmtp.stop();
    }

    @Test
    public final void testSendEmail() throws SecuredAppException {
        emailService.sendEmail("rajadileepkolli@gmail.com",
                "SecuredThymeleafApp - Test Mail",
                "This is a test email from SecuredThymeleafApp");
    }
}
