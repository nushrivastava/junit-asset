package com.deloitte.employee.recruitment.system.service;

import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import com.deloitte.employee.recruitment.system.common.TestCommon;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsEntity;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsResponse;
import com.deloitte.employee.recruitment.system.repository.ApplicationRepository;
import com.deloitte.employee.recruitment.system.repository.JobRepository;
import com.deloitte.employee.recruitment.system.repository.RecruiterRepository;

/**
 * Junits tests for unit testing {@code RecruiterServiceImpl}
 * 
 * @author nushrivastava
 *
 */
//@RunWith(MockitoJUnitRunner.class)
public class TestRecruiterServiceImplWithJMock extends TestCommon {

	public Mockery context = new JUnit4Mockery() {{ setImposteriser(ClassImposteriser.INSTANCE); }};
	
//	@InjectMocks
//	private RecruiterServiceImpl recruiterServiceImpl;

//	@Mock
	private RecruiterRepository recruiterRepo;

//	@Mock
	private ApplicationRepository applicationRepo;

//	@Mock
	private JobRepository jobRepo;

//	@Mock
	private DozerBeanMapper dozerMapper;

	@Captor
	private ArgumentCaptor<InterviewerDetailsEntity> interviewerArgumentCaptor;

	/**
	 * Tests getInterviewerDetailsById success scenario.
	 * 
	 * @throws Exception
	 */
//	@Test
	public void test_getInterviewerDetailsById_Success() throws Exception {
		// setup
		RecruiterService recruiterService = context.mock(RecruiterService.class);
		
		InterviewerDetailsEntity interviewerEntity = buildInterviewerDetailsEntity();
		recruiterRepo = context.mock(RecruiterRepository.class);
		dozerMapper = context.mock(DozerBeanMapper.class);
		context.checking(new Expectations() {{
		    oneOf (recruiterRepo).findById(SAMPLE_INTERVIEWER_ID); will(returnValue(Optional.of(interviewerEntity)));
				oneOf(dozerMapper).map(with(same(InterviewerDetailsEntity.class)),
						with(same(InterviewerDetailsResponse.class)));
				will(returnValue(buildInterviewerDetailsResponseFromEntity(interviewerEntity)));
		}});
		
		InterviewerDetailsResponse response = recruiterService.getInterviewerDetailsById(SAMPLE_INTERVIEWER_ID);
		System.out.println(response);
		context.assertIsSatisfied();
//		InterviewerDetailsEntity interviewerEntity = buildInterviewerDetailsEntity();
//		when(recruiterRepo.findById(SAMPLE_INTERVIEWER_ID)).thenReturn(Optional.of(interviewerEntity));
//		when(dozerMapper.map(any(), eq(InterviewerDetailsResponse.class)))
//				.thenReturn(buildInterviewerDetailsResponseFromEntity(interviewerEntity));
//
//		// execute
//		InterviewerDetailsResponse response = recruiterServiceImpl.getInterviewerDetailsById(SAMPLE_INTERVIEWER_ID);
//
//		// assert
//		assertTrue(null != response);
//		assertEquals(SAMPLE_INTERVIEWER_ID, response.getInterviewerId());
//		verify(recruiterRepo).findById(any(Integer.class));
	}

/*	*//**
	 * Tests getInterviewerDetailsById when no entity is found in database.
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests getInterviewerDetailsById for ServiceException.
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests addOrUpdateInterviewer to add new interviewer.
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests addOrUpdateInterviewer to update an existing interviewer.
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests addOrUpdateInterviewer to update an existing interviewer.
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests addOrUpdateInterviewer to update an existing interviewer.
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests getAllJobByYearsOfExperienceAndSkill with valid skill and
	 * experience
	 * 
	 * @throws Exception
	 *//*
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

	*//**
	 * Tests getAllJobByYearsOfExperienceAndSkill with no skill and experience
	 * 
	 * @throws Exception
	 *//*
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
	}*/
}
