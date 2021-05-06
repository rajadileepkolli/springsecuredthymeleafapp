package com.learning.securedapp.exception;

/**
 * EmptyCartOrderException class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class EmptyCartOrderException extends RuntimeException {
    private static final long serialVersionUID = 7264266305839487606L;

    /**
     * Constructor for EmptyCartOrderException.
     *
     * @param message a {@link java.lang.String} object.
     */
    public EmptyCartOrderException(String message) {
        super(message);
    }
}
