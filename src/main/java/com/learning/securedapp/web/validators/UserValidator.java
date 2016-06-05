package com.learning.securedapp.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserValidator implements Validator {

    @Autowired
    protected SecurityService securityService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.debug("Validating {}", target);
        User user = (User) target;
        validatePasswords(errors, user);
        validateEmail(errors, user);
    }

    private void validateEmail(Errors errors, User user) {
        String email = user.getEmail();
        User userByEmail = securityService.findUserByEmail(email);
        if(userByEmail != null){
            errors.rejectValue("email", "error.exists", new Object[]{email}, "Email "+email+" already in use");
        }
    }

    private void validatePasswords(Errors errors, User user) {
        if (!user.getPassword().equals(user.getPasswordRepeated())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

}
