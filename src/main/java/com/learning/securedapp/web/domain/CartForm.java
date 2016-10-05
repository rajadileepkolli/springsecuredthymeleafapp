package com.learning.securedapp.web.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
/**
 * <p>CartForm class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class CartForm implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty
	@NotNull
	private Set<Integer> lineNo;
}
