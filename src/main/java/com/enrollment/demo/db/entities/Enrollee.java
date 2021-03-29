package com.enrollment.demo.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enrollees")
public class Enrollee {

    protected long id;
	private long personId;
    private boolean activationStatus;
    private String phoneNumber;

    public Enrollee() {
    }

    public Enrollee(long personId, boolean activationStatus, String phoneNumber) {
    	this.personId = personId;
    	this.activationStatus = activationStatus;
    	this.phoneNumber = phoneNumber;
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

    @Column(name = "activation_status", nullable = false)
    public boolean getActivationStatus() {
    	return activationStatus;
    }
    
    public void setActivationStatus(boolean activationStatus) {
    	this.activationStatus = activationStatus;
    }
    
    @Column(name = "phone_number", nullable = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
  
}