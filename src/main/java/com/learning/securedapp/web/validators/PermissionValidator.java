package com.learning.securedapp.web.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.web.services.SecurityService;

import lombok.AllArgsConstructor;

@Component
/**
 * <p>PermissionValidator class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@AllArgsConstructor
public class PermissionValidator implements Validator {

	private SecurityService securityService;

	/** {@inheritDoc} */
	@Override
	public boolean supports(Class<?> clazz) {
		return PermissionValidator.class.isAssignableFrom(clazz);
	}

	/** {@inheritDoc} */
	@Override
	public void validate(Object target, Errors errors) {
		Permission permission = (Permission) target;
		String name = permission.getName();
		if (StringUtils.isBlank(name)) {
			errors.rejectValue("name", "error.required", new Object[] { permission.getName() },
					"PermissionName cannot be empty");
		} else {
			Permission permissionByName = securityService.getPermissionByName(name);
			if (permissionByName != null) {
				errors.rejectValue("name", "error.exists", new Object[] { name },
						"Permission " + name + " already exists");
			}
		}
	}

}
