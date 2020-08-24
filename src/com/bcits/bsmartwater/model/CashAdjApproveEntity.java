package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BSW_CASH_ADJ_APPROVE")

public class CashAdjApproveEntity {
	
	@Id
	@SequenceGenerator(name = "cash_adj", sequenceName = "CASH_ADJ_APROVE_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "cash_adj") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String fromConnectionNo;
	
	@Column(name="RECEIPT_NO")
	private String receiptNo;
	
	@Column(name="PAID_AMT")
	private Double receiptAmnt;
	
	@Column(name="ADJUSTMENT_AMT")
	private Double adjustmentAmnt;
	
	@Column(name="ADJUSTMENT_DATE")
	private Date engAdate;
	
	@Column(name="ADJUSTMENT_NO")
	private String adjustmentNo;
	
	@Column(name="REMARKS")
	private String fromRemarks;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="CREATED_BY")
	private String created_by;
	
	@Column(name="CREATED_DATE")
	private Date created_date;
	
	@Column(name="APPROVED_BY")
	private String approved_by;
	
	@Column(name="APPROVED_DATE")
	private Date approve_date;

	@Column(name="RECEIPT_DATE")
	private Date engRdate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromConnectionNo() {
		return fromConnectionNo;
	}

	public void setFromConnectionNo(String fromConnectionNo) {
		this.fromConnectionNo = fromConnectionNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Double getReceiptAmnt() {
		return receiptAmnt;
	}

	public void setReceiptAmnt(Double receiptAmnt) {
		this.receiptAmnt = receiptAmnt;
	}

	public Double getAdjustmentAmnt() {
		return adjustmentAmnt;
	}

	public void setAdjustmentAmnt(Double adjustmentAmnt) {
		this.adjustmentAmnt = adjustmentAmnt;
	}


	public String getAdjustmentNo() {
		return adjustmentNo;
	}

	public void setAdjustmentNo(String adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
	}

	public String getFromRemarks() {
		return fromRemarks;
	}

	public void setFromRemarks(String fromRemarks) {
		this.fromRemarks = fromRemarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public Date getApprove_date() {
		return approve_date;
	}

	public void setApprove_date(Date approve_date) {
		this.approve_date = approve_date;
	}

	public Date getEngRdate() {
		return engRdate;
	}

	public void setEngRdate(Date engRdate) {
		this.engRdate = engRdate;
	}

	public Date getEngAdate() {
		return engAdate;
	}

	public void setEngAdate(Date engAdate) {
		this.engAdate = engAdate;
	}

	

}
