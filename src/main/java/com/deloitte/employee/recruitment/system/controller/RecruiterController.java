package com.deloitte.employee.recruitment.system.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.employee.recruitment.system.model.AddInterviewerRequest;
import com.deloitte.employee.recruitment.system.model.AddJobRequest;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsResponse;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsResponse;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;
import com.deloitte.employee.recruitment.system.service.ApplicantService;
import com.deloitte.employee.recruitment.system.service.RecruiterService;

/**
 * Controller to cater to functionalities of recruiters.
 * 
 * @author nushrivastava
 *
 */
@RestController
@RequestMapping(value = "/recruiter")
public class RecruiterController extends AbstractRequestController {

	public static Logger LOGGER = LoggerFactory.getLogger(RecruiterController.class);

	@Autowired
	private RecruiterService recruiterService;
	
	@Autowired
	private ApplicantService applicantService;

	/**
	 * Returns all available Interviewers.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/interviewer", method = RequestMethod.GET)
	public ResponseEntity<List<InterviewerDetailsResponse>> getAllInterviewer() throws Exception {
		LOGGER.info("Request received to get all Interviewer");
		return ResponseEntity.ok(recruiterService.getAllInterviewer());
	}

	/**
	 * Adds or updates Interviewer profile.
	 * 
	 * @param addInterviewerRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/interviewer", method = RequestMethod.POST)
	public ResponseEntity<InterviewerDetailsResponse> saveInterviewer(
			@RequestBody @Valid AddInterviewerRequest addInterviewerRequest) throws Exception {
		LOGGER.info("Request received for add-update Interviewer: {}", addInterviewerRequest);
		return ResponseEntity.ok(recruiterService.addOrUpdateInterviewer(addInterviewerRequest));
	}

	/**
	 * Get Recruiter details for a given Recruiter id.
	 * 
	 * @param interviewerId
	 * @return
	 */
	@RequestMapping(value = "/interviewer/{interviewerId}", method = RequestMethod.GET)
	public ResponseEntity<InterviewerDetailsResponse> getInterviewerDetails(@PathVariable Integer interviewerId)
			throws Exception {
		LOGGER.info("Request received for get interviewer details for: {}", interviewerId);
		return ResponseEntity.ok(recruiterService.getInterviewerDetailsById(interviewerId));
	}

	/**
	 * Returns all recruiters for given skill and years of experience.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 */
	@RequestMapping(value = "/interviewer/skill", method = RequestMethod.GET)
	public ResponseEntity<List<InterviewerDetailsResponse>> getInterviewerBySkillAndYearsOfExperience(
			@Pattern(regexp = "^(Spring Boot|Hybris|Salesforce|DBA|Management)$", message = "Invalid skill, possible skill values are Spring Boot, Hybris, Salesforce, DBA, Management") @NotEmpty(message = "skill cannot be null or empty") @RequestParam(value = "skill") String skill,
			@RequestParam(value = "yearsOfExperience") Integer yearsOfExperience) throws Exception {
		LOGGER.info("Request received to get all Interviewer with skill: {} and years of experience: {}", skill,
				yearsOfExperience);
		return ResponseEntity
				.ok(recruiterService.getInterviewerByYearsOfExperienceAndSkill(skill, yearsOfExperience));
	}

	/**
	 * Deletes Recruiter by given recruiter id.
	 * 
	 * @param interviewerId
	 * @return
	 */
	@RequestMapping(value = "/interviewer/{interviewerId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteInterviewer(@PathVariable Integer interviewerId) {
		LOGGER.info("Request received for delete interviewer: {}", interviewerId);
		recruiterService.deleteInterviewerById(interviewerId);
		return ResponseEntity.ok(null);
	}	
	

