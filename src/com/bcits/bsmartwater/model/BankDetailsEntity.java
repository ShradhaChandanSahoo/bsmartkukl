package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_DETAILS")
@NamedQueries({
	@NamedQuery(name="BankDetailsEntity.getAllRecords",query="SELECT b FROM BankDetailsEntity b ORDER BY b.id DESC"),
	@NamedQuery(name="BankDetailsEntity.getBankDataForEditing",query="SELECT b FROM BankDetailsEntity b where b.id=:id"),
	@NamedQuery(name="BankDetailsEntity.checkForBankNameAvailability",query="SELECT count(b) FROM BankDetailsEntity b where b.bankName=:bankName"),
	@NamedQuery(name="BankDetailsEntity.deleteById",query="delete  from BankDetailsEntity b where b.id=:id"),
	@NamedQuery(name="BankDetailsEntity.getBankDetailsEntity",query="SELECT b FROM BankDetailsEntity b ORDER BY b.id ASC")
})


public class BankDetailsEntity{
	
	private static final long serialVersionUID = 1L;  
	
	@Id
	@SequenceGenerator(name = "bank_details_seq", sequenceName = "BANK_DETAILS_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "bank_details_seq") 
	@Column(name="ID")
	private int id;

	@Column(name="BANK_ABBREVIATION")
	private String bankAbbreviation;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="ADDED_BY")
	private String addedBy;

	@Column(name="ADDED_DATETIME")
	private Date addedDateTime;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATETIME")
	private Date updatedDateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBankAbbreviation() {
		return bankAbbreviation;
	}

	public void setBankAbbreviation(String bankAbbreviation) {
		this.bankAbbreviation = bankAbbreviation;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

}
