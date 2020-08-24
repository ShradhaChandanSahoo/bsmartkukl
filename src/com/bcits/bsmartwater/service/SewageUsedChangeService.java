package com.bcits.bsmartwater.service;

import java.util.Date;
import java.util.List;

import com.bcits.bsmartwater.model.ConTypeChangeEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.SewageChangeEntity;

public interface SewageUsedChangeService
{
	void save(SewageChangeEntity sewageChangeEntity);
	void update(SewageChangeEntity sewageChangeEntity);
	List<SewageChangeEntity> getAllSewChanEntityData();
	List<SewageChangeEntity> getSewageApproveList();
	
	List<?> getSewageApprovalReprots(String frmDate,String toDate,String sitecode,int catagory);
	SewageChangeEntity findSewageChangeEntityByAppId(int id);
}
