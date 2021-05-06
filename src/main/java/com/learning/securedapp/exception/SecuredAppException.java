package com.learning.securedapp.exception;

/**
 * SecuredAppException class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class SecuredAppException extends Exception {

    private static final long serialVersionUID = 8900583085166149180L;

    private SecuredAppExceptionBean faultBean;

    /**
     * Constructor for SecuredAppException.
     *
     * @param message a {@link java.lang.String} object.
     */
    public SecuredAppException(String message) {
        super(message);
    }

    /**
     * Constructor for SecuredAppException.
     *
     * @param faultBean a {@link com.learning.securedapp.exception.SecuredAppExceptionBean} object.
     */
    public SecuredAppException(SecuredAppExceptionBean faultBean) {
        super(faultBean.getFaultString());
        this.faultBean = faultBean;
    }

    /**
     * Constructor for SecuredAppException.
     *
     * @param message a {@link java.lang.String} object.
     * @param faultinfo a {@link com.learning.securedapp.exception.SecuredAppExceptionBean} object.
     */
    public SecuredAppException(String message, SecuredAppExceptionBean faultinfo) {
        super(message);
        this.faultBean = faultinfo;
    }

    /**
     * Constructor for SecuredAppException.
     *
     * @param message a {@link java.lang.String} object.
     * @param faultinfo a {@link com.learning.securedapp.exception.SecuredAppExceptionBean} object.
     * @param cause a {@link java.lang.Throwable} object.
     */
    public SecuredAppException(String message, SecuredAppExceptionBean faultinfo, Throwable cause) {
        super(message, cause);
        this.faultBean = faultinfo;
    }

    /**
     * Constructor for SecuredAppException.
     *
     * @param code a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     */
    public SecuredAppException(String code, String message) {
        super(message);
        this.faultBean = new SecuredAppExceptionBean();
        this.faultBean.setFaultCode(code);
        this.faultBean.setFaultString(message);
    }

    /**
     * Constructor for SecuredAppException.
     *
     * @param faultBean a {@link com.learning.securedapp.exception.SecuredAppExceptionBean} object.
     * @param e a {@link java.lang.Exception} object.
     */
    public SecuredAppException(SecuredAppExceptionBean faultBean, Exception e) {
        super(faultBean.getFaultString());
        this.faultBean = faultBean;
    }

    /**
     * Constructor for SecuredAppException.
     *
     * @param message a {@link java.lang.String} object.
     * @param e a {@link com.learning.securedapp.exception.SecuredAppException} object.
     */
    public SecuredAppException(String message, SecuredAppException e) {
        super(e);
        this.faultBean = new SecuredAppExceptionBean();
        this.faultBean.setFaultString(message);
    }

    /**
     * Getter for the field <code>faultBean</code>.
     *
     * @return a {@link com.learning.securedapp.exception.SecuredAppExceptionBean} object.
     */
    public SecuredAppExceptionBean getFaultBean() {
        return faultBean;
    }
}
