package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Class to hold interviewer details validating each application.
 * 
 * @author nushrivastava
 *
 */
@Entity
@Table(name = "INTERVIEWER_DETAILS")
public class InterviewerDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTERVIEWER_ID")
	private Integer interviewerId;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "SKILL")
	private String skill;
	@Column(name = "YEARS_OF_EXPERIENCE")
	private Integer yearsOfExperience;
	@Column(name = "DESIGNATION")
	private String designation;

	@OneToOne(mappedBy = "interviewer")
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "InterviewerDetailsEntity [interviewerId=" + interviewerId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", skill=" + skill + ", yearsOfExperience=" + yearsOfExperience + ", designation="
				+ designation + "]";
	}
}
