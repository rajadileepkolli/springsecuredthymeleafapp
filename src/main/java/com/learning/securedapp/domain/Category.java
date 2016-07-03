package com.learning.securedapp.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Category implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    private String categoryId;
    
    @NotNull
    private String categoryName;
}
