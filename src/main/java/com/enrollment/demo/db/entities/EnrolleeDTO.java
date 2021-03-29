package com.enrollment.demo.db.entities;

import org.springframework.beans.factory.annotation.Autowired;

import com.enrollment.demo.db.controllers.EnrolleeController;

public class EnrolleeDTO extends PersonDTO {

	private long enrolleeId;
	private boolean activationStatus;
	private String phoneNumber;

	@Autowired
	EnrolleeController enrolleeController;

	public EnrolleeDTO() {
	}

	public EnrolleeDTO(Enrollee enrollee, Person person) {
		this.setPersonId(person.getId());
		this.setPersonId(person.getId());
		this.setBirthDate(person.getBirthDate());
		this.setFirstName(person.getFirstName());
		this.setLastName(person.getLastName());
		this.setPhoneNumber(enrollee.getPhoneNumber());
		this.setEnrolleeId(enrollee.getId());
		this.setActivationStatus(enrollee.getActivationStatus());
	}

	public long getEnrolleeId() {
		return enrolleeId;
	}

	public void setEnrolleeId(long enrolleeId) {
		this.enrolleeId = enrolleeId;
	}

	public boolean getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Enrollee persistEnrollee() {
		Enrollee enrollee = new Enrollee(personId, activationStatus, phoneNumber);

		return enrollee;
	}
}