package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.web.services.SecuredAppBaseService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecuredAppBaseServiceImpl implements SecuredAppBaseService {

    private final MessageSource messageSource;

    /**
     * getMessage.
     *
     * @param code a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    /**
     * getCurrentUser.
     *
     * @return a {@link java.lang.String} object.
     */
    @Override
    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
