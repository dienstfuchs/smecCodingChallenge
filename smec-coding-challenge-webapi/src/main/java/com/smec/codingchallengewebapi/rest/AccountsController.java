package com.smec.codingchallengewebapi.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smec.codingchallengewebapi.entities.Account;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	private final List<Account> accounts = new ArrayList<Account>(
			Arrays.asList(new Account(1, "Account1"), new Account(2, "Account2"), new Account(3, "Account3")));

	@GetMapping
	public List<Account> getAllAccounts() {
		return accounts;
	}

	@PostMapping
	Account createAccount(@RequestBody String accountName) {
		Account existingAccount = getAccountByNameImpl(accountName);
		if (existingAccount == null) {
			Account newAccount = new Account(new Random().nextInt(), accountName);
			accounts.add(newAccount);
			return newAccount;
		}
		throw new AccountAlreadyExistsException(accountName);

	}

	@PutMapping("/{accountName}")
	Account createOrUpdateEmployee(@RequestBody String newAccountName, @PathVariable String accountName) {
		Account account = getAccountByNameImpl(accountName);
		if (account != null) {
			account.setName(newAccountName);
			return account;
		} else {
			throw new AccountNotFoundException(accountName);
		}
	}

	private Account getAccountByNameImpl(String accountName) {
		return accounts.stream().filter(a -> accountName.equals(a.getName())).findFirst().orElse(null);
	}

}
