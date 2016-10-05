package com.learning.securedapp.exception;

/**
 * <p>EmptyCartOrderException class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class EmptyCartOrderException extends RuntimeException {
	private static final long serialVersionUID = 7264266305839487606L;

	/**
	 * <p>Constructor for EmptyCartOrderException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public EmptyCartOrderException(String message) {
		super(message);
	}

}
