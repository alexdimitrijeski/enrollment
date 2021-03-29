package com.enrollment.demo.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enrollment.demo.db.entities.Enrollee;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {

}
