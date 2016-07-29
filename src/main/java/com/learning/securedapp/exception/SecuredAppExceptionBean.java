package com.learning.securedapp.exception;

import lombok.Data;

@Data
public class SecuredAppExceptionBean {
	private String faultCode;
	private String faultString;
}
