package com.smec.codingchallengewebapi.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByAccount(Account account);

}
