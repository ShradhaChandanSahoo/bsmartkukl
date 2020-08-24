package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "WARD_DETAILS")
@NamedQueries({

		@NamedQuery(name = "WardDetailsEntity.getAllWardRecords", query = "SELECT b FROM WardDetailsEntity b ORDER BY b.wardNo ASC"),
		//@NamedQuery(name = "WardDetailsEntity.checkForWardNameAvailability", query = "SELECT count(b) FROM WardDetailsEntity b where b.wardName=:wardName"),
		@NamedQuery(name = "WardDetailsEntity.checkForWardNoAvailability", query = "SELECT count(*) FROM WardDetailsEntity b where b.wardNo=:wardNo"),
		@NamedQuery(name = "WardDetailsEntity.getWardDataForEditing", query = "SELECT b FROM WardDetailsEntity b where b.id=:id"),
		@NamedQuery(name = "WardDetailsEntity.uniqueWardNo", query = "SELECT U FROM WardDetailsEntity U WHERE U.wardNo=:wardNo ORDER BY U.id DESC"),
		@NamedQuery(name = "WardDetailsEntity.getWardData", query = "SELECT b FROM WardDetailsEntity b ORDER BY b.id DESC"),
		@NamedQuery(name = "WardDetailsEntity.findByMunicipalityId", query = "SELECT c FROM WardDetailsEntity c WHERE c.muncipal_Id=:muncipal_Id"),
		@NamedQuery(name = "WardDetailsEntity.getWardNoById", query = "SELECT b.wardNo FROM WardDetailsEntity b WHERE b.id=:wardId"),
		@NamedQuery(name="WardDetailsEntity.checkForWardNoUpdate",query="select count(*) from WardDetailsEntity b where b.id!=:id and b.wardNo=:wardNo"),
		//@NamedQuery(name="WardDetailsEntity.getAllWardRecordsOne",query="SELECT b.wardNo,b.wardName,m.muncipalityName,b.muncipal_Id,m.muncipalityDesc,b.id FROM WardDetailsEntity b,MuncipalityDetailsEntity m where b.muncipal_Id=m.id ORDER BY b.wardNo ASC")

})
public class WardDetailsEntity {
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "WARD_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "generator")
	@Column(name = "ID")
	private int id;
	
	@Column(name = "WARD_NO")
	private String wardNo;
	
	@Column(name = "WARD_NAME")
	private String wardName;
	
	@Column(name="MUNICIPALTY_ID")
	private String muncipal_Id;
	
	@OneToOne
	@JoinColumn(name = "MUNICIPALTY_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private MuncipalityDetailsEntity muncipalityDetailsEntity;
	

	public MuncipalityDetailsEntity getMuncipalityDetailsEntity() {
		return muncipalityDetailsEntity;
	}

	public void setMuncipalityDetailsEntity(MuncipalityDetailsEntity muncipalityDetailsEntity) {
		this.muncipalityDetailsEntity = muncipalityDetailsEntity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getWardNo() {
		return wardNo;
	}

	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public String getMuncipal_Id() {
		return muncipal_Id;
	}

	public void setMuncipal_Id(String muncipal_Id) {
		this.muncipal_Id = muncipal_Id;
	}

	
}
