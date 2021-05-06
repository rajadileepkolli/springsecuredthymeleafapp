package com.learning.securedapp.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Product class.
 *
 * @author rajakolli
 * @version 1: 0
 */
@Setter
@Getter
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id private String productId;
    private String productName;
    private String description;
    private String imageUrl;
    private Double price;

    @DBRef(lazy = true)
    private Category category;
}
