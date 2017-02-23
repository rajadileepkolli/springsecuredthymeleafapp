package com.learning.securedapp.web.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.learning.securedapp.domain.User;

/**
 * <p>OnRegistrationCompleteEvent class.</p>
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
	 * <p>Constructor for OnRegistrationCompleteEvent.</p>
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
	 * <p>Getter for the field <code>user</code>.</p>
	 *
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * <p>Setter for the field <code>user</code>.</p>
	 *
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * <p>Getter for the field <code>locale</code>.</p>
	 *
	 * @return a {@link java.util.Locale} object.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * <p>Setter for the field <code>locale</code>.</p>
	 *
	 * @param locale a {@link java.util.Locale} object.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * <p>Getter for the field <code>appUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * <p>Setter for the field <code>appUrl</code>.</p>
	 *
	 * @param appUrl a {@link java.lang.String} object.
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

}
