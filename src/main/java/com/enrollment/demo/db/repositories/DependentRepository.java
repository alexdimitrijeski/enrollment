package com.enrollment.demo.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enrollment.demo.db.entities.Dependent;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long> {

	List<Dependent> findByEnrolleeId(long enrolleeId);

}
