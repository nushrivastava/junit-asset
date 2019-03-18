package com.deloitte.employee.recruitment.system.service;

import java.util.List;

import com.deloitte.employee.recruitment.system.exception.ServiceException;
import com.deloitte.employee.recruitment.system.model.AddApplicantRequest;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;

/**
 * Service layer caters to an applicant.
 * 
 * @author nushrivastava
 *
 */
public interface ApplicantService {

	/**
	 * Adds a new application or updates an existing application.
	 * 
	 * @param addApplicantRequest
	 * @return
	 * @throws ServiceException
	 */
	ApplicantDetailsResponse addOrUpdateApplicant(AddApplicantRequest addApplicantRequest) throws ServiceException;

	/**
	 * Gets applicant details along with application details by applicant id.
	 * 
	 * @param employeeId
	 * @return
	 * @throws ServiceException
	 */
	ApplicantDetailsResponse getApplicantDetailsById(Integer employeeId) throws ServiceException;

	/**
	 * Deletes applicant by its given applicant id.
	 * 
	 * @param applicantId
	 */
	void deleteApplicantById(Integer applicantId);

	/**
	 * Gets all applicants present.
	 * 
	 * @return
	 * @throws ServiceException
	 */
	List<ApplicantDetailsResponse> getAllApplicants() throws ServiceException;

	/**
	 * Gets all applicants by given years of experience and skill.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 * @throws ServiceException
	 */
	List<ApplicantDetailsResponse> getAllApplicantsByYearsOfExperienceAndSkill(String skill, Integer yearsOfExperience)
			throws ServiceException;

	/**
	 * Gets all jobs by given years of experience and skill.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 * @throws ServiceException
	 */
	List<JobDetailsResponse> getAllJobByYearsOfExperienceAndSkill(String skill, Integer yearsOfExperience)
			throws ServiceException;

	/**
	 * Enables applicant to apply for the given job id.
	 * 
	 * @param applicantId
	 * @param jobId
	 * @return
	 * @throws ServiceException
	 */
	ApplicantDetailsResponse applyJob(Integer applicantId, Integer jobId) throws ServiceException;

}
