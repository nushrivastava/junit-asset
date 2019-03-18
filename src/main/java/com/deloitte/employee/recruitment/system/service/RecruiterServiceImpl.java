package com.deloitte.employee.recruitment.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.employee.recruitment.system.exception.ServiceException;
import com.deloitte.employee.recruitment.system.model.AddInterviewerRequest;
import com.deloitte.employee.recruitment.system.model.AddJobRequest;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsResponse;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsEntity;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;
import com.deloitte.employee.recruitment.system.repository.ApplicationRepository;
import com.deloitte.employee.recruitment.system.repository.JobRepository;
import com.deloitte.employee.recruitment.system.repository.RecruiterRepository;

/**
 * Caters to functionalities of a recruiter.
 * 
 * @author nushrivastava
 *
 */
@Service
public class RecruiterServiceImpl implements RecruiterService {

	private Logger LOGGER = LoggerFactory.getLogger(RecruiterServiceImpl.class);

	@Autowired
	private RecruiterRepository recruiterRepo;

	@Autowired
	private ApplicationRepository applicationRepo;

	@Autowired
	private JobRepository jobRepo;

	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public List<InterviewerDetailsResponse> getAllInterviewer() {
		List<InterviewerDetailsResponse> recruiters = new ArrayList<>();
		recruiterRepo.findAll().forEach(recuiter -> dozerMapper.map(recuiter, InterviewerDetailsResponse.class));
		return recruiters;
	}

	@Override
	public InterviewerDetailsResponse getInterviewerDetailsById(Integer interviewerId) throws ServiceException {
		try{
		LOGGER.debug("Getting Interviewer by id: {}", interviewerId);
		Optional<InterviewerDetailsEntity> recruiter = recruiterRepo.findById(interviewerId);
		if (null == recruiter || !recruiter.isPresent()) {
			throw new EntityNotFoundException(String.format("No Interviewers found with id: %s", interviewerId));
		}
		return dozerMapper.map(recruiter, InterviewerDetailsResponse.class);
		} catch (Exception e) {
			if (e instanceof EntityNotFoundException)
				throw e;
			throw new ServiceException(String.format("Error fetching interviewer by id: %s", interviewerId), e);
		}
	}

	@Override
	public InterviewerDetailsResponse addOrUpdateInterviewer(AddInterviewerRequest addInterviewerRequest)
			throws ServiceException {
		final Integer interviewerId = addInterviewerRequest.getInterviewerId();
		try {
			if (null != interviewerId && interviewerId > 0) {
				LOGGER.debug("Update interviewer request received for applicant: {}", interviewerId);
				Optional<InterviewerDetailsEntity> existingInterviewer = recruiterRepo.findById(interviewerId);
				if (null == existingInterviewer || !existingInterviewer.isPresent()) {
					throw new EntityNotFoundException(String.format("No interviewer found with Id: {}", interviewerId));
				}
			}
			InterviewerDetailsEntity applicant = recruiterRepo
					.save(dozerMapper.map(addInterviewerRequest, InterviewerDetailsEntity.class));
			return dozerMapper.map(applicant, InterviewerDetailsResponse.class);
		} catch (Exception e) {
			if (e instanceof EntityNotFoundException)
				throw e;
			throw new ServiceException(
					String.format("Error adding or updating interviewer with details: {}", addInterviewerRequest));
		}
	}

	@Override
	public List<InterviewerDetailsResponse> getInterviewerByYearsOfExperienceAndSkill(String skill,
			Integer yearsOfExperience) throws ServiceException {
		try {
			List<InterviewerDetailsResponse> interviewersResponse = new ArrayList<>();
			List<InterviewerDetailsEntity> interviewers = recruiterRepo.findBySkillAndYearsOfExperience(skill,
					yearsOfExperience);
			if (null != interviewers && !interviewers.isEmpty())
				interviewers.forEach(interviewer -> interviewersResponse
						.add(dozerMapper.map(interviewer, InterviewerDetailsResponse.class)));
			else
				throw new EntityNotFoundException(
						String.format("No interviewers found with skill as {} and experience of {} years: %s", skill,
								yearsOfExperience));
			return interviewersResponse;
		} catch (Exception e) {
			if (e instanceof EntityNotFoundException)
				throw e;
			throw new ServiceException(String.format("Error getting interviewers for skill %s and experience: %s",
					skill, yearsOfExperience), e);
		}
	}

	@Override
	public void deleteInterviewerById(Integer interviewerId) {
		recruiterRepo.deleteById(interviewerId);
	}

	@Override
	public List<JobDetailsResponse> getAllJobByYearsOfExperienceAndSkill(String skill, Integer yearsOfExperience)
			throws ServiceException {
		try {
			List<JobDetailsResponse> jobsResponse = new ArrayList<>();
			Iterable<JobDetailsEntity> jobs = null;
			if(StringUtils.isEmpty(skill) || yearsOfExperience < 0)
				jobs = jobRepo.findAll();
			else
				jobs = jobRepo.findBySkillAndYearsOfExperience(skill, yearsOfExperience);
			if (null != jobs)
				jobs.forEach(job -> {
					jobsResponse.add(dozerMapper.map(job, JobDetailsResponse.class));
				});
			else
				throw new EntityNotFoundException(String.format(
						"No jobs found with skill as {} and experience of {} years: %s", skill, yearsOfExperience));
			return jobsResponse;
		} catch (Exception e) {
			throw new ServiceException(
					String.format("Error getting jobs for skill %s and experience: %s", skill, yearsOfExperience), e);
		}
	}

	@Override
	public JobDetailsResponse getJobDetailsById(Integer jobId) {
		Optional<JobDetailsEntity> jobEntity = jobRepo.findById(jobId);
		return dozerMapper.map(jobEntity, JobDetailsResponse.class);
	}

	@Override
	public void addNewJob(@Valid AddJobRequest addJobRequest) {
		JobDetailsEntity job = dozerMapper.map(addJobRequest, JobDetailsEntity.class);
		jobRepo.save(job);
	}

	@Override
	public List<ApplicationDetailsResponse> getApplicationDetailsByRound(Integer roundId) throws ServiceException {
		try{
			List<ApplicationDetailsResponse> applications = new ArrayList<>();
			List<ApplicationDetailsEntity> applicationsDB = applicationRepo.findByRound(roundId);
			applicationsDB.forEach(applicationDB -> applications.add((dozerMapper.map(applicationDB, ApplicationDetailsResponse.class))));
			return applications;
		}catch(Exception e){
			throw new ServiceException(String.format("Error fetching all applications for round: %s", roundId), e);
		}
	}

	@Override
	public List<ApplicationDetailsResponse> getAcceptedApplications(Boolean isRejected) throws ServiceException {
		try{
			List<ApplicationDetailsResponse> applications = new ArrayList<>();
			List<ApplicationDetailsEntity> applicationsDB = applicationRepo.findByIsRejected(isRejected);
			applicationsDB.forEach(applicationDB -> applications.add((dozerMapper.map(applicationDB, ApplicationDetailsResponse.class))));
			return applications;
		}catch(Exception e){
			throw new ServiceException("Error fetching all accepted applications", e);
		}
	}

}
