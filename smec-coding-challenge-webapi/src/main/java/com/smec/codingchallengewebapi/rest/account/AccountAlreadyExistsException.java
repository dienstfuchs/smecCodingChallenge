package com.smec.codingchallengewebapi.rest.account;

public class AccountAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException(String accountName) {
		super("The following account already exists " + accountName);
	}
}
