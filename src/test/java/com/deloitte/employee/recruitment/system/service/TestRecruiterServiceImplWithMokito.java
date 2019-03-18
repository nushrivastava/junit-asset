package com.deloitte.employee.recruitment.system.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.deloitte.employee.recruitment.system.common.TestCommon;
import com.deloitte.employee.recruitment.system.exception.ServiceException;
import com.deloitte.employee.recruitment.system.model.AddInterviewerRequest;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsEntity;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;
import com.deloitte.employee.recruitment.system.repository.ApplicationRepository;
import com.deloitte.employee.recruitment.system.repository.JobRepository;
import com.deloitte.employee.recruitment.system.repository.RecruiterRepository;

/**
 * Junits tests for unit testing {@code RecruiterServiceImpl}
 * 
 * @author nushrivastava
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestRecruiterServiceImplWithMokito extends TestCommon {

	@InjectMocks
	private RecruiterServiceImpl recruiterServiceImpl;

	@Mock
	private RecruiterRepository recruiterRepo;

	@Mock
	private ApplicationRepository applicationRepo;

	@Mock
	private JobRepository jobRepo;

	@Mock
	private DozerBeanMapper dozerMapper;

	@Captor
	private ArgumentCaptor<InterviewerDetailsEntity> interviewerArgumentCaptor;

	/**
	 * Tests getInterviewerDetailsById success scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_getInterviewerDetailsById_Success() throws Exception {
		// setup
		InterviewerDetailsEntity interviewerEntity = buildInterviewerDetailsEntity();
		when(recruiterRepo.findById(SAMPLE_INTERVIEWER_ID)).thenReturn(Optional.of(interviewerEntity));
		when(dozerMapper.map(any(), eq(InterviewerDetailsResponse.class)))
				.thenReturn(buildInterviewerDetailsResponseFromEntity(interviewerEntity));

		// execute
		InterviewerDetailsResponse response = recruiterServiceImpl.getInterviewerDetailsById(SAMPLE_INTERVIEWER_ID);

		// assert
		assertTrue(null != response);
		assertEquals(SAMPLE_INTERVIEWER_ID, response.getInterviewerId());
		verify(recruiterRepo).findById(any(Integer.class));
	}

	/**
	 * Tests getInterviewerDetailsById when no entity is found in database.
	 * 
	 * @throws Exception
	 */
	@Test(expected = EntityNotFoundException.class)
	public void test_getInterviewerDetailsById_Failure_EntityNotFoundException() throws Exception {
		// setup
		when(recruiterRepo.findById(SAMPLE_INTERVIEWER_ID)).thenReturn(null);

		// execute
		InterviewerDetailsResponse response = recruiterServiceImpl.getInterviewerDetailsById(SAMPLE_INTERVIEWER_ID);

		// assert
		assertTrue(null == response);
		verify(dozerMapper, never()).map(any(InterviewerDetailsEntity.class), eq(InterviewerDetailsResponse.class));
	}

	/**
	 * Tests getInterviewerDetailsById for ServiceException.
	 * 
	 * @throws Exception
	 */
	@Test(expected = ServiceException.class)
	public void test_getInterviewerDetailsById_Failure_ServiceException() throws Exception {
		// setup
		doThrow(NullPointerException.class).when(recruiterRepo).findById(any(Integer.class));

		// execute
		InterviewerDetailsResponse response = recruiterServiceImpl.getInterviewerDetailsById(SAMPLE_INTERVIEWER_ID);

		// assert
		assertTrue(null == response);
		verify(dozerMapper, never()).map(any(InterviewerDetailsEntity.class), eq(InterviewerDetailsResponse.class));
	}

	/**
	 * Tests addOrUpdateInterviewer to add new interviewer.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_addOrUpdateInterviewer_Success_AddInterviewer() throws Exception {
		// setup
		AddInterviewerRequest addInterviewerRequest = buildAddInterviewerRequest();
		addInterviewerRequest.setInterviewerId(null);
		InterviewerDetailsEntity entity = buildInterviewerDetailsEntity();

		when(dozerMapper.map(any(AddInterviewerRequest.class), eq(InterviewerDetailsEntity.class)))
				.thenReturn(buildInterviewerDetailsEntityFromRequest(addInterviewerRequest));
		when(dozerMapper.map(any(InterviewerDetailsEntity.class), eq(InterviewerDetailsResponse.class)))
				.thenReturn(buildInterviewerDetailsResponseFromEntity(entity));

		// captures and asserts void method arguments
		doAnswer((Answer<InterviewerDetailsEntity>) invocation -> {
			InterviewerDetailsEntity savedEntity = invocation.getArgument(0);
			assertNull(savedEntity.getInterviewerId());
			return entity;
		}).when(recruiterRepo).save(any(InterviewerDetailsEntity.class));

		// execute
		InterviewerDetailsResponse response = recruiterServiceImpl.addOrUpdateInterviewer(addInterviewerRequest);

		// assert
		verify(recruiterRepo).save(any(InterviewerDetailsEntity.class));
		assertTrue(response.getInterviewerId() > 0);
	}

	/**
	 * Tests addOrUpdateInterviewer to update an existing interviewer.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_addOrUpdateInterviewer_Success_UpdateInterviewer() throws Exception {
		// setup
		AddInterviewerRequest addInterviewerRequest = buildAddInterviewerRequest();
		addInterviewerRequest.setSkill(SAMPLE_UPDATE_SKILL);
		InterviewerDetailsEntity entity = buildInterviewerDetailsEntity();

		when(dozerMapper.map(any(AddInterviewerRequest.class), eq(InterviewerDetailsEntity.class)))
				.thenReturn(buildInterviewerDetailsEntityFromRequest(addInterviewerRequest));
		when(dozerMapper.map(any(InterviewerDetailsEntity.class), eq(InterviewerDetailsResponse.class)))
				.thenReturn(buildInterviewerDetailsResponseFromEntity(entity));
		when(recruiterRepo.save(interviewerArgumentCaptor.capture())).thenReturn(entity);
		when(recruiterRepo.findById(addInterviewerRequest.getInterviewerId())).thenReturn(Optional.of(entity));

		// execute
		recruiterServiceImpl.addOrUpdateInterviewer(addInterviewerRequest);

		// assert
		verify(recruiterRepo).save(any(InterviewerDetailsEntity.class));
		InterviewerDetailsEntity entitySaved = interviewerArgumentCaptor.getValue();
		assertEquals(addInterviewerRequest.getSkill(), entitySaved.getSkill());
	}

	/**
	 * Tests addOrUpdateInterviewer to update an existing interviewer.
	 * 
	 * @throws Exception
	 */
	@Test(expected = EntityNotFoundException.class)
	public void test_UpdateInterviewer_Failure_EntityNotFoundException() throws Exception {
		// setup
		AddInterviewerRequest addInterviewerRequest = buildAddInterviewerRequest();
		when(recruiterRepo.findById(addInterviewerRequest.getInterviewerId())).thenReturn(null);

		// execute
		recruiterServiceImpl.addOrUpdateInterviewer(addInterviewerRequest);

		// assert
		verify(recruiterRepo, never()).save(any(InterviewerDetailsEntity.class));
	}

	/**
	 * Tests addOrUpdateInterviewer to update an existing interviewer.
	 * 
	 * @throws Exception
	 */
	@Test(expected = ServiceException.class)
	public void test_UpdateInterviewer_Failure_ServiceException() throws Exception {
		// setup
		AddInterviewerRequest addInterviewerRequest = buildAddInterviewerRequest();
		doThrow(NullPointerException.class).when(recruiterRepo).findById(addInterviewerRequest.getInterviewerId());
		// execute
		recruiterServiceImpl.addOrUpdateInterviewer(addInterviewerRequest);

		// assert
		verify(recruiterRepo, never()).save(any(InterviewerDetailsEntity.class));
	}

	/**
	 * Tests getAllJobByYearsOfExperienceAndSkill with valid skill and
	 * experience
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_getAllJobByYearsOfExperienceAndSkill_Success() throws Exception {
		// setup
		List<JobDetailsEntity> entity = buildJobDetailsEntity();
		when(jobRepo.findBySkillAndYearsOfExperience(SAMPLE_SKILL, SAMPLE_YOE)).thenReturn(entity);
		when(dozerMapper.map(any(JobDetailsEntity.class), eq(JobDetailsResponse.class)))
				.thenReturn(buildJobDetailsResponseFromEntity(entity.get(0)));

		// execute
		List<JobDetailsResponse> response = recruiterServiceImpl.getAllJobByYearsOfExperienceAndSkill(SAMPLE_SKILL,
				SAMPLE_YOE);

		// assert
		verify(jobRepo).findBySkillAndYearsOfExperience(SAMPLE_SKILL, SAMPLE_YOE);
		verify(jobRepo, never()).findAll();
		assertTrue(response.size() > 0);
	}

	/**
	 * Tests getAllJobByYearsOfExperienceAndSkill with no skill and experience
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_getAllJobByYearsOfExperienceAndSkill_Success_NoSkill() throws Exception {
		// setup
		List<JobDetailsEntity> entity = buildJobDetailsEntity();
		when(jobRepo.findAll()).thenReturn(entity);
		when(dozerMapper.map(any(JobDetailsEntity.class), eq(JobDetailsResponse.class)))
				.thenReturn(buildJobDetailsResponseFromEntity(entity.get(0)));

		// execute
		List<JobDetailsResponse> response = recruiterServiceImpl.getAllJobByYearsOfExperienceAndSkill(null, null);

		// assert
		verify(jobRepo, never()).findBySkillAndYearsOfExperience(SAMPLE_SKILL, SAMPLE_YOE);
		verify(jobRepo).findAll();
		assertTrue(response.size() > 0);
	}
}
