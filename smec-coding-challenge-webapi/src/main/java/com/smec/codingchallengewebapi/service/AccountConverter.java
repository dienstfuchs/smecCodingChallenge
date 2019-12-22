package com.smec.codingchallengewebapi.service;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;

public class AccountConverter {

	public static AccountDTO toDTO(Account account) {
		return new AccountDTO(account.getName());
	}
	
	public static Account toEntity(AccountDTO accountDTO) {
		return new Account(accountDTO.getName());
	}
	
}
