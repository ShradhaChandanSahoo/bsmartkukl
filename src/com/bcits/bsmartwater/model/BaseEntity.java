package com.bcits.bsmartwater.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.bcits.bsmartwater.utils.SessionData;

/**
 * Office entity. @author Ravi Shankar Reddy
 */

@MappedSuperclass  
public abstract class BaseEntity implements Serializable {  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="STATUS")
	private String status;

	@Column(name = "CREATED_DT")  
    private Timestamp createdDate;  
  
    @Column(name = "CREATED_BY", length = 45)  
    private String createdBy;  
  
    @Column(name = "LAST_UPDATED_BY", length = 45)  
    private String updatedBy;  
    
    @Column(name = "LAST_UPDATED_DT")  
    private Timestamp lastUpdatedDate;  
    
    public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}  
  
	/** 
     * Sets createdDate and createdBy before insert 
     */  
    @PrePersist  
    public void setCreationDate() {  
        this.createdDate = new Timestamp(new java.util.Date().getTime()); 
        this.status = "Inactive";
        //this.updatedBy = (String) SessionData.getUserDetails().get("userID");
  	    this.createdBy = (String) SessionData.getUserDetails().get("userID");
  	    //this.lastUpdatedDate = new Timestamp(new java.util.Date().getTime()); 
    }  
  
    /** 
     * Sets updatedDate and lastupdatedDate before update 
     */  
    @PreUpdate  
    public void setChangeDate() {  
    	//this.createdDate = new Timestamp(new java.util.Date().getTime()); 
        this.updatedBy = (String) SessionData.getUserDetails().get("userID");
  	   // this.createdBy = (String) SessionData.getUserDetails().get("userID");
  	    this.lastUpdatedDate = new Timestamp(new java.util.Date().getTime()); 
    }
}  
