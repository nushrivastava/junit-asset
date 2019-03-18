package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

/**
 * Api success response class, which holds the api response body and success
 * status.
 * 
 * @author cramaswamy
 *
 */
public class ResponseEnvelopeSuccess implements Serializable {

	private static final long serialVersionUID = 1L;

	private String success = "true";

	private Object payload;

	public ResponseEnvelopeSuccess(Object payload) {
		this.payload = payload;
	}

	public String getSuccess() {
		return success;
	}

	public Object getPayload() {
		return payload;
	}

}
