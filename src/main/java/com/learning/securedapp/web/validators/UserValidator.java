package com.learning.securedapp.web.validators;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final SecurityService securityService;

    /** {@inheritDoc} */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        log.debug("Validating {}", user);
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "password", "message.password", "Password is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "userName", "message.username", "UserName is required.");
        validateEmail(errors, user);
    }

    private void validateEmail(Errors errors, User user) {
        String email = user.getEmail();
        if (StringUtils.isNotBlank(email)) {
            User userByEmail = securityService.findUserByEmail(email);
            if (userByEmail != null) {
                errors.rejectValue(
                        "email",
                        "error.exists",
                        new Object[] {email},
                        "Email " + email + " already in use");
            }
        } else {
            errors.rejectValue(
                    "email", "error.emailnull", new Object[] {email}, "Email cannot be null");
        }
    }
}
