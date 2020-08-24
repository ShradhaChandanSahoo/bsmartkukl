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

@SuppressWarnings("serial")
@Entity
@Table(name="BSW_CASHCOUNTERDAYCLOSE")
@NamedQueries({
	@NamedQuery(name="CashCounterDayClose.getMaxDayForDayClose",query="SELECT c FROM CashCounterDayClose c WHERE c.counter_no=:counter_no AND TO_CHAR(c.dayclosedate,'dd/MM/yyyy')=TO_CHAR(:date,'dd/MM/yyyy')"),
	@NamedQuery(name="CashCounterDayClose.getDayCloseDataOncounterNoAndDate",query="SELECT c FROM CashCounterDayClose c WHERE c.counter_no=:counter_no AND c.dayclosedate>=TO_DATE(:date, 'yyyy-MM-dd')")
})
public class CashCounterDayClose extends BaseEntity{

	@Id
	@SequenceGenerator(name = "bsw_cashcounterdayclose_seq", sequenceName = "BSW_CASHCOUNTERDAYCLOSE_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_cashcounterdayclose_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="COUNTER_NO")
	private Integer counter_no;

	@Column(name="DAYCLOSEDATE")
	private Date dayclosedate;
	
	@Column(name="DAYCLOSEDATENEP")
	private String dayclosedatenep;
	
	@Column(name="COUNTERNAME")
	private String countername;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="AMOUNT_IN_HAND")
	private Double amount_in_hand;
	
	@Column(name="TOTALAMOUNT")
	private Double totalamount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCounter_no() {
		return counter_no;
	}

	public void setCounter_no(Integer counter_no) {
		this.counter_no = counter_no;
	}

	public Date getDayclosedate() {
		return dayclosedate;
	}

	public void setDayclosedate(Date dayclosedate) {
		this.dayclosedate = dayclosedate;
	}

	public String getDayclosedatenep() {
		return dayclosedatenep;
	}

	public void setDayclosedatenep(String dayclosedatenep) {
		this.dayclosedatenep = dayclosedatenep;
	}

	public String getCountername() {
		return countername;
	}

	public void setCountername(String countername) {
		this.countername = countername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getAmount_in_hand() {
		return amount_in_hand;
	}

	public void setAmount_in_hand(Double amount_in_hand) {
		this.amount_in_hand = amount_in_hand;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	
}
