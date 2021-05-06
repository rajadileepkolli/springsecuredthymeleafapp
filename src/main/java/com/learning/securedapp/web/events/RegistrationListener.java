package com.learning.securedapp.web.events;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.services.IUserService;
import com.learning.securedapp.web.services.impl.EmailServiceImpl;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final IUserService service;

    private final MessageSource messages;

    private final EmailServiceImpl emailService;

    private final TemplateEngine templateEngine;

    /** {@inheritDoc} */
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (SecuredAppException e) {
            log.error("Error while sending email", e);
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws SecuredAppException {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        emailService.sendEmail(
                user.getEmail(), "Registration Confirmation", getHtmlContent(event, token));
    }

    private String getHtmlContent(OnRegistrationCompleteEvent event, String token) {
        // Prepare the evaluation context
        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("name", event.getUser().getUserName());

        final String message = messages.getMessage("message.regSucc", null, event.getLocale());
        ctx.setVariable("message", message);

        final String confirmationUrl =
                event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        ctx.setVariable("url", confirmationUrl);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("registration-email", ctx);
        return htmlContent;
    }
}
