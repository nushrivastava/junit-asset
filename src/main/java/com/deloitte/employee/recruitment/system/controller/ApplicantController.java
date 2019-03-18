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

import com.deloitte.employee.recruitment.system.model.AddApplicantRequest;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;
import com.deloitte.employee.recruitment.system.service.ApplicantService;

/**
 * Controller to cater to applicant functionalities.
 * 
 * @author nushrivastava
 *
 */
@RestController
@RequestMapping(value = "/applicant")
public class ApplicantController extends AbstractRequestController {

	public static Logger LOGGER = LoggerFactory.getLogger(ApplicantController.class);

	@Autowired
	private ApplicantService applicantService;

	/**
	 * Returns all available applicants.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<ApplicantDetailsResponse>> getAllApplicants() throws Exception {
		LOGGER.info("Request received to get all applicants");
		return ResponseEntity.ok(applicantService.getAllApplicants());
	}

	/**
	 * Adds or updates applicant profile.
	 * 
	 * @param addApplicantRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<ApplicantDetailsResponse> saveApplicant(
			@RequestBody @Valid AddApplicantRequest addApplicantRequest) throws Exception {
		LOGGER.info("Request received for add-update applicant: {}", addApplicantRequest);
		return ResponseEntity.ok(applicantService.addOrUpdateApplicant(addApplicantRequest));
	}

	/**
	 * Get applicant details for a given applicant id.
	 * 
	 * @param applicantId
	 * @return
	 */
	@RequestMapping(value = "/{applicantId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicantDetailsResponse> getApplicantDetails(@PathVariable Integer applicantId) throws Exception{
		LOGGER.info("Request received for get applicant details for: {}", applicantId);
		return ResponseEntity.ok(applicantService.getApplicantDetailsById(applicantId));
	}

	/**
	 * Returns all jobs for given skill and years of experience.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 */
	@RequestMapping(value = "/job", method = RequestMethod.GET)
	public ResponseEntity<List<JobDetailsResponse>> getJobsBySkillAndYearsOfExperience (
			@Pattern(regexp = "^(Spring Boot|Hybris|Salesforce|DBA|Management)$", message = "Invalid skill, possible skill values are Spring Boot, Hybris, Salesforce, DBA, Management") @NotEmpty(message = "skill cannot be null or empty") @RequestParam(value = "skill") String skill,
			@RequestParam(value = "yearsOfExperience") Integer yearsOfExperience) throws Exception {
		LOGGER.info("Request received to get all jobs with skill: {} and years of experience: {}", skill,
				yearsOfExperience);
		return ResponseEntity
				.ok(applicantService.getAllJobByYearsOfExperienceAndSkill(skill, yearsOfExperience));
	}

	/**
	 * Deletes applicant by given applicant id.
	 * 
	 * @param applicantId
	 * @return
	 */
	@RequestMapping(value = "/{applicantId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteApplicant(@PathVariable Integer applicantId) {
		LOGGER.info("Request received for delete applicant: {}", applicantId);
		applicantService.deleteApplicantById(applicantId);
		return ResponseEntity.ok(null);
	}

	/**
	 * Helps applicant apply for a job.
	 * 
	 * @param skill
	 * @param yearsOfExperience
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/job", method = RequestMethod.PUT)
	public ResponseEntity<ApplicantDetailsResponse> applyJob(@RequestParam(value = "jobId") Integer jobId,
			@RequestParam(value = "applicantId") Integer applicantId) throws Exception {
		LOGGER.info("Request received form applicant: {} for job id : {}", applicantId, jobId);
		return ResponseEntity.ok(applicantService.applyJob(applicantId, jobId));
	}
}