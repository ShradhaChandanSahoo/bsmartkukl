package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FISCAL_YEAR_ADVANCE_BALANCE")
@NamedQueries({
	

	
})

public class FiscalYearAdvanceBalance {

	@Id
	@SequenceGenerator(name = "fiscal_year_adv_bal_seq", sequenceName = "FISCAL_YEAR_ADV_BAL_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "fiscal_year_adv_bal_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="FISCAL_YEAR_MONTH")
	private Integer fiscal_year_month;
	
	@Column(name="SITECODE")
	private String sitecode;
	
	@Column(name="CON_TYPE")
	private String con_type;
	
	@Column(name="WARD_NO")
	private String ward_no;
	
	@Column(name="CON_CATEGORY")
	private String con_category;
	
	@Column(name="DENOTED_BY")
	private String denoted_by;
	
	@Column(name="OPENING_BALANCE")
	private Double opening_balance;
	
	@Column(name="WATER_COST")
	private Double water_cost;
	
	@Column(name="SEWERAGE_COST")
	private Double sewerage_cost;
	
	@Column(name="MRENT_COST")
	private Double mrent_cost;
	
	@Column(name = "ADJUSTMENT")
	private Double adjustment;
	
	@Column(name = "CURRENT_ADV_AMT")
	private Double current_adv_amt;
	
	@Column(name = "CURRENT_ADV_REBATE")
	private Double current_adv_rebate;
	
	@Column(name = "CLOSING_BAL")
	private Double closing_bal;
	
	@Column(name="CREATE_BY")
	private String create_by;
	
	@Column(name="CREATE_DATE")
	private Date create_date;
	
	@Column(name="UPDATE_BY")
	private String update_by;
	
	@Column(name="UPDATE_DATE")
	private Date update_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getFiscal_year_month() {
		return fiscal_year_month;
	}

	public void setFiscal_year_month(Integer fiscal_year_month) {
		this.fiscal_year_month = fiscal_year_month;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public String getCon_type() {
		return con_type;
	}

	public void setCon_type(String con_type) {
		this.con_type = con_type;
	}

	public String getWard_no() {
		return ward_no;
	}

	public void setWard_no(String ward_no) {
		this.ward_no = ward_no;
	}

	public String getCon_category() {
		return con_category;
	}

	public void setCon_category(String con_category) {
		this.con_category = con_category;
	}

	public String getDenoted_by() {
		return denoted_by;
	}

	public void setDenoted_by(String denoted_by) {
		this.denoted_by = denoted_by;
	}

	public Double getOpening_balance() {
		return opening_balance;
	}

	public void setOpening_balance(Double opening_balance) {
		this.opening_balance = opening_balance;
	}

	public Double getWater_cost() {
		return water_cost;
	}

	public void setWater_cost(Double water_cost) {
		this.water_cost = water_cost;
	}

	public Double getSewerage_cost() {
		return sewerage_cost;
	}

	public void setSewerage_cost(Double sewerage_cost) {
		this.sewerage_cost = sewerage_cost;
	}

	public Double getMrent_cost() {
		return mrent_cost;
	}

	public void setMrent_cost(Double mrent_cost) {
		this.mrent_cost = mrent_cost;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public Double getCurrent_adv_amt() {
		return current_adv_amt;
	}

	public void setCurrent_adv_amt(Double current_adv_amt) {
		this.current_adv_amt = current_adv_amt;
	}

	public Double getCurrent_adv_rebate() {
		return current_adv_rebate;
	}

	public void setCurrent_adv_rebate(Double current_adv_rebate) {
		this.current_adv_rebate = current_adv_rebate;
	}

	public Double getClosing_bal() {
		return closing_bal;
	}

	public void setClosing_bal(Double closing_bal) {
		this.closing_bal = closing_bal;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	
	
}
