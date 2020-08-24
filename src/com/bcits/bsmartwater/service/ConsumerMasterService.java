package com.bcits.bsmartwater.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.ConsumerMasterApproval;

public interface ConsumerMasterService {

	List<?> findByConnectionNo(String connectionNo);
	
	long getMaxConsumerId(); 

	long countByWardNo(String wardNo);


	void saveConsumerMasterDetails(ConsumerMasterApproval consumerMaster,
			ModelMap model, HttpServletRequest req);

	ConsumerMasterApproval findByConsumerId(String consumerId);


	List<String> getDistictWardNos();
	List<String> getDistictAllWardNos();

    public String updateTables();

	List<String> getDistictMrNos();


	List<Integer> getDistictReadingDays();
	

	ConsumerMaster findconsumer(String connectionNo);



	List<String> getDistictAreaNos();

	void updateConsumerMasterDetails(
			ConsumerMaster consumerMaster, ModelMap model,
			HttpServletRequest request);


	String approveConsumerDetails(HttpServletRequest request);
	
	String rejectConsumerDetails(HttpServletRequest request);


	List<?> getNewConnectionApproveList(String sitecode);
	List<ConsumerMaster> getMasterDataByConnectionNum(String conNum);

	List<ConsumerMaster> getMasterDataByName(String name);

	List<ConsumerMaster> getMasterDataByWardNum(String wardNum);

	Object[] getPipeSizeAndConType(String connection_no);


	String getTotalConsumersCount(String siteCode);
	List<String> getDistictConnCategory();


	List<Double> getDistictpipeSize();


	List<ConsumerMaster> getMasterDataByAreaNo(String areaNo);

	List<ConsumerMaster> getMasterDataByPhoneNum(String phoneNum);


	void update(ConsumerMaster customer);


	long countByWardNoRdayPS(String wardNo, Integer readingday, double pipesize, String concategory);


	List<ConsumerMaster> getMasterDataByOldConnNum(String oldConNum);


	List<String> getDistictWardNosUnmetered();


	List<String> getDistictMonthYearNep();

	List<?> getPendingConnForBilling(int approve_status,HttpServletRequest request);

	List<ConsumerMaster> getMasterDataByConnectionNumNew(String conNum,String schemaName);

	List<?> findByApplicationById(long applicationId);

	List<?> getAllSchemaData();

	List<?> getMonthWisecCollection();

	List<?> getBranchWiseData();

	Object[] getTariffData(String connectionNo);

	List<?> getEstimationData(long applicationId);

	long countByConCategory(String con_category1);




}
