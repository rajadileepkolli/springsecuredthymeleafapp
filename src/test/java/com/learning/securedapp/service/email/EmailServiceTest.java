package com.learning.securedapp.service.email;

import static com.icegreen.greenmail.util.ServerSetupTest.SMTP;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.icegreen.greenmail.util.GreenMail;
import com.learning.securedapp.ApplicationTests;
import com.learning.securedapp.exception.SecuredAppException;

public class EmailServiceTest extends ApplicationTests
{

    @Autowired
    private EmailService emailService;

    private GreenMail testSmtp;

    @Before
    public void testSmtpInit()
    {
        testSmtp = new GreenMail(SMTP);
        testSmtp.start();
    }

    @After
    public void cleanup()
    {
        testSmtp.stop();
    }

    @Test
    public final void testSendEmail() throws SecuredAppException
    {
        emailService.sendEmail("rajadileepkolli@gmail.com", "JCart - Test Mail",
                "This is a test email from JCart");
    }
}
