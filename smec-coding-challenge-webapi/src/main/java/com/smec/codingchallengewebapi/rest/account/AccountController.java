package com.smec.codingchallengewebapi.rest.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public List<AccountDTO>  getAllAccounts() {
		logger.info("getAllAccounts() executed");
		return accountService.findAll();
	}

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	AccountDTO createAccount(@RequestBody AccountDTO account) {
		logger.info("createAccount({})executed", account.getName());
		return accountService.createAccount(account);
	}

	@PutMapping("/{accountName}")
	AccountDTO updateAccount(@RequestBody AccountDTO newAccount, @PathVariable String accountName) {
		logger.info("updateAccount({}) for account '{}' executed", newAccount.getName(), accountName);
		return accountService.updateAccount(accountName, newAccount);
	}

}
