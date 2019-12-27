package com.smec.codingchallengewebapi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Statistics;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsDTO;

public class StatistitcsConverterTest {

	@Test
	public void convertToDTO() {
		// given
		Statistics statistics = new Statistics(LocalDate.now(), "Event 1", 1, new Account("Account A"));

		// when
		StatisticsDTO statisticsDTO = new StatisticsConverter().toDTO(statistics);

		// then
		assertThat(statisticsDTO.getType(), is(equalTo(statistics.getType())));
		assertThat(statisticsDTO.getDay(), is(equalTo(statistics.getDay())));
		assertThat(statisticsDTO.getCount(), is(equalTo(statistics.getCount())));
	}

	@Test
	public void convertNullToDTO() {
		// given

		// when
		StatisticsDTO statisticsDTO = new StatisticsConverter().toDTO(null);

		// then
		assertThat(statisticsDTO, is(nullValue()));
	}

}
