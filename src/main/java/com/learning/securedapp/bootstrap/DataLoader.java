package com.learning.securedapp.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Product;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.repositories.PermissionRepository;
import com.learning.securedapp.web.repositories.ProductRepository;
import com.learning.securedapp.web.repositories.RoleRepository;
import com.learning.securedapp.web.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor // From spring 4.3 by creating constructor we can autowire all objects
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (productRepository.findAll().size() <= 0) {
            Product shirt = new Product();
            shirt.setDescription("Spring Framework Guru Shirt");
            shirt.setPrice(new BigDecimal("18.95"));
            shirt.setImageUrl(
                    "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
            productRepository.save(shirt);

            log.info("Saved Shirt - id: " + shirt.getProductId());

            Product mug = new Product();
            mug.setDescription("Spring Framework Guru Mug");
            mug.setImageUrl(
                    "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
            productRepository.save(mug);

            log.info("Saved Mug - id:" + mug.getProductId());
        }
        if (permissionRepository.findAll().size() <= 0) {
            List<String> permissionList = new ArrayList<String>();
            permissionList.addAll(Arrays.asList("MANAGE_USERS", "MANAGE_ROLES",
                    "MANAGE_PERMISSIONS", "MANAGE_SETTINGS", "MANAGE_PRODUCTS"));
            for (String string : permissionList) {
                Permission permission = new Permission();
                permission.setName(string);
                permissionRepository.save(permission);
            }

        }
        if (roleRepository.findAll().size() <= 0) {
            List<String> roleList = new ArrayList<String>();
            roleList.addAll(
                    Arrays.asList("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_CMS_ADMIN", "ROLE_USER"));
            for (String string : roleList) {
                Role role = new Role();
                role.setRoleName(string);
                role.setPermissions(Arrays
                        .asList(permissionRepository.findByName("MANAGE_SETTINGS")));
                roleRepository.save(role);
            }
            Role role = roleRepository.findByRoleName("ROLE_SUPER_ADMIN");
            role.setPermissions(permissionRepository.findAll());
            roleRepository.save(role);
        }
        if (userRepository.findAll().size() <= 0) {
            User user = new User();
            user.setUserName("superadmin");
            user.setPassword(new BCryptPasswordEncoder(10).encode("superadmin"));
            user.setEmail("rajadileepkolli@gmail.com");
            user.setEnabled(true);
            user.setRoleList(roleRepository.findAll());
            userRepository.save(user);
        }
    }
}
