package com.smec.codingchallengewebapi.rest.statistics;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

	private final StatisticsService statisticsService;

	public StatisticsController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	@GetMapping("/accounts/{accountName}/statistics")
	public List<StatisticsDTO> getAllEvents(@PathVariable String accountName) {
		return statisticsService.getAllStatisticsByAccountName(accountName);
	}

}
