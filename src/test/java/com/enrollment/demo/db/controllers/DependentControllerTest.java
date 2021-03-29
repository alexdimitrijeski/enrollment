package com.enrollment.demo.db.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
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
import com.enrollment.demo.db.repositories.DependentRepository;
import com.enrollment.demo.db.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class DependentControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	DependentRepository dependentRepository;
	@Autowired
	PersonRepository personRepository;

	long testDependentId = 0;
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
	void testCreateDependent() throws Exception {
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
	@Order(2)
	void testGetAllDependents() throws Exception {
		String uri = "/demo/dependents";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		DependentDTO[] dependentList = mapFromJson(content, DependentDTO[].class);
		for (DependentDTO dependentDTO : dependentList) {
			testDependentId = dependentDTO.getDependentId();
		}
		assertTrue(dependentList.length > 0);
	}

	@Test
	@Order(3)
	void testGetDependentById() throws Exception {
		testGetAllDependents();
		String uri = "/demo/dependents/{id}";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri, testDependentId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		DependentDTO dependent = mapFromJson(content, DependentDTO.class);
		assertNotNull(dependent.getFirstName());
	}

	@Test
	@Order(4)
	void testGetDependentsByEnrolleeId() throws Exception {

		testGetAllDependents();
		String uri = "/demo/dependents/enrollee/{id}";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri, testEnrolleeId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();

		DependentDTO[] dependents = mapFromJson(content, DependentDTO[].class);
		for (DependentDTO dependent : dependents) {
			assertNotNull(dependent.getFirstName());
		}
	}

	@Test
	@Order(5)
	void testUpdateDependent() throws Exception {

		testGetAllDependents();
		String uri = "/demo/dependents/{id}";
		String firstName = "Jake";
		String lastName = "Testerson";
		String birthDate = "05/06/1980";
		long enrolleeID = 1;
		DependentDTO dependent = new DependentDTO();
		dependent.setFirstName(firstName);
		dependent.setLastName(lastName);
		dependent.setBirthDate(birthDate);
		dependent.setEnrolleeId(enrolleeID);

		String inputJson = mapToJson(dependent);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, testDependentId)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@Order(6)
	void testDeleteDependent() throws Exception {
		testGetAllDependents();
		String uri = "/demo/dependents/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, testDependentId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
	
	public void setTestEnrolleeId(long testEnrolleeId) {
		this.testEnrolleeId = testEnrolleeId;
	}

}
