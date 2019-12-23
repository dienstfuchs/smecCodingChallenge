package com.smec.codingchallengewebapi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;
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

	@Override
	@Transactional
	public void createStatisticsForEvent(EventDTO eventDTO, String accountName) {
		Account account = accountResolver.findAccountByNameOrThrow(accountName);
		Statistics statistics = findByAccountAndDayAndType(eventDTO, account);
		if(statistics == null) {
			try {
				statistics = statisticsRepository.saveAndFlush(new Statistics(eventDTO.getHappenedAt().toLocalDate(), eventDTO.getType(), 0, account));
			}
			catch(DataIntegrityViolationException ex) {
				// could happen if an other request creates the statistics at the same time.
				statistics = findByAccountAndDayAndType(eventDTO, account);
			}
		}
		statisticsRepository.updateCounter(account, eventDTO.getHappenedAt().toLocalDate(), eventDTO.getType());
	}
	
	private Statistics findByAccountAndDayAndType(EventDTO event, Account account) {
		return statisticsRepository.findByAccountAndDayAndType(account, event.getHappenedAt().toLocalDate(), event.getType());
	}

}
