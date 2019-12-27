package com.smec.codingchallengewebapi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.rest.event.EventDTO;

public class EventConverterTest {

	@Test
	public void convertToDTO() {
		// given
		Event event = new Event(LocalDateTime.now(), "Event 1", new Account("Account A"));

		// when
		EventDTO eventDTO = new EventConverter().toDTO(event);

		// then
		assertThat(eventDTO.getType(), is(equalTo(event.getType())));
		assertThat(eventDTO.getHappenedAt(), is(equalTo(event.getHappenedAt())));
	}

	@Test
	public void convertNullToDTO() {
		// given

		// when
		EventDTO eventDTO = new EventConverter().toDTO(null);

		// then
		assertThat(eventDTO, is(nullValue()));
	}

	@Test
	public void convertToEntity() {
		// given
		EventDTO eventDTO = new EventDTO(LocalDateTime.now(), "Event 1");
		Account account = new Account("Account A");

		// when
		Event event = new EventConverter().toEntity(eventDTO, account);

		// then
		assertThat(event.getType(), is(equalTo(eventDTO.getType())));
		assertThat(event.getHappenedAt(), is(equalTo(eventDTO.getHappenedAt())));
		assertThat(event.getAccount().getName(), is(equalTo(account.getName())));
	}

	@Test
	public void convertNullToEntity() {
		// given

		// when
		Event event = new EventConverter().toEntity(null, new Account("Account"));

		// then
		assertThat(event, is(nullValue()));
	}

}
