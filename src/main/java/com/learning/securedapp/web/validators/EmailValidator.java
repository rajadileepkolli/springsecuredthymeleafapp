package com.learning.securedapp.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /** {@inheritDoc} */
    @Override
    public void initialize(final ValidEmail constraintAnnotation) {
        // Nothing to do
    }

    /** {@inheritDoc} */
    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return validateEmail(username);
    }

    private boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
