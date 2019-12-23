package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.rest.account.AccountAlreadyExistsException;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.account.AccountService;

@Component
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final AccountConverter accountConverter;

	protected AccountServiceImpl(AccountRepository accountRepository, AccountConverter accountConverter) {
		this.accountRepository = accountRepository;
		this.accountConverter = accountConverter;
	}

	@Override
	public List<AccountDTO> findAll() {
		return accountRepository.findAll().stream().map(account -> accountConverter.toDTO(account)).collect(Collectors.toList());
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDTO) {
		try {
			Account createdAccount = accountRepository.save(accountConverter.toEntity(accountDTO));
			return accountConverter.toDTO(createdAccount);
		} catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException(accountDTO.getName());
		}
	}

	@Override
	public AccountDTO updateAccount(String accountName, AccountDTO newAccount) {
		Account account = accountRepository.findAccountByNameOrThrow(accountName);
		account.setName(newAccount.getName());
		return accountConverter.toDTO(accountRepository.save(account));
	}

}
