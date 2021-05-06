package com.learning.securedapp.web.validators;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.web.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
/**
 * CategoryValidator class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class CategoryValidator implements Validator {

    private final CategoryService categoryService;

    /** {@inheritDoc} */
    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryValidator.class.isAssignableFrom(clazz);
    }

    /** {@inheritDoc} */
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "categoryName", "message.categoryName", "CategoryName is Mandatory");
        Category category = (Category) target;
        Category perisistedCategory = categoryService.getbyName(category.getCategoryName());
        if (perisistedCategory != null) {
            errors.rejectValue(
                    "name",
                    "error.exists",
                    new Object[] {category.getCategoryName()},
                    "Category " + category.getCategoryName() + " already exists");
        }
    }
}
