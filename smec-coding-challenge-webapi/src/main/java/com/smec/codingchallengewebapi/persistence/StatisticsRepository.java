package com.smec.codingchallengewebapi.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

	List<Statistics> findByAccount(Account account);
	
	Statistics findByAccountAndDayAndType(Account account, LocalDate day, String type);

}
