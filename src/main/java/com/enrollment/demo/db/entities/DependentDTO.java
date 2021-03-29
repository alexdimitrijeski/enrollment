package com.enrollment.demo.db.entities;

import org.springframework.beans.factory.annotation.Autowired;

import com.enrollment.demo.db.controllers.DependentController;

public class DependentDTO extends PersonDTO {

	private long dependentId;
	private long enrolleeId;

	@Autowired
	DependentController dependentController;

	public DependentDTO() {
	}

	public DependentDTO(Dependent dependent, Person person) {
		this.setBirthDate(person.getBirthDate());
		this.setFirstName(person.getFirstName());
		this.setLastName(person.getLastName());
		this.setPersonId(person.getId());
		this.setEnrolleeId(dependent.getEnrolleeId());
		this.setDependentId(dependent.getId());
	}

	public long getDependentId() {
		return dependentId;
	}

	public void setDependentId(long dependentId) {
		this.dependentId = dependentId;
	}

	public long getEnrolleeId() {
		return enrolleeId;
	}

	public void setEnrolleeId(long enrolleeId) {
		this.enrolleeId = enrolleeId;
	}

	public Dependent persistDependent() {
		Dependent dependent = new Dependent(personId, enrolleeId);
		return dependent;
	}

}