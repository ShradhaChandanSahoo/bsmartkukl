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
@Table(name = "BSW_WRONG_POSTING")

@NamedQueries({
	 @NamedQuery(name="WrongPosting.getWrongPostApproveList",query="SELECT b.fromConnectionNo,c.name_eng,c.area_no,c.pipe_size,c.con_type,b.adjustmentNo,b.adjustmentAmnt,b.engAdate,b.remarks,b.added_by,b.to_connection_no,b.receiptNo,b.engRdate,b.added_date,b.approved_by,b.approved_date,b.status,b.monthyearnep FROM WrongPosting b,ConsumerMaster c WHERE b.fromConnectionNo=c.connection_no AND b.status=:status"),
	 @NamedQuery(name="WrongPosting.getWrongPostApproveListAll",query="SELECT b.fromConnectionNo,c.name_eng,c.area_no,c.pipe_size,c.con_type,b.adjustmentNo,b.adjustmentAmnt,b.engAdate,b.remarks,b.added_by,b.to_connection_no,b.receiptNo,b.engRdate,b.added_date,b.approved_by,b.approved_date,b.status FROM WrongPosting b,ConsumerMaster c WHERE b.fromConnectionNo=c.connection_no"),
	 @NamedQuery(name="WrongPosting.getRecordByConNo",query="SELECT b FROM WrongPosting b WHERE b.fromConnectionNo=:connection_no AND b.status=:status"),

	 
})
public class WrongPosting {
	
	@Id
	@SequenceGenerator(name = "wrong_posting_seq", sequenceName = "WRONG_POSTING_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "wrong_posting_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="FR_CONNECTION_NO")
	private String fromConnectionNo;
	
	@Column(name="RECEIPT_NO")
	private String receiptNo;
	
	@Column(name="REC_DATE")
	private Date engRdate;
	
	@Column(name="REC_AMT")
	private String receiptAmnt;
	
	@Column(name="TO_CONNECTION_NO")
	private String to_connection_no;
	
	@Column(name="ADJUST_NO")
	private String adjustmentNo;
	
	@Column(name="ADJUST_DATE")
	private Date engAdate;
	
	@Column(name="ADJUST_AMT")
	private Double adjustmentAmnt;
	
	@Column(name="ADDED_BY")
	private String added_by;
	
	@Column(name="ADDED_DATE")
	private Date added_date;
	
	public Date getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(Date approved_date) {
		this.approved_date = approved_date;
	}

	@Column(name="APPROVED_BY")
	private String approved_by;
	
	@Column(name="APPROVED_DATE")
	private Date approved_date;
	
	@Column(name="STATUS")
	private int status;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="MONTHYEARNEP")
	private Integer monthyearnep;

	public String getRemarks() {
		return remarks;
	}

	public Integer getMonthyearnep() {
		return monthyearnep;
	}

	public void setMonthyearnep(Integer monthyearnep) {
		this.monthyearnep = monthyearnep;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

	public Date getEngRdate() {
		return engRdate;
	}

	public void setEngRdate(Date engRdate) {
		this.engRdate = engRdate;
	}

	public String getReceiptAmnt() {
		return receiptAmnt;
	}

	public void setReceiptAmnt(String receiptAmnt) {
		this.receiptAmnt = receiptAmnt;
	}

	public String getTo_connection_no() {
		return to_connection_no;
	}

	public void setTo_connection_no(String to_connection_no) {
		this.to_connection_no = to_connection_no;
	}

	public String getAdjustmentNo() {
		return adjustmentNo;
	}

	public void setAdjustmentNo(String adjustmentNo) {
		this.adjustmentNo = adjustmentNo;
	}

	
	public Date getEngAdate() {
		return engAdate;
	}

	public void setEngAdate(Date engAdate) {
		this.engAdate = engAdate;
	}

	public Double getAdjustmentAmnt() {
		return adjustmentAmnt;
	}

	public void setAdjustmentAmnt(Double adjustmentAmnt) {
		this.adjustmentAmnt = adjustmentAmnt;
	}

	public String getAdded_by() {
		return added_by;
	}

	public void setAdded_by(String added_by) {
		this.added_by = added_by;
	}

	public Date getAdded_date() {
		return added_date;
	}

	public void setAdded_date(Date added_date) {
		this.added_date = added_date;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	

	
	
	
	
}
