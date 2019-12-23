package com.smec.codingchallengewebapi.rest.statistics;

import com.smec.codingchallengewebapi.entities.Statistics;

public class StatisticsConverter {

	public static StatisticsDTO toDTO(Statistics statistics) {
		return new StatisticsDTO(statistics.getDay(), statistics.getType(), statistics.getCount());
	}
}
