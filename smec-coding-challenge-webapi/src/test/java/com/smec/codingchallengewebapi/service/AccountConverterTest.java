package com.smec.codingchallengewebapi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;

public class AccountConverterTest {

	@Test
	public void convertToDTO() {
		// given
		Account account = new Account("Account A");

		// when
		AccountDTO accountDTO = new AccountConverter().toDTO(account);

		// then
		assertThat(accountDTO.getName(), is(equalTo(account.getName())));
	}

	@Test
	public void convertNullToDTO() {
		// given

		// when
		AccountDTO accountDTO = new AccountConverter().toDTO(null);

		// then
		assertThat(accountDTO, is(nullValue()));
	}

	@Test
	public void convertToEntity() {
		// given
		AccountDTO accountDTO = new AccountDTO("Account A");

		// when
		Account account = new AccountConverter().toEntity(accountDTO);

		// then
		assertThat(account.getName(), is(equalTo(accountDTO.getName())));
	}
	
	@Test
	public void convertNullToEntity() {
		// given

		// when
		Account account = new AccountConverter().toEntity(null);

		// then
		assertThat(account, is(nullValue()));
	}

}
