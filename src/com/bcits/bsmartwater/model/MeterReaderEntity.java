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
@Table(name = "BSW_METER_READER")

@NamedQueries({
	
	@NamedQuery(name="MeterReaderEntity.readMrDetails",query="SELECT M FROM MeterReaderEntity M where M.mrCode!='0' ORDER BY M.id ASC"),
	@NamedQuery(name="MeterReaderEntity.uniqueMtrReader",query="SELECT M FROM MeterReaderEntity M WHERE M.mrCode=:mrCode ORDER BY M.id DESC"),
	@NamedQuery(name="MeterReaderEntity.deleteMrDetails",query="DELETE FROM MeterReaderEntity m WHERE m.id=:idVal"),
	@NamedQuery(name="MeterReaderEntity.getMeterReaderDeatils",query="SELECT M.mrCode FROM MeterReaderEntity M"),
	@NamedQuery(name="MeterReaderEntity.getMRName",query="SELECT m.mrName FROM MeterReaderEntity m WHERE m.id=:id"),
	@NamedQuery(name="MeterReaderEntity.getMeterReaderEntity",query="SELECT M FROM MeterReaderEntity M  WHERE M.id=:idVal and M.mrCode=:mrCode"),
	
	
})
public class MeterReaderEntity extends BaseEntity
{
	@Id
	@SequenceGenerator(name = "bsw_meter_reader_seq", sequenceName = "BSW_METER_READER_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_meter_reader_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="MRCODE")
	private String mrCode;
	
	@Column(name="MRNAME")
	private String mrName;
	
	@Column(name="OLD_METER_READER_NAME")
	private String oldMrName;
	
	@Column(name="METER_READER_CHANGE_DATE")
	private String mrNameChangeDate;
	
	public String getOldMrName() {
		return oldMrName;
	}

	public void setOldMrName(String oldMrName) {
		this.oldMrName = oldMrName;
	}

	public String getMrNameChangeDate() {
		return mrNameChangeDate;
	}

	public void setMrNameChangeDate(String mrNameChangeDate) {
		this.mrNameChangeDate = mrNameChangeDate;
	}

	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="MOBILENO")
	private Long mobileno;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMrCode() {
		return mrCode;
	}

	public void setMrCode(String mrCode) {
		this.mrCode = mrCode;
	}

	public String getMrName() {
		return mrName;
	}

	public void setMrName(String mrName) {
		this.mrName = mrName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMobileno() {
		return mobileno;
	}

	public void setMobileno(Long mobileno) {
		this.mobileno = mobileno;
	}
	
	

}
