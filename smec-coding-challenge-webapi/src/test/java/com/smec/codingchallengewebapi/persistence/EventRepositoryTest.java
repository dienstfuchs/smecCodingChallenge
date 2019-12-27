package com.smec.codingchallengewebapi.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void findByAccountNameAccountNotExists() {
		// given
		// when
		List<Event> events = eventRepository.findByAccountName("Account A");
		// then
		assertThat(events.size(), is(0));
	}

	@Test
	public void findByAccountName() {
		// given
		Account accountA = new Account("Account A");
		Account accountB = new Account("Account B");
		
		entityManager.persist(accountA);
		entityManager.persist(accountB);
		
		Event eventA1 = new Event(LocalDateTime.now(), "Event A1", accountA);
		Event eventA2 = new Event(LocalDateTime.now(), "Event A2", accountA);
		
		Event eventB1 = new Event(LocalDateTime.now(), "Event B1", accountB);
		Event eventB2 = new Event(LocalDateTime.now(), "Event B2", accountB);
		
		entityManager.persist(eventA1);
		entityManager.persist(eventA2);
		
		entityManager.persist(eventB1);
		entityManager.persist(eventB2);
		
		entityManager.flush();

		// when
		List<Event> events = eventRepository.findByAccountName(accountA.getName());

		// then
		assertThat(events.size(), is(2));
		assertThat(events.get(0).getType(), is(equalTo(eventA1.getType())));
		assertThat(events.get(1).getType(), is(equalTo(eventA2.getType())));
	}

}
