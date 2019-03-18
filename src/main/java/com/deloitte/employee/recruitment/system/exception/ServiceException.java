package com.deloitte.employee.recruitment.system.exception;

/**
 * Checked service exception.
 * 
 * @author nushrivastava
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {
	
	public ServiceException(String msg)
	{
		super(msg);
	}
	
	public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
