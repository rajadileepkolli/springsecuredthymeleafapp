package com.learning.securedapp.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.security.SecurityUtil;
import com.learning.securedapp.web.services.SecurityService;
import com.learning.securedapp.web.validators.UserValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * <p>UserController class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Controller
@Secured(SecurityUtil.MANAGE_USERS)
public class UserController extends SecuredAppBaseController {

	private static final String viewPrefix = "users/";

	@Autowired
	protected SecurityService securityService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * <p>rolesList.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	@ModelAttribute("rolesList")
	public List<Role> rolesList() {
		return securityService.getAllRoles();
	}

	/**
	 * <p>listUsers.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/users")
	public String listUsers(Model model) {
		List<User> list = securityService.getAllUsers();
		model.addAttribute("users", list);
		return viewPrefix + "users";
	}

	/**
	 * <p>createUserForm.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping(value = "/users/new")
	public String createUserForm(Model model) {
		User user = User.builder().build();
		model.addAttribute("user", user);
		return viewPrefix + "create_user";
	}

	/**
	 * <p>createUser.</p>
	 *
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 * @param result a {@link org.springframework.validation.BindingResult} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @param redirectAttributes a {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
	 * @return a {@link java.lang.String} object.
	 */
	@PostMapping(value = "/users")
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return viewPrefix + "create_user";
		}
		String password = user.getPassword();
		String encodedPwd = passwordEncoder.encode(password);
		user.setPassword(encodedPwd);
		user.setEnabled(true);
		User persistedUser = securityService.createUser(user);
		log.debug("Created new User with id : {} and name : {}", persistedUser.getId(), persistedUser.getUserName());
		redirectAttributes.addFlashAttribute("success",
				"User :: { " + persistedUser.getUserName() + " } created successfully");
		return "redirect:/users";
	}

	/**
	 * <p>editUserForm.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @return a {@link java.lang.String} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	@Secured(value = { "ROLE_USER" })
	@GetMapping(value = "/users/{id}")
	public String editUserForm(@PathVariable String id, Model model) throws SecuredAppException {
		User user = securityService.getUserById(id);
		if (null == user) {
			throw new SecuredAppException("409", "User Doesn't Exists");
		}

		List<String> assignedRoleList = user.getRoleList().parallelStream().map(role -> role.getId())
				.collect(Collectors.toList());

		List<Role> userRoles = new ArrayList<>();
		List<Role> allRoles = securityService.getAllRoles();
		for (Role role : allRoles) {
			if (assignedRoleList.contains(role.getId())) {
				userRoles.add(role);
			} else {
				userRoles.add(null);
			}
		}
		user.setRoleList(userRoles);
		model.addAttribute("user", user);
		// model.addAttribute("rolesList",allRoles);
		return viewPrefix + "edit_user";
	}

	/**
	 * <p>updateUser.</p>
	 *
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 * @param result a {@link org.springframework.validation.BindingResult} object.
	 * @param model a {@link org.springframework.ui.Model} object.
	 * @param redirectAttributes a {@link org.springframework.web.servlet.mvc.support.RedirectAttributes} object.
	 * @return a {@link java.lang.String} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	@PostMapping(value = "/users/{id}")
	public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) throws SecuredAppException {
		if (result.hasErrors()) {
			return viewPrefix + "edit_user";
		}
		User persistedUser = securityService.updateUser(user, user.getId());
		log.debug("Updated user with id : {} and name : {}", persistedUser.getId(), persistedUser.getUserName());
		redirectAttributes.addFlashAttribute("msg", persistedUser.getUserName() + " updated successfully");
		return "redirect:/users";
	}

	// Delete
	/**
	 * <p>delete.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	@GetMapping("user/delete/{id}")
	public String delete(@PathVariable String id) {
		securityService.deleteUser(id);
		return "redirect:/users";
	}

}
