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
@Table(name = "FISCAL_YEAR_BALANCE")

@NamedQueries({

	@NamedQuery(name="FiscalYearBalance.checkMonthYearExists",query="SELECT COUNT(*) FROM FiscalYearBalance bl WHERE bl.fiscal_year_month=:fiscal_year_month"),
	@NamedQuery(name="FiscalYearBalance.getTotalRecords",query="SELECT COUNT(*) FROM FiscalYearBalance bl"),
	@NamedQuery(name="FiscalYearBalance.getAllRecordsByMyn",query="SELECT bl FROM FiscalYearBalance bl WHERE bl.fiscal_year_month=:fiscal_year_month"),
	
})
public class FiscalYearBalance {

	
	@Id
	@SequenceGenerator(name = "fiscal_year_bal_seq", sequenceName = "FISCAL_YEAR_BAL_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "fiscal_year_bal_seq") 
	@Column(name="ID")
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
	
	@Column(name="MISC_COST")
	private Double misc_cost;
	
	@Column(name="ADD_PEN_COST")
	private Double add_pen_cost;
	
	@Column(name="PENALTY")
	private Double penalty;
	
	@Column(name="POS_ADJUSTMENT")
	private Double pos_adjustment;
	
	@Column(name="NEG_ADJUSTMENT")
	private Double neg_adjustment;
	
	@Column(name="REBATE")
	private Double rebate;
	
	@Column(name="ADV_REB")
	private Double adv_reb;
	
	@Column(name="RECEIVED_AMOUNT")
	private Double received_amount;
	
	@Column(name="CLOSING_BALANCE")
	private Double closing_balance;

	@Column(name="SUSPENSE")
	private Double suspense;
	
	@Column(name="CREATE_BY")
	private String create_by;
	
	@Column(name="CREATE_DATE")
	private Date create_date;
	
	@Column(name="UPDATE_BY")
	private String update_by;
	
	@Column(name="UPDATE_DATE")
	private Date update_date;
	
	@Column(name="PAY_TOWARDS")
	private String pay_towards;
	
	@Column(name="ADJCWOP")
	private Double adjcwop;
	
	@Column(name="ADJ_VOUCHER")
	private Double adj_voucher;
	
	

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

	public Double getMisc_cost() {
		return misc_cost;
	}

	public void setMisc_cost(Double misc_cost) {
		this.misc_cost = misc_cost;
	}

	public Double getAdd_pen_cost() {
		return add_pen_cost;
	}

	public void setAdd_pen_cost(Double add_pen_cost) {
		this.add_pen_cost = add_pen_cost;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Double getPos_adjustment() {
		return pos_adjustment;
	}

	public void setPos_adjustment(Double pos_adjustment) {
		this.pos_adjustment = pos_adjustment;
	}

	public Double getNeg_adjustment() {
		return neg_adjustment;
	}

	public void setNeg_adjustment(Double neg_adjustment) {
		this.neg_adjustment = neg_adjustment;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getReceived_amount() {
		return received_amount;
	}

	public void setReceived_amount(Double received_amount) {
		this.received_amount = received_amount;
	}

	public Double getClosing_balance() {
		return closing_balance;
	}

	public void setClosing_balance(Double closing_balance) {
		this.closing_balance = closing_balance;
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

	public Double getSuspense() {
		return suspense;
	}

	public void setSuspense(Double suspense) {
		this.suspense = suspense;
	}

	public Double getAdv_reb() {
		return adv_reb;
	}

	public void setAdv_reb(Double adv_reb) {
		this.adv_reb = adv_reb;
	}

	public String getPay_towards() {
		return pay_towards;
	}

	public void setPay_towards(String pay_towards) {
		this.pay_towards = pay_towards;
	}

	public Double getAdjcwop() {
		return adjcwop;
	}

	public void setAdjcwop(Double adjcwop) {
		this.adjcwop = adjcwop;
	}

	public Double getAdj_voucher() {
		return adj_voucher;
	}

	public void setAdj_voucher(Double adj_voucher) {
		this.adj_voucher = adj_voucher;
	}
	
	
	
}
