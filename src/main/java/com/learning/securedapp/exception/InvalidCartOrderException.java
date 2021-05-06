package com.learning.securedapp.exception;

/**
 * InvalidCartOrderException class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class InvalidCartOrderException extends RuntimeException {
    private static final long serialVersionUID = -7991559713697297612L;

    /**
     * Constructor for InvalidCartOrderException.
     *
     * @param message a {@link java.lang.String} object.
     */
    public InvalidCartOrderException(String message) {
        super(message);
    }
}
