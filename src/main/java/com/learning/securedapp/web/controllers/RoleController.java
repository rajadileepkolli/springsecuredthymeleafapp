package com.learning.securedapp.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.security.SecurityUtil;
import com.learning.securedapp.web.services.SecurityService;
import com.learning.securedapp.web.validators.RoleValidator;

import lombok.extern.slf4j.Slf4j;

@Controller
/**
 * <p>RoleController class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Slf4j
@Secured(SecurityUtil.MANAGE_ROLES)
public class RoleController extends SecuredAppBaseController {
	private static final String VIEWPREFIX = "roles/";

	@Autowired
	private SecurityService securityService;
	@Autowired
	private RoleValidator roleValidator;

	/**
	 * <p>permissionsList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	@ModelAttribute("permissionsList")
	public List<Permission> permissionsList() {
		return securityService.getAllPermissions();
	}

	/**
	 * <p>listRoles.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/roles")
	public String listRoles(Model model) {
		List<Role> list = securityService.getAllRoles();
		model.addAttribute("roles", list);
		return VIEWPREFIX + "roles";
	}

	/**
	 * <p>createRoleForm.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/roles/new")
	public String createRoleForm(Model model) {
		Role role = Role.builder().build();
		model.addAttribute("role", role);
		// model.addAttribute("permissionsList",securityService.getAllPermissions());

		return VIEWPREFIX + "create_role";
	}

	/**
	 * <p>createRole.</p>
	 *
	 * @param role a {@link com.learning.securedapp.domain.Role} object.
	 * @param result a {@link org.springframework.validation.BindingResult} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @param redirectAttributes a {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
	 * @return a {@link java.lang.String} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	@PostMapping(value = "/roles")
	public String createRole(@Valid @ModelAttribute("role") Role role, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) throws SecuredAppException {
		roleValidator.validate(role, result);
		if (result.hasErrors()) {
			return VIEWPREFIX + "create_role";
		}
		try {
			Role persistedRole = securityService.createRole(role);
			log.debug("Created new role with id : {} and name : {}", persistedRole.getId(),
					persistedRole.getRoleName());
			redirectAttributes.addFlashAttribute("success",
					"Role " + persistedRole.getRoleName() + " created successfully");
		} catch (SecuredAppException e) {
			model.addAttribute("errorMessage", e.getMessage());
			redirectAttributes.addFlashAttribute("error", "Role " + role.getRoleName() + " was not created");
			throw new SecuredAppException("Role " + role.getRoleName() + " was not created", e);
		}
		return "redirect:/roles";
	}

	/**
	 * <p>editRoleForm.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/roles/{id}")
	public String editRoleForm(@PathVariable String id, Model model) {
		Role role = securityService.getRoleById(id);
		Map<String, Permission> assignedPermissionMap = new HashMap<>();
		List<Permission> permissions = role.getPermissions();
		for (Permission permission : permissions) {
			assignedPermissionMap.put(permission.getId(), permission);
		}
		List<Permission> rolePermissions = new ArrayList<>();
		List<Permission> allPermissions = securityService.getAllPermissions();
		for (Permission permission : allPermissions) {
			if (assignedPermissionMap.containsKey(permission.getId())) {
				rolePermissions.add(permission);
			} else {
				rolePermissions.add(null);
			}
		}
		role.setPermissions(rolePermissions);
		model.addAttribute("role", role);
		// model.addAttribute("permissionsList",allPermissions);
		return VIEWPREFIX + "edit_role";
	}

	/**
	 * <p>updateRole.</p>
	 *
	 * @param role a {@link com.learning.securedapp.domain.Role} object.
	 * @param result a {@link org.springframework.validation.BindingResult} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @param redirectAttributes a {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
	 * @return a {@link java.lang.String} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	@PostMapping(value = "/roles/{id}")
	public String updateRole(@ModelAttribute("role") Role role, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) throws SecuredAppException {
		try {
			Role persistedRole = securityService.updateRole(role, role.getId());
			log.debug("Updated role with id : {} and name : {}", persistedRole.getId(), persistedRole.getRoleName());
			redirectAttributes.addFlashAttribute("success",
					"Role updated successfully for " + persistedRole.getRoleName());
		} catch (SecuredAppException e) {
			model.addAttribute("errorMessage", e.getMessage());
			throw new SecuredAppException(e.getMessage());
		}

		return "redirect:/roles";
	}

	// Delete
	/**
	 * <p>delete.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping("/roles/delete/{id}")
	public String delete(@PathVariable String id) {
		securityService.deleteRole(id);
		return "redirect:/roles";
	}
}
