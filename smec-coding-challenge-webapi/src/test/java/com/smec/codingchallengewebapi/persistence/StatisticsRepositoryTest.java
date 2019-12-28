package com.smec.codingchallengewebapi.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StatisticsRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private StatisticsRepository statisticsRepository;

	@Test
	public void findByAccount() {
		// given
		Account accountA = new Account("Account A");
		Account accountB = new Account("Account B");
		entityManager.persist(accountA);
		entityManager.persist(accountB);
		statisticsRepository.save(new Statistics(LocalDate.of(2020, 01, 01), "Event 1A", 1, accountA));
		statisticsRepository.save(new Statistics(LocalDate.of(2020, 01, 01), "Event 1B", 1, accountB));

		// when
		List<Statistics> statistics = statisticsRepository.findByAccount(accountA, LocalDate.of(2019, 1, 1),
				LocalDate.of(2020, 1, 1));

		// then
		assertThat(statistics.size(), is(1));
		assertThat(statistics.get(0).getType(), is("Event 1A"));
	}

	@Test
	public void updateCounterNotExists() {
		// given
		Account account = new Account("Account");
		entityManager.persist(account);
		entityManager.flush();

		// when
		int count = statisticsRepository.updateCounter(account, LocalDate.now(), "Event 1");

		// then
		assertThat(count, is(0));
	}

	@Test
	public void updateCounter() {
		// given
		Account account = new Account("Account");
		entityManager.persist(account);
		LocalDate date = LocalDate.of(2020, 01, 01);
		String eventName = "Event 1";
		int count = 1;
		statisticsRepository.save(new Statistics(date, eventName, count, account));
		entityManager.flush();

		// when
		int updatedCount = statisticsRepository.updateCounter(account, date, eventName);

		// then
		assertThat(updatedCount, is(1));
		List<Statistics> updatedStatistics = statisticsRepository.findByAccount(account, LocalDate.of(2019, 1, 1),
				LocalDate.of(2020, 1, 1));
		assertThat(updatedStatistics.size(), is(1));
		assertThat(updatedStatistics.get(0).getCount(), is(2L));
	}

	@Test
	public void createStatisticsForEvent() {
		// given
		Account account = new Account("Account");
		entityManager.persist(account);
		entityManager.flush();

		// when
		statisticsRepository.createStatisticsForEvent(new EventDTO(LocalDateTime.of(2018, 01, 02, 12, 01), "Event 1"),
				account);

		statisticsRepository.createStatisticsForEvent(new EventDTO(LocalDateTime.of(2019, 01, 01, 12, 00), "Event 1"),
				account);
		statisticsRepository.createStatisticsForEvent(new EventDTO(LocalDateTime.of(2019, 01, 01, 12, 01), "Event 1"),
				account);

		statisticsRepository.createStatisticsForEvent(new EventDTO(LocalDateTime.of(2019, 01, 02, 12, 01), "Event 1"),
				account);

		statisticsRepository.createStatisticsForEvent(new EventDTO(LocalDateTime.of(2021, 01, 02, 12, 01), "Event 1"),
				account);

		// then
		List<Statistics> updatedStatistics = statisticsRepository.findByAccount(account, LocalDate.of(2019, 1, 1),
				LocalDate.of(2020, 1, 1));
		assertThat(updatedStatistics.size(), is(2));
		assertThat(updatedStatistics.get(0).getCount(), is(2L));

	}

	@Test
	public void dateFiltering() {
		// given
		Account accountA = new Account("Account A");
		entityManager.persist(accountA);
		statisticsRepository.save(new Statistics(LocalDate.of(2018, 01, 01), "Event 1A", 1, accountA));
		statisticsRepository.save(new Statistics(LocalDate.of(2019, 01, 01), "Event 1A", 1, accountA));
		statisticsRepository.save(new Statistics(LocalDate.of(2020, 01, 01), "Event 1A", 1, accountA));
		statisticsRepository.save(new Statistics(LocalDate.of(2021, 01, 01), "Event 1A", 1, accountA));
		statisticsRepository.save(new Statistics(LocalDate.of(2022, 01, 01), "Event 1A", 1, accountA));

		assertThat(
				statisticsRepository.findByAccount(accountA, LocalDate.of(2019, 1, 1), LocalDate.of(2020, 1, 1)).size(),
				is(2));

		assertThat(
				statisticsRepository.findByAccount(accountA, LocalDate.of(2015, 1, 1), LocalDate.of(2025, 1, 1)).size(),
				is(5));

		assertThat(
				statisticsRepository.findByAccount(accountA, LocalDate.of(2025, 1, 1), LocalDate.of(2019, 1, 1)).size(),
				is(0));

	}

}
