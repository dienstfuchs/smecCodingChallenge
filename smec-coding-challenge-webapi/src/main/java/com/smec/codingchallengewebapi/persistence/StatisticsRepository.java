package com.smec.codingchallengewebapi.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

	List<Statistics> findByAccount(Account account);

	Statistics findByAccountAndDayAndType(Account account, LocalDate day, String type);

	@Modifying
	@Query("update Statistics s set s.count = s.count + 1 where s.day = :day and s.type = :type and s.account = :account")
	int updateCounter(@Param("account") Account account, @Param("day") LocalDate day, @Param("type") String type);

}
