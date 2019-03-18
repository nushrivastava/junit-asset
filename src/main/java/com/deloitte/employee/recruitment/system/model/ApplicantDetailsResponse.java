package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;
import java.util.List;

/**
 * Employee details response object.
 * 
 * @author nushrivastava
 *
 */
public class ApplicantDetailsResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer applicantId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String skill;
	private Integer yearsOfExperience;
	private List<ApplicationDetailsResponse> application;

	public Integer getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public List<ApplicationDetailsResponse> getApplication() {
		return application;
	}

	public void setApplication(List<ApplicationDetailsResponse> application) {
		this.application = application;
	}

	@Override
	public String toString() {
		return "ApplicantDetailsResponse [applicantId=" + applicantId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", skill=" + skill
				+ ", yearsOfExperience=" + yearsOfExperience + ", application=" + application + "]";
	}
}
