package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.StatisticsRepository;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsConverter;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsService;

@Component
public class StatisticsServiceImpl implements StatisticsService {

	private final AccountResolver accountResolver;
	private final StatisticsRepository statisticsRepository;

	public StatisticsServiceImpl(AccountResolver accountResolver, StatisticsRepository statisticsRepository) {
		this.accountResolver = accountResolver;
		this.statisticsRepository = statisticsRepository;
	}

	@Override
	public List<StatisticsDTO> getAllStatisticsByAccountName(String accountName) {
		Account account = accountResolver.findAccountByNameOrThrow(accountName);
		return statisticsRepository.findByAccount(account).stream().map(event -> StatisticsConverter.toDTO(event))
				.collect(Collectors.toList());
	}

	public void createStatisticsForEvent(EventDTO eventDTO, String accountName) {
		Account account = accountResolver.findAccountByNameOrThrow(accountName);
		try {
			statisticsRepository.createStatisticsForEventImpl(eventDTO, account);

		} catch (DataIntegrityViolationException e) {
			// this can happen if more than one thread is creating the first statistics entry.
			statisticsRepository.createStatisticsForEventImpl(eventDTO, account);
		}
	}

}
