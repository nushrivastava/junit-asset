package com.deloitte.employee.recruitment.system.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.deloitte.employee.recruitment.system.common.TestCommon;
import com.google.gson.JsonObject;

//@RunWith(JMock.class)O
public class TestRecruiterControllerWithJMockit extends TestCommon {

//	Mockery context = new Mockery();
//	@Injectable
//	private ApplicantService applicantService;

	
//	private final RecruiterServiceImpl recruiterService =  context.mock(RecruiterServiceImpl.class);

	private MockMvc mockMvc;

//	private RecruiterController recruiterController;
	
//	private ConfigurableApplicationContext applicationContext;

//	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	@Before
	public void setup() {
//		mockMvc = MockMvcBuilders.standaloneSetup(recruiterController).build();
//		recruiterController.add(recruiterService);
//		recruiterController = new RecruiterController();
	}

	/**
	 * Tests adding new interviewer success scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSaveInterviewer_Success() throws Exception {
		// set up
		/*final RecruiterServiceImpl recruiterService =  context.mock(RecruiterServiceImpl.class);
		RecruiterController recruiterController = new RecruiterController();
		context.checking(new Expectations() {
			{
				oneOf (recruiterService).addOrUpdateInterviewer(buildAddInterviewerRequest());
				will(returnValue(buildInterviewerDetailsResponse()));
			}
		});*/
		// execute
//		MockHttpServletResponse response = mockMvc.perform(
//				post("/recruiter/interviewer").contentType(MediaType.APPLICATION_JSON).content(request.toString()))
//				.andReturn().getResponse();
//		
//		mockMvc.perform(post("/recruiter/interviewer")).andExpect(status().isOk());
		/*recruiterController.saveInterviewer(buildAddInterviewerRequest());
		
		//verify
		context.assertIsSatisfied();*/
		/*new Verifications() {
			{
				recruiterService.addOrUpdateInterviewer(any(AddInterviewerRequest.class));
				times = 1;
			}
		};*/
//		assertEquals(response.getStatus(), HttpStatus.OK.value());
//		assertNotNull(response);
	}

	/**
	 * Tests adding new interviewer with incorrect skill parameter value.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSaveInterviewer_Failure_BadRequest_IncorrectSkill() throws Exception {
		// set up
		JsonObject request = readJsonFile(SAVE_INTERVIEWER_BAD_REQUEST);

		// execute
		mockMvc.perform(
				post("/recruiter/interviewer").contentType(MediaType.APPLICATION_JSON).content(request.toString()))
		.andExpect(status().isBadRequest());
	}

	/**
	 * Tests adding new interviewer with missing request parameters.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSaveInterviewer_Failure_BadRequest_MissingParameter() throws Exception {
		// set up
		JsonObject request = readJsonFile(SAVE_INTERVIEWER_BAD_REQUEST);
		request.remove(YRS_EXPERIENCE_PARAM);

		// execute
		mockMvc.perform(
				post("/recruiter/interviewer").contentType(MediaType.APPLICATION_JSON).content(request.toString()))
		.andExpect(status().isBadRequest());
	}

	/**
	 * Tests get interviewer by Id success scenario.
	 * 
	 * @throws Exception
	 */
//	@Test
	public void testGetInterviewerById_Success() throws Exception {
		// set up
//		when(recruiterService.getInterviewerDetailsById(any(Integer.class)))
//				.thenReturn(buildInterviewerDetailsResponse());

		// execute
		mockMvc.perform(get("/recruiter/interviewer/{interviewerId}", "1")).andExpect(status().isOk());
	}

	/**
	 * Tests get interviewer by Id failure scenario where the service throws
	 * exception during execution.
	 * 
	 * @throws Exception
	 */
//	@Test
	public void testGetInterviewerById_Failure_ServiceException() throws Exception {
		// set up
//		doThrow(NullPointerException.class).when(recruiterService).getInterviewerDetailsById(any(Integer.class));

		// execute
		mockMvc.perform(get("/recruiter/interviewer/{interviewerId}", "1")).andExpect(status().isInternalServerError());
	}

	/**
	 * Tests delete interviewer by Id success scenario.
	 * 
	 * @throws Exception
	 */
//	@Test
	public void testDeleteInterviewerById_Success() throws Exception {
		// set up
//		doNothing().when(recruiterService).deleteInterviewerById(any());

		// execute
		mockMvc.perform(delete("/recruiter/interviewer/{interviewerId}", "1")).andExpect(status().isOk());
	}
}
