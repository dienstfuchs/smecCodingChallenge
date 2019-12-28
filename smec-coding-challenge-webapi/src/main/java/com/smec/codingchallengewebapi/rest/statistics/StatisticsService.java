package com.smec.codingchallengewebapi.rest.statistics;

import java.time.LocalDate;
import java.util.List;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

public interface StatisticsService {

	List<StatisticsDTO> getAllStatisticsByAccountName(String accountName, LocalDate startDate, LocalDate endDate);

	void createStatisticsForEvent(EventDTO event, Account account);

}
