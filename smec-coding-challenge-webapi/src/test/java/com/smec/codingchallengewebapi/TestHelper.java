package com.smec.codingchallengewebapi;

import static com.smec.codingchallengewebapi.ResponseBodyMatchers.responseBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smec.codingchallengewebapi.rest.account.AccountDTO;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

@Component
public class TestHelper {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;
	
	public void getAndAssertEvents(AccountDTO account, List<EventDTO> events, String startDate, String endDate)
			throws Exception {
		mvc.perform(get("/accounts/" + account.getName() + "/events").param("startDate", startDate)
				.param("endDate", endDate)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(responseBody().containsObjectAsJson(events, new TypeReference<List<EventDTO>>() {
				}));
	}
	
	public void createAndAssertEvent(EventDTO eventDTO, String accountName) throws Exception {
		mvc.perform(post("/accounts/" + accountName + "/events").content(objectMapper.writeValueAsString(eventDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(responseBody().containsObjectAsJson(eventDTO, EventDTO.class));
	}

	public void createAndAssertAccount(AccountDTO account) throws Exception {
		mvc.perform(post("/accounts").content(objectMapper.writeValueAsString(account))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(responseBody().containsObjectAsJson(account, AccountDTO.class));
	}
	
}
