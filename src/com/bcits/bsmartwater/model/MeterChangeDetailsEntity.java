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
@Table(name="BSW_METER_CHANGE")
@NamedQueries({
	
	//@NamedQuery(name="MeterChangeDetailsEntity.getMeterByConNo",query="SELECT M FROM MeterChangeDetailsEntity M WHERE m.connectionno=:connection_no"),
	@NamedQuery(name="MeterChangeDetailsEntity.getMeterChangeDetailsByConnNum",query="SELECT M FROM MeterChangeDetailsEntity M WHERE M.connectionno=:connId ORDER BY M.id DESC"),
	@NamedQuery(name="MeterChangeDetailsEntity.getMeterChangeDetails",query="SELECT M FROM MeterChangeDetailsEntity M  ORDER BY M.id DESC"),
})
public class MeterChangeDetailsEntity 
{
	@Id
	@SequenceGenerator(name = "bsw_meter_change_seq", sequenceName = "BSW_METER_CHANGE_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_meter_change_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="CONNECTIONNO")
	private String connectionno;
	
	@Column(name="LEDGNO")
	private String ledgno;
	
	@Column(name="FOLIO")
	private String folio;
	
	@Column(name="FR")
	private int fr;
	
	@Column(name="IR")
	private int ir;
	
	@Column(name="OLDMETERNO")
	private String oldmeterno;
	
	@Column(name="RELEASEDATE")
	private Date releasedate;
	
	@Column(name="FIXEDDATE")
	private Date fixeddate;
	
	@Column(name="NEWMETERNO")
	private String newmeterno;
	
	@Column(name="REASON")
	private String reason;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="DATESTAMP")
	private Date datestamp;

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

	public String getLedgno() {
		return ledgno;
	}

	public void setLedgno(String ledgno) {
		this.ledgno = ledgno;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public int getFr() {
		return fr;
	}

	public void setFr(int fr) {
		this.fr = fr;
	}

	public int getIr() {
		return ir;
	}

	public void setIr(int ir) {
		this.ir = ir;
	}

	public String getOldmeterno() {
		return oldmeterno;
	}

	public void setOldmeterno(String oldmeterno) {
		this.oldmeterno = oldmeterno;
	}

	public Date getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(Date releasedate) {
		this.releasedate = releasedate;
	}

	public Date getFixeddate() {
		return fixeddate;
	}

	public void setFixeddate(Date fixeddate) {
		this.fixeddate = fixeddate;
	}

	public String getNewmeterno() {
		return newmeterno;
	}

	public void setNewmeterno(String newmeterno) {
		this.newmeterno = newmeterno;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	
	
	
}
