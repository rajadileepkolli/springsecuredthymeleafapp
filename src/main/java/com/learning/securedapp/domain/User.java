package com.learning.securedapp.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class User {

    @Id
    private String id;

    @Indexed
    private String userName;

    private String password;
    
    private String passwordResetToken;

    @NotNull
    private String email;
    
    private boolean enabled = false;

    @DBRef(lazy= true)
    private List<Role> roleList;
}
