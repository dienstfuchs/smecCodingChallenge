package com.smec.codingchallengewebapi.rest.statistics;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final StatisticsService statisticsService;

	public StatisticsController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	@GetMapping("/accounts/{accountName}/statistics")
	public List<StatisticsDTO> getAllStatistics(@PathVariable String accountName, @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
		logger.info("getAllStatistics() executed");
		return statisticsService.getAllStatisticsByAccountName(accountName, startDate, endDate);
	}

}
