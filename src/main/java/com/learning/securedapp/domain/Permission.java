package com.learning.securedapp.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Permission {
    @Id
    private String id;
    @NotNull
    private String name;
    private String description;
}
