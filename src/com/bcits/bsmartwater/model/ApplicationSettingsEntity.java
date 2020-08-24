package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author usews56
 *
 */
@Entity
@Table(name="APPLICATION_SETTINGS")
@NamedQueries({
    @NamedQuery(name="ApplicationSettingsEntity.getSettingsData", query="SELECT a.optionValue FROM ApplicationSettingsEntity a WHERE a.optionType=:themeLayout ORDER BY a.settingId ASC"),
    @NamedQuery(name="ApplicationSettingsEntity.getApplicationSettingsData", query="SELECT a FROM ApplicationSettingsEntity a ORDER BY a.settingId ASC"),
})
public class ApplicationSettingsEntity {
		
	private int settingId;	
	private String optionType;	
	private String optionValue;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	@Column(name="OPTION_TYPE")
	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	@Column(name="OPTION_VALUE")
	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
}
