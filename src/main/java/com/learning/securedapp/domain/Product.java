package com.learning.securedapp.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
/**
 * <p>Product class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String productId;
	private String productName;
	private String description;
	private String imageUrl;
	private Double price;
	@DBRef(lazy = true)
	private Category category;
}
