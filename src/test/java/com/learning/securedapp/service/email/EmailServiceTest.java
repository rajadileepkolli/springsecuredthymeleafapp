package com.learning.securedapp.service.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.learning.securedapp.AbstractApplicationTest;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.services.EmailService;
import java.util.concurrent.TimeUnit;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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

    @Autowired private EmailService emailService;

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
                            assertThat(receivedMessages.length).isEqualTo(1);

                            MimeMessage receivedMessage = receivedMessages[0];
                            assertThat(GreenMailUtil.getBody(receivedMessage))
                                    .isEqualTo("Hello World!");
                            assertThat(receivedMessage.getAllRecipients().length).isEqualTo(1);
                            assertThat(receivedMessage.getAllRecipients()[0].toString())
                                    .isEqualTo("rajadileepkolli@gmail.como");
                        });
    }
}
