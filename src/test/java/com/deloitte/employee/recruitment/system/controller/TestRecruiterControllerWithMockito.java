package com.deloitte.employee.recruitment.system.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.deloitte.employee.recruitment.system.common.TestCommon;
import com.deloitte.employee.recruitment.system.model.AddInterviewerRequest;
import com.deloitte.employee.recruitment.system.service.ApplicantService;
import com.deloitte.employee.recruitment.system.service.RecruiterServiceImpl;
import com.google.gson.JsonObject;

@RunWith(MockitoJUnitRunner.class)
public class TestRecruiterControllerWithMockito extends TestCommon {

	@Mock
	private ApplicantService applicantService;

	@Mock
	private RecruiterServiceImpl recruiterService;

	private MockMvc mockMvc;

	@InjectMocks
	private RecruiterController recruiterController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(recruiterController).build();
	}

	/**
	 * Tests adding new interviewer success scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSaveInterviewer_Success() throws Exception {
		// set up
		JsonObject request = readJsonFile(SAVE_INTERVIEWER_REQUEST);
		when(recruiterService.addOrUpdateInterviewer(any(AddInterviewerRequest.class)))
				.thenReturn(buildInterviewerDetailsResponse());

		// execute
		MockHttpServletResponse response = mockMvc.perform(
				post("/recruiter/interviewer").contentType(MediaType.APPLICATION_JSON).content(request.toString()))
				.andReturn().getResponse();
		
		//assert
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertNotNull(response);
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
		MockHttpServletResponse response = mockMvc.perform(
				post("/recruiter/interviewer").contentType(MediaType.APPLICATION_JSON).content(request.toString())).andReturn().getResponse();
//		.andExpect(status().isBadRequest());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	/**
	 * Tests get interviewer by Id success scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetInterviewerById_Success() throws Exception {
		// set up
		when(recruiterService.getInterviewerDetailsById(any(Integer.class)))
				.thenReturn(buildInterviewerDetailsResponse());

		// execute
		mockMvc.perform(get("/recruiter/interviewer/{interviewerId}", "1")).andExpect(status().isOk());
	}

	/**
	 * Tests get interviewer by Id failure scenario where the service throws
	 * exception during execution.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetInterviewerById_Failure_ServiceException() throws Exception {
		// set up
		doThrow(NullPointerException.class).when(recruiterService).getInterviewerDetailsById(any(Integer.class));

		// execute
		mockMvc.perform(get("/recruiter/interviewer/{interviewerId}", "1")).andExpect(status().isInternalServerError());
	}

	/**
	 * Tests delete interviewer by Id success scenario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteInterviewerById_Success() throws Exception {
		// set up
		doNothing().when(recruiterService).deleteInterviewerById(any());

		// execute
		mockMvc.perform(delete("/recruiter/interviewer/{interviewerId}", "1")).andExpect(status().isOk());
	}
}
