package com.learning.securedapp.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Product {
    
    @Id
    private String productId;

    private String description;
    private String imageUrl;
    private BigDecimal price;

}
