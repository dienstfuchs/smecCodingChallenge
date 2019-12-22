package com.smec.codingchallengewebapi.rest.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccountAlreadyExistsAdvice {

	@ResponseBody
	@ExceptionHandler(AccountAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String accountAlreadyExistsHandler(AccountAlreadyExistsException ex) {
		return ex.getMessage();
	}

}
