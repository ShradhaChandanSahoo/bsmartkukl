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
@Table(name="COUNTER_TAB")

@NamedQueries({
	
	@NamedQuery(name="CounterDetails.getAllCounterRecords",query="SELECT b FROM CounterDetails b ORDER BY b.id ASC"),
	@NamedQuery(name="CounterDetails.checkForCounterNameAvailability",query="SELECT count(b) FROM CounterDetails b where b.counterName=:counterName"),
	@NamedQuery(name = "CounterDetails.checkForCounterNumberAvailability", query = "SELECT count(b) FROM CounterDetails b where b.counterNumber=:counterNumber"),
	@NamedQuery(name = "CounterDetails.getcounterName", query = "SELECT b FROM CounterDetails b where b.counterNumber=:counter_no"),
	@NamedQuery(name = "CounterDetails.checkCounterNameForUpdate", query = "SELECT count(b) FROM CounterDetails b where b.id!=:id and b.counterName=:counterName"),
	@NamedQuery(name = "CounterDetails.checkCounterNumberForUpdate", query = "SELECT count(b) FROM CounterDetails b where b.id!=:id and b.counterNumber=:counterNumber"),
	@NamedQuery(name="CounterDetails.getAllCounterRecordsNA",query="SELECT b FROM CounterDetails b WHERE b.counterNumber IN(:counterNos) ORDER BY b.counterNumber ASC"),
	
})
public class CounterDetails extends BaseEntity
{
@Id
@SequenceGenerator(name="generator",sequenceName="COUNTER_SEQ",allocationSize=1)
@GeneratedValue(generator="generator")
@Column(name="ID")
private int id;

@Column(name="COUNTERNUMBER")
private String counterNumber;

@Column(name="COUNTERNAME")
private String counterName;

@Column(name="COUNTERADDRESS")
private String counterAddress;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getCounterNumber() {
	return counterNumber;
}

public void setCounterNumber(String counterNumber) {
	this.counterNumber = counterNumber;
}

public String getCounterName() {
	return counterName;
}

public void setCounterName(String counterName) {
	this.counterName = counterName;
}

public String getCounterAddress() {
	return counterAddress;
}

public void setCounterAddress(String counterAddress) {
	this.counterAddress = counterAddress;
}


}
