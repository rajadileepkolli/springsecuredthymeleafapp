/** */
package com.learning.securedapp.domain;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Address class.
 *
 * @author Raja
 * @version $Id: $Id
 */
@Data
@Document
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id private String id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
