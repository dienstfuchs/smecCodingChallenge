package com.smec.codingchallengewebapi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

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
	private TestHelper testHelper;

	@Test
	public void deleteEventsWithScheduler() throws Exception {
		AccountDTO accountA = new AccountDTO(UUID.randomUUID().toString());

		testHelper.createAndAssertAccount(accountA);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowNotExact = LocalDateTime.of(now.toLocalDate(), LocalTime.of(now.getHour(),  now.getMinute()));
		
		EventDTO event1 = new EventDTO(nowNotExact.minusDays(31), "Event 1");
		EventDTO event2 = new EventDTO(nowNotExact.minusDays(35), "Event 2");
		EventDTO event3 = new EventDTO(nowNotExact.minusDays(20), "Event 3");
		EventDTO event4 = new EventDTO(nowNotExact.minusDays(1), "Event 4");

		testHelper.createAndAssertEvent(event1, accountA.getName());
		testHelper.createAndAssertEvent(event2, accountA.getName());
		testHelper.createAndAssertEvent(event3, accountA.getName());
		testHelper.createAndAssertEvent(event4, accountA.getName());

		Thread.sleep(5000);

		testHelper.getAndAssertEvents(accountA, List.of(event3, event4), LocalDateTime.now().minusDays(32).toString(),
				LocalDateTime.now().toString());
	}



}
