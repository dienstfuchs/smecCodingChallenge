package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.rest.account.AccountAlreadyExistsException;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.account.AccountService;

@Component
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountConverter accountConverter;

	protected AccountServiceImpl() {
	}

	@Override
	public List<AccountDTO> findAll() {
		return accountRepository.findAll()
				.stream()
				.map(account -> accountConverter.toDTO(account))
				.collect(Collectors.toList());
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
		if (accountRepository.findByName(newAccount.getName()) != null) {
			throw new AccountAlreadyExistsException(newAccount.getName());
		}
		account.setName(newAccount.getName());
		return accountConverter.toDTO(accountRepository.save(account));
	}

}
