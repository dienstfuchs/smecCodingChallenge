package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.rest.account.AccountAlreadyExistsException;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;
import com.smec.codingchallengewebapi.rest.account.AccountService;

@Component
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	protected AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<AccountDTO> findAll() {
		return accountRepository.findAll().stream().map(account -> new AccountDTO(account.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDTO) {
		try {
			Account createdAccount = accountRepository.save(AccountConverter.toEntity(accountDTO));
			return AccountConverter.toDTO(createdAccount);
		} catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException(accountDTO.getName());
		}
	}

	@Override
	public AccountDTO updateAccount(String accountName, AccountDTO newAccount) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		
		return AccountConverter.toDTO(accountRepository.save(account));
	}

}
