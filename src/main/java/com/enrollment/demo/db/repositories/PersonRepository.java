package com.enrollment.demo.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enrollment.demo.db.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
