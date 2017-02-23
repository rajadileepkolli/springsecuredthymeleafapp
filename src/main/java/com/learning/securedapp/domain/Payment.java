package com.learning.securedapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * <p>Payment class.</p>
 *
 * @author Raja
 * @version $Id: $Id
 */
@Data
@Document(collection = "payments")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Field(value = "cc_Number")
	private String ccNumber;
	private String cvv;
	private BigDecimal amount;

}