	/**
	 * Returns all available applicants.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/applicants", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicantDetailsResponse>> getAllApplicants() throws Exception {
		LOGGER.info("Request received to get all applicants");
		return ResponseEntity.ok(applicantService.getAllApplicants());
	}

	/**
	 * Get applicant details for a given applicant id.
	 * 
	 * @param applicantId
	 * @return
	 */
	@RequestMapping(value = "/{applicantId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicantDetailsResponse> getApplicantDetails(@PathVariable Integer applicantId) throws Exception {
		LOGGER.info("Request received for get applicant details for: {}", applicantId);
		return ResponseEntity.ok(applicantService.getApplicantDetailsById(applicantId));
	}

	/**
	 * Returns all applicants for given skill and years of experience.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 */
	@RequestMapping(value = "/applicant/skill", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicantDetailsResponse>> getApplicantsBySkillAndYearsOfExperience(
			@Pattern(regexp = "^(Spring Boot|Hybris|Salesforce|DBA|Management)$", message = "Invalid skill, possible skill values are Spring Boot, Hybris, Salesforce, DBA, Management") @NotEmpty(message = "skill cannot be null or empty") @RequestParam(value = "skill") String skill,
			@RequestParam(value = "yearsOfExperience") Integer yearsOfExperience) throws Exception {
		LOGGER.info("Request received to get all applicant with skill: {} and years of experience: {}", skill,
				yearsOfExperience);
		return ResponseEntity
				.ok(applicantService.getAllApplicantsByYearsOfExperienceAndSkill(skill, yearsOfExperience));
	}
	
	/**
	 * Deletes applicant by given applicant id.
	 * 
	 * @param applicantId
	 * @return
	 */
	@RequestMapping(value = "/applicant/{applicantId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteApplicant(@PathVariable Integer applicantId) {
		LOGGER.info("Request received for delete applicant: {}", applicantId);
		applicantService.deleteApplicantById(applicantId);
		return ResponseEntity.ok(null);
	}
	
	/**
	 * Adds or updates Interviewer profile.
	 * 
	 * @param addInterviewerRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/job", method = RequestMethod.POST)
	public ResponseEntity<Void> saveJob(
			@RequestBody @Valid AddJobRequest addJobRequest) throws Exception {
		LOGGER.info("Request received for add-update job: {}", addJobRequest);
		recruiterService.addNewJob(addJobRequest);
		return ResponseEntity.ok(null);
	}

	/**
	 * Get Recruiter details for a given Recruiter id.
	 * 
	 * @param jobId
	 * @return
	 */
	@RequestMapping(value = "/job/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<JobDetailsResponse> getJobDetails(@PathVariable Integer jobId) throws Exception {
		LOGGER.info("Request received for get job details for id : {}", jobId);
		return ResponseEntity.ok(recruiterService.getJobDetailsById(jobId));
	}
	
	/**
	 * Returns all jobs for given skill and years of experience.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/job", method = RequestMethod.GET)
	public ResponseEntity<List<JobDetailsResponse>> getJobsBySkillAndYearsOfExperience(
			@Pattern(regexp = "^(Spring Boot|Hybris|Salesforce|DBA|Management)$", message = "Invalid skill, possible skill values are Spring Boot, Hybris, Salesforce, DBA, Management") @NotEmpty(message = "skill cannot be null or empty") @RequestParam(value = "skill") String skill,
			@RequestParam(value = "yearsOfExperience") Integer yearsOfExperience) throws Exception {
		LOGGER.info("Request received to get all jobs with skill: {} and years of experience: {}", skill,
				yearsOfExperience);
		return ResponseEntity
				.ok(recruiterService.getAllJobByYearsOfExperienceAndSkill(skill, yearsOfExperience));
	}
	
	/**
	 * Returns all applications by round
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/application/round/{roundId}", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicationDetailsResponse>> getApplicationDetailsByRound(@PathVariable Integer roundId) throws Exception {
		LOGGER.info("Request received for get application details for id : {}", roundId);
		return ResponseEntity.ok(recruiterService.getApplicationDetailsByRound(roundId));
	}
	
}