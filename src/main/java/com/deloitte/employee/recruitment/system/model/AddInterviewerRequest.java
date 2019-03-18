package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Request to add or update an interviewer.
 * 
 * @author nushrivastava
 *
 */
public class AddInterviewerRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer interviewerId;
	@NotEmpty(message = "firstName cannot be empty or null")
	private String firstName;
	@NotEmpty(message = "lastName cannot be empty or null")
	private String lastName;
	@NotEmpty(message = "designation cannot be empty or null")
	private String designation;
	@NotEmpty(message = "skill cannot be empty or null")
	@Pattern(regexp = "^(Spring Boot|Hybris|Salesforce|DBA|Management)$", message = "Invalid skill, possible skill values are Spring Boot, Hybris, Salesforce, DBA, Management")
	private String skill;
	@NotNull(message = "yearsOfExperience cannot be empty or null")
	private Integer yearsOfExperience;

	public Integer getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(Integer interviewerId) {
		this.interviewerId = interviewerId;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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
		return "AddInterviewerRequest [interviewerId=" + interviewerId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", designation=" + designation + ", skill=" + skill + ", yearsOfExperience="
				+ yearsOfExperience + "]";
	}
}
