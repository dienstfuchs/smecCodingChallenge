package com.smec.codingchallengewebapi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ResponseBodyMatchers {

	private final ObjectMapper objectMapper;
	
	public ResponseBodyMatchers() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	public <T> ResultMatcher containsObjectAsJson(Object expectedObject, TypeReference<T> valueTypeRef) {
		return mvcResult -> {
			String json = mvcResult.getResponse().getContentAsString();
			T actualObject = objectMapper.readValue(json, valueTypeRef);
			assertThat(actualObject, is(expectedObject));
		};
	}
	
	public <T> ResultMatcher containsObjectAsJson(Object expectedObject, Class<T> targetClass) {
		return mvcResult -> {
			String json = mvcResult.getResponse().getContentAsString();
			T actualObject = objectMapper.readValue(json, targetClass);
			assertThat(actualObject, is(expectedObject));
		};
	}

	static ResponseBodyMatchers responseBody() {
		return new ResponseBodyMatchers();
	}

}
