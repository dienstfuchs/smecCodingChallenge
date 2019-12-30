package com.smec.codingchallengewebapi;

import static com.smec.codingchallengewebapi.ResponseBodyMatchers.responseBody;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.event.EventDTO;
import com.smec.codingchallengewebapi.rest.statistics.StatisticsDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CodingChallengeWebapiApplicationTests {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	/**
	 * The Test uses the DAOs so that the tests are more readable. The json
	 * serialization/deserialization is tested in AccountControllerTest,
	 * EventControllerTest and StatisticsControllerTest.
	 */
	@Test
	@DirtiesContext
	void accountsWorkflow() throws Exception {
		AccountDTO accountA = new AccountDTO("Account A");
		AccountDTO accountB = new AccountDTO("Account B");
		AccountDTO accountC = new AccountDTO("Account C");

		List<AccountDTO> accountAB = List.of(accountA, accountB);
		List<AccountDTO> accountCB = List.of(accountC, accountB);

		mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		createAndAssertAccount(accountA);
		createAndAssertAccount(accountB);

		mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(responseBody().containsObjectAsJson(accountAB, new TypeReference<List<AccountDTO>>() {
				}));

		mvc.perform(post("/accounts").content(objectMapper.writeValueAsString(accountA))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

		mvc.perform(put("/accounts/" + accountA.getName()).content(objectMapper.writeValueAsString(accountC))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(responseBody().containsObjectAsJson(accountC, AccountDTO.class));

		mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(responseBody().containsObjectAsJson(accountCB, new TypeReference<List<AccountDTO>>() {
				}));

		mvc.perform(put("/accounts/" + accountB.getName()).content(objectMapper.writeValueAsString(accountC))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

	}

	@Test
	@DirtiesContext
	public void fullWorkflow() throws Exception {
		AccountDTO accountD = new AccountDTO("Account D");
		AccountDTO accountE = new AccountDTO("Account E");

		createAndAssertAccount(accountD);
		createAndAssertAccount(accountE);

		EventDTO event1D = new EventDTO(LocalDateTime.of(2020, 01, 01, 12, 00), "Event 1D");
		EventDTO event2D = new EventDTO(LocalDateTime.of(2020, 01, 01, 12, 01), "Event 2D");
		EventDTO event3D = new EventDTO(LocalDateTime.of(2020, 01, 02, 12, 01), "Event 1D");
		EventDTO event4D = new EventDTO(LocalDateTime.of(2020, 01, 02, 12, 02), "Event 2D");
		EventDTO event5D = new EventDTO(LocalDateTime.of(2020, 01, 02, 12, 03), "Event 2D");
		EventDTO event6D = new EventDTO(LocalDateTime.of(2020, 01, 02, 12, 05), "Event 2D");

		EventDTO event1E = new EventDTO(LocalDateTime.of(2020, 01, 02, 12, 04), "Event 1E");
		EventDTO event2E = new EventDTO(LocalDateTime.of(2020, 01, 02, 12, 05), "Event 1E");

		createAndAssertEvent(event1D, accountD.getName());
		createAndAssertEvent(event2D, accountD.getName());
		createAndAssertEvent(event3D, accountD.getName());
		createAndAssertEvent(event4D, accountD.getName());
		createAndAssertEvent(event5D, accountD.getName());
		createAndAssertEvent(event6D, accountD.getName());

		createAndAssertEvent(event1E, accountE.getName());
		createAndAssertEvent(event2E, accountE.getName());

		getAndAssertEvents(accountD, List.of(event1D, event2D, event3D, event4D, event5D, event6D), "2019-01-01T00:00",
				"2020-01-02T13:00");
		getAndAssertEvents(accountD, List.of(event1D, event2D), "2019-01-01T00:00", "2020-01-01T13:00");
		getAndAssertEvents(accountD, List.of(), "2017-01-01T00:00", "2018-01-01T13:00");

		getAndAssertEvents(accountE, List.of(event1E, event2E), "2019-01-01T00:00", "2020-01-02T13:00");

		StatisticsDTO statisticsD1 = new StatisticsDTO(LocalDate.of(2020, 1, 1), "Event 1D", 1);
		StatisticsDTO statisticsD2 = new StatisticsDTO(LocalDate.of(2020, 1, 1), "Event 2D", 1);
		StatisticsDTO statisticsD3 = new StatisticsDTO(LocalDate.of(2020, 1, 2), "Event 1D", 1);
		StatisticsDTO statisticsD4 = new StatisticsDTO(LocalDate.of(2020, 1, 2), "Event 2D", 3);

		StatisticsDTO statisticsE1 = new StatisticsDTO(LocalDate.of(2020, 1, 2), "Event 1E", 2);

		getAndAssertStatistics(accountD, List.of(statisticsD1, statisticsD2, statisticsD3, statisticsD4), "2019-01-01",
				"2020-01-02");
		getAndAssertStatistics(accountD, List.of(statisticsD1, statisticsD2), "2020-01-01", "2020-01-01");
		getAndAssertStatistics(accountD, List.of(statisticsD3, statisticsD4), "2020-01-02", "2020-01-03");

		getAndAssertStatistics(accountE, List.of(statisticsE1), "2020-01-01", "2020-01-02");
	}

	@Test
	@DirtiesContext
	public void concurrentWorkflow() throws Exception {
		AccountDTO accountF = new AccountDTO("Account F");
		AccountDTO accountG = new AccountDTO("Account G");

		createAndAssertAccount(accountF);
		createAndAssertAccount(accountG);

		ExecutorService executor = Executors.newFixedThreadPool(10);

		List<Future<?>> futures = new ArrayList<Future<?>>();

		futures.add(executor.submit(() -> {
			addEvents(LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", accountF.getName());
		}));

		futures.add(executor.submit(() -> {
			addEvents(LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", accountF.getName());
		}));

		futures.add(executor.submit(() -> {
			addEvents(LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", accountF.getName());
		}));

		futures.add(executor.submit(() -> {
			addEvents(LocalDateTime.of(2019, 12, 1, 15, 0), "Event_1", accountG.getName());
		}));

		futures.add(executor.submit(() -> {
			addEvents(LocalDateTime.of(2019, 12, 1, 15, 0), "Event_2", accountG.getName());
		}));

		futures.add(executor.submit(() -> {
			addEvents(LocalDateTime.of(2019, 12, 2, 15, 0), "Event_2", accountG.getName());
		}));

		for (Future<?> f : futures) {
			f.get();
		}

		StatisticsDTO statisticsF1 = new StatisticsDTO(LocalDate.of(2019, 12, 1), "Event_1", 1000);
		StatisticsDTO statisticsF2 = new StatisticsDTO(LocalDate.of(2019, 12, 1), "Event_2", 1000);
		StatisticsDTO statisticsF3 = new StatisticsDTO(LocalDate.of(2019, 12, 2), "Event_2", 1000);

		StatisticsDTO statisticsG1 = new StatisticsDTO(LocalDate.of(2019, 12, 1), "Event_1", 1000);
		StatisticsDTO statisticsG2 = new StatisticsDTO(LocalDate.of(2019, 12, 1), "Event_2", 1000);
		StatisticsDTO statisticsG3 = new StatisticsDTO(LocalDate.of(2019, 12, 2), "Event_2", 1000);

		getAndAssertStatistics(accountF, List.of(statisticsF1, statisticsF2, statisticsF3), "2019-01-01", "2020-01-01");
		getAndAssertStatistics(accountF, List.of(statisticsG1, statisticsG2, statisticsG3), "2019-01-01", "2020-01-01");

	}

	private void addEvents(LocalDateTime dateTime, String eventName, String accountName) {
		for (int i = 0; i < 1000; i++) {
			try {
				createAndAssertEvent(new EventDTO(dateTime, eventName), accountName);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void getAndAssertStatistics(AccountDTO account, List<StatisticsDTO> statistics, String startDate,
			String endDate) throws Exception {
		mvc.perform(get("/accounts/" + account.getName() + "/statistics").contentType(MediaType.APPLICATION_JSON)
				.param("startDate", startDate)
				.param("endDate", endDate))
				.andExpect(status().isOk())
				.andExpect(responseBody().containsObjectAsJson(statistics, new TypeReference<List<StatisticsDTO>>() {
				}));
	}

	private void getAndAssertEvents(AccountDTO account, List<EventDTO> events, String startDate, String endDate)
			throws Exception {
		mvc.perform(get("/accounts/" + account.getName() + "/events").param("startDate", startDate)
				.param("endDate", endDate)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(responseBody().containsObjectAsJson(events, new TypeReference<List<EventDTO>>() {
				}));

	}

	private void createAndAssertEvent(EventDTO eventDTO, String accountName) throws Exception {
		mvc.perform(post("/accounts/" + accountName + "/events").content(objectMapper.writeValueAsString(eventDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(responseBody().containsObjectAsJson(eventDTO, EventDTO.class));
	}

	private void createAndAssertAccount(AccountDTO account) throws Exception {
		mvc.perform(post("/accounts").content(objectMapper.writeValueAsString(account))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(responseBody().containsObjectAsJson(account, AccountDTO.class));
	}
}
