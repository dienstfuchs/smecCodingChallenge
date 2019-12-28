package com.smec.codingchallengewebapi.rest.statistics;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StatisticsService statisticsService;

	@Test
	public void getAllStatisticsWhenAccountNotExists() throws Exception {
		when(statisticsService.getAllStatisticsByAccountName("Account A", LocalDate.of(2020, 1, 1),
				LocalDate.of(2020, 1, 1))).thenThrow(AccountNotFoundException.class);
		mvc.perform(
				get("/accounts/Account A/statistics").param("startDate", "2020-01-01").param("endDate", "2020-01-01"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getAllStatistics() throws Exception {
		StatisticsDTO statistics1 = new StatisticsDTO(LocalDate.of(2020, 1, 1), "Event 1", 5);
		StatisticsDTO statistics2 = new StatisticsDTO(LocalDate.of(2020, 1, 1), "Event 2", 6);
		List<StatisticsDTO> statisticsDTOs = List.of(statistics1, statistics2);

		when(statisticsService.getAllStatisticsByAccountName("Account A", LocalDate.of(2020, 1, 1),
				LocalDate.of(2020, 1, 1))).thenReturn(statisticsDTOs);
		
		mvc.perform(
				get("/accounts/Account A/statistics").param("startDate", "2020-01-01").param("endDate", "2020-01-01"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].type", is("Event 1")))
				.andExpect(jsonPath("$[0].day", is("2020-01-01")))
				.andExpect(jsonPath("$[0].count", is(5)))
				.andExpect(jsonPath("$[1].type", is("Event 2")))
				.andExpect(jsonPath("$[1].day", is("2020-01-01")))
				.andExpect(jsonPath("$[1].count", is(6)));
	}

}
