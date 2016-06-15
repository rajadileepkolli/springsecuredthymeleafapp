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
@Slf4j
@Secured(SecurityUtil.MANAGE_ROLES)
public class RoleController extends SecuredAppBaseController
{
    private static final String viewPrefix = "roles/";
    
    @Autowired protected SecurityService securityService;
    @Autowired private RoleValidator roleValidator;

    @Override
    protected String getHeaderTitle()
    {
        return "Manage Roles";
    }
    
    @ModelAttribute("permissionsList")
    public List<Permission> permissionsList(){
        return securityService.getAllPermissions();
    }
    
    @GetMapping(value="/roles")
    public String listRoles(Model model) {
        List<Role> list = securityService.getAllRoles();
        model.addAttribute("roles",list);
        return viewPrefix+"roles";
    }
    
    @GetMapping(value="/roles/new")
    public String createRoleForm(Model model) {
        Role role = new Role();
        model.addAttribute("role",role);
        //model.addAttribute("permissionsList",securityService.getAllPermissions());        
        
        return viewPrefix+"create_role";
    }

    @PostMapping(value="/roles")
    public String createRole(@Valid @ModelAttribute("role") Role role, BindingResult result, 
            Model model, RedirectAttributes redirectAttributes) {
        roleValidator.validate(role, result);
        if(result.hasErrors()){
            return viewPrefix+"create_role";
        }
        try {
            Role persistedRole = securityService.createRole(role);
            log.debug("Created new role with id : {} and name : {}", persistedRole.getId(), persistedRole.getRoleName());
        } catch (SecuredAppException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/accessDenied";
        }
        redirectAttributes.addFlashAttribute("success", "Role created successfully");
        return "redirect:/roles";
    }
    
    
    @GetMapping(value="/roles/{id}")
    public String editRoleForm(@PathVariable String id, Model model) {
        Role role = securityService.getRoleById(id);
        Map<String, Permission> assignedPermissionMap = new HashMap<>();
        List<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions)
        {
            assignedPermissionMap.put(permission.getId(), permission);
        }
        List<Permission> rolePermissions = new ArrayList<>();
        List<Permission> allPermissions = securityService.getAllPermissions();
        for (Permission permission : allPermissions)
        {
            if(assignedPermissionMap.containsKey(permission.getId())){
                rolePermissions.add(permission);
            } else {
                rolePermissions.add(null);
            }
        }
        role.setPermissions(rolePermissions);
        model.addAttribute("role",role);
        //model.addAttribute("permissionsList",allPermissions);     
        return viewPrefix+"edit_role";
    }
    
    @PostMapping(value="/roles/{id}")
    public String updateRole(@ModelAttribute("role") Role role, BindingResult result, 
            Model model, RedirectAttributes redirectAttributes) {       
        try {
            Role persistedRole = securityService.updateRole(role);
            log.debug("Updated role with id : {} and name : {}", persistedRole.getId(), persistedRole.getRoleName());
            redirectAttributes.addFlashAttribute("msg", "Role updated successfully for "+ persistedRole.getRoleName());
        } catch (SecuredAppException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/error/accessDenied";
        }
        
        return "redirect:/roles";
    }
    
}

