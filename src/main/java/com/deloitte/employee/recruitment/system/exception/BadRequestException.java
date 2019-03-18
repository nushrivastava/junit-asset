package com.deloitte.employee.recruitment.system.exception;

/**
 * Exception class to handle bad requests.
 * 
 * @author nushrivastava
 *
 */
@SuppressWarnings("serial")
public class BadRequestException extends Exception {
	private Integer errorCode;

	public BadRequestException(Integer errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public BadRequestException(Integer errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
}
