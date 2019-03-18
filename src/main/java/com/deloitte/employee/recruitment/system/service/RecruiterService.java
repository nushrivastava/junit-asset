package com.deloitte.employee.recruitment.system.service;

import java.util.List;

import javax.validation.Valid;

import com.deloitte.employee.recruitment.system.exception.ServiceException;
import com.deloitte.employee.recruitment.system.model.AddInterviewerRequest;
import com.deloitte.employee.recruitment.system.model.AddJobRequest;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsResponse;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;

/**
 * Services to cater to recruiter functionalities.
 * 
 * @author nushrivastava
 *
 */
public interface RecruiterService {

	/** interviewer details endpoints */
	List<InterviewerDetailsResponse> getAllInterviewer();

	InterviewerDetailsResponse addOrUpdateInterviewer(@Valid AddInterviewerRequest addRecruiterRequest) throws ServiceException;

	List<InterviewerDetailsResponse> getInterviewerByYearsOfExperienceAndSkill(String skill, Integer yearsOfExperience) throws ServiceException;

	void deleteInterviewerById(Integer interviewerId);

	InterviewerDetailsResponse getInterviewerDetailsById(Integer interviewerId) throws ServiceException;

	
	/** job details endpoints */
	JobDetailsResponse getJobDetailsById(Integer jobId);
	
	List<JobDetailsResponse> getAllJobByYearsOfExperienceAndSkill(String skill, Integer yearsOfExperience) throws ServiceException;

	void addNewJob(@Valid AddJobRequest addJobRequest);

	/** application details endpoints */
	List<ApplicationDetailsResponse> getApplicationDetailsByRound(Integer roundId) throws ServiceException;
	
	List<ApplicationDetailsResponse> getAcceptedApplications(Boolean isRejected) throws ServiceException;

}
