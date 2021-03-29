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
import com.enrollment.demo.db.entities.DependentDTO;
import com.enrollment.demo.db.entities.Person;
import com.enrollment.demo.db.repositories.DependentRepository;
import com.enrollment.demo.db.repositories.PersonRepository;

@RestController
@RequestMapping("/demo")
public class DependentController {
	
	@Autowired
	private DependentRepository dependentRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/dependents")
	public List<DependentDTO> getAllDependents() {
		List<Dependent> allDependents = dependentRepository.findAll();
		List<DependentDTO> allDependentDTOs = new ArrayList<>();
		for (Dependent dependent : allDependents) {
			DependentDTO dependentDTO = getDependentById(dependent.getId()).getBody();

			allDependentDTOs.add(dependentDTO);
		}
		return allDependentDTOs;
	}

	@GetMapping("/dependents/{id}")
	public ResponseEntity<DependentDTO> getDependentById(@PathVariable(value = "id") Long dependentId) {
		Dependent dependent = dependentRepository.findById(dependentId).orElseThrow();
		Person person = personRepository.findById(dependent.getPersonId()).orElseThrow();
		DependentDTO dependentDTO = new DependentDTO(dependent, person);
		return ResponseEntity.ok().body(dependentDTO);
	}

	@GetMapping("/dependents/enrollee/{id}")
	public List<DependentDTO> getDependentsByEnrolleeId(@PathVariable(value = "id") Long enrolleeId) {
		List<Dependent> allDependents = dependentRepository.findByEnrolleeId(enrolleeId);
		List<DependentDTO> allDependentDTOs = new ArrayList<>();
		for (Dependent dependent : allDependents) {
			Person person = personRepository.findById(dependent.getPersonId()).orElseThrow();
			DependentDTO dependentDTO = new DependentDTO(dependent, person);
			allDependentDTOs.add(dependentDTO);
		}
		return allDependentDTOs;
	}

	@PostMapping("/dependents")
	public DependentDTO createDependent(@RequestBody DependentDTO dependent) {

		Person person = personRepository.save(dependent.persistPerson());
		dependent.setPersonId(person.getId());
		Dependent newDependent = dependentRepository.save(dependent.persistDependent());
		dependent.setDependentId(newDependent.getId());
		
		return dependent;
	}

	@PutMapping("/dependents/{id}")
	public ResponseEntity<DependentDTO> updateDependent(@PathVariable(value = "id") Long dependentId,
			@RequestBody DependentDTO dependentDetails) {
		Dependent dependent = dependentRepository.findById(dependentId).orElseThrow();
		Person person = personRepository.findById(dependent.getPersonId()).orElseThrow();

		dependentDetails.setDependentId(dependent.getId());
		dependentDetails.setPersonId(person.getId());

		dependentRepository.save(dependentDetails.persistDependent());
		personRepository.save(dependentDetails.persistPerson());

		return ResponseEntity.ok(dependentDetails);
	}
	
	@DeleteMapping("/dependents/{id}")
	public Map<String, Boolean> deleteDependent(@PathVariable(value = "id") Long dependentId) {
		Dependent dependent = dependentRepository.findById(dependentId).orElseThrow();
		Person person = personRepository.findById(dependent.getPersonId()).orElseThrow();
		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}