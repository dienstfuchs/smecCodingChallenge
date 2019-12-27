package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.StatisticsRepository;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsService;

@Component
public class StatisticsServiceImpl implements StatisticsService {

	private final AccountRepository accountRepository;
	private final StatisticsRepository statisticsRepository;
	private final StatisticsConverter statisticsConverter;

	public StatisticsServiceImpl(AccountRepository accountRepository, StatisticsRepository statisticsRepository, StatisticsConverter statisticsConverter) {
		this.accountRepository = accountRepository;
		this.statisticsRepository = statisticsRepository;
		this.statisticsConverter = statisticsConverter;
	}

	@Override
	public List<StatisticsDTO> getAllStatisticsByAccountName(String accountName) {
		Account account = accountRepository.findAccountByNameOrThrow(accountName);
		return statisticsRepository.findByAccount(account).stream().map(event -> statisticsConverter.toDTO(event))
				.collect(Collectors.toList());
	}

	public void createStatisticsForEvent(EventDTO eventDTO, Account account) {
		try {
			statisticsRepository.createStatisticsForEvent(eventDTO, account);

		} catch (DataIntegrityViolationException e) {
			statisticsRepository.createStatisticsForEvent(eventDTO, account);
		}
	}

}
