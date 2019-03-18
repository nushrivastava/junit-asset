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
 * Holds information of all available jobs.
 * 
 * @author nushrivastava
 *
 */
@Entity
@Table(name = "JOB_DETAILS")
public class JobDetailsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "JOB_ID")
	private Integer jobId;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "SKILLS_REQUIRED")
	private String skillsRequired;
	@Column(name = "DESIGNATION")
	private String designation;
	@Column(name = "YEARS_OF_EXPERIENCE", nullable = true)
	private Integer yearsOfExperience;

	@OneToOne(mappedBy = "job")
	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSkillsRequired() {
		return skillsRequired;
	}

	public void setSkillsRequired(String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	@Override
	public String toString() {
		return "JobDetailsEntity [jobId=" + jobId + ", description=" + description + ", skillsRequired="
				+ skillsRequired + ", designation=" + designation + ", yearsOfExperience=" + yearsOfExperience + "]";
	}
}
