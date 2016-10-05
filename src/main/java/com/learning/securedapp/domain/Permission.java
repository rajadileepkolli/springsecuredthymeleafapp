package com.learning.securedapp.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
/**
 * <p>Permission class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Document
public class Permission implements Serializable {

	private static final long serialVersionUID = -6260569351005920639L;

	@Id
	private String id;
	@NotNull
	private String name;
	private String description;
}
