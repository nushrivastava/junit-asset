package com.deloitte.employee.recruitment.system.common;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.deloitte.employee.recruitment.system.model.AddInterviewerRequest;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicantDetailsResponse;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsResponse;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsEntity;
import com.deloitte.employee.recruitment.system.model.InterviewerDetailsResponse;
import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;
import com.deloitte.employee.recruitment.system.model.JobDetailsResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class TestCommon {

	protected static final String INTERVIEWER_DETAILS_RESPONSE = "interviewer_details_response.json";
	protected static final String INTERVIEWER_DETAILS_ENTITY = "interviewer_details_entity.json";
	protected static final String SAVE_INTERVIEWER_REQUEST = "save_interviewer_request.json";
	protected static final String SAVE_INTERVIEWER_BAD_REQUEST = "save_interviewer_bad_request.json";
	protected static final String JOB_DETAILS_ENTITY = "job_details_entity.json";
	
	protected static final String YRS_EXPERIENCE_PARAM = "yearsOfExperience";
	protected static final Integer SAMPLE_INTERVIEWER_ID = 1;
	protected static final String SAMPLE_SKILL = "Spring Boot";
	protected static final Integer SAMPLE_YOE = 8;
	protected static final Integer SAMPLE_APPLICANT_ID = 1;
	protected static final Integer SAMPLE_JOB_ID = 2;
	protected static final String SAMPLE_UPDATE_SKILL = "Hybris";
	protected static final String APPLICANT_DETAILS_ENTITY = "applicant_details_entity.json";
	protected static final String APPLICATION_DETAILS_ENTITY = "application_details_entity.json";
	
	
	/**
	 * Reads json file from the resources folder.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected JsonObject readJsonFile(String fileName) throws Exception {
		final JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser
				.parse(new FileReader(TestCommon.class.getClassLoader().getResource(fileName).getFile()));
		return jsonElement.getAsJsonObject();
	}
	
	/**
	 * builds an objectMapper object.
	 * 
	 * @return
	 */
	protected ObjectMapper buildObjectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * builds interviewer details response object
	 * @return
	 * @throws Exception
	 */
	protected InterviewerDetailsResponse buildInterviewerDetailsResponse() throws Exception{
		return buildObjectMapper().readValue(readJsonFile(INTERVIEWER_DETAILS_RESPONSE).toString(),
				InterviewerDetailsResponse.class);
	}
	
	/**
	 * builds interviewer details response object
	 * @return
	 * @throws Exception
	 */
	protected InterviewerDetailsEntity buildInterviewerDetailsEntity() throws Exception{
		return buildObjectMapper().readValue(readJsonFile(INTERVIEWER_DETAILS_ENTITY).toString(),
				InterviewerDetailsEntity.class);
	}
	
	/**
	 * builds interviewer details response object from entity object.
	 * @return
	 * @throws Exception
	 */
	protected InterviewerDetailsResponse buildInterviewerDetailsResponseFromEntity(InterviewerDetailsEntity entity) throws Exception{
		InterviewerDetailsResponse response = new InterviewerDetailsResponse();
		response.setDesignation(entity.getDesignation());
		response.setFirstName(entity.getFirstName());
		response.setLastName(entity.getLastName());
		response.setSkill(entity.getSkill());
		response.setYearsOfExperience(entity.getYearsOfExperience());
		response.setInterviewerId(entity.getInterviewerId());
		return response;
	}
	
	/**
	 * builds add interviewer request object
	 * @return
	 * @throws Exception
	 */
	protected AddInterviewerRequest buildAddInterviewerRequest() throws Exception{
		return buildObjectMapper().readValue(readJsonFile(SAVE_INTERVIEWER_REQUEST).toString(),
				AddInterviewerRequest.class);
	}
	
	/**
	 * builds interviewer details response object from entity object.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected InterviewerDetailsEntity buildInterviewerDetailsEntityFromRequest(AddInterviewerRequest request)
			throws Exception {
		InterviewerDetailsEntity response = new InterviewerDetailsEntity();
		response.setDesignation(request.getDesignation());
		response.setFirstName(request.getFirstName());
		response.setLastName(request.getLastName());
		response.setSkill(request.getSkill());
		response.setYearsOfExperience(request.getYearsOfExperience());
		response.setInterviewerId(request.getInterviewerId());
		return response;
	}
	
	/**
	 * builds job details list.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected List<JobDetailsEntity> buildJobDetailsEntity() throws Exception {
		final JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser
				.parse(new FileReader(TestCommon.class.getClassLoader().getResource(JOB_DETAILS_ENTITY).getFile()));
		
		List<JobDetailsEntity> response = new ArrayList<JobDetailsEntity>();
		JsonArray entities = jsonElement.getAsJsonArray();
		Iterator<JsonElement> iterator = entities.iterator();
		while (iterator.hasNext()) {
			JobDetailsEntity entity = buildObjectMapper().readValue(iterator.next().getAsJsonObject().toString(), JobDetailsEntity.class);
			response.add(entity);
		}
		return response;
	}
	
	/**
	 * builds job details response object from entity object.
	 * @return
	 * @throws Exception
	 */
	protected JobDetailsResponse buildJobDetailsResponseFromEntity(JobDetailsEntity entity) throws Exception{
		JobDetailsResponse response = new JobDetailsResponse();
		response.setDesignation(entity.getDesignation());
		response.setDescription(entity.getDescription());
		response.setJobId(entity.getJobId());
		response.setSkillsRequired(entity.getSkillsRequired());
		response.setYearsOfExperience(entity.getYearsOfExperience());
		return response;
	}
	
	/**
	 * builds applicant details response object
	 * @return
	 * @throws Exception
	 */
	protected ApplicantDetailsEntity buildApplicantEntity() throws Exception{
		return buildObjectMapper().readValue(readJsonFile(APPLICANT_DETAILS_ENTITY).toString(),
				ApplicantDetailsEntity.class);
	}
	
	/**
	 * builds applicant details response object from entity object.
	 * @return
	 * @throws Exception
	 */
	protected ApplicantDetailsResponse buildApplicantResponseFromEntity(ApplicantDetailsEntity entity) throws Exception{
		ApplicantDetailsResponse response = new ApplicantDetailsResponse();
		response.setApplicantId(entity.getApplicantId());
		response.setFirstName(entity.getFirstName());
		response.setLastName(entity.getLastName());
		response.setSkill(entity.getSkill());
		response.setYearsOfExperience(entity.getYearsOfExperience());
		response.setEmail(entity.getEmail());
		return response;
	}
	
	/**
	 * builds application details response object
	 * @return
	 * @throws Exception
	 */
	protected ApplicationDetailsEntity buildApplicationDetailsEntity() throws Exception{
		return buildObjectMapper().readValue(readJsonFile(APPLICATION_DETAILS_ENTITY).toString(),
				ApplicationDetailsEntity.class);
	}
	
	/**
	 * builds applicant details response object from entity object.
	 * @return
	 * @throws Exception
	 */
	protected ApplicationDetailsResponse buildApplicationResponseFromEntity(ApplicationDetailsEntity entity) throws Exception{
		ApplicationDetailsResponse response = new ApplicationDetailsResponse();
		response.setApplicationId(entity.getApplicationId());
		response.setNote(entity.getNote());
		response.setNote(entity.getNote());
		response.setRound(entity.getRound());
		return response;
	}
}

