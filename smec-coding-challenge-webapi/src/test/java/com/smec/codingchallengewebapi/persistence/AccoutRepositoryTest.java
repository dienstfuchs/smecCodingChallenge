package com.smec.codingchallengewebapi.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccoutRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void findAccountByNameNotExists() {
		Account account = accountRepository.findByName("Account A");
		assertThat(account, is(nullValue()));
	}

	@Test
	public void findAccountByName() {
		// given
		Account givenAccount = new Account("Account A");
		entityManager.persist(givenAccount);
		entityManager.flush();

		// when
		Account account = accountRepository.findByName(givenAccount.getName());

		// then
		assertThat(account.getName(), is(equalTo(givenAccount.getName())));
	}

	@Test(expected = AccountNotFoundException.class)
	public void findAccountByNameOrThrowNotExists() {
		Account account = accountRepository.findAccountByNameOrThrow("Account A");
		assertThat(account, is(nullValue()));
	}

	@Test
	public void findAccountByNameOrThrow() {
		// given
		Account givenAccount = new Account("Account A");
		entityManager.persist(givenAccount);
		entityManager.flush();

		// when
		Account account = accountRepository.findAccountByNameOrThrow(givenAccount.getName());

		// then
		assertThat(account.getName(), is(equalTo(givenAccount.getName())));
	}

}
