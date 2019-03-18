package com.deloitte.employee.recruitment.system.exception;

@SuppressWarnings("serial")
public class EntityAlreadyExistsException extends ServiceException {

	private Integer errorCode;

	public EntityAlreadyExistsException(String msg) {
		super(msg);
	}

	public EntityAlreadyExistsException(Integer errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
}
