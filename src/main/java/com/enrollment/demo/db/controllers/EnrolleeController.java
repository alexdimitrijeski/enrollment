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

import com.enrollment.demo.db.entities.Dependent;
import com.enrollment.demo.db.entities.Enrollee;
import com.enrollment.demo.db.entities.EnrolleeDTO;
import com.enrollment.demo.db.entities.Person;
import com.enrollment.demo.db.repositories.DependentRepository;
import com.enrollment.demo.db.repositories.EnrolleeRepository;
import com.enrollment.demo.db.repositories.PersonRepository;

@RestController
@RequestMapping("/demo")
public class EnrolleeController {

	@Autowired
	private EnrolleeRepository enrolleeRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private DependentRepository dependentRepository;

	@GetMapping("/enrollees")
	public List<EnrolleeDTO> getAllEnrollees() {
		List<Enrollee> allEnrollees = enrolleeRepository.findAll();
		List<EnrolleeDTO> allEnrolleeDTOs = new ArrayList<>();
		for (Enrollee enrollee : allEnrollees) {
			allEnrolleeDTOs.add(getEnrolleeById(enrollee.getId()).getBody());
		}
		return allEnrolleeDTOs;
	}

	@GetMapping("/enrollees/{id}")
	public ResponseEntity<EnrolleeDTO> getEnrolleeById(@PathVariable(value = "id") Long enrolleeId) {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow();
		Person person = personRepository.findById(enrollee.getPersonId()).orElseThrow();
		EnrolleeDTO enrolleeDTO = new EnrolleeDTO(enrollee, person);
		return ResponseEntity.ok().body(enrolleeDTO);
	}

	@PostMapping("/enrollees")
	public EnrolleeDTO createEnrollee(@RequestBody EnrolleeDTO enrollee) {
		Person person = personRepository.save(enrollee.persistPerson());
		enrollee.setPersonId(person.getId());
		Enrollee newEnrollee = enrolleeRepository.save(enrollee.persistEnrollee());
		enrollee.setEnrolleeId(newEnrollee.getId());

		return enrollee;
	}

	@PutMapping("/enrollees/{id}")
	public ResponseEntity<EnrolleeDTO> updateEnrollee(@PathVariable(value = "id") Long enrolleeId,
			@RequestBody EnrolleeDTO enrolleeDetails) {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow();
		Person person = personRepository.findById(enrollee.getPersonId()).orElseThrow();

		enrolleeDetails.setEnrolleeId(enrollee.getId());
		enrolleeDetails.setPersonId(person.getId());

		enrolleeRepository.save(enrolleeDetails.persistEnrollee());
		personRepository.save(enrolleeDetails.persistPerson());

		return ResponseEntity.ok(enrolleeDetails);
	}

	@DeleteMapping("/enrollees/{id}")
	public Map<String, Boolean> deleteEnrollee(@PathVariable(value = "id") Long enrolleeId) {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow();
		Person person = personRepository.findById(enrollee.getPersonId()).orElseThrow();
		List<Dependent> dependents = dependentRepository.findByEnrolleeId(enrolleeId);
		for (Dependent dependent : dependents) {
			dependentRepository.delete(dependent);
		}
		enrolleeRepository.delete(enrollee);
		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}