package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.ApplicationSettingsDAO;
import com.bcits.bsmartwater.model.ApplicationSettingsEntity;
import com.bcits.bsmartwater.service.ApplicationSettingService;

@Service
public class ApplicationSettingServiceImpl implements ApplicationSettingService {
	
	@Autowired
	private ApplicationSettingsDAO applicationSettingsDAO;

	@Override
	public String getSettingsData(String themeLayout) {
		return applicationSettingsDAO.getSettingsData(themeLayout);
	}

	@Override
	public List<ApplicationSettingsEntity> getApplicationSettingsData() {	
		return applicationSettingsDAO.getApplicationSettingsData();
	}

	@Override
	public String getDateDefaultFormat() {
		return applicationSettingsDAO.find(1).getOptionValue();
	}

}
