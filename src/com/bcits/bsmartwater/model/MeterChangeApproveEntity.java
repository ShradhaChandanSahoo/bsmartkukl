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
@Table(name = "BSW_APPROVE_METER_CHANGE")

@NamedQueries({
	
	//@NamedQuery(name="BillApproveEntity.setBillApprove",query="UPDATE BillApproveEntity b SET b.bill_app_status= :bill_app_status WHERE b.id= :billId"),
	@NamedQuery(name="MeterChangeApproveEntity.getMeterPendingApproval",query="SELECT c.connection_no,b.new_meter_no,b.fr,b.ir,b.mcunits,b.new_ins_date_nep,b.new_ins_date_eng,b.id FROM MeterChangeApproveEntity b,ConsumerMaster c WHERE c.connection_no=b.connectionno AND b.mtr_change_app_status=0"),
	@NamedQuery(name="MeterChangeApproveEntity.findByConnectionNo",query="SELECT bl FROM MeterChangeApproveEntity bl WHERE bl.connectionno=:connection_no  AND bl.mtr_change_app_status=0"),//AND bl.monthyear in(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble WHERE bl.connection_no=:connection_no)
	@NamedQuery(name="MeterChangeApproveEntity.findByConnNo",query="SELECT bl FROM MeterChangeApproveEntity bl WHERE bl.connectionno=:connNo"),//AND bl.monthyear in(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble WHERE bl.connection_no=:connection_no)
	@NamedQuery(name="MeterChangeApproveEntity.getMeterApproveList", query="SELECT b.connectionno,b.new_meter_no,b.fr,b.ir,b.mcunits,b.new_ins_date_nep,b.new_ins_date_eng,b.mtr_change_app_status FROM MeterChangeApproveEntity b"),
	@NamedQuery(name="MeterChangeApproveEntity.getByConnectionNo",query="SELECT bl FROM MeterChangeApproveEntity bl WHERE upper(bl.connectionno)=:connNo"),
})

public class MeterChangeApproveEntity 
{
	@Id
	@SequenceGenerator(name = "bsw_approve_mc_seq", sequenceName = "BSW_APPROVE_MC_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_approve_mc_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="CONNECTIONNO")
	private String connectionno;
	
	@Column(name="IR")
	private int ir;
	
	@Column(name="NEW_METER_MAKE")
	private String new_meter_make;
	
	@Column(name="NEW_METER_NO")
	private String new_meter_no;
	
	@Column(name="NEW_METER_CAPACITY")
	private String new_meter_capacity;
	
	@Column(name="NEW_METER_OWN")
	private String new_meter_own;
	
	@Column(name="NEW_INS_DATE_ENG")
	private Date new_ins_date_eng;
	
	@Column(name="NEW_INS_DATE_NEP")
	private String new_ins_date_nep;
	
	@Column(name="NEW_METCON_DATE_ENG")
	private Date new_metcon_date_eng;
	
	@Column(name="NEW_METCON_DATE_NEP")
	private String new_metcon_date_nep;
	
	@Column(name="NEW_CAL_DATE_ENG")
	private Date new_cal_date_eng;
	
	@Column(name="NEW_CAL_DATE_NEP")
	private String new_cal_date_nep;
	
	@Column(name="NEW_ENT_DATE_ENG")
	private Date new_ent_date_eng;
	
	@Column(name="NEW_ENT_DATE_NEP")
	private String new_ent_date_nep;
	
	@Column(name="RELEASE_DATE_ENG")
	private Date release_date_eng;
	
	@Column(name="RELEASE_DATE_NEP")
	private String release_date_nep;
	
	@Column(name="GIVEN_DATE_ENG")
	private Date given_date_eng;
	
	@Column(name="GIVEN_DATE_NEP")
	private String given_date_nep;
	
	@Column(name="NEW_CALIBRATED_OFFICER")
	private String new_calibrated_officer;
	
	@Column(name="MCUNITS")
	private Double mcunits;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="MTR_CHANGE_APP_STATUS")
	private Integer mtr_change_app_status;
	
	@Column(name="FR")
	private int fr;
	
	@Column(name="SUBMITTED_BY")
	private String submitted_by;
	
	@Column(name="SUBMITTED_DATE")
	private Date submitted_date;
	
	@Column(name="APPROVED_BY")
	private String approved_by;
	
	@Column(name="APPROVED_DATE")
	private Date approved_date;
	
	
	@Column(name="MONTHYEARNEP")
	private String monthyearnep;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConnectionno() {
		return connectionno;
	}

	public void setConnectionno(String connectionno) {
		this.connectionno = connectionno;
	}

