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
import com.enrollment.demo.db.entities.PersonDTO;
import com.enrollment.demo.db.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnrollmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	PersonRepository personRepository;

	long testPersonId = 0;

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
	void testCreatePerson() throws Exception {
		String uri = "/demo/persons";
		String firstName = "Jacob";
		String lastName = "Testerson";
		String birthDate = "05/06/1980";
		PersonDTO person = new PersonDTO();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setBirthDate(birthDate);

		mvc.perform(MockMvcRequestBuilders.post(uri).content(mapToJson(person)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	@Order(2)
	void testGetAllPersons() throws Exception {
		String uri = "/demo/persons";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		PersonDTO[] personList = mapFromJson(content, PersonDTO[].class);
		for (PersonDTO personDTO : personList) {
			testPersonId = personDTO.getPersonId();
		}
		assertTrue(personList.length > 0);
	}

	@Test
	@Order(3)
	void testGetPersonById() throws Exception {
		testGetAllPersons();
		String uri = "/demo/persons/{id}";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri, testPersonId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		PersonDTO person = mapFromJson(content, PersonDTO.class);
		assertNotNull(person.getFirstName());
	}

	@Test
	@Order(4)
	void testUpdatePerson() throws Exception {
		testGetAllPersons();
		String uri = "/demo/persons/{id}";
		String firstName = "Jake";
		String lastName = "Testerson";
		String birthDate = "05/06/1980";
		PersonDTO person = new PersonDTO();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setBirthDate(birthDate);

		String inputJson = mapToJson(person);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, testPersonId)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@Order(5)
	void testDeletePerson() throws Exception {
		testGetAllPersons();
		String uri = "/demo/persons/{id}";
		mvc.perform(MockMvcRequestBuilders.delete(uri, testPersonId).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

}
