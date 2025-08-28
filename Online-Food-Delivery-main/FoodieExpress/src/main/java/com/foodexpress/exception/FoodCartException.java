package com.foodexpress.exception;

public class FoodCartException extends RuntimeException {

	public FoodCartException() {
		
	}
	
	public FoodCartException(String message) {
		super(message);
	}
	
}
