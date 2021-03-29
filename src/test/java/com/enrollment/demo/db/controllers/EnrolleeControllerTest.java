package com.enrollment.demo.db.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.enrollment.demo.EnrollmentApplication;
import com.enrollment.demo.db.entities.DependentDTO;
import com.enrollment.demo.db.entities.EnrolleeDTO;
import com.enrollment.demo.db.repositories.EnrolleeRepository;
import com.enrollment.demo.db.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class EnrolleeControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	EnrolleeRepository enrolleeRepository;
	@Autowired
	PersonRepository personRepository;

	long testEnrolleeId = 1;

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	@Order(1)
	void testCreateEnrollee() throws Exception {
		String uri = "/demo/enrollees";
		String firstName = "Jacob";
		String lastName = "Testerson";
		String birthDate = "05/06/1980";
		String phoneNumber = "(313)555-1212";
		boolean activationStatus = true;
		EnrolleeDTO enrollee = new EnrolleeDTO();
		enrollee.setFirstName(firstName);
		enrollee.setLastName(lastName);
		enrollee.setBirthDate(birthDate);
		enrollee.setPhoneNumber(phoneNumber);
		enrollee.setActivationStatus(activationStatus);
		mvc.perform(MockMvcRequestBuilders.post(uri).content(mapToJson(enrollee))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	@Order(2)
	void testGetAllEnrollees() throws Exception {
		String uri = "/demo/enrollees";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		EnrolleeDTO[] enrolleeList = mapFromJson(content, EnrolleeDTO[].class);
		for (EnrolleeDTO enrolleeDTO : enrolleeList) {

			testEnrolleeId = enrolleeDTO.getEnrolleeId();
		}
		assertTrue(enrolleeList.length > 0);
	}

	@Test
	@Order(3)
	void testAddDependentToEnrollee() throws Exception {
		testGetAllEnrollees();
		String uri = "/demo/dependents";
		String firstName = "Jacob";
		String lastName = "Testerson";
		String birthDate = "05/06/1980";
		DependentDTO dependent = new DependentDTO();
		dependent.setFirstName(firstName);
		dependent.setLastName(lastName);
		dependent.setBirthDate(birthDate);
		dependent.setEnrolleeId(testEnrolleeId);
		mvc.perform(MockMvcRequestBuilders.post(uri).content(mapToJson(dependent))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	@Order(4)
	void testGetEnrolleeById() throws Exception {
		testGetAllEnrollees();
		String uri = "/demo/enrollees/{id}";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri, testEnrolleeId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		EnrolleeDTO enrollee = mapFromJson(content, EnrolleeDTO.class);
		assertNotNull(enrollee.getFirstName());
	}

	@Test
	@Order(5)
	void testUpdateEnrollee() throws Exception {
		testGetAllEnrollees();
		String uri = "/demo/enrollees/{id}";
		String firstName = "Jake";
		String lastName = "Testerson";
		String birthDate = "05/06/1980";
		String phoneNumber = "(313)555-1212";
		boolean activationStatus = false;
		EnrolleeDTO enrollee = new EnrolleeDTO();
		enrollee.setFirstName(firstName);
		enrollee.setLastName(lastName);
		enrollee.setBirthDate(birthDate);
		enrollee.setPhoneNumber(phoneNumber);
		enrollee.setActivationStatus(activationStatus);

		String inputJson = mapToJson(enrollee);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, testEnrolleeId)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@Order(6)
	void testDeleteEnrollee() throws Exception {
		testAddDependentToEnrollee();
		String uri = "/demo/enrollees/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, testEnrolleeId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

}
