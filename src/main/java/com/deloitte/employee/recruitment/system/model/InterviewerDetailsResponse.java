package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

/**
 * Interviewer details response.
 * 
 * @author nushrivastava
 *
 */
public class InterviewerDetailsResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer interviewerId;
	private String firstName;
	private String lastName;
	private String designation;
	private String skill;
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
		return "InterviewerDetailsResponse [interviewerId=" + interviewerId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", designation=" + designation + ", skill=" + skill + ", yearsOfExperience="
				+ yearsOfExperience + "]";
	}
}
