package com.smec.codingchallengewebapi.rest.account;

import java.util.List;

import com.smec.codingchallengewebapi.entities.Account;

public interface AccountService {

	List<AccountDTO> findAll();

	AccountDTO createAccount(AccountDTO account);

	AccountDTO updateAccount(String accountName, AccountDTO newAccount);

}
