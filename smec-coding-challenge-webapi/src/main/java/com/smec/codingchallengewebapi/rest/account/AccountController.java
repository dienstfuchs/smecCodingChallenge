package com.smec.codingchallengewebapi.rest.account;

import java.util.List;

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
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public List<Account> getAllAccounts() {
		return accountService.findAll();
	}

	@PostMapping
	Account createAccount(@RequestBody String accountName) {
		return accountService.createAccount(accountName);
	}

	@PutMapping("/{accountName}")
	Account updateAccount(@RequestBody String newAccountName, @PathVariable String accountName) {
		return accountService.updateAccount(accountName, newAccountName);
	}

}
