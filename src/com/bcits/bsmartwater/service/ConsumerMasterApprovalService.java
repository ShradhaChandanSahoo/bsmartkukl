package com.bcits.bsmartwater.service;

import java.util.List;

public interface ConsumerMasterApprovalService {

	List<?> getPendingConsumersToApprove();

	List<?> getConsumerApproveList();

}
