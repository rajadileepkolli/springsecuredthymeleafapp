package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * CartForm class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Data
public class CartForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty @NotNull private Set<Integer> lineNo;
}
