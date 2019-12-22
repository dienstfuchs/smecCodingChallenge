package com.smec.codingchallengewebapi.rest.account;

import java.util.List;

public interface AccountService {

	List<AccountDTO> findAll();

	AccountDTO createAccount(AccountDTO account);

	AccountDTO updateAccount(String accountName, AccountDTO newAccount);

}
