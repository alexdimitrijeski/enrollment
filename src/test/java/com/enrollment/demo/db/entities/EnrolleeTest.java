package com.enrollment.demo.db.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EnrolleeTest {

	@Test
	void givenEnrolleeInfoWhenCreatingEnrolleeExpectMatching() {
		int personId = 1;
		boolean activtionStatus = true;
		String phoneNumber = "(313-555-1212)";

		Enrollee enrollee = new Enrollee(personId, activtionStatus, phoneNumber);

		assertTrue(personId == enrollee.getPersonId() && activtionStatus == enrollee.getActivationStatus()
				&& phoneNumber.equals(enrollee.getPhoneNumber()));
	}

}
