package com.smec.codingchallengewebapi.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.rest.account.AccountAlreadyExistsException;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;
import com.smec.codingchallengewebapi.rest.account.AccountService;

@RunWith(SpringRunner.class)
public class AccountServiceImpTest {

	@TestConfiguration
	static class AccountServiceImplTestContextConfiguration {

		@Bean
		public AccountService accountService() {
			return new AccountServiceImpl();
		}

		@Bean
		public AccountConverter accountConverter() {
			return new AccountConverter();
		}
	}

	@Autowired
	private AccountService accountService;

	@MockBean
	private AccountRepository accountRepository;

	@Test
	public void findAll() {
		// given
		Account accountA = new Account("Account A");
		Account accountB = new Account("Account B");
		List<Account> accountList = List.of(accountA, accountB);
		Mockito.when(accountRepository.findAll()).thenReturn(accountList);
		// when
		List<AccountDTO> accounts = accountService.findAll();
		// then
		Mockito.verify(accountRepository).findAll();
		assertThat(accounts.size(), is(2));
		assertThat(accounts.get(0).getName(), is("Account A"));
		assertThat(accounts.get(1).getName(), is("Account B"));
	}

	@Test(expected = AccountAlreadyExistsException.class)
	public void createAccountAlreadyExists() {
		// given
		Account account = new Account("Account A");
		AccountDTO accountDTO = new AccountDTO("Account A");

		Mockito.when(accountRepository.save(account)).thenThrow(DataIntegrityViolationException.class);
		// when
		accountService.createAccount(accountDTO);
		// then
	}

	@Test
	public void createAccount() {
		// given
		Account account = new Account("Account A");
		AccountDTO accountDTO = new AccountDTO("Account A");

		Mockito.when(accountRepository.save(account)).thenReturn(account);
		// when
		AccountDTO createAccountDTO = accountService.createAccount(accountDTO);
		// then
		Mockito.verify(accountRepository).save(account);
		assertThat(createAccountDTO.getName(), is("Account A"));
	}
	
	@Test(expected = AccountNotFoundException.class)
	public void updateAccountNotFound() {
		// given
		AccountDTO accountDTO = new AccountDTO("Account A");

		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenThrow(AccountNotFoundException.class);
		// when
		accountService.updateAccount("Account A", accountDTO);
		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
	}
	
	@Test
	public void updateAccount() {
		// given
		Account accountA = new Account("Account A");
		Account accountB = new Account("Account B");
		AccountDTO accountDTO = new AccountDTO("Account B");

		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenReturn(accountA);
		Mockito.when(accountRepository.findByNameOrderByNameAsc("Account B")).thenReturn(null);
		Mockito.when(accountRepository.save(accountA)).thenReturn(accountB);
		// when
		AccountDTO updatedAccount = accountService.updateAccount("Account A", accountDTO);
		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
		Mockito.verify(accountRepository).findByNameOrderByNameAsc("Account B");
		Mockito.verify(accountRepository).save(accountB);
		assertThat(updatedAccount.getName(), is("Account B"));
	}
	
	@Test(expected = AccountAlreadyExistsException.class)
	public void updateAccountAlreadyExists() {
		// given
		Account accountA = new Account("Account A");
		Account accountB = new Account("Account B");
		AccountDTO accountDTO = new AccountDTO("Account B");

		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenReturn(accountA);
		Mockito.when(accountRepository.findByNameOrderByNameAsc("Account B")).thenReturn(accountB);
		Mockito.when(accountRepository.save(accountA)).thenReturn(accountB);
		// when
		accountService.updateAccount("Account A", accountDTO);
	}

}
