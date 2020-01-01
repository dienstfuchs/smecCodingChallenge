package com.smec.codingchallengewebapi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByNameOrderByNameAsc(String name);

	default Account findAccountByNameOrThrow(String accountName) {
		Account account = findByNameOrderByNameAsc(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		return account;
	}

}
