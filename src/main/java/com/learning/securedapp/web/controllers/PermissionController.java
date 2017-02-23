package com.learning.securedapp.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.security.SecurityUtil;
import com.learning.securedapp.web.services.SecurityService;
import com.learning.securedapp.web.validators.PermissionValidator;

import lombok.extern.slf4j.Slf4j;

@Controller
/**
 * <p>PermissionController class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Slf4j
@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends SecuredAppBaseController {
	private static final String viewPrefix = "permissions/";

	@Autowired
	protected SecurityService securityService;

	@Autowired
	private PermissionValidator permissionValidator;

	/**
	 * <p>listPermissions.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/permissions")
	public String listPermissions(Model model) {
		List<Permission> list = securityService.getAllPermissions();
		model.addAttribute("permissions", list);
		return viewPrefix + "permissions";
	}

	/**
	 * <p>createPermissionForm.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/permissions/new")
	public String createPermissionForm(Model model) {
		Permission permission = new Permission();
		model.addAttribute("permission", permission);
		return viewPrefix + "create_permission";
	}

	/**
	 * <p>createPermission.</p>
	 *
	 * @param permission a {@link com.learning.securedapp.domain.Permission} object.
	 * @param result a {@link org.springframework.validation.BindingResult} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @param redirectAttributes a {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
	 * @return a {@link java.lang.String} object.
	 */
	@PostMapping(value = "/permissions/new")
	public String createPermission(@Valid @ModelAttribute("permission") Permission permission, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		permissionValidator.validate(permission, result);
		if (result.hasErrors()) {
			return viewPrefix + "create_role";
		}
		Permission persistedPermission = securityService.createPermission(permission);
		log.debug("Created new permission with id : {} and name : {}", persistedPermission.getId(),
				persistedPermission.getName());
		redirectAttributes.addFlashAttribute("msg",
				"Pemission " + persistedPermission.getName() + " is created successfully");
		return "redirect:/permissions";
	}

}
