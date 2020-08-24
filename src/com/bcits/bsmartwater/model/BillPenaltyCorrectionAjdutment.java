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
@Table(name="BILL_PENALTY_CORRECTION_ADJ")

@NamedQueries({
	@NamedQuery(name="BillPenaltyCorrectionAjdutment.getPendingConnectionsToApprove",query="SELECT b.connection_no,c.name_eng,c.area_no,c.pipe_size,b.bill_adj_amount,b.penalty_adj_amount,b.from_mon_year,b.to_mon_year,b.submit_by,b.submit_date,b.remarks FROM BillPenaltyCorrectionAjdutment b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND b.approve_status=:approve_status"),
	@NamedQuery(name="BillPenaltyCorrectionAjdutment.getConTypeByConNo",query="SELECT b FROM BillPenaltyCorrectionAjdutment b WHERE b.approve_status=:approve_status AND UPPER(b.connection_no)=:connection_no"),

	
	
})

public class BillPenaltyCorrectionAjdutment {

	@Id
	@SequenceGenerator(name = "bill_penalty_adj_seq", sequenceName = "BILL_PENALTY_CORR_ADJ_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "bill_penalty_adj_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="BILL_ADJ_AMOUNT")
	private Double bill_adj_amount;
	
	@Column(name="PENALTY_ADJ_AMOUNT")
	private Double penalty_adj_amount;
	
	@Column(name="FROM_MON_YEAR")
	private Integer from_mon_year;
	
	@Column(name="TO_MON_YEAR")
	private Integer to_mon_year;
	
	@Column(name="SUBMIT_BY")
	private String submit_by;
	
	@Column(name="SUBMIT_DATE")
	private Date submit_date;
	
	@Column(name="APPROVED_BY")
	private String approved_by;
	
	@Column(name="APPROVED_DATE")
	private Date approved_date;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ADJ_VOUCHER_NO")
	private String adj_voucher_no;
	
	@Column(name="APPROVE_STATUS")
	private Integer approve_status;
	
	@Column(name="ARREARS")
	private Double arrears;
	
	@Column(name="NET_AMOUNT")
	private Double net_amount;
	
	public Integer getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(Integer approve_status) {
		this.approve_status = approve_status;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConnection_no() {
		return connection_no;
	}

	public void setConnection_no(String connection_no) {
		this.connection_no = connection_no;
	}

	public Double getBill_adj_amount() {
		return bill_adj_amount;
	}

	public void setBill_adj_amount(Double bill_adj_amount) {
		this.bill_adj_amount = bill_adj_amount;
	}

	public Double getPenalty_adj_amount() {
		return penalty_adj_amount;
	}

	public void setPenalty_adj_amount(Double penalty_adj_amount) {
		this.penalty_adj_amount = penalty_adj_amount;
	}

	public Integer getFrom_mon_year() {
		return from_mon_year;
	}

	public void setFrom_mon_year(Integer from_mon_year) {
		this.from_mon_year = from_mon_year;
	}

	public Integer getTo_mon_year() {
		return to_mon_year;
	}

	public void setTo_mon_year(Integer to_mon_year) {
		this.to_mon_year = to_mon_year;
	}

	public String getSubmit_by() {
		return submit_by;
	}

	public void setSubmit_by(String submit_by) {
		this.submit_by = submit_by;
	}

	public Date getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(Date submit_date) {
		this.submit_date = submit_date;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public Date getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(Date approved_date) {
		this.approved_date = approved_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAdj_voucher_no() {
		return adj_voucher_no;
	}

	public void setAdj_voucher_no(String adj_voucher_no) {
		this.adj_voucher_no = adj_voucher_no;
	}

	public Double getArrears() {
		return arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public Double getNet_amount() {
		return net_amount;
	}

	public void setNet_amount(Double net_amount) {
		this.net_amount = net_amount;
	}
	
	
	
}
