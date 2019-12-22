package com.smec.codingchallengewebapi.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smec.codingchallengewebapi.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByName(String name);
	
}
