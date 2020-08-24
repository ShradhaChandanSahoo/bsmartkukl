package com.bcits.bsmartwater.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bsmartwater.model.ConsumerMaster;

public interface ConsumerMasterDao extends GenericDAO<ConsumerMaster>{

	List<ConsumerMaster> findByConnectionNo(String connectionNo);

	long countByWardNo(String wardNo);

	List<String> getDistictWardNos();
	
	List<String> getDistictAllWardNos();
	

	List<String> getDistictMrNos();
	
	List<Integer> getDistictReadingDays();
	
	long getMaxConsumerId();

	List<ConsumerMaster> viewRtPlanGen(String mrCode, int readingDay, int wardNo,String areaNumber);
	
	ConsumerMaster findconsumer(String connectionNo);


	List<String> getDistictAreaNos();
	List<?> getNewConnectionApproveList(String sitecode);


	List<ConsumerMaster> findByConnNo(String connectionNo);

	Object[] getPipeSizeAndConType(String connection_no);

	List<Object[]> genDisConnList(String wardNo, String connCat, Double pipeSize,Double cutoffAmtfrm, Double cutoffAmtto);

	List<String> getDistictConnCategory();

	List<Double> getDistictpipeSize();

	long checkConnect_noInMaster(String connection_no);

	List<ConsumerMaster> getMasterDataByConnectionNum(String conNum);

	List<ConsumerMaster> getMasterDataByName(String name);

	List<ConsumerMaster> getMasterDataByWardNum(String wardNum);

	String getTotalConsumersCount(String siteCode);

	List<ConsumerMaster> getMasterDataByAreaNo(String areaNo);

	List<ConsumerMaster> getMasterDataByPhoneNum(String phoneNum);

	long countByWardNoRdayPS(String wardNo, Integer readingday, double pipesize, String concategory);

	List<ConsumerMaster> getMasterDataByOldConnNum(String oldConNum);

	List<String> getDistictWardNosUnmetered();

	List<String> getDistictConnType();

	List<String> getDistictMonthYearNep();

	List<?> getPendingConnForBilling(int approve_status,HttpServletRequest req);

	List<ConsumerMaster> getMasterDataByConnectionNumNew(String conNum,String schemaName);

	List<?> findByApplicationById(long applicationId);

	List<?> getAllSchemaData();

	List<?> getMonthWisecCollection();

	List<?> getBranchWiseData();

	Object[] getTariffData(String connectionNo);

	List<?> getEstimationData(long applicationId);

	long countByConCategory(String con_category1);

	public String updateTables();
	

}
