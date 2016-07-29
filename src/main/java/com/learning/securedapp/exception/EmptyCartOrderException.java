package com.learning.securedapp.exception;

public class EmptyCartOrderException extends RuntimeException {
	private static final long serialVersionUID = 7264266305839487606L;

	public EmptyCartOrderException(String message) {
		super(message);
	}

}
