package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BSW_TARIFF_MASTER")

@NamedQueries({
	
	@NamedQuery(name="TariffRateMaster.getTariffRate",query="SELECT t FROM TariffRateMaster t WHERE t.diameter_tap=:diameter_tap AND UPPER(t.meter_type)=:meter_type",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name = "TariffRateMaster.getTariffRates", query = "SELECT b FROM TariffRateMaster b ORDER BY b.id DESC"),
	@NamedQuery(name="TariffRateMaster.checkTapAndMeterType",query="SELECT b FROM TariffRateMaster b where b.diameter_tap=:diameter_tap AND b.meter_type=:meter_type"),
	@NamedQuery(name="TariffRateMaster.getById",query="SELECT b FROM TariffRateMaster b where b.id!=:id AND b.diameter_tap=:diameter_tap AND b.meter_type=:meter_type"),
})
public class TariffRateMaster {

	@Id
	@SequenceGenerator(name = "bsw_tariff_master_seq", sequenceName = "BSW_TARIFF_MASTER_SEQ1",allocationSize=1) 
	@GeneratedValue(generator = "bsw_tariff_master_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="DIAMETER_TAP")
	private double diameter_tap;
	
	@Column(name="MIN_CONSUMPTION")
	private double min_consumption;
	
	@Column(name="METER_TYPE")
	private String meter_type;
	
	@Column(name="MINIMUM_CHARAGES")
	private double minimum_charages;
	
	@Column(name="RATE_PER_1000_LTR")
	private double rate_per_1000_ltr;
	
	@Column(name="MONTHLY_CHARGES")
	private double monthly_charges;
	
	@Column(name="SW_CHARGE_PERCENT")
	private double sw_charge_percent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDiameter_tap() {
		return diameter_tap;
	}

	public void setDiameter_tap(double diameter_tap) {
		this.diameter_tap = diameter_tap;
	}

	public double getMin_consumption() {
		return min_consumption;
	}

	public void setMin_consumption(double min_consumption) {
		this.min_consumption = min_consumption;
	}

	public String getMeter_type() {
		return meter_type;
	}

	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}

	public double getMinimum_charages() {
		return minimum_charages;
	}

	public void setMinimum_charages(double minimum_charages) {
		this.minimum_charages = minimum_charages;
	}

	public double getRate_per_1000_ltr() {
		return rate_per_1000_ltr;
	}

	public void setRate_per_1000_ltr(double rate_per_1000_ltr) {
		this.rate_per_1000_ltr = rate_per_1000_ltr;
	}

	public double getMonthly_charges() {
		return monthly_charges;
	}

	public void setMonthly_charges(double monthly_charges) {
		this.monthly_charges = monthly_charges;
	}

	public double getSw_charge_percent() {
		return sw_charge_percent;
	}

	public void setSw_charge_percent(double sw_charge_percent) {
		this.sw_charge_percent = sw_charge_percent;
	}
	
	
	

}
