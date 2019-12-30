package com.smec.codingchallengewebapi.rest.event;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EventService eventService;

	@Test
	public void getAllEventsWhenAccountNotExists() throws Exception {
		when(eventService.getAllEventsByAccountName("Account A", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 14, 0)))
				.thenThrow(AccountNotFoundException.class);
		mvc.perform(get("/accounts/Account A/events").param("startDate", "2020-01-01T00:00").param("endDate", "2020-01-01T14:00"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getAllEvents() throws Exception {
		EventDTO event1 = new EventDTO(LocalDateTime.of(2020, 1, 1, 12, 0), "Event 1");
		EventDTO event2 = new EventDTO(LocalDateTime.of(2020, 1, 1, 13, 0), "Event 2");
		List<EventDTO> events = List.of(event1, event2);

		when(eventService.getAllEventsByAccountName("Account A", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 14, 0)))
				.thenReturn(events);
		mvc.perform(get("/accounts/Account A/events").param("startDate", "2020-01-01T00:00").param("endDate", "2020-01-01T14:00"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].type", is("Event 1")))
				.andExpect(jsonPath("$[0].happenedAt", is("2020-01-01T12:00:00")))
				.andExpect(jsonPath("$[1].type", is("Event 2")))
				.andExpect(jsonPath("$[1].happenedAt", is("2020-01-01T13:00:00")));
	}

	@Test
	public void createEventWhenAccountNotExists() throws Exception {
		EventDTO event = new EventDTO(LocalDateTime.of(2020, 1, 1, 12, 0), "Event 1");
		when(eventService.createEvent(event, "Account A")).thenThrow(AccountNotFoundException.class);
		mvc.perform(post("/accounts/Account A/events")
				.content("{\"happenedAt\":\"2020-01-01T12:00:00\",\"type\":\"Event 1\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void createEvent() throws Exception {
		EventDTO event = new EventDTO(LocalDateTime.of(2020, 1, 1, 12, 0), "Event 1");
		when(eventService.createEvent(event, "Account A")).thenReturn(event);
		mvc.perform(post("/accounts/Account A/events")
				.content("{\"happenedAt\":\"2020-01-01T12:00:00\",\"type\":\"Event 1\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.type", is("Event 1")))
				.andExpect(jsonPath("$.happenedAt", is("2020-01-01T12:00:00")));
	}

}
