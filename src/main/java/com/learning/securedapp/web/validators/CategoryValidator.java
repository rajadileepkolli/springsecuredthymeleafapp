package com.learning.securedapp.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.web.services.CategoryService;

@Component
/**
 * <p>CategoryValidator class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class CategoryValidator implements Validator {

	@Autowired
	CategoryService categoryService;

	/** {@inheritDoc} */
	@Override
	public boolean supports(Class<?> clazz) {
		return CategoryValidator.class.isAssignableFrom(clazz);
	}

	/** {@inheritDoc} */
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", "message.categoryName",
				"CategoryName is Mandatory");
		Category category = (Category) target;
		Category perisistedCategory = categoryService.getbyName(category.getCategoryName());
		if (perisistedCategory != null) {
			errors.rejectValue("name", "error.exists", new Object[] { category.getCategoryName() },
					"Category " + category.getCategoryName() + " already exists");
		}
	}
}
