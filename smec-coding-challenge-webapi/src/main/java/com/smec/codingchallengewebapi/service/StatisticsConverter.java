package com.smec.codingchallengewebapi.service;

import org.springframework.stereotype.Component;

import com.smec.codingchallengewebapi.entities.Statistics;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsDTO;

@Component
public class StatisticsConverter {

	public StatisticsDTO toDTO(Statistics statistics) {
		if(statistics == null) {
			return null;
		}
		return new StatisticsDTO(statistics.getDay(), statistics.getType(), statistics.getCount());
	}
}
