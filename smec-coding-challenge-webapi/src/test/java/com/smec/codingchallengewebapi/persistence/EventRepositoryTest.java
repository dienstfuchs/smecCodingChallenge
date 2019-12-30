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
	public void findByAccountName() {
		// given
		Account accountA = new Account("Account A");
		Account accountB = new Account("Account B");

		entityManager.persist(accountA);
		entityManager.persist(accountB);

		Event eventA1 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event A1", accountA);
		Event eventA2 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event A2", accountA);

		Event eventB1 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event B1", accountB);
		Event eventB2 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event B2", accountB);

		entityManager.persist(eventA1);
		entityManager.persist(eventA2);

		entityManager.persist(eventB1);
		entityManager.persist(eventB2);

		entityManager.flush();

		// when
		List<Event> events = eventRepository.findByAccount(accountA, LocalDateTime.of(2020, 1, 1, 0, 0),
				LocalDateTime.of(2020, 1, 1, 13, 0));

		// then
		assertThat(events.size(), is(2));
		assertThat(events.get(0).getType(), is(equalTo(eventA1.getType())));
		assertThat(events.get(1).getType(), is(equalTo(eventA2.getType())));
	}

	@Test
	public void dateFiltering() {
		// given
		Account accountA = new Account("Account A");

		entityManager.persist(accountA);

		Event event1 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event A1", accountA);
		Event event2 = new Event(LocalDateTime.of(2020, 1, 2, 12, 0), "Event A2", accountA);
		Event event3 = new Event(LocalDateTime.of(2020, 1, 3, 12, 0), "Event B1", accountA);
		Event event4 = new Event(LocalDateTime.of(2020, 1, 4, 12, 0), "Event B2", accountA);
		Event event5 = new Event(LocalDateTime.of(2020, 1, 5, 12, 0), "Event B2", accountA);

		entityManager.persist(event1);
		entityManager.persist(event2);
		entityManager.persist(event3);
		entityManager.persist(event4);
		entityManager.persist(event5);

		assertThat(eventRepository.findByAccount(accountA, LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 5, 13, 0)).size(),
				is(5));

		assertThat(eventRepository.findByAccount(accountA, LocalDateTime.of(2020, 1, 5, 0, 0), LocalDateTime.of(2020, 1, 5, 13, 0)).size(),
				is(1));

		assertThat(eventRepository.findByAccount(accountA, LocalDateTime.of(2029, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 3, 13, 0)).size(),
				is(0));

	}
}
