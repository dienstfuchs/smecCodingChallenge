package com.smec.codingchallengewebapi.rest.account;

public class AccountNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String accountName) {
		super("Could not find account " + accountName);
	}
}
