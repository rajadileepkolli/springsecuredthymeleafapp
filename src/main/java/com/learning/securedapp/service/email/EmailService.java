package com.learning.securedapp.service.email;

import com.learning.securedapp.exception.SecuredAppException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${support.email}")
    String supportEmail;

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
            message.setFrom(supportEmail);
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
