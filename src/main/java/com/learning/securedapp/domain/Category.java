package com.learning.securedapp.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
/**
 * <p>Category class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String categoryId;

	@NotNull
	private String categoryName;
}
