package com.learning.securedapp.service.email;

import static com.icegreen.greenmail.util.ServerSetupTest.SMTP;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.learning.securedapp.exception.SecuredAppException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EmailServiceTest
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
