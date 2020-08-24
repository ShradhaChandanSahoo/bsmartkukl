package com.bcits.bsmartwater.dao;

import java.util.Date;
import java.util.List;

import com.bcits.bsmartwater.model.SewageChangeEntity;

public interface SewageUsedChangeDAO<SewageChangeEntity> extends GenericDAO<SewageChangeEntity>
{
	
	List<SewageChangeEntity> getAllSewChanEntityData();
	List<SewageChangeEntity> getSewageApproveList();
	List<?> getSewageApprovalReprots(String frmDate,String toDate,String sitecode,int catagory);
	SewageChangeEntity findSewageChangeEntityByAppId(int id);
}
