package com.enrollment.demo.db.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DependentTest {

	@Test
	void givenDependentInfoWhenCreatingDependentExpectMatching() {
		int personId = 1;
		int enrolleeId = 1;

		Dependent dependent = new Dependent(personId, enrolleeId);

		assertTrue(personId == dependent.getPersonId() && enrolleeId == dependent.getEnrolleeId());
	}

}
