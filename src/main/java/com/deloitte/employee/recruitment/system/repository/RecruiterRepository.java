package com.deloitte.employee.recruitment.system.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.employee.recruitment.system.model.InterviewerDetailsEntity;

/**
 * CRUD repo to perform DML operations on {@code RecruiterDetailsEntity}
 * 
 * @author nushrivastava
 *
 */
@Repository
public interface RecruiterRepository extends CrudRepository<InterviewerDetailsEntity, Integer> {

	InterviewerDetailsEntity findByInterviewerId(Integer interviewerId);

	List<InterviewerDetailsEntity> findBySkillAndYearsOfExperience(String skill, Integer yearsOfExperience);

}