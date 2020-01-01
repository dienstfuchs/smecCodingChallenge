package com.smec.codingchallengewebapi;

import static com.smec.codingchallengewebapi.ResponseBodyMatchers.responseBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "deleteeventsscheduler.cron=0/5 * * ? * *" })
@EnableScheduling
@AutoConfigureMockMvc
public class DeleteEventsSchedulerIntegrationTests {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	@Test
	public void deleteEventsWithScheduler() throws Exception {
		AccountDTO accountA = new AccountDTO(UUID.randomUUID().toString());

		createAndAssertAccount(accountA);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowNotExact = LocalDateTime.of(now.toLocalDate(), LocalTime.of(now.getHour(),  now.getMinute()));
		
		EventDTO event1 = new EventDTO(nowNotExact.minusDays(31), "Event 1");
		EventDTO event2 = new EventDTO(nowNotExact.minusDays(35), "Event 2");
		EventDTO event3 = new EventDTO(nowNotExact.minusDays(20), "Event 3");
		EventDTO event4 = new EventDTO(nowNotExact.minusDays(1), "Event 4");

		createAndAssertEvent(event1, accountA.getName());
		createAndAssertEvent(event2, accountA.getName());
		createAndAssertEvent(event3, accountA.getName());
		createAndAssertEvent(event4, accountA.getName());

		Thread.sleep(5000);

		getAndAssertEvents(accountA, List.of(event3, event4), LocalDateTime.now().minusDays(32).toString(),
				LocalDateTime.now().toString());
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
