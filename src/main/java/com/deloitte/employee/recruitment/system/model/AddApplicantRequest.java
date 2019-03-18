package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddApplicantRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer applicantId;
	@NotEmpty(message = "firstName cannot be empty or null")
	private String firstName;
	@NotEmpty(message = "lastName cannot be empty or null")
	private String lastName;
	@NotEmpty(message = "email cannot be empty or null")
	private String email;
	@NotEmpty(message = "phoneNumber cannot be empty or null")
	private String phoneNumber;
	@NotEmpty(message = "skill cannot be empty or null")
	@Pattern(regexp = "^(Spring Boot|Hybris|Salesforce|DBA|Management)$", message = "Invalid skill, possible skill values are Spring Boot, Hybris, Salesforce, DBA, Management")
	private String skill;
	@NotNull(message = "yearsOfExperience cannot be empty or null")
	private Integer yearsOfExperience;

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

	@Override
	public String toString() {
		return "AddApplicantRequest [applicantId=" + applicantId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", skill=" + skill + ", yearsOfExperience="
				+ yearsOfExperience + "]";
	}
}
