package com.smec.codingchallengewebapi.rest.account;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountService accountService;

	@Test
	public void findAllAccountsEmpty() throws Exception {
		when(accountService.findAll()).thenReturn(new ArrayList<>());
		mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void findAllAccounts() throws Exception {
		when(accountService.findAll()).thenReturn(Arrays.asList(new AccountDTO("Account A"), new AccountDTO("Account B")));
		mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is("Account A")))
				.andExpect(jsonPath("$[1].name", is("Account B")));
	}
	
	@Test
	public void createAccount() throws Exception {
		AccountDTO account = new AccountDTO("Account A");
		when(accountService.createAccount(account)).thenReturn(account);
		mvc.perform(post("/accounts").content("{\"name\":\"Account A\"}").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Account A")));
	}
	
	@Test
	public void createAccountAlreadyExists() throws Exception {
		AccountDTO account = new AccountDTO("Account A");
		when(accountService.createAccount(account)).thenThrow(AccountAlreadyExistsException.class);
		mvc.perform(post("/accounts").content("{\"name\":\"Account A\"}").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void updateAccountNotFound() throws Exception {
		AccountDTO account = new AccountDTO("Account B");
		when(accountService.updateAccount("Account A", account)).thenThrow(AccountNotFoundException.class);
		mvc.perform(put("/accounts/Account A").content("{\"name\":\"Account B\"}").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateAccountConflict() throws Exception {
		AccountDTO account = new AccountDTO("Account B");
		when(accountService.updateAccount("Account A", account)).thenThrow(AccountAlreadyExistsException.class);
		mvc.perform(put("/accounts/Account A").content("{\"name\":\"Account B\"}").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}
	
	@Test
	public void updateAccount() throws Exception {
		AccountDTO account = new AccountDTO("Account B");
		when(accountService.updateAccount("Account A", account)).thenReturn(account);
		mvc.perform(put("/accounts/Account A").content("{\"name\":\"Account B\"}").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("Account B")))
				.andExpect(status().isOk());
	}

}
