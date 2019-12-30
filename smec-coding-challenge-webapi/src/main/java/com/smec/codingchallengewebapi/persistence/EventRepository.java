package com.smec.codingchallengewebapi.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("select e from Event e where e.account =:account and e.happenedAt >= :startDate and e.happenedAt <=:endDate order by e.happenedAt asc")
	List<Event> findByAccount(Account account, LocalDateTime startDate, LocalDateTime endDate);

}
