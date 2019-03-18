package com.deloitte.employee.recruitment.system.exception;

/**
 * A checked exception to require callers to handle DB failures.
 *
 * @author nushrivastava
 */
@SuppressWarnings("serial")
public class DaoException extends Exception {

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
