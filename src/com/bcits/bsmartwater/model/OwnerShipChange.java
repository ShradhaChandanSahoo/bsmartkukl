package com.bcits.bsmartwater.model;

import java.sql.Blob;
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
@Table(name = "BSW_OWNERSHIP_CHANGE")
@NamedQueries({
	@NamedQuery(name="OwnerShipChange.getPendingConnectionsToApprove",query="SELECT c FROM OwnerShipChange c WHERE c.approve_status=:approve_status"),
	@NamedQuery(name="OwnerShipChange.getRecordByConnectionNo",query="SELECT c FROM OwnerShipChange c WHERE c.approve_status=:approve_status AND UPPER(c.connection_no)=:connection_no"),
	@NamedQuery(name="OwnerShipChange.getOwnerShipChangeList",query="SELECT c FROM OwnerShipChange c"),
	
})

public class OwnerShipChange {

	
	@Id
	@SequenceGenerator(name = "ownershipchange_seq", sequenceName = "OWNERSHIPCHANGE_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "ownershipchange_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="OLD_CONSUMER_TITLE")
	private String old_consumer_title;
	
	@Column(name="OLD_NAME_ENG")
	private String old_name_eng;
	
	@Column(name="OLD_NAME_NEP")
	private String old_name_nep;
	
	@Column(name="OLD_FNAME_ENG")
	private String old_fname_eng;
	
	@Column(name="OLD_FNAME_NEP")
	private String old_fname_nep;
	
	@Column(name="OLD_GFNAME_ENG")
	private String old_gfname_eng;
	
	@Column(name="OLD_GFNAME_NEP")
	private String old_gfname_nep;
	
	@Column(name="OLD_CITIZENSHIPNO")
	private String old_citizenshipno;
	
	@Column(name="NEW_CONSUMER_TITLE")
	private String new_consumer_title;
	
	@Column(name="NEW_NAME_ENG")
	private String new_name_eng;
	
	@Column(name="NEW_NAME_NEP")
	private String new_name_nep;
	
	@Column(name="NEW_FNAME_ENG")
	private String new_fname_eng;
	
	@Column(name="NEW_FNAME_NEP")
	private String new_fname_nep;
	
	@Column(name="NEW_GFNAME_ENG")
	private String new_gfname_eng;
	
	@Column(name="NEW_GFNAME_NEP")
	private String new_gfname_nep;
	
	@Column(name="NEW_CITIZENSHIPNO")
	private String new_citizenshipno;
	
	@Column(name="REASON")
	private String reason;
	
	@Column(name="REQUEST_BY")
	private String request_by;
	
	@Column(name="REQUEST_DATE")
	private Date request_date;
	
	@Column(name="EFFECTIVE_DATE")
	private String effective_date;
	
	@Column(name="APPROVE_BY")
	private String approve_by;
	
	@Column(name="APPROVE_DATE")
	private Date approve_date;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="APPROVE_STATUS")
	private Integer approve_status;

	@Column(name="PUBLIC_NOTICE_PUBLISHED")
	private String public_notice_published;
	
	@Column(name="PUBLIC_NOTICE_DOC")
	private byte[] public_notice_doc;
	
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

	public String getOld_consumer_title() {
		return old_consumer_title;
	}

	public void setOld_consumer_title(String old_consumer_title) {
		this.old_consumer_title = old_consumer_title;
	}

	public String getOld_name_eng() {
		return old_name_eng;
	}

	public void setOld_name_eng(String old_name_eng) {
		this.old_name_eng = old_name_eng;
	}

	public String getOld_name_nep() {
		return old_name_nep;
	}

	public void setOld_name_nep(String old_name_nep) {
		this.old_name_nep = old_name_nep;
	}

	public String getOld_fname_eng() {
		return old_fname_eng;
	}

	public void setOld_fname_eng(String old_fname_eng) {
		this.old_fname_eng = old_fname_eng;
	}

	public String getOld_fname_nep() {
		return old_fname_nep;
	}

	public void setOld_fname_nep(String old_fname_nep) {
		this.old_fname_nep = old_fname_nep;
	}

	public String getOld_gfname_eng() {
		return old_gfname_eng;
	}

	public void setOld_gfname_eng(String old_gfname_eng) {
		this.old_gfname_eng = old_gfname_eng;
	}

	public String getOld_gfname_nep() {
		return old_gfname_nep;
	}

	public void setOld_gfname_nep(String old_gfname_nep) {
		this.old_gfname_nep = old_gfname_nep;
	}

	public String getOld_citizenshipno() {
		return old_citizenshipno;
	}

	public void setOld_citizenshipno(String old_citizenshipno) {
		this.old_citizenshipno = old_citizenshipno;
	}

	public String getNew_consumer_title() {
		return new_consumer_title;
	}

	public void setNew_consumer_title(String new_consumer_title) {
		this.new_consumer_title = new_consumer_title;
	}

	public String getNew_name_eng() {
		return new_name_eng;
	}

	public void setNew_name_eng(String new_name_eng) {
		this.new_name_eng = new_name_eng;
	}

	public String getNew_name_nep() {
		return new_name_nep;
	}

	public void setNew_name_nep(String new_name_nep) {
		this.new_name_nep = new_name_nep;
	}

	public String getNew_fname_eng() {
		return new_fname_eng;
	}

	public void setNew_fname_eng(String new_fname_eng) {
		this.new_fname_eng = new_fname_eng;
	}

	public String getNew_fname_nep() {
		return new_fname_nep;
	}

	public void setNew_fname_nep(String new_fname_nep) {
		this.new_fname_nep = new_fname_nep;
	}

	public String getNew_gfname_eng() {
		return new_gfname_eng;
	}

	public void setNew_gfname_eng(String new_gfname_eng) {
		this.new_gfname_eng = new_gfname_eng;
	}

	public String getNew_gfname_nep() {
		return new_gfname_nep;
	}

	public void setNew_gfname_nep(String new_gfname_nep) {
		this.new_gfname_nep = new_gfname_nep;
	}

	public String getNew_citizenshipno() {
		return new_citizenshipno;
	}

	public void setNew_citizenshipno(String new_citizenshipno) {
		this.new_citizenshipno = new_citizenshipno;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRequest_by() {
		return request_by;
	}

	public void setRequest_by(String request_by) {
		this.request_by = request_by;
	}

	public Date getRequest_date() {
		return request_date;
	}

	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}

	

	public String getApprove_by() {
		return approve_by;
	}

	public void setApprove_by(String approve_by) {
		this.approve_by = approve_by;
	}

	public Date getApprove_date() {
		return approve_date;
	}

	public void setApprove_date(Date approve_date) {
		this.approve_date = approve_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(Integer approve_status) {
		this.approve_status = approve_status;
	}

	public String getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}

	

	public String getPublic_notice_published() {
		return public_notice_published;
	}

	public void setPublic_notice_published(String public_notice_published) {
		this.public_notice_published = public_notice_published;
	}

	public byte[] getPublic_notice_doc() {
		return public_notice_doc;
	}

	public void setPublic_notice_doc(byte[] public_notice_doc) {
		this.public_notice_doc = public_notice_doc;
	}
	
	
	
}
