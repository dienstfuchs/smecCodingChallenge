package com.smec.codingchallengewebapi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.StatisticsRepository;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsService;

@RunWith(SpringRunner.class)
public class StatisticsServiceImpTest {

	@TestConfiguration
	static class StatisticsServiceImplTestContextConfiguration {

		@Bean
		public StatisticsService statisticsService() {
			return new StatisticsServiceImpl();
		}

		@Bean
		public StatisticsConverter statisticsConverter() {
			return new StatisticsConverter();
		}
	}

	@Autowired
	private StatisticsService statisticsService;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private StatisticsRepository statisticsRepository;


	@Test(expected = AccountNotFoundException.class)
	public void getAllStatisticsByAccountNameNotFound() {
		// given

		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenThrow(AccountNotFoundException.class);
		// when
		statisticsService.getAllStatisticsByAccountName("Account A");
		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
	}

	@Test
	public void getAllStatistics() {
		// given
		Account accountA = new Account("Account A");
		Statistics statistics = new Statistics(LocalDate.now(), "Event 1", 1, accountA);
		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenReturn(accountA);
		Mockito.when(statisticsRepository.findByAccountOrderByDayAscTypeAsc(accountA)).thenReturn(List.of(statistics));
		
		// when
		List<StatisticsDTO> statisticsDTOs = statisticsService.getAllStatisticsByAccountName("Account A");
		
		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
		Mockito.verify(statisticsRepository).findByAccountOrderByDayAscTypeAsc(accountA);
		assertThat(statisticsDTOs.size(), is(1));
		assertThat(statisticsDTOs.get(0).getType(), is(equalTo("Event 1")));
	}
	
	@Test
	public void createStatisticsForEvent() {
		// given
		Account accountA = new Account("Account A");
		EventDTO eventDTO = new EventDTO(LocalDateTime.now(), "Event 1");
		
		Mockito.doThrow(new DataIntegrityViolationException("")).doNothing().when(statisticsRepository).createStatisticsForEvent(eventDTO, accountA);
		Mockito.doNothing().when(statisticsRepository).createStatisticsForEvent(eventDTO, accountA);
		
		// when
		statisticsService.createStatisticsForEvent(eventDTO, accountA);
		
		// then
		Mockito.verify(statisticsRepository).createStatisticsForEvent(eventDTO, accountA);
		Mockito.verify(statisticsRepository).createStatisticsForEvent(eventDTO, accountA);
	}

}
