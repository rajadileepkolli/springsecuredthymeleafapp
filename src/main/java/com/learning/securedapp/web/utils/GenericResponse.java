package com.learning.securedapp.web.utils;

import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
/**
 * <p>GenericResponse class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@AllArgsConstructor
public class GenericResponse {

	private String message;
	private String error;

	/**
	 * <p>Constructor for GenericResponse.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public GenericResponse(final String message) {
		super();
		this.message = message;
	}

	/**
	 * <p>Constructor for GenericResponse.</p>
	 *
	 * @param fieldErrors a {@link java.util.List} object.
	 * @param globalErrors a {@link java.util.List} object.
	 */
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
