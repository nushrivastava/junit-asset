package com.deloitte.employee.recruitment.system.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import com.deloitte.employee.recruitment.system.common.SystemUtil;
import com.deloitte.employee.recruitment.system.common.TestCommon;
import com.deloitte.employee.recruitment.system.exception.ServiceException;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsResponse;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;
import com.deloitte.employee.recruitment.system.repository.ApplicantRepository;
import com.deloitte.employee.recruitment.system.repository.JobRepository;

/**
 * Junits tests for unit testing {@code ApplicantServiceImpl}
 * @author nushrivastava
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest({SystemUtil.class})
public class TestApplicantServiceImplWithPowerMockito extends TestCommon{
	
	@InjectMocks
	private ApplicantServiceImpl applicantServiceImpl;

	@Mock
	private ApplicantRepository applicantRepo;

	@Mock
	private RecruiterService recruiterService;

	@Mock
	private JobRepository jobRepo;

	@Mock
	private DozerBeanMapper dozerMapper;
	
	@Captor
	private ArgumentCaptor<ApplicantDetailsEntity> applicantApplyJobArgumentCaptor;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { new JobDetailsEntity(3, "Sample Job 3", "JAVA", "Consultant", 6) },
			{ new JobDetailsEntity(4, "Sample Job 4", "JAVA", "Manager", 12) } });
	}

	@Parameterized.Parameter(value = 0)
	public JobDetailsEntity job;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

	/**
	 * Tests applyJob success scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_applyJob_Success() throws Exception {
		// setup
		ApplicantDetailsEntity applicantEntity = buildApplicantEntity();
		ApplicationDetailsEntity applicationEntity = buildApplicationDetailsEntity();

		PowerMockito.mockStatic(SystemUtil.class);
		when(SystemUtil.generateApplication(any(ApplicantDetailsEntity.class), any(JobDetailsEntity.class)))
				.thenReturn(applicationEntity);
		when(dozerMapper.map(any(ApplicantDetailsEntity.class), eq(ApplicantDetailsResponse.class)))
				.thenReturn(buildApplicantResponseFromEntity(applicantEntity));
		when(dozerMapper.map(any(ApplicationDetailsEntity.class), eq(ApplicationDetailsResponse.class)))
				.thenReturn(buildApplicationResponseFromEntity(applicationEntity));
		when(applicantRepo.findByApplicantId(any(Integer.class))).thenReturn(applicantEntity);
		when(jobRepo.findByJobId(any(Integer.class))).thenReturn(buildJobDetailsEntity().get(0));
		when(applicantRepo.save(applicantApplyJobArgumentCaptor.capture())).thenReturn(applicantEntity);

		// execute
		ApplicantDetailsResponse response = applicantServiceImpl.applyJob(SAMPLE_APPLICANT_ID, SAMPLE_JOB_ID);

		// assert
		PowerMockito.verifyStatic(SystemUtil.class);
		SystemUtil.generateApplication(any(ApplicantDetailsEntity.class), any(JobDetailsEntity.class));

		verify(applicantRepo).save(any(ApplicantDetailsEntity.class));
		
		ApplicantDetailsEntity savedApplicant = applicantApplyJobArgumentCaptor.getValue();
		assertTrue(null != savedApplicant.getApplicationDetails());
		assertTrue(null != savedApplicant.getApplicationDetails().get(0).getJob());
		assertTrue(response.getApplication().get(0).getApplicationId() > 0);
	}

	/**
	 * Tests applyJob when applicants years of experience is less that required
	 * experience of the job.
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test_applyJob_Failure_IllegalArgumentException() throws Exception {
		// setup
		ApplicantDetailsEntity applicantEntity = buildApplicantEntity();
		applicantEntity.setYearsOfExperience(job.getYearsOfExperience() - 2);

		when(applicantRepo.findByApplicantId(any(Integer.class))).thenReturn(applicantEntity);
		when(jobRepo.findByJobId(any(Integer.class))).thenReturn(job);
		
		// execute
		applicantServiceImpl.applyJob(SAMPLE_APPLICANT_ID, SAMPLE_JOB_ID);

		// assert
		verify(applicantRepo, never()).save(any(ApplicantDetailsEntity.class));
	}
	
	/**
	 * Tests applyJob when applicants years of experience is less that required
	 * experience of the job.
	 * 
	 * @throws Exception
	 */
	@Test(expected = ServiceException.class)
	public void test_applyJob_Failure_ServiceException() throws Exception {
		// setup
		ApplicantDetailsEntity applicantEntity = buildApplicantEntity();
		applicantEntity.setYearsOfExperience(job.getYearsOfExperience() - 2);

		when(applicantRepo.findByApplicantId(any(Integer.class))).thenReturn(applicantEntity);
		doThrow(EntityNotFoundException.class).when(jobRepo).findByJobId(any(Integer.class));
		
		// execute
		applicantServiceImpl.applyJob(SAMPLE_APPLICANT_ID, SAMPLE_JOB_ID);

		// assert
		verify(applicantRepo, never()).save(any(ApplicantDetailsEntity.class));
	}
}

