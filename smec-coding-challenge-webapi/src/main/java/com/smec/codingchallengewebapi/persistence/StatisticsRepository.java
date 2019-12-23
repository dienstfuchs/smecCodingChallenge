package com.smec.codingchallengewebapi.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

	List<Statistics> findByAccount(Account account);

	Statistics findByAccountAndDayAndType(Account account, LocalDate day, String type);

	@Modifying
	@Query("update Statistics s set s.count = s.count + 1 where s.day = :day and s.type = :type and s.account = :account")
	int updateCounter(@Param("account") Account account, @Param("day") LocalDate day, @Param("type") String type);

	@Transactional
	default void createStatisticsForEventImpl(EventDTO eventDTO, Account account) {
		Statistics statistics = findByAccountAndDayAndType(eventDTO, account);
		if (statistics == null) {
			statistics = saveAndFlush(
					new Statistics(eventDTO.getHappenedAt().toLocalDate(), eventDTO.getType(), 0, account));
		}
		updateCounter(account, eventDTO.getHappenedAt().toLocalDate(), eventDTO.getType());
	}

	default Statistics findByAccountAndDayAndType(EventDTO event, Account account) {
		return findByAccountAndDayAndType(account, event.getHappenedAt().toLocalDate(),
				event.getType());
	}
}
