package com.deloitte.employee.recruitment.system.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.employee.recruitment.system.model.ApplicationDetailsEntity;

/**
 * CRUD repo to perform DML operations on {@code ApplicantDetailsEntity}
 * 
 * @author nushrivastava
 *
 */
@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationDetailsEntity, Integer> {

	ApplicationDetailsEntity findByApplicationId(Integer applicantId);

	List<ApplicationDetailsEntity> findByRound(Integer roundId);

	List<ApplicationDetailsEntity> findByIsRejected(Boolean isRejected);

}