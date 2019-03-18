package com.deloitte.employee.recruitment.system;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for {@code JunitAssetApplication}
 * 
 * @author nushrivastava
 *
 */
@Configuration
public class EmployeeRecruitmentConfig {

	@Bean
	public DozerBeanMapper dozerBean(){
		DozerBeanMapper dozerBean = new DozerBeanMapper();
		return dozerBean;
	}
}
