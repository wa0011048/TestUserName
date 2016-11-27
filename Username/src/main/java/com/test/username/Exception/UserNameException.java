/**
 * 
 */
package com.test.username.Exception;

/**
 * @author mbt
 *
 */
public class UserNameException extends Exception {
	
	 /**
     * Error message code
     */
    private String errorCode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UserNameException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UserNameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 */
	public UserNameException(String code, String message) {
		super(message);
		this.setErrorCode(code);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UserNameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserNameException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
