package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

/**
 * This class holds api error code.
 * 
 * @author cramaswamy
 *
 */
public class ErrorEnv implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer code;

	public ErrorEnv(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

}
