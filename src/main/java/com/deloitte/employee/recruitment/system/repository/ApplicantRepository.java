package com.deloitte.employee.recruitment.system.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.employee.recruitment.system.model.ApplicantDetailsEntity;

/**
 * CRUD repo to perform DML operations on {@code ApplicantDetailsEntity}
 * 
 * @author nushrivastava
 *
 */
@Repository
public interface ApplicantRepository extends CrudRepository<ApplicantDetailsEntity, Integer> {

	ApplicantDetailsEntity findByApplicantId(Integer applicantId);

	List<ApplicantDetailsEntity> findBySkillAndYearsOfExperience(String skill, Integer yearsOfExperience);

}