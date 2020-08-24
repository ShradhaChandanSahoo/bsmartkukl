package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.config.CacheConfiguration.TransactionalMode;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.ConsumerMasterApprovalDao;
import com.bcits.bsmartwater.model.ConsumerMasterApproval;

@Repository
public class ConsumerMasterApprovalDaoImpl extends GenericDAOImpl<ConsumerMasterApproval> implements ConsumerMasterApprovalDao {

	@Override
	public List<?> getPendingConsumersToApprove() {
			return getCustomEntityManager().createNamedQuery("ConsumerMasterApproval.getPendingConsumersToApprove").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMasterApproval> findByConnectionNo(String consumerId) {
		return getCustomEntityManager().createNamedQuery("ConsumerMasterApproval.findByConnectionNo").setParameter("connectionNo", consumerId).getResultList();
	}

	@Override
	public ConsumerMasterApproval findCustomerById(int parseInt) {
		System.out.println("parseInt ===> "+parseInt);
		ConsumerMasterApproval master = (ConsumerMasterApproval) getCustomEntityManager().createNamedQuery("ConsumerMasterApproval.findCustomerById").setParameter("id", parseInt).getSingleResult();
		System.out.println("master ===> "+master.getAddress_eng());
		return master;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMasterApproval> findRecordById(int parseInt) {
		 List<ConsumerMasterApproval> c = null;
		 try {
			c = getCustomEntityManager().createNamedQuery("ConsumerMasterApproval.findCustomerById").setParameter("id", parseInt).getResultList();
			for (ConsumerMasterApproval consumerMasterApproval : c) {
				System.out.println(" consumerMasterApproval ===> "+consumerMasterApproval.getAddress_eng());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public List<?> getConsumerApproveList() {
		return getCustomEntityManager().createNamedQuery("ConsumerMasterApproval.getConsumerApproveList").getResultList();
	}
}
