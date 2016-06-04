package com.learning.securedapp.web.controllers;

import static com.learning.securedapp.web.utils.MessageCodes.ERROR_INVALID_PASSWORD_RESET_REQUEST;
import static com.learning.securedapp.web.utils.MessageCodes.ERROR_PASSWORD_CONF_PASSWORD_MISMATCH;
import static com.learning.securedapp.web.utils.MessageCodes.INFO_PASSWORD_RESET_LINK_SENT;
import static com.learning.securedapp.web.utils.MessageCodes.INFO_PASSWORD_UPDATED_SUCCESS;
import static com.learning.securedapp.web.utils.MessageCodes.LABEL_PASSWORD_RESET_EMAIL_SUBJECT;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.service.email.EmailService;
import com.learning.securedapp.web.services.SecurityService;
import com.learning.securedapp.web.utils.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserAuthController extends SecuredAppBaseController {
    private static final String viewPrefix = "public/";

    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected EmailService emailService;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired 
    private TemplateEngine templateEngine;

    @Override
    protected String getHeaderTitle() {
        return "User";
    }

    @GetMapping(value = "/forgotPwd")
    public String forgotPwd() {
        return viewPrefix + "forgotPwd";
    }

    @PostMapping(value = "/forgotPwd")
    public String handleForgotPwd(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        try {
            String token = securityService.resetPassword(email);
            String resetPwdURL = WebUtils.getURLWithContextPath(request)
                    + "/resetPwd?email=" + email + "&token=" + token;
            log.debug(resetPwdURL);
            this.sendForgotPasswordEmail(email, resetPwdURL);
            redirectAttributes.addFlashAttribute("msg", getMessage(INFO_PASSWORD_RESET_LINK_SENT));
        } catch (SecuredAppException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/forgotPwd";
    }

    @GetMapping(value = "/resetPwd")
    public String resetPwd(HttpServletRequest request, Model model,
            RedirectAttributes redirectAttributes) throws SecuredAppException {
        String email = request.getParameter("email");
        String token = request.getParameter("token");

        boolean valid = securityService.verifyPasswordResetToken(email, token);
        if (valid) {
            model.addAttribute("email", email);
            model.addAttribute("token", token);
            return viewPrefix + "resetPwd";
        } else {
            redirectAttributes.addFlashAttribute("msg", getMessage(ERROR_INVALID_PASSWORD_RESET_REQUEST));
            return "redirect:/login";
        }

    }

    @PostMapping(value = "/resetPwd")
    public String handleResetPwd(HttpServletRequest request, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            String email = request.getParameter("email");
            String token = request.getParameter("token");
            String password = request.getParameter("password");
            String confPassword = request.getParameter("confPassword");
            if (!password.equals(confPassword)) {
                model.addAttribute("email", email);
                model.addAttribute("token", token);
                model.addAttribute("msg", getMessage(ERROR_PASSWORD_CONF_PASSWORD_MISMATCH));
                return viewPrefix + "resetPwd";
            }
            String encodedPwd = passwordEncoder.encode(password);
            securityService.updatePassword(email, token, encodedPwd);

            redirectAttributes.addFlashAttribute("msg", getMessage(INFO_PASSWORD_UPDATED_SUCCESS));
        } catch (SecuredAppException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg", getMessage(ERROR_INVALID_PASSWORD_RESET_REQUEST));
        }
        return "redirect:/login";
    }

    protected void sendForgotPasswordEmail(String email, String resetPwdURL) {
        try {

            // Prepare the evaluation context
            final Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("resetPwdURL", resetPwdURL);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine.process("forgot-password-email", ctx);

            emailService.sendEmail(email, getMessage(LABEL_PASSWORD_RESET_EMAIL_SUBJECT), htmlContent);
        } catch (SecuredAppException e) {
            log.error(e.getMessage());
        }
    }
}
