package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@NamedQueries({
	
	@NamedQuery(name="ObservationEntity.getAllObservationRecords",query="SELECT b FROM ObservationEntity b ORDER BY b.observationName ASC"),
	@NamedQuery(name="ObservationEntity.checkForObservationNameAvailability",query="SELECT count(b) FROM ObservationEntity b where b.observationName=:observationName"),
	@NamedQuery(name="ObservationEntity.editObservationDetails",query="SELECT b FROM ObservationEntity b where b.id=:id"),
	@NamedQuery(name="ObservationEntity.findNameById",query="SELECT b.observationName FROM ObservationEntity b where b.id=:id"),
	@NamedQuery(name="ObservationEntity.getAllObservationRecordsBill",query="SELECT b FROM ObservationEntity b WHERE b.status='Active' ORDER BY b.observationName ASC"),

	
})
@SuppressWarnings("serial")
@Entity
@Table(name="OBSERVATION_MASTER")
public class ObservationEntity extends BaseEntity
{
@Id
@SequenceGenerator(name="generator",sequenceName="OBSERVATION_MASTER_SEQ",allocationSize=1)
@GeneratedValue(generator="generator")
@Column(name="ID")
private int id;
@Column(name="OBSERVATION_NAME")
private String observationName;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getObservationName() {
	return observationName;
}
public void setObservationName(String observationName) {
	this.observationName = observationName;
}

}
