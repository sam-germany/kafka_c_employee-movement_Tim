package com.course.microservice.command.service;

import com.course.microservice.command.action.EmployeeAssignmentPollingAction;
import com.course.microservice.entity.EmployeeAssignment;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployeeAssignmentPollingService {

	@Autowired
	private EmployeeAssignmentPollingAction action;

	@Transactional
	public void delete(long id) throws JsonProcessingException {

		var existingEntity = action.findById(id);

		if (existingEntity.isPresent()) {
			action.deleteById(id);
			action.insertIntoOutbox(existingEntity, "DELETE");
		}
	}

	@Transactional
	public long insert(EmployeeAssignment entity) throws JsonProcessingException {

		var savedEntity = action.insert(entity);
		action.insertIntoOutbox(savedEntity, "INSERT");

		return savedEntity.getAssignmentId();
	}


}
