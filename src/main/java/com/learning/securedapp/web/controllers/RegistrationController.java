package com.learning.securedapp.web.controllers;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.events.OnRegistrationCompleteEvent;
import com.learning.securedapp.web.services.IUserService;
import com.learning.securedapp.web.services.SecurityService;
import com.learning.securedapp.web.utils.GenericResponse;
import com.learning.securedapp.web.utils.WebAppUtils;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * RegistrationController class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Slf4j
@AllArgsConstructor
public class RegistrationController {

    private SecurityService securityService;
    private ApplicationEventPublisher eventPublisher;
    private IUserService userService;
    private MessageSource messages;

    // Registration
    /**
     * registerUserAccount.
     *
     * @param accountDto a {@link com.learning.securedapp.domain.User} object.
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @return a {@link com.learning.securedapp.web.utils.GenericResponse} object.
     */
    @PostMapping(value = "/user/registration")
    public GenericResponse registerUserAccount(
            @Valid final User accountDto, final HttpServletRequest request) {
        log.debug("Registering user account with information: {}", accountDto);
        final User registered = securityService.createUser(accountDto, false);
        if (null == registered.getId()) {
            return new GenericResponse("UserAlreadyExist");
        }
        eventPublisher.publishEvent(
                new OnRegistrationCompleteEvent(
                        registered,
                        request.getLocale(),
                        WebAppUtils.getURLWithContextPath(request)));
        return new GenericResponse("success");
    }

    /**
     * confirmRegistration.
     *
     * @param locale a {@link java.util.Locale} object.
     * @param model a {@link org.springframework.ui.Model} object.
     * @param token a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    @GetMapping(value = "/registrationConfirm")
    public String confirmRegistration(
            Locale locale, Model model, @RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result == null) {
            model.addAttribute(
                    "message", messages.getMessage("message.accountVerified", null, locale));
            return "login";
        }
        if (result == "expired") {
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
        }
        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        return "redirect:/badUser?lang=" + locale.getLanguage();
    }
}
