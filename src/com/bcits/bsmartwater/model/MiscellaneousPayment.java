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
@Table(name="MISCELLANEOUS_PAYMENTS")
@NamedQueries({

	@NamedQuery(name="MiscellaneousPayment.getSyncToPGRS",query="SELECT m FROM MiscellaneousPayment m where m.status=:status")
})
public class MiscellaneousPayment {

	@Id
	@SequenceGenerator(name = "miscellaneous_payments_seq", sequenceName = "MISCELLANEOUS_PAYMENTS_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "miscellaneous_payments_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connectionNo;
	
	@Column(name="SITECODE")
	private String sitecode;
	
	@Column(name="NCTAP")
	private Double nctap;
	
	@Column(name="NCDEPOSIT")
	private Double ncdeposit;
	
	@Column(name="MVALUE")
	private Double mvalue;
	
	@Column(name="TEMPHOLEAMT")
	private Double tempholeamt;
	
	@Column(name="NAMECHANGEAMT")
	private Double nameChangeAmt;
	
	@Column(name="ILG_CON_AMT")
	private Double ilg_con_amt;

	@Column(name="USERID")
	private String userid;
	
	@Column(name="CREATED_DATE")
	private Date created_date;
	
	@Column(name="APPLICATION_ID")
	private Long application_id;
	
	@Column(name="STATUS")
	private Integer status;

	@Column(name="AMOUNT")
	private Double amount;
	
	
	@Column(name="RECNO")
	private String recno;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public Double getNctap() {
		return nctap;
	}

	public void setNctap(Double nctap) {
		this.nctap = nctap;
	}

	public Double getNcdeposit() {
		return ncdeposit;
	}

	public void setNcdeposit(Double ncdeposit) {
		this.ncdeposit = ncdeposit;
	}

	public Double getMvalue() {
		return mvalue;
	}

	public void setMvalue(Double mvalue) {
		this.mvalue = mvalue;
	}

	public Double getTempholeamt() {
		return tempholeamt;
	}

	public void setTempholeamt(Double tempholeamt) {
		this.tempholeamt = tempholeamt;
	}

	public Double getNameChangeAmt() {
		return nameChangeAmt;
	}

	public void setNameChangeAmt(Double nameChangeAmt) {
		this.nameChangeAmt = nameChangeAmt;
	}

	public Double getIlg_con_amt() {
		return ilg_con_amt;
	}

	public void setIlg_con_amt(Double ilg_con_amt) {
		this.ilg_con_amt = ilg_con_amt;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Long application_id) {
		this.application_id = application_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRecno() {
		return recno;
	}

	public void setRecno(String recno) {
		this.recno = recno;
	}

	
	
	
	
	
	
	
}
