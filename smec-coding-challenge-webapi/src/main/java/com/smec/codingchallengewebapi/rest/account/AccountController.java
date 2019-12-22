package com.smec.codingchallengewebapi.rest.account;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public List<AccountDTO>  getAllAccounts() {
		return accountService.findAll();
	}

	@PostMapping()
	AccountDTO createAccount(@RequestBody AccountDTO account) {
		return accountService.createAccount(account);
	}

	@PutMapping("/{accountName}")
	AccountDTO updateAccount(@RequestBody AccountDTO newAccount, @PathVariable String accountName) {
		return accountService.updateAccount(accountName, newAccount);
	}

}
