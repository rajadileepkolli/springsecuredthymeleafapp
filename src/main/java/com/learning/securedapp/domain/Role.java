package com.learning.securedapp.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class Role {
    @Id
    private String id;
    private String roleName;
    private String description;
    @DBRef(lazy= true)
    private List<Permission> permissions;
}
