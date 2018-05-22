package com.learning.securedapp.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Product;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.repositories.CategoryRepository;
import com.learning.securedapp.web.repositories.PermissionRepository;
import com.learning.securedapp.web.repositories.ProductRepository;
import com.learning.securedapp.web.repositories.RoleRepository;
import com.learning.securedapp.web.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor // From spring 4.3 by creating constructor we can autowire
                    // all objects
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private CategoryRepository categoryRepository;

    /** {@inheritDoc} */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryRepository.findAll().isEmpty()) {
            Category clothingCategory = new Category();
            clothingCategory.setCategoryId("1");
            clothingCategory.setCategoryName("Clothing");
            Category foodCategory = new Category();
            foodCategory.setCategoryId("2");
            foodCategory.setCategoryName("Food");
            Category mobileCategory = new Category();
            mobileCategory.setCategoryId("3");
            mobileCategory.setCategoryName("Mobile");
            categoryRepository.saveAll(
                    Arrays.asList(clothingCategory, foodCategory, mobileCategory));
        }

        if (productRepository.findAll().isEmpty()) {
            Product shirt = Product.builder().productName("Shirt")
                    .description("Spring Framework Guru Shirt")
                    .price(Double.valueOf("18.95"))
                    .category(categoryRepository.findById("1").orElse(null))
                    .imageUrl(
                            "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg")
                    .build();
            productRepository.save(shirt);

            log.info("Saved Shirt - id: " + shirt.getProductId());

            Product mug = Product.builder().productName("Mug")
                    .description("Spring Framework Guru Mug")
                    .imageUrl(
                            "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg")
                    .category(categoryRepository.findById("1").orElse(null)).build();
            productRepository.save(mug);

            log.info("Saved Mug - id:" + mug.getProductId());
        }
        if (permissionRepository.findAll().isEmpty()) {
            List<String> permissionList = new ArrayList<>();
            permissionList.addAll(Arrays.asList("MANAGE_USERS", "MANAGE_ROLES",
                    "MANAGE_PERMISSIONS", "MANAGE_SETTINGS", "MANAGE_PRODUCTS"));
            for (String string : permissionList) {
                Permission permission = new Permission();
                permission.setName(string);
                permissionRepository.save(permission);
            }
        }
        if (roleRepository.findAll().isEmpty()) {
            List<String> roleList = new ArrayList<>();
            roleList.addAll(Arrays.asList("ROLE_SUPER_ADMIN", "ROLE_ADMIN",
                    "ROLE_CMS_ADMIN", "ROLE_USER"));
            for (String string : roleList) {
                Role role = Role.builder().roleName(string)
                        .permissions(Arrays.asList(
                                permissionRepository.findByName("MANAGE_SETTINGS")))
                        .build();
                roleRepository.save(role);
            }
            Role role = roleRepository.findByRoleName("ROLE_SUPER_ADMIN");
            role.setPermissions(permissionRepository.findAll());
            roleRepository.save(role);
        }
        if (userRepository.findAll().isEmpty()) {
            User user = User.builder().userName("superadmin")
                    .password(new BCryptPasswordEncoder(10).encode("superadmin"))
                    .email("rajadileepkolli@gmail.com").enabled(true)
                    .roleList(roleRepository.findAll()).build();
            userRepository.save(user);
        }
    }
}
