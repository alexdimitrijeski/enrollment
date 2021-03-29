package com.enrollment.demo.db.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void givenPersonInfoWhenCreatingPersonExpectMatching() {
		String firstName = "James";
		String lastName = "Tester";
		String birthDate = "05/06/1980";
		
		Person person = new Person(firstName, lastName, birthDate);
		
		assertTrue(firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()) && birthDate.equals(person.getBirthDate()));
	}

}
