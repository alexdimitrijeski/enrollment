package com.enrollment.demo.db.entities;


import org.springframework.beans.factory.annotation.Autowired;

import com.enrollment.demo.db.controllers.PersonController;

public class PersonDTO {

	protected long personId;
	protected String firstName;
	protected String lastName;
	protected String birthDate;
	@Autowired
	PersonController personController;

	public PersonDTO() {
	}

	public PersonDTO(Person person) {
		this.personId = person.getId();
		this.birthDate = person.getBirthDate();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Person persistPerson() {
		Person person = new Person(firstName, lastName, birthDate);
		person.setId(personId);

		return person;
	}
}