package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bcits.bsmartwater.utils.SessionData;

@Entity
@Table(name = "BRANCH_DETAILS")

@NamedQueries({
/*	@NamedQuery(name="DisconnectionRulesEntity.findByConnectionNo",query="SELECT c FROM ConsumerMaster c WHERE c.connection_no=:connectionNo")*/
	
	@NamedQuery(name="BranchDetailsEntity.getAllBranchRecords",query="SELECT b FROM BranchDetailsEntity b ORDER BY b.id DESC"),
	@NamedQuery(name="BranchDetailsEntity.checkForBranchNameAvailability",query="SELECT count(b) FROM BranchDetailsEntity b where b.branchName=:branchName"),
	@NamedQuery(name="BranchDetailsEntity.checkForBranchCodeAvailability",query="SELECT count(b) FROM BranchDetailsEntity b where b.branchCode=:branchCode"),
	@NamedQuery(name="BranchDetailsEntity.getBranchDataForEditing",query="SELECT b FROM BranchDetailsEntity b where b.id=:id"),
	@NamedQuery(name="BranchDetailsEntity.checkBranchNameForUpdate",query="SELECT COUNT(b) FROM BranchDetailsEntity b where b.id!=:id AND b.branchName=:branchName"),
	@NamedQuery(name="BranchDetailsEntity.checkBranchCodeForUpdate",query="SELECT COUNT(b) FROM BranchDetailsEntity b where b.id!=:id AND b.branchCode=:branchCode"),
	
})

public class BranchDetailsEntity {  
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "branchDetails_seq", sequenceName = "BRANCH_DETAILS_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "branchDetails_seq") 
	@Column(name="ID")
	private int id;

	@Column(name="BRANCH_CODE")
	private String branchCode;

	@Column(name="BRANCH_NAME")  
	private String branchName;

	@Column(name="BRANCH_ADDRESS")
	private String branchAddress;
	
	@Column(name="BANK_ID")
	private String bankId;

	@Column(name="ADDED_BY")
	private String addedBy;

	@Column(name="ADDED_DATETIME")
	private Date addedDateTime;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATETIME")
	private Date updatedDateTime;
	
	@OneToOne
	@JoinColumn(name = "BANK_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private BankDetailsEntity bankDetailsEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public BankDetailsEntity getBankDetailsEntity() {
		return bankDetailsEntity;
	}

	public void setBankDetailsEntity(BankDetailsEntity bankDetailsEntity) {
		this.bankDetailsEntity = bankDetailsEntity;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getAddedDateTime() {
		return addedDateTime;
	}

	public void setAddedDateTime(Date addedDateTime) {
		this.addedDateTime = addedDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
	  @PrePersist  
	    public void setCreationDate() {  
	        this.addedDateTime = new Date(); 
	        this.updatedBy = (String) SessionData.getUserDetails().get("userID");
	  	    this.addedBy = (String) SessionData.getUserDetails().get("userID");
	  	    this.updatedDateTime = new Date(); 
	    }  
	  
	    /** 
	     * Sets updatedDate and lastupdatedDate before update 
	     */  
	    @PreUpdate  
	    public void setChangeDate() {  
	    	this.updatedBy = (String) SessionData.getUserDetails().get("userID");
	    	this.updatedDateTime = new Date(); 
	    }

}
