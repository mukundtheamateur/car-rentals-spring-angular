package com.cts.thundercars.exceptions;


@SuppressWarnings("serial")
public class AlreadyExistsException extends Exception{
	
	public AlreadyExistsException(String message) {
		super(message);
	}

}
