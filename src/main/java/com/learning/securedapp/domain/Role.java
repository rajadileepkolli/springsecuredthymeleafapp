package com.learning.securedapp.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Role {
    @Id
    private String id;
    
    @Indexed
    @NotNull
    private String roleName;
    private String description;
    @DBRef(lazy= true)
    private List<Permission> permissions;
}
