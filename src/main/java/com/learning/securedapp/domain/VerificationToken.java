package com.learning.securedapp.domain;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
/**
 * <p>VerificationToken class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class VerificationToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	private String id;

	private String token;

	private Date expiryDate;

	@DBRef(lazy = true)
	private User user;

	/**
	 * <p>Constructor for VerificationToken.</p>
	 */
	public VerificationToken() {
		super();
	}

	/**
	 * <p>Constructor for VerificationToken.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 */
	public VerificationToken(final String token, final User user) {
		super();

		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <p>Getter for the field <code>token</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * <p>Setter for the field <code>token</code>.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * <p>Getter for the field <code>expiryDate</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getExpiryDate() {
		return (Date) expiryDate.clone();
	}

	/**
	 * <p>Setter for the field <code>expiryDate</code>.</p>
	 *
	 * @param expiryDate a {@link java.util.Date} object.
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate != null ? (Date) expiryDate.clone() : null;
	}
}
