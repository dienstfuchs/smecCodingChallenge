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
	private final AccountResolver accountResolver;

	protected AccountServiceImpl(AccountRepository accountRepository, AccountResolver accountResolver) {
		this.accountRepository = accountRepository;
		this.accountResolver = accountResolver;
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
		Account account = accountResolver.findAccountByNameOrThrow(accountName);
		account.setName(newAccount.getName());
		return AccountConverter.toDTO(accountRepository.save(account));
	}

}
