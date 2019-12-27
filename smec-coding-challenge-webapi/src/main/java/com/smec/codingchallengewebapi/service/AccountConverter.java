package com.smec.codingchallengewebapi.service;

import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;

@Component
public class AccountConverter {

	public AccountDTO toDTO(Account account) {
		if (account == null) {
			return null;
		}
		return new AccountDTO(account.getName());
	}

	public Account toEntity(AccountDTO accountDTO) {
		if (accountDTO == null) {
			return null;
		}
		return new Account(accountDTO.getName());
	}

}
