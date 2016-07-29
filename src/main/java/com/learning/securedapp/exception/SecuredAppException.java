package com.learning.securedapp.exception;

public class SecuredAppException extends Exception {

	private static final long serialVersionUID = 8900583085166149180L;

	private SecuredAppExceptionBean faultBean;

	public SecuredAppException(String message) {
		super(message);
	}

	public SecuredAppException(SecuredAppExceptionBean faultBean) {
		super(faultBean.getFaultString());
		this.faultBean = faultBean;
	}

	public SecuredAppException(String message, SecuredAppExceptionBean faultinfo) {
		super(message);
		this.faultBean = faultinfo;
	}

	public SecuredAppException(String message, SecuredAppExceptionBean faultinfo, Throwable cause) {
		super(message, cause);
		this.faultBean = faultinfo;
	}

	public SecuredAppException(String code, String message) {
		super(message);
		this.faultBean = new SecuredAppExceptionBean();
		this.faultBean.setFaultCode(code);
		this.faultBean.setFaultString(message);
	}

	public SecuredAppException(SecuredAppExceptionBean faultBean, Exception e) {
		super(faultBean.getFaultString());
		this.faultBean = faultBean;
	}

	public SecuredAppException(String message, SecuredAppException e) {
		super(e);
	}

	public SecuredAppExceptionBean getFaultBean() {
		return faultBean;
	}

}
