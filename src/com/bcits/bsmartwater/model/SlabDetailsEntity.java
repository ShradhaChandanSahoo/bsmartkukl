package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SLAB_DETAILS")

/*@NamedQueries({
	@NamedQuery(name="DisconnectionRulesEntity.findByConnectionNo",query="SELECT c FROM ConsumerMaster c WHERE c.connection_no=:connectionNo")
})
*/
public class SlabDetailsEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "slabDetails_seq", sequenceName = "SLAB_DETAILS_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "slabDetails_seq")
	@Column(name="ID")
	private int id;

	@Column(name="SLAB_NO")
	private int slabNo;

	@Column(name="SLAB_TYPE")
	private String slabType;

	@Column(name="NO_OF")
	private int noOf;

	@Column(name="DAYS/MONTHS")
	private String daysMonths;

	@Column(name="REBATE")
	private Double rebate;

	@Column(name="ADV_REBATE")
	private Double advRebate;

	@Column(name="PENALITY")
	private Double renality;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="ADDED_BY")
	private String addedBy;

	@Column(name="ADDED_DATETIME")
	private Date addedDateTime;

	@Column(name="BRANCH_CODE")
	private int branchCode;

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

	public int getSlabNo() {
		return slabNo;
	}

	public void setSlabNo(int slabNo) {
		this.slabNo = slabNo;
	}

	public String getSlabType() {
		return slabType;
	}

	public void setSlabType(String slabType) {
		this.slabType = slabType;
	}

	public int getNoOf() {
		return noOf;
	}

	public void setNoOf(int noOf) {
		this.noOf = noOf;
	}

	public String getDaysMonths() {
		return daysMonths;
	}

	public void setDaysMonths(String daysMonths) {
		this.daysMonths = daysMonths;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getAdvRebate() {
		return advRebate;
	}

	public void setAdvRebate(Double advRebate) {
		this.advRebate = advRebate;
	}

	public Double getRenality() {
		return renality;
	}

	public void setRenality(Double renality) {
		this.renality = renality;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
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
