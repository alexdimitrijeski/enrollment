package com.enrollment.demo.db.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "dependents")
public class Dependent {

    protected long id;
	private long enrolleeId;
	private long personId;

    public Dependent() {
    }
    
    public Dependent(long personId, long enrolleeId) {
    	this.personId = personId;
    	this.enrolleeId = enrolleeId;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    
    @Column(name = "person_id", nullable = false)
    public long getPersonId() {
    	return personId;
    }

    public void setPersonId(long personId) {
    	this.personId = personId;
    }
    
    @Column(name = "enrollee_id", nullable = false)
    public long getEnrolleeId() {
    	return enrolleeId;
    }

    public void setEnrolleeId(long enrolleeId) {
    	this.enrolleeId = enrolleeId;
    }
    
  
    
}