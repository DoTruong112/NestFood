package com.phitruong.exception;

public class PasswordMismatchException extends RuntimeException{
	public PasswordMismatchException() {
        super("Password and Confirm Password do not match. Please try again.");
    }
}
