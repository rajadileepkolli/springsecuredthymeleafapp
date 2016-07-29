package com.learning.securedapp.web.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddToCartForm {

	@NotNull
	private String productId;
	@Min(1)
	@Max(50)
	private int quantity;
	private String categoryId;

}
