package com.learning.securedapp.web.controllers;

import static com.learning.securedapp.web.utils.MessageCodes.ERROR_INVALID_PASSWORD_RESET_REQUEST;
import static com.learning.securedapp.web.utils.MessageCodes.ERROR_PASSWORD_CONF_PASSWORD_MISMATCH;
import static com.learning.securedapp.web.utils.MessageCodes.INFO_PASSWORD_RESET_LINK_SENT;
import static com.learning.securedapp.web.utils.MessageCodes.INFO_PASSWORD_UPDATED_SUCCESS;
import static com.learning.securedapp.web.utils.MessageCodes.LABEL_PASSWORD_RESET_EMAIL_SUBJECT;

import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.services.SecuredAppBaseService;
import com.learning.securedapp.web.services.SecurityService;
import com.learning.securedapp.web.services.impl.EmailServiceImpl;
import com.learning.securedapp.web.utils.WebAppUtils;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserAuthController {
    private static final String viewPrefix = "public/";

    private final SecurityService securityService;
    private final EmailServiceImpl emailService;
    private final TemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;
    private final SecuredAppBaseService securedAppBaseService;

    /**
     * forgotPwd.
     *
     * @return a {@link java.lang.String} object.
     */
    @GetMapping(value = "/forgotPwd")
    public String forgotPwd() {
        return viewPrefix + "forgotPwd";
    }

    /**
     * handleForgotPwd.
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @param redirectAttributes a {@link
     *     org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
     * @return a {@link java.lang.String} object.
     */
    @PostMapping(value = "/forgotPwd")
    public String handleForgotPwd(
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        try {
            String token = securityService.resetPassword(email);
            String resetPwdURL =
                    WebAppUtils.getURLWithContextPath(request)
                            + "/resetPwd?email="
                            + email
                            + "&token="
                            + token;
            log.debug(resetPwdURL);
            this.sendForgotPasswordEmail(email, resetPwdURL);
            redirectAttributes.addFlashAttribute(
                    "success", securedAppBaseService.getMessage(INFO_PASSWORD_RESET_LINK_SENT));
        } catch (SecuredAppException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/forgotPwd";
    }

    /**
     * resetPwd.
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @param model a {@link org.springframework.ui.Model} object.
     * @param redirectAttributes a {@link
     *     org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
     * @return a {@link java.lang.String} object.
     */
    @GetMapping(value = "/resetPwd")
    public String resetPwd(
            HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String token = request.getParameter("token");

        try {
            boolean valid = securityService.verifyPasswordResetToken(email, token);
            if (valid) {
                model.addAttribute("email", email);
                model.addAttribute("token", token);
                return viewPrefix + "resetPwd";
            }
        } catch (SecuredAppException e) {
            redirectAttributes.addFlashAttribute(
                    "msg", securedAppBaseService.getMessage(ERROR_INVALID_PASSWORD_RESET_REQUEST));
            return "redirect:/login";
        }
        return null;
    }

    /**
     * handleResetPwd.
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @param model a {@link org.springframework.ui.Model} object.
     * @param redirectAttributes a {@link
     *     org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
     * @return a {@link java.lang.String} object.
     */
    @PostMapping(value = "/resetPwd")
    public String handleResetPwd(
            HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        try {
            String email = request.getParameter("email");
            String token = request.getParameter("token");
            String password = request.getParameter("password");
            String confPassword = request.getParameter("confPassword");
            if (!password.equals(confPassword)) {
                model.addAttribute("email", email);
                model.addAttribute("token", token);
                model.addAttribute(
                        "msg",
                        securedAppBaseService.getMessage(ERROR_PASSWORD_CONF_PASSWORD_MISMATCH));
                return viewPrefix + "resetPwd";
            }
            String encodedPwd = passwordEncoder.encode(password);
            securityService.updatePassword(email, token, encodedPwd);
            redirectAttributes.addFlashAttribute(
                    "success", securedAppBaseService.getMessage(INFO_PASSWORD_UPDATED_SUCCESS));
        } catch (SecuredAppException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "msg", securedAppBaseService.getMessage(ERROR_INVALID_PASSWORD_RESET_REQUEST));
        }
        return "redirect:/login";
    }

    /**
     * sendForgotPasswordEmail.
     *
     * @param email a {@link java.lang.String} object.
     * @param resetPwdURL a {@link java.lang.String} object.
     */
    protected void sendForgotPasswordEmail(String email, String resetPwdURL) {
        try {

            // Prepare the evaluation context
            final Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("resetPwdURL", resetPwdURL);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.templateEngine.process("forgot-password-email", ctx);

            emailService.sendEmail(
                    email,
                    securedAppBaseService.getMessage(LABEL_PASSWORD_RESET_EMAIL_SUBJECT),
                    htmlContent);
        } catch (SecuredAppException e) {
            log.error(e.getMessage());
        }
    }
}
