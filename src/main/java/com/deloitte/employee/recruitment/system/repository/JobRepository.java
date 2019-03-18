package com.deloitte.employee.recruitment.system.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.employee.recruitment.system.model.JobDetailsEntity;

/**
 * CRUD repo to perform DML operations on {@code JobDetailsEntity}
 * 
 * @author nushrivastava
 *
 */
@Repository
public interface JobRepository extends CrudRepository<JobDetailsEntity, Integer> {

	JobDetailsEntity findByJobId(Integer jobId);

	List<JobDetailsEntity> findBySkillAndYearsOfExperience(String skill, Integer yearsOfExperience);

}