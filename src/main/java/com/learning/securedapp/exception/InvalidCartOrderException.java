package com.learning.securedapp.exception;

/**
 * <p>InvalidCartOrderException class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class InvalidCartOrderException extends RuntimeException {
	private static final long serialVersionUID = -7991559713697297612L;

	/**
	 * <p>Constructor for InvalidCartOrderException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public InvalidCartOrderException(String message) {
		super(message);
	}

}
