package com.learning.securedapp.web.utils;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {

	private String message;
	private String error;

	public GenericResponse(final String message) {
		super();
		this.message = message;
	}

	public GenericResponse(List<FieldError> fieldErrors, List<ObjectError> globalErrors) {
		super();
		final ObjectMapper mapper = new ObjectMapper();
		try {
			this.message = mapper.writeValueAsString(fieldErrors);
			this.error = mapper.writeValueAsString(globalErrors);
		} catch (final JsonProcessingException e) {
			this.message = "";
			this.error = "";
		}
	}

}
