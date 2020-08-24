package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.ConsumerMasterApproval;

public interface ConsumerMasterApprovalDao extends GenericDAO<ConsumerMasterApproval>{

	List<?> getPendingConsumersToApprove();

	List<ConsumerMasterApproval> findByConnectionNo(String consumerId);

	ConsumerMasterApproval findCustomerById(int parseInt);

	List<ConsumerMasterApproval> findRecordById(int parseInt);

	List<?> getConsumerApproveList();


}
