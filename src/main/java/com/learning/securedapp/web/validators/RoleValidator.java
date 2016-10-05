package com.learning.securedapp.web.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.securedapp.domain.Role;
import com.learning.securedapp.web.services.SecurityService;

import lombok.AllArgsConstructor;

@Component
/**
 * <p>RoleValidator class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@AllArgsConstructor // used for creating autowired bean for securityService
public class RoleValidator implements Validator {

	private SecurityService securityService;

	/** {@inheritDoc} */
	@Override
	public boolean supports(Class<?> clazz) {
		return RoleValidator.class.isAssignableFrom(clazz);
	}

	/** {@inheritDoc} */
	@Override
	public void validate(Object target, Errors errors) {
		Role role = (Role) target;
		String name = role.getRoleName();
		if (StringUtils.isNotBlank(name)) {
			Role roleByName = securityService.getRoleByName(name);
			if (roleByName != null) {
				errors.rejectValue("roleName", "error.exists", new Object[] { name },
						"Role " + name + " already exists");
			}
		} else {
			errors.rejectValue("roleName", "error.required", new Object[] { name }, "RoleName cannot be empty");
		}
	}

}