	public int getIr() {
		return ir;
	}

	public void setIr(int ir) {
		this.ir = ir;
	}

	public String getNew_meter_make() {
		return new_meter_make;
	}

	public void setNew_meter_make(String new_meter_make) {
		this.new_meter_make = new_meter_make;
	}

	public String getNew_meter_no() {
		return new_meter_no;
	}

	public void setNew_meter_no(String new_meter_no) {
		this.new_meter_no = new_meter_no;
	}

	public String getNew_meter_capacity() {
		return new_meter_capacity;
	}

	public void setNew_meter_capacity(String new_meter_capacity) {
		this.new_meter_capacity = new_meter_capacity;
	}

	public String getNew_meter_own() {
		return new_meter_own;
	}

	public void setNew_meter_own(String new_meter_own) {
		this.new_meter_own = new_meter_own;
	}

	public Date getNew_ins_date_eng() {
		return new_ins_date_eng;
	}

	public void setNew_ins_date_eng(Date new_ins_date_eng) {
		this.new_ins_date_eng = new_ins_date_eng;
	}

	public String getNew_ins_date_nep() {
		return new_ins_date_nep;
	}

	public void setNew_ins_date_nep(String new_ins_date_nep) {
		this.new_ins_date_nep = new_ins_date_nep;
	}

	public Date getNew_metcon_date_eng() {
		return new_metcon_date_eng;
	}

	public void setNew_metcon_date_eng(Date new_metcon_date_eng) {
		this.new_metcon_date_eng = new_metcon_date_eng;
	}

	public String getNew_metcon_date_nep() {
		return new_metcon_date_nep;
	}

	public void setNew_metcon_date_nep(String new_metcon_date_nep) {
		this.new_metcon_date_nep = new_metcon_date_nep;
	}

	public Date getNew_cal_date_eng() {
		return new_cal_date_eng;
	}

	public void setNew_cal_date_eng(Date new_cal_date_eng) {
		this.new_cal_date_eng = new_cal_date_eng;
	}

	public String getNew_cal_date_nep() {
		return new_cal_date_nep;
	}

	public void setNew_cal_date_nep(String new_cal_date_nep) {
		this.new_cal_date_nep = new_cal_date_nep;
	}

	public Date getNew_ent_date_eng() {
		return new_ent_date_eng;
	}

	public void setNew_ent_date_eng(Date new_ent_date_eng) {
		this.new_ent_date_eng = new_ent_date_eng;
	}

	public String getNew_ent_date_nep() {
		return new_ent_date_nep;
	}

	public void setNew_ent_date_nep(String new_ent_date_nep) {
		this.new_ent_date_nep = new_ent_date_nep;
	}

	public Date getRelease_date_eng() {
		return release_date_eng;
	}

	public void setRelease_date_eng(Date release_date_eng) {
		this.release_date_eng = release_date_eng;
	}

	public String getRelease_date_nep() {
		return release_date_nep;
	}

	public void setRelease_date_nep(String release_date_nep) {
		this.release_date_nep = release_date_nep;
	}

	public Date getGiven_date_eng() {
		return given_date_eng;
	}

	public void setGiven_date_eng(Date given_date_eng) {
		this.given_date_eng = given_date_eng;
	}

	public String getGiven_date_nep() {
		return given_date_nep;
	}

	public void setGiven_date_nep(String given_date_nep) {
		this.given_date_nep = given_date_nep;
	}

	public String getNew_calibrated_officer() {
		return new_calibrated_officer;
	}

	public void setNew_calibrated_officer(String new_calibrated_officer) {
		this.new_calibrated_officer = new_calibrated_officer;
	}

	public Double getMcunits() {
		return mcunits;
	}

	public void setMcunits(Double mcunits) {
		this.mcunits = mcunits;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getMtr_change_app_status() {
		return mtr_change_app_status;
	}

	public void setMtr_change_app_status(Integer mtr_change_app_status) {
		this.mtr_change_app_status = mtr_change_app_status;
	}

	public int getFr() {
		return fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public String getSubmitted_by() {
		return submitted_by;
	}

	public void setSubmitted_by(String submitted_by) {
		this.submitted_by = submitted_by;
	}

	public Date getSubmitted_date() {
		return submitted_date;
	}

	public void setSubmitted_date(Date submitted_date) {
		this.submitted_date = submitted_date;
	}

	

	public Date getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(Date approved_date) {
		this.approved_date = approved_date;
	}

	public String getMonthyearnep() {
		return monthyearnep;
	}

	public void setMonthyearnep(String monthyearnep) {
		this.monthyearnep = monthyearnep;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}
	
	
}
