package com.phitruong.exception;

public class UsernameAlreadyTakenException extends RuntimeException{
	public UsernameAlreadyTakenException() {
        super("Username already taken. Please choose another one.");
    }
}
