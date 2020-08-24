package com.bcits.bsmartwater.model;

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
@Table(name="MUNICIPALITY_MASTER")

@NamedQueries({
	@NamedQuery(name="MuncipalityDetailsEntity.getAllMuncipalityRecords",query="SELECT b FROM MuncipalityDetailsEntity b ORDER BY b.id ASC"),
	@NamedQuery(name="MuncipalityDetailsEntity.checkForMunicipalityNameAvailability",query="SELECT COUNT(b) FROM MuncipalityDetailsEntity b WHERE b.muncipalityName=:muncipalityName"),
	
	@NamedQuery(name="MuncipalityDetailsEntity.checkMunicipalityNames",query="SELECT COUNT(b) FROM MuncipalityDetailsEntity b WHERE b.id!=:id AND b.muncipalityName=:muncipalityName")
})
public class MuncipalityDetailsEntity extends BaseEntity
{

	@Id
	@SequenceGenerator(name="municipalitySequence",sequenceName="MUNICIPALITY_SEQ",allocationSize=1)
	@GeneratedValue(generator="municipalitySequence")
	@Column(name="ID")
	private int id;
	
	@Column(name="MUNICIPALTY_NAME")
	private String muncipalityName;

	@Column(name="MUNICIPALTY_DESCRIPTION")
	private String muncipalityDesc;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMuncipalityName() {
		return muncipalityName;
	}

	public void setMuncipalityName(String muncipalityName) {
		this.muncipalityName = muncipalityName;
	}

	public String getMuncipalityDesc() {
		return muncipalityDesc;
	}

	public void setMuncipalityDesc(String muncipalityDesc) {
		this.muncipalityDesc = muncipalityDesc;
	}
	
	
	
}
