package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.configuration.AppConfigurationProperties;
import com.learning.securedapp.exception.SecuredAppException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author rajakolli
 * @version $Id: $Id
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender javaMailSender;
    private final AppConfigurationProperties appConfigurationProperties;

    /**
     * sendEmail.
     *
     * @param to a {@link java.lang.String} object.
     * @param subject a {@link java.lang.String} object.
     * @param htmlContent a {@link java.lang.String} object.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    public void sendEmail(String to, String subject, String htmlContent)
            throws SecuredAppException {
        try {
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom(appConfigurationProperties.getSupportEmail());
            message.setTo(to);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
            log.debug("........");

            javaMailSender.send(message.getMimeMessage());
        } catch (MailException | MessagingException e) {
            log.error(e.getMessage());
            throw new SecuredAppException(e.getMessage());
        }
    }
}
