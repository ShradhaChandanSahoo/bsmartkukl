package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.BillPenaltyAdjDao;
import com.bcits.bsmartwater.model.BillPenaltyAdjustment;

@Repository
public class BillPenaltyAdjDaoImpl extends GenericDAOImpl<BillPenaltyAdjustment> implements BillPenaltyAdjDao {

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getPendingConnectionsToApprove").setParameter("approve_status", approve_status).getResultList();

	}

	@Override
	public BillPenaltyAdjustment getConTypeByConNo(String connection_no,int approve_status) {
		
		try{
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getConTypeByConNo1",BillPenaltyAdjustment.class).setParameter("connection_no", connection_no).setParameter("approve_status", approve_status).getSingleResult();
		}catch(Exception e){
		e.printStackTrace();
		return null;
		}
	}
	
	@Override
	public BillPenaltyAdjustment getPendingBoardByConNo(String connection_no,int approve_status) {
		
		try{
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getPendingBoardByConNo",BillPenaltyAdjustment.class).setParameter("connection_no", connection_no).setParameter("approve_status", approve_status).getSingleResult();
		}catch(Exception e){
		e.printStackTrace();
		return null;
		}
	}
	
	@Override
	public BillPenaltyAdjustment checkPendingRequests(String connection_no, int approve_status, String adj_type) {
		System.out.println("con No---"+connection_no+" ----Status----"+approve_status+"----adj_type-----"+adj_type);
		try{
		     if("BDJ".equalsIgnoreCase(adj_type)){
		    	 return getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.checkPendingRequestsT",BillPenaltyAdjustment.class).setParameter("connection_no", connection_no).setParameter("adj_type", adj_type).getSingleResult();
 
		     }else{
		    	 return getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.checkPendingRequests",BillPenaltyAdjustment.class).setParameter("connection_no", connection_no.toUpperCase()).setParameter("approve_status", approve_status).setParameter("adj_type", adj_type).getSingleResult();
		     }
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public BillPenaltyAdjustment checkPendingRequestsSameMonth(String connection_no, String monthyear) {
		try{
		return getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.checkPendingRequestsSameMonth",BillPenaltyAdjustment.class).setParameter("connection_no", connection_no.toUpperCase()).setParameter("to_mon_year", Integer.parseInt(monthyear)).getSingleResult();
		} catch(NoResultException e){
			return null;
		}
	}
	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status,String adj_type) {
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getPendingConnectionsToApprove1").setParameter("approve_status", approve_status).setParameter("adj_type", adj_type).getResultList();

	}

	@Override
	public long getTransactionPending(String connectionNo) {
		try{
			System.out.println(connectionNo+"---connectionNo");
			return 	(long) getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getTransactionPending").setParameter("connectionNo", connectionNo.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List<?> getAdjCorrList() {
		try{
			return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getAdjCorrList").getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<?> getAdjTransList(String adj_type) {
		try{
			return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getAdjTransList").setParameter("adj_type", adj_type).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getBrdCorrList(String adj_type) {
		try{
			return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getAdjTransList1").setParameter("adj_type", adj_type).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long pendingCount(String monthyearnep) {
		try{
			return 	(long) getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.pendingCount").setParameter("monthyearnep", monthyearnep).getSingleResult();	
		}catch(Exception e){
			
			return 0;
		}
	}

	@Override
	public Object[] getTransactionCount(String connection_no) {
		
		String sql = "SELECT COUNT(CASE WHEN APPROVE_STATUS=0 THEN 1 END), COUNT(CASE WHEN APPROVE_STATUS=1 THEN 1 END), COUNT(CASE WHEN APPROVE_STATUS=3 THEN 1 END)"
				+ "FROM BILL_PENALTY_ADJ WHERE CONNECTION_NO = '"+connection_no+"' AND ADJ_TYPE = 'BOARD' AND APPROVE_STATUS NOT IN(2) ";
		try{
		return (Object[]) getCustomEntityManager().createNativeQuery(sql).getSingleResult();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long pendingForArrCorr(String connectionNo) {
		try{
			System.out.println(connectionNo+"---connectionNo");
			return 	(long) getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.pendingForArrCorr").setParameter("connectionNo", connectionNo.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<?> getAdjTransListByBranch(String branch) {
		try{
			return 	getCustomEntityManager(branch).createNamedQuery("BillPenaltyAdjustment.getAdjTransListByBranch").getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BillPenaltyAdjustment getConTypeByConNo(String conNo, int approve_status, String branch) {
		try{
			return 	getCustomEntityManager(branch).createNamedQuery("BillPenaltyAdjustment.getConTypeByConNo1",BillPenaltyAdjustment.class).setParameter("connection_no", conNo.toUpperCase()).setParameter("approve_status", approve_status).getSingleResult();
			}catch(Exception e){
			e.printStackTrace();
			return null;
			}
	}

	@Override
	public void update(BillPenaltyAdjustment billPenaltyAdjustment, String branch) {
		try {
			Session session = getCustomEntityManager(branch).unwrap(Session.class);
			session.update(billPenaltyAdjustment);
			session.flush();
			session.clear();
		} catch (HibernateException e) {
			throw e;
		}
	}

	@Override
	public Object[] getTransactionCountForBrdCr(String connection_no) {
		String sql = "SELECT COUNT(CASE WHEN APPROVE_STATUS=0 THEN 1 END), COUNT(CASE WHEN APPROVE_STATUS=1 THEN 1 END), COUNT(CASE WHEN APPROVE_STATUS=3 THEN 1 END)"
				+ "FROM BILL_PENALTY_ADJ WHERE CONNECTION_NO = '"+connection_no+"' AND ADJ_TYPE = 'BRDCR' AND APPROVE_STATUS NOT IN(2) ";
		try{
		return (Object[]) getCustomEntityManager().createNativeQuery(sql).getSingleResult();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getPendingBrdCrConnectionsToApprove(int approve_status,String adj_type) {
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getPendingConnectionsToApprove2").setParameter("approve_status", approve_status).setParameter("adj_type", adj_type).getResultList();
	}

	@Override
	public BillPenaltyAdjustment getPendingBoardCrByConNo(String string, int approve_status) {
		try{
			return 	getCustomEntityManager().createNamedQuery("BillPenaltyAdjustment.getPendingBoardCrByConNo",BillPenaltyAdjustment.class).setParameter("connection_no", string).setParameter("approve_status", approve_status).getSingleResult();
			}catch(Exception e){
			e.printStackTrace();
			return null;
			}
	}

}
