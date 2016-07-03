package com.learning.securedapp.web.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
public class CartForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty
    @NotNull
    private Set<Integer> lineNo;
}
