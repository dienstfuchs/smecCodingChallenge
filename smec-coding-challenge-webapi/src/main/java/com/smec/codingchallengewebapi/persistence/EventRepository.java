package com.smec.codingchallengewebapi.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smec.codingchallengewebapi.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByAccountName(String accountName);

}
