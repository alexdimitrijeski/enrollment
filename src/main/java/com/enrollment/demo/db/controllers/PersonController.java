package com.enrollment.demo.db.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enrollment.demo.db.entities.Person;
import com.enrollment.demo.db.entities.PersonDTO;
import com.enrollment.demo.db.repositories.PersonRepository;

@RestController
@RequestMapping("/demo")
public class PersonController {
	
	@Autowired
    public PersonRepository personRepository;

	@GetMapping("/persons")
	public List<PersonDTO> getAllPersons() {
		List<Person> allPersons = personRepository.findAll();
		List<PersonDTO> allPersonDTOs = new ArrayList<>();
		for (Person person : allPersons) {
			allPersonDTOs.add(new PersonDTO(person));
		}
		return allPersonDTOs;
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<PersonDTO> getPersonById(@PathVariable(value = "id") Long personId) {
		Person person = personRepository.findById(personId).orElseThrow();
		PersonDTO personDTO = new PersonDTO(person);
		return ResponseEntity.ok().body(personDTO);
	}

	@PostMapping("/persons")
	public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
		return new PersonDTO(personRepository.save(personDTO.persistPerson()));
	}
	
	@PutMapping("/persons/{id}")
	public ResponseEntity<PersonDTO> updatePerson(@PathVariable(value = "id") Long personId,
			@RequestBody PersonDTO personDetails) {
		Person person = personRepository.findById(personId).orElseThrow();

		person.setLastName(personDetails.getLastName());
		person.setFirstName(personDetails.getFirstName());
		final Person updatedPerson = personRepository.save(person);
		return ResponseEntity.ok(new PersonDTO(updatedPerson));
	}

	@DeleteMapping("/persons/{id}")
	public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personId) {
		Person person = personRepository.findById(personId).orElseThrow();

		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}