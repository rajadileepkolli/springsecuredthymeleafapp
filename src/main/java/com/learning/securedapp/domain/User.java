package com.learning.securedapp.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;

@Document
@Data
public class User {

    @Id
    private String id;

    @Indexed
    private String userName;

    private String password;
    
    @Getter
    private String passwordRepeated;

    private String passwordResetToken;

    @NotNull
    private String email;

    @DBRef(lazy= true)
    private List<Role> roleList;
}
