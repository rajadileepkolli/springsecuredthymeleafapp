package com.learning.securedapp.service.email;

import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
    
    @Autowired
    JavaMailSender javaMailSender;
    
    @Autowired 
    private TemplateEngine templateEngine;

    @Value("${support.email}")
    String supportEmail;

    public void sendEmail(String to, String subject, String content, Locale locale) {
        try {
            // Prepare message using a Spring helper
            final Context ctx = new Context(locale);
            ctx.setVariable("subscriptionDate", new Date());
            ctx.setVariable("content", content);
            ctx.setVariable("name", "Admin");
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom(supportEmail);
            message.setTo(to);
            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine.process("email-simple.html", ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
            System.out.println("........");

            javaMailSender.send(message.getMimeMessage());
        } catch (MailException | MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
