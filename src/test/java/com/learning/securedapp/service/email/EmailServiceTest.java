package com.learning.securedapp.service.email;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.learning.securedapp.AbstractApplicationTest;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.services.impl.EmailServiceImpl;
import java.util.concurrent.TimeUnit;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
class EmailServiceTest extends AbstractApplicationTest {

    @RegisterExtension
    static GreenMailExtension greenMail =
            new GreenMailExtension(ServerSetupTest.SMTP)
                    .withConfiguration(
                            GreenMailConfiguration.aConfig().withUser("spring", "springboot"))
                    .withPerMethodLifecycle(false);

    @Autowired private EmailServiceImpl emailService;

    @Test
    void testSendEmail() throws SecuredAppException {
        emailService.sendEmail(
                "rajadileepkolli@gmail.com",
                "SecuredThymeleafApp - Test Mail",
                "This is a test email from SecuredThymeleafApp");

        await().atMost(2, TimeUnit.SECONDS)
                .untilAsserted(
                        () -> {
                            MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
                            assertEquals(1, receivedMessages.length);

                            MimeMessage receivedMessage = receivedMessages[0];
                            assertEquals("Hello World!", GreenMailUtil.getBody(receivedMessage));
                            assertEquals(1, receivedMessage.getAllRecipients().length);
                            assertEquals(
                                    "duke@spring.io",
                                    receivedMessage.getAllRecipients()[0].toString());
                        });
    }
}
