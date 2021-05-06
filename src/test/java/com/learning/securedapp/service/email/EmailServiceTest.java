package com.learning.securedapp.service.email;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.learning.securedapp.AbstractApplicationTests;
import com.learning.securedapp.exception.SecuredAppException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
public class EmailServiceTest extends AbstractApplicationTests {

    @Autowired private EmailService emailService;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @Test
    public final void testSendEmail() throws SecuredAppException {
        emailService.sendEmail(
                "rajadileepkolli@gmail.com",
                "SecuredThymeleafApp - Test Mail",
                "This is a test email from SecuredThymeleafApp");
    }
}
