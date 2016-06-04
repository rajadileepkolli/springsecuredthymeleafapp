package com.learning.securedapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Permission {
    @Id
    private String id;
    private String name;
    private String description;
}
