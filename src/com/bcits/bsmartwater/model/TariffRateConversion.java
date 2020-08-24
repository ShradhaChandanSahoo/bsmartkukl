package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BSW_TARIFF_CONVERSION")
@NamedQueries({
	
	@NamedQuery(name="TariffRateConversion.getTariffDetails",query="SELECT b FROM TariffRateConversion b ORDER BY b.id ASC"),
	
})
public class TariffRateConversion {
	
	@Id
	@SequenceGenerator(name="tariff_con_seq",sequenceName="TARIFF_CONVERSION_SEQ")
	@GeneratedValue(generator="tariff_con_seq")
	@Column(name="ID")
	private int id;
	
	@Column(name="CON_TYPE")
	private String con_type;
	
	@Column(name="CON_CATEGORY")
	private String con_category;
	
	@Column(name="TARIFF")
	private String tariff;
	
	@Column(name="TAP_SIZE")
	private String tap_Size;
	
	@Column(name="RATE_CONSTANT")
	private String rate_constant;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCon_type() {
		return con_type;
	}

	public void setCon_type(String con_type) {
		this.con_type = con_type;
	}

	public String getCon_category() {
		return con_category;
	}

	public void setCon_category(String con_category) {
		this.con_category = con_category;
	}

	public String getTariff() {
		return tariff;
	}

	public void setTariff(String tariff) {
		this.tariff = tariff;
	}

	public String getTap_Size() {
		return tap_Size;
	}

	public void setTap_Size(String tap_Size) {
		this.tap_Size = tap_Size;
	}

	public String getRate_constant() {
		return rate_constant;
	}

	public void setRate_constant(String rate_constant) {
		this.rate_constant = rate_constant;
	}
	

}
