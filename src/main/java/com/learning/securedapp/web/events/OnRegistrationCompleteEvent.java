package com.learning.securedapp.web.events;

import com.learning.securedapp.domain.User;
import java.util.Locale;
import org.springframework.context.ApplicationEvent;

/**
 * OnRegistrationCompleteEvent class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private static final long serialVersionUID = -201370809535977542L;
    private User user;
    private Locale locale;
    private String appUrl;

    /**
     * Constructor for OnRegistrationCompleteEvent.
     *
     * @param user a {@link com.learning.securedapp.domain.User} object.
     * @param locale a {@link java.util.Locale} object.
     * @param appUrl a {@link java.lang.String} object.
     */
    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    /**
     * Getter for the field <code>user</code>.
     *
     * @return a {@link com.learning.securedapp.domain.User} object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the field <code>user</code>.
     *
     * @param user a {@link com.learning.securedapp.domain.User} object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter for the field <code>locale</code>.
     *
     * @return a {@link java.util.Locale} object.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Setter for the field <code>locale</code>.
     *
     * @param locale a {@link java.util.Locale} object.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Getter for the field <code>appUrl</code>.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAppUrl() {
        return appUrl;
    }

    /**
     * Setter for the field <code>appUrl</code>.
     *
     * @param appUrl a {@link java.lang.String} object.
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
