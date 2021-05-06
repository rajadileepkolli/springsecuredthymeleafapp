package com.learning.securedapp.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Data
@Document
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id private String categoryId;

    @NotNull private String categoryName;
}
