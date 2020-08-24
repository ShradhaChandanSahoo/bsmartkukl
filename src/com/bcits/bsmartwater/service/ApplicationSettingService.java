package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.ApplicationSettingsEntity;

public interface ApplicationSettingService {

	String getSettingsData(String themeLayout);

	List<ApplicationSettingsEntity> getApplicationSettingsData();

	String getDateDefaultFormat();

}
