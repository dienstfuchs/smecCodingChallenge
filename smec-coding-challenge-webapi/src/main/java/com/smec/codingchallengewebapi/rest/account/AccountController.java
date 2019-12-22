package com.smec.codingchallengewebapi.rest.account;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountRepository accountRepository;

	public AccountController(AccountRepository accountRepostitory) {
		this.accountRepository = accountRepostitory;
	}

	@GetMapping
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@PostMapping
	Account createAccount(@RequestBody String accountName) {
		try {
			return accountRepository.save(new Account(accountName));
		} catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException(accountName);
		}
	}

	@PutMapping("/{accountName}")
	Account updateEmployee(@RequestBody String newAccountName, @PathVariable String accountName) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		account.setName(newAccountName);
		accountRepository.save(account);
		return account;
	}

}
