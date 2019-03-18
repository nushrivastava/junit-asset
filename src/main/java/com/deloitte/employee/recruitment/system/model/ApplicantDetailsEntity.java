package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Holds all details of an Applicant applying for a job.
 * 
 * @author nushrivastava
 *
 */
@Entity
@Table(name = "APPLICANT_DETAILS")
public class ApplicantDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPLICANT_ID", nullable = false)
	private int applicantId;
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	@Column(name = "EMAIL", nullable = false)
	private String email;
	@Column(name = "PHONE_NUMBER", nullable = false)
	private String phoneNumber;
	@Column(name = "SKILL", nullable = false)
	private String skill;
	@Column(name = "YEARS_OF_EXPERIENCE", nullable = true)
	private Integer yearsOfExperience;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "applicantDetails", cascade = CascadeType.ALL)
	List<ApplicationDetailsEntity> applicationDetails;

	public int getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(int applicantId) {
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

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public List<ApplicationDetailsEntity> getApplicationDetails() {
		return applicationDetails;
	}

	public void setApplicationDetails(List<ApplicationDetailsEntity> applicationDetails) {
		this.applicationDetails = applicationDetails;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	@Override
	public String toString() {
		return "ApplicantDetailsEntity [applicantId=" + applicantId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", skill=" + skill
				+ ", yearsOfExperience=" + yearsOfExperience + ", applicationDetails=" + applicationDetails + "]";
	}
}
