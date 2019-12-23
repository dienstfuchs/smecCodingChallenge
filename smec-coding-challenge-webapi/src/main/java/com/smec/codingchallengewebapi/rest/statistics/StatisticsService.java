package com.smec.codingchallengewebapi.rest.statistics;

import java.util.List;

import com.smec.codingchallengewebapi.rest.event.EventDTO;

public interface StatisticsService {

	List<StatisticsDTO> getAllStatisticsByAccountName(String accountName);

	void createStatisticsForEvent(EventDTO event, String accountName);

}
