package com.cts.thundercars.exceptions;

@SuppressWarnings("serial")
public class NoUserFoundException extends Exception {

	public NoUserFoundException(String message) {
		super(message);
	}
}
