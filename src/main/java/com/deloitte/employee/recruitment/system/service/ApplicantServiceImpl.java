package com.deloitte.employee.recruitment.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.employee.recruitment.system.common.SystemUtil;
import com.deloitte.employee.recruitment.system.exception.ServiceException;
import com.deloitte.employee.recruitment.system.model.AddApplicantRequest;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsResponse;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;
import com.deloitte.employee.recruitment.system.repository.ApplicantRepository;
import com.deloitte.employee.recruitment.system.repository.JobRepository;

@Service
public class ApplicantServiceImpl implements ApplicantService {

	private static Logger LOGGER = LoggerFactory.getLogger(ApplicantServiceImpl.class);

	@Autowired
	private ApplicantRepository applicantRepo;

	@Autowired
	private RecruiterService recruiterService;

	@Autowired
	private JobRepository jobRepo;

	@Autowired
	private DozerBeanMapper dozerMapper;

	@Override
	public ApplicantDetailsResponse addOrUpdateApplicant(AddApplicantRequest addApplicantRequest)
			throws ServiceException {
		final Integer applicantId = addApplicantRequest.getApplicantId();
		ApplicantDetailsEntity existingApplicant = null;
		try {
			if (null != applicantId && applicantId > 0) {
				LOGGER.debug("Update applicant request received for applicant: {}", applicantId);
				existingApplicant = applicantRepo.findById(applicantId).get();
				if (null == existingApplicant) {
					throw new EntityNotFoundException(String.format("No applicant found with Id: {}", applicantId));
				}
			}
			ApplicantDetailsEntity applicantToBeSaved = dozerMapper.map(addApplicantRequest,
					ApplicantDetailsEntity.class);
			if (null != existingApplicant) {
				applicantToBeSaved.setApplicantId(existingApplicant.getApplicantId());
			}
			ApplicantDetailsEntity applicant = applicantRepo.save(applicantToBeSaved);
			ApplicantDetailsResponse response = dozerMapper.map(applicant, ApplicantDetailsResponse.class);
			return response;
		} catch (Exception e) {
			if (e instanceof EntityNotFoundException)
				throw e;
			throw new ServiceException(
					String.format("Error adding or updating applicant with details: {}", addApplicantRequest));
		}
	}

	@Override
	public ApplicantDetailsResponse getApplicantDetailsById(Integer applicantId) throws ServiceException {
		LOGGER.debug("Getting applicant by id: {}", applicantId);
		try {
			Optional<ApplicantDetailsEntity> applicant = applicantRepo.findById(applicantId);
			if (null == applicant || applicant.isPresent()) {
				throw new EntityNotFoundException(String.format("No applicant found with id: %s", applicantId));
			}
			ApplicantDetailsResponse response = dozerMapper.map(applicant.get(), ApplicantDetailsResponse.class);
			response.getApplication().forEach(application -> application.setInterviewer(null));
			return response;
		} catch (Exception e) {
			throw new ServiceException(String.format("Error getting applicants by id: %s", applicantId), e);
		}
	}

	@Override
	public List<ApplicantDetailsResponse> getAllApplicants() throws ServiceException {
		LOGGER.debug("Fetching list of all applicants");
		try {
			List<ApplicantDetailsResponse> applicants = new ArrayList<>();

			Iterable<ApplicantDetailsEntity> applicantsDB = applicantRepo.findAll();
			for (ApplicantDetailsEntity applicant : applicantsDB) {
				List<ApplicationDetailsResponse> applicationResponse = new ArrayList<>();
				List<ApplicationDetailsEntity> applicationsDB = applicant.getApplicationDetails();
				for (ApplicationDetailsEntity applicationDB : applicationsDB) {
					applicationResponse.add(dozerMapper.map(applicationDB, ApplicationDetailsResponse.class));
				}
				ApplicantDetailsResponse response = dozerMapper.map(applicant, ApplicantDetailsResponse.class);
				response.setApplication(applicationResponse);
				applicants.add(response);
			}
			return applicants;
		} catch (Exception e) {
			throw new ServiceException("Error getting applicants", e);
		}
	}

	@Override
	public void deleteApplicantById(Integer applicantId) {
		LOGGER.debug("Deleting applicant by id: {}", applicantId);
		applicantRepo.deleteById(applicantId);
	}

	@Override
	public List<ApplicantDetailsResponse> getAllApplicantsByYearsOfExperienceAndSkill(String skill,
			Integer yearsOfExperience) throws ServiceException {
		try {
			List<ApplicantDetailsResponse> applicantsResponse = new ArrayList<>();
			List<ApplicantDetailsEntity> applicants = applicantRepo.findBySkillAndYearsOfExperience(skill,
					yearsOfExperience);
			if (null != applicants && !applicants.isEmpty())
				applicants.forEach(applicant -> {
					ApplicantDetailsResponse response = dozerMapper.map(applicant, ApplicantDetailsResponse.class);
					response.getApplication().forEach(application -> application.setInterviewer(null));
					applicantsResponse.add(response);
				});
			else
				throw new EntityNotFoundException(String.format(
						"No applicants found with skill as %s and years of experience: %s", skill, yearsOfExperience));
			return applicantsResponse;
		} catch (Exception e) {
			throw new ServiceException(
					String.format("Error getting applicants for skill %s and experience: %s", skill, yearsOfExperience),
					e);
		}
	}

	@Override
	public List<JobDetailsResponse> getAllJobByYearsOfExperienceAndSkill(String skill, Integer yearsOfExperience)
			throws ServiceException {
		return recruiterService.getAllJobByYearsOfExperienceAndSkill(skill, yearsOfExperience);
	}

	@Override
	public ApplicantDetailsResponse applyJob(Integer applicantId, Integer jobId) throws ServiceException {
		try {
			List<ApplicationDetailsEntity> applications = new ArrayList<>();
			ApplicantDetailsEntity applicant = applicantRepo.findByApplicantId(applicantId);
			JobDetailsEntity job = jobRepo.findByJobId(jobId);
			if (null != job && job.getYearsOfExperience() > applicant.getYearsOfExperience()) {
				throw new IllegalArgumentException("Job requires more experienced candidate for this position");
			}

			ApplicationDetailsEntity application = SystemUtil.generateApplication(applicant, job);
			applications.add(application);
			applicant.setApplicationDetails(applications);
			
			ApplicantDetailsEntity savedApplicant = applicantRepo.save(applicant);
			List<ApplicationDetailsEntity> savedApplications = savedApplicant.getApplicationDetails();
			ApplicantDetailsResponse response = dozerMapper.map(savedApplicant, ApplicantDetailsResponse.class);
			response.setApplication(new ArrayList<ApplicationDetailsResponse>());
			savedApplications.forEach(savedApplication -> {
				response.getApplication().add(dozerMapper.map(savedApplication, ApplicationDetailsResponse.class));
			});
			return response;
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException)
				throw e;
			throw new ServiceException(String.format("Error applying applicant %s for job: %s", jobId, applicantId), e);
		}
	}
}
