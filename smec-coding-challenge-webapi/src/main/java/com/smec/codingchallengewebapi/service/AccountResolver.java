package com.smec.codingchallengewebapi.service;

import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;

@Component
public class AccountResolver {

	private final AccountRepository accountRepository;

	public AccountResolver(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account findAccountByNameOrThrow(String accountName) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		return account;
	}
	
}
