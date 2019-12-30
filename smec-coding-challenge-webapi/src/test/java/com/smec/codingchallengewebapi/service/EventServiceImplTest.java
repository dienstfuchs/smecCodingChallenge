package com.smec.codingchallengewebapi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.EventRepository;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.event.EventService;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsService;

@RunWith(SpringRunner.class)
public class EventServiceImplTest {

	@TestConfiguration
	static class EventServiceImplTestContextConfiguration {

		@Bean
		public EventService eventService() {
			return new EventServiceImpl();
		}

		@Bean
		public EventConverter eventConverter() {
			return new EventConverter();
		}
	}

	@Autowired
	private EventService eventService;

	@MockBean
	private AccountRepository accountRepository;

	@MockBean
	private EventRepository eventRepository;

	@MockBean
	private StatisticsService statisticsService;

	@Test(expected = AccountNotFoundException.class)
	public void getAllEventsByAccountNameNotExists() {
		// given

		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenThrow(AccountNotFoundException.class);
		// when
		eventService.getAllEventsByAccountName("Account A", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 13, 0));
		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
	}

	@Test
	public void getAllEventsByAccountName() {
		// given
		Account accountA = new Account("Account A");
		Event event1 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event 1", accountA);
		Event event2 = new Event(LocalDateTime.of(2020, 1, 1, 12, 0), "Event 2", accountA);

		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenReturn(accountA);
		Mockito.when(eventRepository.findByAccount(accountA, LocalDateTime.of(2020, 1, 1, 0, 0),
				LocalDateTime.of(2020, 1, 1, 13, 0))).thenReturn(List.of(event1, event2));

		// when
		List<EventDTO> eventDTOs = eventService.getAllEventsByAccountName("Account A", LocalDateTime.of(2020, 1, 1, 0, 0),
				LocalDateTime.of(2020, 1, 1, 13, 0));

		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
		Mockito.verify(eventRepository)
				.findByAccount(accountA, LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 13, 0));
		assertThat(eventDTOs.size(), is(2));
		assertThat(eventDTOs.get(0).getType(), is(equalTo("Event 1")));
		assertThat(eventDTOs.get(1).getType(), is(equalTo("Event 2")));
	}

	@Test(expected = AccountNotFoundException.class)
	public void createEventNotExists() {
		// given
		EventDTO eventDTO = new EventDTO(LocalDateTime.now(), "Event 1");
		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenThrow(AccountNotFoundException.class);
		// when
		eventService.createEvent(eventDTO, "Account A");
		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
	}

	@Test
	public void createEvent() {
		// given
		Account account = new Account("Account A");
		EventDTO eventDTO = new EventDTO(LocalDateTime.of(2020, 1, 1, 12, 00), "Event 1");
		Event event = new Event(LocalDateTime.of(2020, 1, 1, 12, 00), "Event 1", account);
		Mockito.when(accountRepository.findAccountByNameOrThrow("Account A")).thenReturn(account);
		Mockito.when(eventRepository.save(event)).thenReturn(event);
		Mockito.doNothing().when(statisticsService).createStatisticsForEvent(eventDTO, account);

		// when
		EventDTO createEventDTO = eventService.createEvent(eventDTO, "Account A");

		// then
		Mockito.verify(accountRepository).findAccountByNameOrThrow("Account A");
		Mockito.verify(eventRepository).save(event);
		Mockito.verify(statisticsService).createStatisticsForEvent(eventDTO, account);
		assertThat(createEventDTO.getType(), is(equalTo(eventDTO.getType())));
	}

}
