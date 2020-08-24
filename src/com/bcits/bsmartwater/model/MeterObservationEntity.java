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
@Table(name = "BSW_OBSERVATION")

@NamedQueries({
	
	@NamedQuery(name="MeterObservationEntity.uniqueObsNo",query="SELECT M FROM MeterObservationEntity M WHERE M.observationno=:observationno ORDER BY M.id DESC"),
	@NamedQuery(name="MeterObservationEntity.searchConnNo",query="SELECT M FROM MeterObservationEntity M WHERE M.connectionno=:connectionno ORDER BY M.id DESC"),
	@NamedQuery(name="MeterObservationEntity.uniqueObsSearchNoForEdit",query="SELECT M FROM MeterObservationEntity M WHERE M.observationno=:observationno AND M.id!=:id ORDER BY M.id DESC"),
})
public class MeterObservationEntity 
{
	@Id
	@SequenceGenerator(name = "bsw_observation_seq", sequenceName = "BSW_OBSERVATION_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_observation_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="CONNECTIONNO")
	private String connectionno;
	
	@Column(name="OBSERVATIONNO")
	private String observationno;
	
	@Column(name="LEDGNO")
	private int ledgno;
	
	@Column(name="FOLIO")
	private String folio;
	
	@Column(name="MRCODE")
	private String mrcode;
	
	@Column(name="OBSERVATION")
	private String observation;
	
	@Column(name="OBS_DATE_ENG")
	private Date obs_date_eng;
	
	@Column(name="OBS_DATE_NEP")
	private String obs_date_nep;
	
	@Column(name="ENTEREDDATE_ENG")
	private Date entereddate_eng;
	
	@Column(name="ENTEREDDATE_NEP")
	private String entereddate_nep;
	
	@Column(name="REMARKS")
	private String remarks;

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

	public String getObservationno() {
		return observationno;
	}

	public void setObservationno(String observationno) {
		this.observationno = observationno;
	}

	public int getLedgno() {
		return ledgno;
	}

	public void setLedgno(int ledgno) {
		this.ledgno = ledgno;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getMrcode() {
		return mrcode;
	}

	public void setMrcode(String mrcode) {
		this.mrcode = mrcode;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getObs_date_eng() {
		return obs_date_eng;
	}

	public void setObs_date_eng(Date obs_date_eng) {
		this.obs_date_eng = obs_date_eng;
	}

	public String getObs_date_nep() {
		return obs_date_nep;
	}

	public void setObs_date_nep(String obs_date_nep) {
		this.obs_date_nep = obs_date_nep;
	}

	public Date getEntereddate_eng() {
		return entereddate_eng;
	}

	public void setEntereddate_eng(Date entereddate_eng) {
		this.entereddate_eng = entereddate_eng;
	}

	public String getEntereddate_nep() {
		return entereddate_nep;
	}

	public void setEntereddate_nep(String entereddate_nep) {
		this.entereddate_nep = entereddate_nep;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
