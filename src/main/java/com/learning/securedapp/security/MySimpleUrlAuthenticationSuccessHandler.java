package com.learning.securedapp.security;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * MySimpleUrlAuthenticationSuccessHandler class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Component("myAuthenticationSuccessHandler")
@Slf4j
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /** {@inheritDoc} */
    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException {
        // handle(request, response, authentication);
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(30 * 60);
        }
        clearAuthenticationAttributes(request);
    }

    /**
     * handle.
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @param response a {@link javax.servlet.http.HttpServletResponse} object.
     * @param authentication a {@link org.springframework.security.core.Authentication} object.
     * @throws java.io.IOException if any.
     */
    protected void handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException {
        final String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * determineTargetUrl.
     *
     * @param authentication a {@link org.springframework.security.core.Authentication} object.
     * @return a {@link java.lang.String} object.
     */
    protected String determineTargetUrl(final Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if ("ROLE_USER".equals(grantedAuthority.getAuthority())) {
                isUser = true;
            } else if ("ROLE_ADMIN".equals(grantedAuthority.getAuthority())) {
                isAdmin = true;
                isUser = false;
                break;
            }
        }
        if (isUser) {
            return "/homepage.html?user=" + authentication.getName();
        } else if (isAdmin) {
            return "/home.html";
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * clearAuthenticationAttributes.
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     */
    protected void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    /**
     * Setter for the field <code>redirectStrategy</code>.
     *
     * @param redirectStrategy a {@link org.springframework.security.web.RedirectStrategy} object.
     */
    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    /**
     * Getter for the field <code>redirectStrategy</code>.
     *
     * @return a {@link org.springframework.security.web.RedirectStrategy} object.
     */
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
