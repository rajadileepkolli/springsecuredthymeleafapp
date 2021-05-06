package com.learning.securedapp.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Data
/**
 * GenericResponse class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@AllArgsConstructor
public class GenericResponse {

    private String message;
    private String error;

    /**
     * Constructor for GenericResponse.
     *
     * @param message a {@link java.lang.String} object.
     */
    public GenericResponse(final String message) {
        super();
        this.message = message;
    }

    /**
     * Constructor for GenericResponse.
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
