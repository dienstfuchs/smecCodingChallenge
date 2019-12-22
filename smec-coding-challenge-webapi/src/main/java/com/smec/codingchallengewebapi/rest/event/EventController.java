package com.smec.codingchallengewebapi.rest.event;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smec.codingchallengewebapi.entities.Account;
import com.smec.codingchallengewebapi.entities.Event;
import com.smec.codingchallengewebapi.persistence.AccountRepository;
import com.smec.codingchallengewebapi.persistence.EventRepository;
import com.smec.codingchallengewebapi.rest.account.AccountNotFoundException;

@RestController
public class EventController {

	private final AccountRepository accountRepository;
	private final EventRepository eventRepository;

	public EventController(AccountRepository accountRepository, EventRepository eventRepository) {
		this.accountRepository = accountRepository;
		this.eventRepository = eventRepository;
	}

	@GetMapping("/accounts/{accountName}/events")
	public List<Event> getAllEvents(@PathVariable String accountName) {
		Account account = accountRepository.findByName(accountName);
		if (account == null) {
			throw new AccountNotFoundException(accountName);
		}
		return eventRepository.findByAccount(account);
	}

//	@PostMapping
//	Event createEvent(@RequestBody Event event) {
//		//return eventRepository.save(new Account(accountName));
//		return null;
//	}

}
