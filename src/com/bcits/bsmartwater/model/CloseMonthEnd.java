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
@Table(name = "BSW_CLOSE_MONTH_END")
@NamedQueries({
	
	@NamedQuery(name="CloseMonthEnd.checkCloseMonthExists",query="SELECT COUNT(*) FROM CloseMonthEnd c WHERE c.month_year=:monthyearnep"),
	@NamedQuery(name="CloseMonthEnd.getLatestMonthYear",query="SELECT MAX(c.month_year) FROM CloseMonthEnd c WHERE c.month_year IS NOT NULL"),

	
})
public class CloseMonthEnd {


	@Id
	@SequenceGenerator(name = "bsw_close_month_end_seq", sequenceName = "BSW_CLOSE_MONTH_END_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_close_month_end_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="MONTH_YEAR")
	private String month_year;
	
	@Column(name="TOT_WATER_CHARGES")
	private Double tot_water_charges;
	
	@Column(name="TOT_SW_CHARGES")
	private Double tot_sw_charges;
	
	@Column(name="TOT_MTR_CHARGES")
	private Double tot_mtr_charges;
	
	@Column(name="TOT_ARREARS")
	private Double tot_arrears;
	
	@Column(name="TOT_NET_AMOUNT")
	private Double tot_net_amount;
	
	@Column(name="TOT_COLLECTION")
	private Double tot_collection;
	
	@Column(name="TOT_PENALTY")
	private Double tot_penalty;
	
	@Column(name="TOT_REBATE")
	private Double tot_rebate;
	
	@Column(name="TOT_MISCELLANEOUS")
	private Double tot_miscellaneous;
	
	@Column(name="TOT_CLOSE_BAL")
	private Double tot_close_bal;
	
	public Double getTot_close_bal() {
		return tot_close_bal;
	}

	public void setTot_close_bal(Double tot_close_bal) {
		this.tot_close_bal = tot_close_bal;
	}

	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="CREATED_DATE")
	private Date created_date;

	public int getId() {
		return id;
	}

	public Double getTot_penalty() {
		return tot_penalty;
	}

	public void setTot_penalty(Double tot_penalty) {
		this.tot_penalty = tot_penalty;
	}

	public Double getTot_rebate() {
		return tot_rebate;
	}

	public void setTot_rebate(Double tot_rebate) {
		this.tot_rebate = tot_rebate;
	}

	public Double getTot_miscellaneous() {
		return tot_miscellaneous;
	}

	public void setTot_miscellaneous(Double tot_miscellaneous) {
		this.tot_miscellaneous = tot_miscellaneous;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonth_year() {
		return month_year;
	}

	public void setMonth_year(String month_year) {
		this.month_year = month_year;
	}

	public Double getTot_water_charges() {
		return tot_water_charges;
	}

	public void setTot_water_charges(Double tot_water_charges) {
		this.tot_water_charges = tot_water_charges;
	}

	public Double getTot_sw_charges() {
		return tot_sw_charges;
	}

	public void setTot_sw_charges(Double tot_sw_charges) {
		this.tot_sw_charges = tot_sw_charges;
	}

	public Double getTot_mtr_charges() {
		return tot_mtr_charges;
	}

	public void setTot_mtr_charges(Double tot_mtr_charges) {
		this.tot_mtr_charges = tot_mtr_charges;
	}

	public Double getTot_arrears() {
		return tot_arrears;
	}

	public void setTot_arrears(Double tot_arrears) {
		this.tot_arrears = tot_arrears;
	}

	public Double getTot_net_amount() {
		return tot_net_amount;
	}

	public void setTot_net_amount(Double tot_net_amount) {
		this.tot_net_amount = tot_net_amount;
	}

	public Double getTot_collection() {
		return tot_collection;
	}

	public void setTot_collection(Double tot_collection) {
		this.tot_collection = tot_collection;
	}
	
}
