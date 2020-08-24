package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.ApplicationSettingsEntity;

public interface ApplicationSettingsDAO extends GenericDAO<ApplicationSettingsEntity> {

	String getSettingsData(String themeLayout);

	List<ApplicationSettingsEntity> getApplicationSettingsData();

}
