package com.deloitte.employee.recruitment.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({EmployeeRecruitmentConfig.class})
public class EmployeeRecruitmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRecruitmentApplication.class, args);
	}
}