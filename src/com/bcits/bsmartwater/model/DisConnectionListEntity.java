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
@Table(name="BSW_DISCONNECTION_MASTER")
@NamedQueries({
	
	@NamedQuery(name="DisConnectionListEntity.genDisList",query="SELECT c FROM DisConnectionListEntity c where c.status=0"),
	@NamedQuery(name="DisConnectionListEntity.uniqueDisConnNo",query="SELECT M FROM DisConnectionListEntity M WHERE M.connectionno=:connectionno"),
	@NamedQuery(name="DisConnectionListEntity.getListDetailsByConnNo",query="SELECT c FROM DisConnectionListEntity c WHERE c.connectionno=:connectionno"),
	@NamedQuery(name="DisConnectionListEntity.uniqueDisConnEntry",query="SELECT c FROM DisConnectionListEntity c WHERE c.connectionno=:connectionno and c.status in (1,2)"),
	@NamedQuery(name="DisConnectionListEntity.uniqueRecConnEntry",query="SELECT c FROM DisConnectionListEntity c WHERE c.connectionno=:connectionno and c.status in (2,0)"),
})
public class DisConnectionListEntity 
{
	@Id
	@SequenceGenerator(name = "bsw_disconnection_master_seq", sequenceName = "BSW_DISCONNECTION_MASTER_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_disconnection_master_seq") 
	@Column(name = "LISTNO", nullable = false, precision = 6, scale = 0)
	private Integer listno;
	
	@Column(name="CONNECTIONNO")
	private String connectionno;
	
	@Column(name="MONTHYEAR")
	private int monthyear;
	
	@Column(name="DIS_DATE_ENG")
	private Date dis_date_eng;
	
	@Column(name="REC_DATE_ENG")
	private Date rec_date_eng;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="DATESTAMP")
	private Date datestamp;
	
	@Column(name="PRESENT_READING")
	private int present_reading;
	
	@Column(name="PREVIOUS_READING")
	private int previous_reading;
	
	@Column(name="WATER_CHARGES")
	private int water_charges;

	@Column(name="ARREARS")
	private Double arrears;
	
	@Column(name="INTEREST")
	private int interest;
	
	@Column(name="OTHERS")
	private int others;

	@Column(name="BALANCE")
	private int balance;
	
	@Column(name="OBTOTAL")
	private int obtotal;
	
	@Column(name="TOTALAMTPAIDS")
	private int totalamtpaids;
	
	@Column(name="LIST_DATE")
	private Date list_date;
	
	@Column(name="DR_AMOUNT")
	private int dr_amount;
	
	@Column(name="ENT_DATE_ENG")
	private Date ent_date_eng;
	
	@Column(name="ENT_DATE_NEP")
	private String ent_date_nep;
	
	@Column(name="NAME_ENG")
	private String name_eng;
	
	@Column(name="NAME_NEP")
	private String name_nep;
	
	@Column(name="ADDRESS_ENG")
	private String address_eng;
	
	@Column(name="ADDRESS_NEP")
	private String address_nep;

	@Column(name="PIPE_SIZE")
	private Double pipe_size;
	
	@Column(name="CON_CATEGORY")
	private String con_category;
	
	@Column(name="WARD_NO")
	private String ward_no;
	
	@Column(name="AREA_NO")
	private String area_no;	
	
	@Column(name="DIS_DATE_NEP")
	private String dis_date_nep;
	
	@Column(name="DIS_FR")
	private Integer dis_fr;
	
	@Column(name="REC_DATE_NEP")
	private String rec_date_nep;
	
	
	public Integer getListno() {
		return listno;
	}

	public void setListno(Integer listno) {
		this.listno = listno;
	}

	public String getConnectionno() {
		return connectionno;
	}

	public void setConnectionno(String connectionno) {
		this.connectionno = connectionno;
	}

	public int getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(int monthyear) {
		this.monthyear = monthyear;
	}

	public Date getDis_date_eng() {
		return dis_date_eng;
	}

	public void setDis_date_eng(Date dis_date_eng) {
		this.dis_date_eng = dis_date_eng;
	}

	public Date getRec_date_eng() {
		return rec_date_eng;
	}

	public void setRec_date_eng(Date rec_date_eng) {
		this.rec_date_eng = rec_date_eng;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDatestamp() {
		return datestamp;
	}

	public void setDatestamp(Date datestamp) {
		this.datestamp = datestamp;
	}

	public int getPresent_reading() {
		return present_reading;
	}

	public void setPresent_reading(int present_reading) {
		this.present_reading = present_reading;
	}

	public int getPrevious_reading() {
		return previous_reading;
	}

	public void setPrevious_reading(int previous_reading) {
		this.previous_reading = previous_reading;
	}

	public int getWater_charges() {
		return water_charges;
	}

	public void setWater_charges(int water_charges) {
		this.water_charges = water_charges;
	}

	public Double getArrears() {
		return arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getObtotal() {
		return obtotal;
	}

	public void setObtotal(int obtotal) {
		this.obtotal = obtotal;
	}

	public int getTotalamtpaids() {
		return totalamtpaids;
	}

	public void setTotalamtpaids(int totalamtpaids) {
		this.totalamtpaids = totalamtpaids;
	}

	public Date getList_date() {
		return list_date;
	}

	public void setList_date(Date list_date) {
		this.list_date = list_date;
	}

	public int getDr_amount() {
		return dr_amount;
	}

	public void setDr_amount(int dr_amount) {
		this.dr_amount = dr_amount;
	}

	public Date getEnt_date_eng() {
		return ent_date_eng;
	}

	public void setEnt_date_eng(Date ent_date_eng) {
		this.ent_date_eng = ent_date_eng;
	}

	public String getEnt_date_nep() {
		return ent_date_nep;
	}

	public void setEnt_date_nep(String ent_date_nep) {
		this.ent_date_nep = ent_date_nep;
	}

	public String getName_eng() {
		return name_eng;
	}

	public void setName_eng(String name_eng) {
		this.name_eng = name_eng;
	}

	public String getName_nep() {
		return name_nep;
	}

	public void setName_nep(String name_nep) {
		this.name_nep = name_nep;
	}

	public String getAddress_eng() {
		return address_eng;
	}

	public void setAddress_eng(String address_eng) {
		this.address_eng = address_eng;
	}

	public String getAddress_nep() {
		return address_nep;
	}

	public void setAddress_nep(String address_nep) {
		this.address_nep = address_nep;
	}

	public Double getPipe_size() {
		return pipe_size;
	}

	public void setPipe_size(Double pipe_size) {
		this.pipe_size = pipe_size;
	}

	public String getCon_category() {
		return con_category;
	}

	public void setCon_category(String con_category) {
		this.con_category = con_category;
	}

	public String getWard_no() {
		return ward_no;
	}

	public void setWard_no(String ward_no) {
		this.ward_no = ward_no;
	}

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}

	public String getDis_date_nep() {
		return dis_date_nep;
	}

	public void setDis_date_nep(String dis_date_nep) {
		this.dis_date_nep = dis_date_nep;
	}

	public Integer getDis_fr() {
		return dis_fr;
	}

	public void setDis_fr(Integer dis_fr) {
		this.dis_fr = dis_fr;
	}

	public String getRec_date_nep() {
		return rec_date_nep;
	}

	public void setRec_date_nep(String rec_date_nep) {
		this.rec_date_nep = rec_date_nep;
	}
	
	
}
