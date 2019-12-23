package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.EventRepository;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.event.EventService;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsService;

@Component
public class EventServiceImpl implements EventService {

	private final AccountResolver accountResolver;
	private final EventRepository eventRepository;
	private final StatisticsService statisticsService;

	public EventServiceImpl(AccountResolver accountResolver, EventRepository eventRepository, StatisticsService statisticsService) {
		this.accountResolver = accountResolver;
		this.eventRepository = eventRepository;
		this.statisticsService = statisticsService;
	}

	@Override
	public List<EventDTO> getAllEventsByAccountName(String accountName) {
		Account account = accountResolver.findAccountByNameOrThrow(accountName);
		return eventRepository.findByAccount(account).stream().map(event -> EventConverter.toDTO(event))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public EventDTO createEvent(EventDTO eventDTO, String accountName) {
		Account account = accountResolver.findAccountByNameOrThrow(accountName);
		Event createdEvent = eventRepository.save(EventConverter.toEntity(eventDTO, account));
		EventDTO createdEventDTO = EventConverter.toDTO(createdEvent);
		statisticsService.createStatisticsForEvent(createdEventDTO, accountName);
		return createdEventDTO;
	}
	
}
