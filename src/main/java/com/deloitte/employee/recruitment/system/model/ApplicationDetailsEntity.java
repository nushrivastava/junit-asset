package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * Application entity class to hold information related to applicant's
 * application.
 * 
 * @author nushrivastava
 *
 */
@Entity
@Table(name = "APPLICATION_DETAILS")
public class ApplicationDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPLICATION_ID")
	private Integer applicationId;
	@Column(name = "IS_REJECTED", nullable = false)
	private Boolean isRejected;
	@Column(name = "NOTE", nullable = false)
	private String note;
	@Column(name = "ROUND", nullable = false)
	private Integer round;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "APPLICANT_ID", referencedColumnName = "APPLICANT_ID")
	private ApplicantDetailsEntity applicantDetails;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "INTERVIEWER_ID")
	private InterviewerDetailsEntity interviewer;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "JOB_ID")
	private JobDetailsEntity job;

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public InterviewerDetailsEntity getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(InterviewerDetailsEntity interviewer) {
		this.interviewer = interviewer;
	}

	public JobDetailsEntity getJob() {
		return job;
	}

	public void setJob(JobDetailsEntity job) {
		this.job = job;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
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

	public ApplicantDetailsEntity getApplicantDetails() {
		return applicantDetails;
	}

	public void setApplicantDetails(ApplicantDetailsEntity applicantDetails) {
		this.applicantDetails = applicantDetails;
	}

	@Override
	public String toString() {
		return "ApplicationDetailsEntity [applicationId=" + applicationId + ", isRejected=" + isRejected + ", note="
				+ note + ", round=" + round + ", applicantDetails=" + applicantDetails + ", interviewer=" + interviewer
				+ ", job=" + job + "]";
	}
}
