package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDetailsResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer applicationId;
	private String isRejected;
	private String note;
	private Integer round;

	private InterviewerDetailsResponse interviewer;

	private JobDetailsResponse job;

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(String isRejected) {
		this.isRejected = isRejected;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public InterviewerDetailsResponse getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(InterviewerDetailsResponse interviewer) {
		this.interviewer = interviewer;
	}

	public JobDetailsResponse getJob() {
		return job;
	}

	public void setJob(JobDetailsResponse job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "ApplicationDetailsResponse [applicationId=" + applicationId + ", isRejected=" + isRejected + ", note="
				+ note + ", round=" + round + ", interviewer=" + interviewer + ", job=" + job + "]";
	}	
}
