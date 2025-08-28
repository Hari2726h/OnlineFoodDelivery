package com.foodexpress.exception;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException() {
		
	}
	
	public ItemNotFoundException(String message) {
		super(message);
	}
	
}
