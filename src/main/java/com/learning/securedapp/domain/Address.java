/**
 * 
 */
package com.learning.securedapp.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * @author Raja
 *
 */
@Data
@Document
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;
	private String country;
}
