package com.deloitte.employee.recruitment.system.model;

import java.io.Serializable;

public class JobDetailsResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private int jobId;
	private String description;
	private String skillsRequired;
	private String designation;
	private int yearsOfExperience;
	
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
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
	public int getYearsOfExperience() {
		return yearsOfExperience;
	}
	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
	@Override
	public String toString() {
		return "JobDetailsResponse [jobId=" + jobId + ", description=" + description + ", skillsRequired="
				+ skillsRequired + ", designation=" + designation + ", yearsOfExperience=" + yearsOfExperience + "]";
	}	
}
