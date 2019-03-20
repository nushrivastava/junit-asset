package com.deloitte.employee.recruitment.system.common;

import com.deloitte.employee.recruitment.system.model.ApplicantDetailsEntity;
import com.deloitte.employee.recruitment.system.model.ApplicationDetailsEntity;
import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;

public class SystemUtil {

	public static ApplicationDetailsEntity generateApplication(ApplicantDetailsEntity applicant, JobDetailsEntity job) {
		ApplicationDetailsEntity application = new ApplicationDetailsEntity();
		application.setNote("New Note");
		application.setIsRejected(false);
		application.setRound(0);
		application.setJob(job);
		application.setApplicationId(applicant.getApplicantId());
		application.setApplicantDetails(applicant);
		return application;
	}
}
