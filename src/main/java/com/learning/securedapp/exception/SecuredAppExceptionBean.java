package com.learning.securedapp.exception;

import lombok.Data;

@Data
/**
 * SecuredAppExceptionBean class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class SecuredAppExceptionBean {
    private String faultCode;
    private String faultString;
}
