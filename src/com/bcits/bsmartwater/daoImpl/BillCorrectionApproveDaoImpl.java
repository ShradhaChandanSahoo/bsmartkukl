package com.bcits.bsmartwater.daoImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.BillCorrectionApproveDao;
import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;

@Repository
public class BillCorrectionApproveDaoImpl extends GenericDAOImpl<BillApproveEntity> implements BillCorrectionApproveDao{

	
	@Autowired
	private BillingLedgerDao billingLedgerDao;
	
	@Override
	public List<?> getBillPendingApproval() {
		try{
		return getCustomEntityManager().createNamedQuery("BillApproveEntity.getBillPendingApproval").getResultList();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setBillApprove(int billId,int billStatus) {
		getCustomEntityManager().createNamedQuery("BillApproveEntity.setBillApprove").setParameter("bill_app_status", billStatus).setParameter("billId", billId).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillApproveEntity> findByConnectionNo(String connectionNo) {
		return getCustomEntityManager().createNamedQuery("BillApproveEntity.findByConnectionNo").setParameter("connection_no", connectionNo).getResultList();
	}

	@Override
	public long findCountByConNoBillStatus(String connectionNo, int bill_app_status) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillApproveEntity.findCountByConNoBillStatus").setParameter("connection_no", connectionNo).setParameter("bill_app_status", bill_app_status).getSingleResult();
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public List<?> getBillCorrectionChangeList() {
		return getCustomEntityManager().createNamedQuery("BillApproveEntity.getBillCorrectionChangeList").getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateMasterDataDaily() {
		
		String testLocation="TESTLOC_2222";
		String tripurLocation="TRIPUR_1115";
		String baneshLocation="BANESH_1112";
		String lalitLocation="LALITPUR_1118";
		String maharLocation="MAHAR_1111";
		String bhaktLocation="BHAKTAPUR_1116";
		String chhetLocation="CHHETRA_1114";
		String thimitLocation="THIMI_1117";
		String kirtipurLocation="KIRTIPUR_1119";
		String kamaladiLocation="KAMALADI_1113";
		String mahankalLocation="MAHAN_1110";
		
		
		//TEST LOCATION START
		
		String testLedWard="UPDATE "+testLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+testLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+testLocation+".BSW_LEDGER)";
		String testLedRD="UPDATE "+testLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+testLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+testLocation+".BSW_LEDGER)";
		String testLedPS="UPDATE "+testLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+testLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+testLocation+".BSW_LEDGER)";
		
		String testLedPCC="UPDATE "+testLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+testLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+testLocation+".BSW_LEDGER)";
		String testLedPCT="UPDATE "+testLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+testLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+testLocation+".BSW_LEDGER)";

		
		String testLeddDBSA="UPDATE "+testLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String testLeddDBTHA="UPDATE "+testLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		String testMasDBSA="UPDATE "+testLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String testMasDBTHA="UPDATE "+testLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		String testPayDBSA="UPDATE "+testLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String testPayDBTHA="UPDATE "+testLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		
		String mr5="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
		String mr17="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
		String mr33="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
		String mr67="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
		String mr133="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
		String mr250="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
		String mr500="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
		String mr0="UPDATE "+testLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
		
		try{
		
		BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED TEST LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
	    int ctestLedWard = entityManager.createNativeQuery(testLedWard).executeUpdate();
	    int ctestLedRD =   entityManager.createNativeQuery(testLedRD).executeUpdate();
	    int ctestLedPS =   entityManager.createNativeQuery(testLedPS).executeUpdate();
	    int ctestLeddDBSA= entityManager.createNativeQuery(testLeddDBSA).executeUpdate();
	    int ctestLeddDBTHA=entityManager.createNativeQuery(testLeddDBTHA).executeUpdate();
	    int ctestMasDBSA = entityManager.createNativeQuery(testMasDBSA).executeUpdate();
	    int ctestMasDBTHA =entityManager.createNativeQuery(testMasDBTHA).executeUpdate();
	    int ctestPayDBSA = entityManager.createNativeQuery(testPayDBSA).executeUpdate();
	    int ctestPayDBTHA =entityManager.createNativeQuery(testPayDBTHA).executeUpdate();
	    
	    entityManager.createNativeQuery(testLedPCC).executeUpdate();
	    entityManager.createNativeQuery(testLedPCT).executeUpdate();
	    
	    //DENOTED BY 
	    String bswDBYSA="UPDATE "+testLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
		String bswDBYTHA="UPDATE "+testLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
		String bswLDBYSA="UPDATE "+testLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
		String bswLDBYTHA="UPDATE "+testLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
		String bswPDBYSA="UPDATE "+testLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
		String bswDPBYTHA="UPDATE "+testLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
	    
	    entityManager.createNativeQuery(bswDBYSA).executeUpdate();
	    entityManager.createNativeQuery(bswDBYTHA).executeUpdate();
	    entityManager.createNativeQuery(bswLDBYSA).executeUpdate();
	    entityManager.createNativeQuery(bswLDBYTHA).executeUpdate();
	    entityManager.createNativeQuery(bswPDBYSA).executeUpdate();
	    entityManager.createNativeQuery(bswDPBYTHA).executeUpdate();
	    //End DENOTED BY
	    
	    
	    int cmr5 =entityManager.createNativeQuery(mr5).executeUpdate();
	    int cmr17 =entityManager.createNativeQuery(mr17).executeUpdate();
	    int cmr33 =entityManager.createNativeQuery(mr33).executeUpdate();
	    int cmr67 =entityManager.createNativeQuery(mr67).executeUpdate();
	    int cmr133 =entityManager.createNativeQuery(mr133).executeUpdate();
	    int cmr250=entityManager.createNativeQuery(mr250).executeUpdate();
	    int cmr500 =entityManager.createNativeQuery(mr500).executeUpdate();
	    int cmr0 =entityManager.createNativeQuery(mr0).executeUpdate();
	    
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestLedWard);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestLedRD);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestLedPS);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestLeddDBSA);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestLeddDBTHA);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestMasDBSA);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestMasDBTHA);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestPayDBSA);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctestPayDBTHA);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr5);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr17);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr33);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr67);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr133);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr250);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr500);
	    BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmr0);
	    BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED TEST LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
	    
	    //TEST LOCATION END
		}catch(Exception e){
			e.printStackTrace();
		}
	    
       //CHHETRAPATI LOCATION START
	
  		String chhetLedWard="UPDATE "+chhetLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+chhetLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+chhetLocation+".BSW_LEDGER)";
  		String chhetLedRD="UPDATE "+chhetLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+chhetLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+chhetLocation+".BSW_LEDGER)";
  		String chhetLedPS="UPDATE "+chhetLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+chhetLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+chhetLocation+".BSW_LEDGER)";
  		
  		String chhetLedPCC="UPDATE "+chhetLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+chhetLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+chhetLocation+".BSW_LEDGER)";
		String chhetLedPCT="UPDATE "+chhetLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+chhetLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+chhetLocation+".BSW_LEDGER)";

  		
  		String chhetLeddDBSA="UPDATE "+chhetLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String chhetLeddDBTHA="UPDATE "+chhetLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String chhetMasDBSA="UPDATE "+chhetLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String chhetMasDBTHA="UPDATE "+chhetLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String chhetPayDBSA="UPDATE "+chhetLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String chhetPayDBTHA="UPDATE "+chhetLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrc5="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrc17="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrc33="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrc67="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrc133="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrc250="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrc500="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrc0="UPDATE "+chhetLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED CHHETRAPATI LOCATION >>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int cchhetLedWard = entityManager.createNativeQuery(chhetLedWard).executeUpdate();
  			int cchhetLedRD =   entityManager.createNativeQuery(chhetLedRD).executeUpdate();
  			int cchhetLedPS =   entityManager.createNativeQuery(chhetLedPS).executeUpdate();
  			int cchhetLeddDBSA= entityManager.createNativeQuery(chhetLeddDBSA).executeUpdate();
  			int cchhetLeddDBTHA=entityManager.createNativeQuery(chhetLeddDBTHA).executeUpdate();
  			int cchhetMasDBSA = entityManager.createNativeQuery(chhetMasDBSA).executeUpdate();
  			int cchhetMasDBTHA =entityManager.createNativeQuery(chhetMasDBTHA).executeUpdate();
  			int cchhetPayDBSA = entityManager.createNativeQuery(chhetPayDBSA).executeUpdate();
  			int cchhetPayDBTHA =entityManager.createNativeQuery(chhetPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(chhetLedPCC).executeUpdate();
  			entityManager.createNativeQuery(chhetLedPCT).executeUpdate();

  			int cmrc5 =entityManager.createNativeQuery(mrc5).executeUpdate();
  			int cmrc17 =entityManager.createNativeQuery(mrc17).executeUpdate();
  			int cmrc33 =entityManager.createNativeQuery(mrc33).executeUpdate();
  			int cmrc67 =entityManager.createNativeQuery(mrc67).executeUpdate();
  			int cmrc133 =entityManager.createNativeQuery(mrc133).executeUpdate();
  			int cmrc250=entityManager.createNativeQuery(mrc250).executeUpdate();
  			int cmrc500 =entityManager.createNativeQuery(mrc500).executeUpdate();
  			int cmrc0 =entityManager.createNativeQuery(mrc0).executeUpdate();

  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cchhetPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrc0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrc0);

  			//DENOTED BY 
  			String cbswDBYSA="UPDATE "+chhetLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String cbswDBYTHA="UPDATE "+chhetLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String cbswLDBYSA="UPDATE "+chhetLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String cbswLDBYTHA="UPDATE "+chhetLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String cbswPDBYSA="UPDATE "+chhetLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String cbswDPBYTHA="UPDATE "+chhetLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(cbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(cbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(cbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(cbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(cbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(cbswDPBYTHA).executeUpdate();
  			//End DENOTED BY


  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED CHHETRAPATI LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

  			//CHHETRAPATI LOCATION END

  		}catch(Exception e){
			e.printStackTrace();
		}
       //TRIPURESHWOR LOCATION START
	
		String tripurLedWard="UPDATE "+tripurLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+tripurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+tripurLocation+".BSW_LEDGER)";
		String tripurLedRD="UPDATE "+tripurLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+tripurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+tripurLocation+".BSW_LEDGER)";
		String tripurLedPS="UPDATE "+tripurLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+tripurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+tripurLocation+".BSW_LEDGER)";
		
		String tripurLedPCC="UPDATE "+tripurLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+tripurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+tripurLocation+".BSW_LEDGER)";
		String tripurLedPCT="UPDATE "+tripurLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+tripurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+tripurLocation+".BSW_LEDGER)";

		
		String tripurLeddDBSA="UPDATE "+tripurLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String tripurLeddDBTHA="UPDATE "+tripurLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		String tripurMasDBSA="UPDATE "+tripurLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String tripurMasDBTHA="UPDATE "+tripurLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		String tripurPayDBSA="UPDATE "+tripurLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String tripurPayDBTHA="UPDATE "+tripurLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		
		String mrtr5="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
		String mrtr17="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
		String mrtr33="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
		String mrtr67="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
		String mrtr133="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
		String mrtr250="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
		String mrtr500="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
		String mrtr0="UPDATE "+tripurLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
		
		try{
			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED TRIPURESHWOR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
			int ctripurLedWard = entityManager.createNativeQuery(tripurLedWard).executeUpdate();
			int ctripurLedRD =   entityManager.createNativeQuery(tripurLedRD).executeUpdate();
			int ctripurLedPS =   entityManager.createNativeQuery(tripurLedPS).executeUpdate();
			int ctripurLeddDBSA= entityManager.createNativeQuery(tripurLeddDBSA).executeUpdate();
			int ctripurLeddDBTHA=entityManager.createNativeQuery(tripurLeddDBTHA).executeUpdate();
			int ctripurMasDBSA = entityManager.createNativeQuery(tripurMasDBSA).executeUpdate();
			int ctripurMasDBTHA =entityManager.createNativeQuery(tripurMasDBTHA).executeUpdate();
			int ctripurPayDBSA = entityManager.createNativeQuery(tripurPayDBSA).executeUpdate();
			int ctripurPayDBTHA =entityManager.createNativeQuery(tripurPayDBTHA).executeUpdate();

			entityManager.createNativeQuery(tripurLedPCC).executeUpdate();
			entityManager.createNativeQuery(tripurLedPCT).executeUpdate();


			int cmrtr5 =entityManager.createNativeQuery(mrtr5).executeUpdate();
			int cmrtr17 =entityManager.createNativeQuery(mrtr17).executeUpdate();
			int cmrtr33 =entityManager.createNativeQuery(mrtr33).executeUpdate();
			int cmrtr67 =entityManager.createNativeQuery(mrtr67).executeUpdate();
			int cmrtr133 =entityManager.createNativeQuery(mrtr133).executeUpdate();
			int cmrtr250=entityManager.createNativeQuery(mrtr250).executeUpdate();
			int cmrtr500 =entityManager.createNativeQuery(mrtr500).executeUpdate();
			int cmrtr0 =entityManager.createNativeQuery(mrtr0).executeUpdate();

			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurLedWard);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurLedRD);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurLedPS);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurLeddDBSA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurLeddDBTHA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurMasDBSA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurMasDBTHA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurPayDBSA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ctripurPayDBTHA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr5);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr17);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr33);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr67);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr133);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr250);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr500);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrtr0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrtr0);

			//DENOTED BY 
			String tbswDBYSA="UPDATE "+tripurLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
			String tbswDBYTHA="UPDATE "+tripurLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
			String tbswLDBYSA="UPDATE "+tripurLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
			String tbswLDBYTHA="UPDATE "+tripurLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
			String tbswPDBYSA="UPDATE "+tripurLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
			String tbswDPBYTHA="UPDATE "+tripurLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

			entityManager.createNativeQuery(tbswDBYSA).executeUpdate();
			entityManager.createNativeQuery(tbswDBYTHA).executeUpdate();
			entityManager.createNativeQuery(tbswLDBYSA).executeUpdate();
			entityManager.createNativeQuery(tbswLDBYTHA).executeUpdate();
			entityManager.createNativeQuery(tbswPDBYSA).executeUpdate();
			entityManager.createNativeQuery(tbswDPBYTHA).executeUpdate();
			//End DENOTED BY


			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED TRIPURESHWOR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

			//TRIPURESHWOR LOCATION END

		}catch(Exception e){
			e.printStackTrace();
		}
	    
	   //BANESHWOR LOCATION START
		
		String baneshLedWard="UPDATE "+baneshLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+baneshLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+baneshLocation+".BSW_LEDGER)";
		String baneshLedRD="UPDATE "+baneshLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+baneshLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+baneshLocation+".BSW_LEDGER)";
		String baneshLedPS="UPDATE "+baneshLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+baneshLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+baneshLocation+".BSW_LEDGER)";
		
		String baneshLedPCC="UPDATE "+baneshLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+baneshLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+baneshLocation+".BSW_LEDGER)";
		String baneshLedPCT="UPDATE "+baneshLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+baneshLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+baneshLocation+".BSW_LEDGER)";

		
		
		String baneshLeddDBSA="UPDATE "+baneshLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String baneshLeddDBTHA="UPDATE "+baneshLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		String baneshMasDBSA="UPDATE "+baneshLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String baneshMasDBTHA="UPDATE "+baneshLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		String baneshPayDBSA="UPDATE "+baneshLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
		String baneshPayDBTHA="UPDATE "+baneshLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
		
		String mrbn5="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
		String mrbn17="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
		String mrbn33="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
		String mrbn67="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
		String mrbn133="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
		String mrbn250="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
		String mrbn500="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
		String mrbn0="UPDATE "+baneshLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
		
		try{
			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED BANESHWOR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
			int cbaneshLedWard = entityManager.createNativeQuery(baneshLedWard).executeUpdate();
			int cbaneshLedRD =   entityManager.createNativeQuery(baneshLedRD).executeUpdate();
			int cbaneshLedPS =   entityManager.createNativeQuery(baneshLedPS).executeUpdate();
			int cbaneshLeddDBSA= entityManager.createNativeQuery(baneshLeddDBSA).executeUpdate();
			int cbaneshLeddDBTHA=entityManager.createNativeQuery(baneshLeddDBTHA).executeUpdate();
			int cbaneshMasDBSA = entityManager.createNativeQuery(baneshMasDBSA).executeUpdate();
			int cbaneshMasDBTHA =entityManager.createNativeQuery(baneshMasDBTHA).executeUpdate();
			int cbaneshPayDBSA = entityManager.createNativeQuery(baneshPayDBSA).executeUpdate();
			int cbaneshPayDBTHA =entityManager.createNativeQuery(baneshPayDBTHA).executeUpdate();

			entityManager.createNativeQuery(baneshLedPCC).executeUpdate();
			entityManager.createNativeQuery(baneshLedPCT).executeUpdate();


			int cmrbn5 =entityManager.createNativeQuery(mrbn5).executeUpdate();
			int cmrbn17 =entityManager.createNativeQuery(mrbn17).executeUpdate();
			int cmrbn33 =entityManager.createNativeQuery(mrbn33).executeUpdate();
			int cmrbn67 =entityManager.createNativeQuery(mrbn67).executeUpdate();
			int cmrbn133 =entityManager.createNativeQuery(mrbn133).executeUpdate();
			int cmrbn250=entityManager.createNativeQuery(mrbn250).executeUpdate();
			int cmrbn500 =entityManager.createNativeQuery(mrbn500).executeUpdate();
			int cmrbn0 =entityManager.createNativeQuery(mrbn0).executeUpdate();
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshLedWard);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshLedRD);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshLedPS);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshLeddDBSA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshLeddDBTHA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshMasDBSA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshMasDBTHA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshPayDBSA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbaneshPayDBTHA);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn5);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn17);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn33);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn67);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn133);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn250);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn500);
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbn0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbn0);

			//DENOTED BY 
			String bbswDBYSA="UPDATE "+baneshLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
			String bbswDBYTHA="UPDATE "+baneshLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
			String bbswLDBYSA="UPDATE "+baneshLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
			String bbswLDBYTHA="UPDATE "+baneshLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
			String bbswPDBYSA="UPDATE "+baneshLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
			String bbswDPBYTHA="UPDATE "+baneshLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

			entityManager.createNativeQuery(bbswDBYSA).executeUpdate();
			entityManager.createNativeQuery(bbswDBYTHA).executeUpdate();
			entityManager.createNativeQuery(bbswLDBYSA).executeUpdate();
			entityManager.createNativeQuery(bbswLDBYTHA).executeUpdate();
			entityManager.createNativeQuery(bbswPDBYSA).executeUpdate();
			entityManager.createNativeQuery(bbswDPBYTHA).executeUpdate();
			//End DENOTED BY


			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED BANESHWOR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

			//BANESHWOR LOCATION END

		}catch(Exception e){
			e.printStackTrace();
		}
	   //LALITPUR LOCATION START
		
  		String lalitLedWard="UPDATE "+lalitLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+lalitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+lalitLocation+".BSW_LEDGER)";
  		String lalitLedRD="UPDATE "+lalitLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+lalitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+lalitLocation+".BSW_LEDGER)";
  		String lalitLedPS="UPDATE "+lalitLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+lalitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+lalitLocation+".BSW_LEDGER)";
  		
  		String lalitLedPCC="UPDATE "+lalitLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+lalitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+lalitLocation+".BSW_LEDGER)";
		String lalitLedPCT="UPDATE "+lalitLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+lalitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+lalitLocation+".BSW_LEDGER)";

  		
  		String lalitLeddDBSA="UPDATE "+lalitLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String lalitLeddDBTHA="UPDATE "+lalitLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String lalitMasDBSA="UPDATE "+lalitLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String lalitMasDBTHA="UPDATE "+lalitLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String lalitPayDBSA="UPDATE "+lalitLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String lalitPayDBTHA="UPDATE "+lalitLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrla5="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrla17="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrla33="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrla67="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrla133="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrla250="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrla500="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrla0="UPDATE "+lalitLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED LALITPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int clalitLedWard = entityManager.createNativeQuery(lalitLedWard).executeUpdate();
  			int clalitLedRD =   entityManager.createNativeQuery(lalitLedRD).executeUpdate();
  			int clalitLedPS =   entityManager.createNativeQuery(lalitLedPS).executeUpdate();
  			int clalitLeddDBSA= entityManager.createNativeQuery(lalitLeddDBSA).executeUpdate();
  			int clalitLeddDBTHA=entityManager.createNativeQuery(lalitLeddDBTHA).executeUpdate();
  			int clalitMasDBSA = entityManager.createNativeQuery(lalitMasDBSA).executeUpdate();
  			int clalitMasDBTHA =entityManager.createNativeQuery(lalitMasDBTHA).executeUpdate();
  			int clalitPayDBSA = entityManager.createNativeQuery(lalitPayDBSA).executeUpdate();
  			int clalitPayDBTHA =entityManager.createNativeQuery(lalitPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(lalitLedPCC).executeUpdate();
  			entityManager.createNativeQuery(lalitLedPCT).executeUpdate();


  			int cmrla5 =entityManager.createNativeQuery(mrla5).executeUpdate();
  			int cmrla17 =entityManager.createNativeQuery(mrla17).executeUpdate();
  			int cmrla33 =entityManager.createNativeQuery(mrla33).executeUpdate();
  			int cmrla67 =entityManager.createNativeQuery(mrla67).executeUpdate();
  			int cmrla133 =entityManager.createNativeQuery(mrla133).executeUpdate();
  			int cmrla250=entityManager.createNativeQuery(mrla250).executeUpdate();
  			int cmrla500 =entityManager.createNativeQuery(mrla500).executeUpdate();
  			int cmrla0 =entityManager.createNativeQuery(mrla0).executeUpdate();
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+clalitPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrla0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrla0);

  			//DENOTED BY 
  			String lbswDBYSA="UPDATE "+lalitLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String lbswDBYTHA="UPDATE "+lalitLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String lbswLDBYSA="UPDATE "+lalitLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String lbswLDBYTHA="UPDATE "+lalitLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String lbswPDBYSA="UPDATE "+lalitLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String lbswDPBYTHA="UPDATE "+lalitLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(lbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(lbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(lbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(lbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(lbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(lbswDPBYTHA).executeUpdate();
  			//End DENOTED BY


  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED LALITPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  		}catch(Exception e){
			e.printStackTrace();
		}
  	    //LALITPUR LOCATION END
  	    
  	    //MAHARAJGUNG LOCATION START
  		String maharLedWard="UPDATE "+maharLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+maharLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+maharLocation+".BSW_LEDGER)";
  		String maharLedRD="UPDATE "+maharLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+maharLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+maharLocation+".BSW_LEDGER)";
  		String maharLedPS="UPDATE "+maharLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+maharLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+maharLocation+".BSW_LEDGER)";
  		
  		String maharLedPCC="UPDATE "+maharLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+maharLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+maharLocation+".BSW_LEDGER)";
		String maharLedPCT="UPDATE "+maharLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+maharLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+maharLocation+".BSW_LEDGER)";

  		
  		String maharLeddDBSA="UPDATE "+maharLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String maharLeddDBTHA="UPDATE "+maharLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String maharMasDBSA="UPDATE "+maharLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String maharMasDBTHA="UPDATE "+maharLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String maharPayDBSA="UPDATE "+maharLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String maharPayDBTHA="UPDATE "+maharLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrma5="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrma17="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrma33="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrma67="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrma133="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrma250="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrma500="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrma0="UPDATE "+maharLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED MAHARAJGUNG LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int cmaharLedWard = entityManager.createNativeQuery(maharLedWard).executeUpdate();
  			int cmaharLedRD =   entityManager.createNativeQuery(maharLedRD).executeUpdate();
  			int cmaharLedPS =   entityManager.createNativeQuery(maharLedPS).executeUpdate();
  			int cmaharLeddDBSA= entityManager.createNativeQuery(maharLeddDBSA).executeUpdate();
  			int cmaharLeddDBTHA=entityManager.createNativeQuery(maharLeddDBTHA).executeUpdate();
  			int cmaharMasDBSA = entityManager.createNativeQuery(maharMasDBSA).executeUpdate();
  			int cmaharMasDBTHA =entityManager.createNativeQuery(maharMasDBTHA).executeUpdate();
  			int cmaharPayDBSA = entityManager.createNativeQuery(maharPayDBSA).executeUpdate();
  			int cmaharPayDBTHA =entityManager.createNativeQuery(maharPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(maharLedPCC).executeUpdate();
  			entityManager.createNativeQuery(maharLedPCT).executeUpdate();


  			int cmrma5 =entityManager.createNativeQuery(mrma5).executeUpdate();
  			int cmrma17 =entityManager.createNativeQuery(mrma17).executeUpdate();
  			int cmrma33 =entityManager.createNativeQuery(mrma33).executeUpdate();
  			int cmrma67 =entityManager.createNativeQuery(mrma67).executeUpdate();
  			int cmrma133 =entityManager.createNativeQuery(mrma133).executeUpdate();
  			int cmrma250=entityManager.createNativeQuery(mrma250).executeUpdate();
  			int cmrma500 =entityManager.createNativeQuery(mrma500).executeUpdate();
  			int cmrma0 =entityManager.createNativeQuery(mrma0).executeUpdate();
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmaharPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrma0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrma0);

  			//DENOTED BY 
  			String mbswDBYSA="UPDATE "+maharLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String mbswDBYTHA="UPDATE "+maharLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String mbswLDBYSA="UPDATE "+maharLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String mbswLDBYTHA="UPDATE "+maharLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String mbswPDBYSA="UPDATE "+maharLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String mbswDPBYTHA="UPDATE "+maharLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(mbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(mbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(mbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(mbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(mbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(mbswDPBYTHA).executeUpdate();
  			//End DENOTED BY


  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED MAHARAJGUNG LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

  			//MAHARAJGUNG LOCATION END
  		}catch(Exception e){
			e.printStackTrace();
		}
  	    
  	    //BHAKTAPUR LOCATION START
  		String bhaktLedWard="UPDATE "+bhaktLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+bhaktLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+bhaktLocation+".BSW_LEDGER)";
  		String bhaktLedRD="UPDATE "+bhaktLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+bhaktLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+bhaktLocation+".BSW_LEDGER)";
  		String bhaktLedPS="UPDATE "+bhaktLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+bhaktLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+bhaktLocation+".BSW_LEDGER)";
  		
  		String bhakLedPCC="UPDATE "+bhaktLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+bhaktLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+bhaktLocation+".BSW_LEDGER)";
		String bhakLedPCT="UPDATE "+bhaktLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+bhaktLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+bhaktLocation+".BSW_LEDGER)";

  		
  		String bhaktLeddDBSA="UPDATE "+bhaktLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String bhaktLeddDBTHA="UPDATE "+bhaktLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String bhaktMasDBSA="UPDATE "+bhaktLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String bhaktMasDBTHA="UPDATE "+bhaktLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String bhaktPayDBSA="UPDATE "+bhaktLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String bhaktPayDBTHA="UPDATE "+bhaktLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrbha5="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrbha17="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrbha33="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrbha67="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrbha133="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrbha250="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrbha500="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrbha0="UPDATE "+bhaktLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED BHAKTAPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int cbhaktLedWard = entityManager.createNativeQuery(bhaktLedWard).executeUpdate();
  			int cbhaktLedRD =   entityManager.createNativeQuery(bhaktLedRD).executeUpdate();
  			int cbhaktLedPS =   entityManager.createNativeQuery(bhaktLedPS).executeUpdate();
  			int cbhaktLeddDBSA= entityManager.createNativeQuery(bhaktLeddDBSA).executeUpdate();
  			int cbhaktLeddDBTHA=entityManager.createNativeQuery(bhaktLeddDBTHA).executeUpdate();
  			int cbhaktMasDBSA = entityManager.createNativeQuery(bhaktMasDBSA).executeUpdate();
  			int cbhaktMasDBTHA =entityManager.createNativeQuery(bhaktMasDBTHA).executeUpdate();
  			int cbhaktPayDBSA = entityManager.createNativeQuery(bhaktPayDBSA).executeUpdate();
  			int cbhaktPayDBTHA =entityManager.createNativeQuery(bhaktPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(bhakLedPCC).executeUpdate();
  			entityManager.createNativeQuery(bhakLedPCT).executeUpdate();


  			int cmrbha5 =entityManager.createNativeQuery(mrbha5).executeUpdate();
  			int cmrbha17 =entityManager.createNativeQuery(mrbha17).executeUpdate();
  			int cmrbha33 =entityManager.createNativeQuery(mrbha33).executeUpdate();
  			int cmrbha67 =entityManager.createNativeQuery(mrbha67).executeUpdate();
  			int cmrbha133 =entityManager.createNativeQuery(mrbha133).executeUpdate();
  			int cmrbha250=entityManager.createNativeQuery(mrbha250).executeUpdate();
  			int cmrbha500 =entityManager.createNativeQuery(mrbha500).executeUpdate();
  			int cmrbha0 =entityManager.createNativeQuery(mrbha0).executeUpdate();
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cbhaktPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrbha0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrbha0);

  			//DENOTED BY 
  			String bhbswDBYSA="UPDATE "+bhaktLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String bhbswDBYTHA="UPDATE "+bhaktLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String bhbswLDBYSA="UPDATE "+bhaktLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String bhbswLDBYTHA="UPDATE "+bhaktLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String bhbswPDBYSA="UPDATE "+bhaktLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String bhbswDPBYTHA="UPDATE "+bhaktLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(bhbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(bhbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(bhbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(bhbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(bhbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(bhbswDPBYTHA).executeUpdate();
  			//End DENOTED BY

  		}catch(Exception e){
			e.printStackTrace();
		}
  	    BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED BHAKTAPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  	    
  	    //BHAKTAPUR LOCATION END
  	    
  	    
  	    
  	    //THIMI LOCATION START
  		String thimitLedWard="UPDATE "+thimitLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+thimitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+thimitLocation+".BSW_LEDGER)";
  		String thimitLedRD="UPDATE "+thimitLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+thimitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+thimitLocation+".BSW_LEDGER)";
  		String thimitLedPS="UPDATE "+thimitLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+thimitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+thimitLocation+".BSW_LEDGER)";
  		
  		String thimiLedPCC="UPDATE "+thimitLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+thimitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+thimitLocation+".BSW_LEDGER)";
		String thimiLedPCT="UPDATE "+thimitLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+thimitLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+thimitLocation+".BSW_LEDGER)";

  		String thimitLeddDBSA="UPDATE "+thimitLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String thimitLeddDBTHA="UPDATE "+thimitLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String thimitMasDBSA="UPDATE "+thimitLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String thimitMasDBTHA="UPDATE "+thimitLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String thimitPayDBSA="UPDATE "+thimitLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String thimitPayDBTHA="UPDATE "+thimitLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrthimi5="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrthimi17="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrthimi33="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrthimi67="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrthimi133="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrthimi250="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrthimi500="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrthimi0="UPDATE "+thimitLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED thimiTAPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int cthimitLedWard = entityManager.createNativeQuery(thimitLedWard).executeUpdate();
  			int cthimitLedRD =   entityManager.createNativeQuery(thimitLedRD).executeUpdate();
  			int cthimitLedPS =   entityManager.createNativeQuery(thimitLedPS).executeUpdate();
  			int cthimitLeddDBSA= entityManager.createNativeQuery(thimitLeddDBSA).executeUpdate();
  			int cthimitLeddDBTHA=entityManager.createNativeQuery(thimitLeddDBTHA).executeUpdate();
  			int cthimitMasDBSA = entityManager.createNativeQuery(thimitMasDBSA).executeUpdate();
  			int cthimitMasDBTHA =entityManager.createNativeQuery(thimitMasDBTHA).executeUpdate();
  			int cthimitPayDBSA = entityManager.createNativeQuery(thimitPayDBSA).executeUpdate();
  			int cthimitPayDBTHA =entityManager.createNativeQuery(thimitPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(thimiLedPCC).executeUpdate();
  			entityManager.createNativeQuery(thimiLedPCT).executeUpdate();


  			int cmrthi5 =entityManager.createNativeQuery(mrthimi5).executeUpdate();
  			int cmrthi17 =entityManager.createNativeQuery(mrthimi17).executeUpdate();
  			int cmrthi33 =entityManager.createNativeQuery(mrthimi33).executeUpdate();
  			int cmrthi67 =entityManager.createNativeQuery(mrthimi67).executeUpdate();
  			int cmrthi133 =entityManager.createNativeQuery(mrthimi133).executeUpdate();
  			int cmrthi250=entityManager.createNativeQuery(mrthimi250).executeUpdate();
  			int cmrthi500 =entityManager.createNativeQuery(mrthimi500).executeUpdate();
  			int cmrthi0 =entityManager.createNativeQuery(mrthimi0).executeUpdate();
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cthimitPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrthimi0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrthi0);

  			//DENOTED BY 
  			String thbswDBYSA="UPDATE "+thimitLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String thbswDBYTHA="UPDATE "+thimitLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String thbswLDBYSA="UPDATE "+thimitLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String thbswLDBYTHA="UPDATE "+thimitLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String thbswPDBYSA="UPDATE "+thimitLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String thbswDPBYTHA="UPDATE "+thimitLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(thbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(thbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(thbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(thbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(thbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(thbswDPBYTHA).executeUpdate();
  			//End DENOTED BY


  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED BHAKTAPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

  			//THIMI LOCATION END

  		}catch(Exception e){
			e.printStackTrace();
		}
  	    
  	    

  	    //KIRTIPUR LOCATION START
  		String kirtipurLedWard="UPDATE "+kirtipurLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+kirtipurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kirtipurLocation+".BSW_LEDGER)";
  		String kirtipurLedRD="UPDATE "+kirtipurLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+kirtipurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kirtipurLocation+".BSW_LEDGER)";
  		String kirtipurLedPS="UPDATE "+kirtipurLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+kirtipurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kirtipurLocation+".BSW_LEDGER)";
  		
  		String kirtipurLedPCC="UPDATE "+kirtipurLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+kirtipurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kirtipurLocation+".BSW_LEDGER)";
		String kirtipurLedPCT="UPDATE "+kirtipurLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+kirtipurLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kirtipurLocation+".BSW_LEDGER)";

  		
  		String kirtipurLeddDBSA="UPDATE "+kirtipurLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String kirtipurLeddDBTHA="UPDATE "+kirtipurLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String kirtipurMasDBSA="UPDATE "+kirtipurLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String kirtipurMasDBTHA="UPDATE "+kirtipurLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String kirtipurPayDBSA="UPDATE "+kirtipurLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String kirtipurPayDBTHA="UPDATE "+kirtipurLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrkirtipur5="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrkirtipur17="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrkirtipur33="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrkirtipur67="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrkirtipur133="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrkirtipur250="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrkirtipur500="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrkirtipur0="UPDATE "+kirtipurLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED kirtipurAPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int ckirtipurLedWard = entityManager.createNativeQuery(kirtipurLedWard).executeUpdate();
  			int ckirtipurLedRD =   entityManager.createNativeQuery(kirtipurLedRD).executeUpdate();
  			int ckirtipurLedPS =   entityManager.createNativeQuery(kirtipurLedPS).executeUpdate();
  			int ckirtipurLeddDBSA= entityManager.createNativeQuery(kirtipurLeddDBSA).executeUpdate();
  			int ckirtipurLeddDBTHA=entityManager.createNativeQuery(kirtipurLeddDBTHA).executeUpdate();
  			int ckirtipurMasDBSA = entityManager.createNativeQuery(kirtipurMasDBSA).executeUpdate();
  			int ckirtipurMasDBTHA =entityManager.createNativeQuery(kirtipurMasDBTHA).executeUpdate();
  			int ckirtipurPayDBSA = entityManager.createNativeQuery(kirtipurPayDBSA).executeUpdate();
  			int ckirtipurPayDBTHA =entityManager.createNativeQuery(kirtipurPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(kirtipurLedPCC).executeUpdate();
  			entityManager.createNativeQuery(kirtipurLedPCT).executeUpdate();


  			int cmrkir5 =entityManager.createNativeQuery(mrkirtipur5).executeUpdate();
  			int cmrkir17 =entityManager.createNativeQuery(mrkirtipur17).executeUpdate();
  			int cmrkir33 =entityManager.createNativeQuery(mrkirtipur33).executeUpdate();
  			int cmrkir67 =entityManager.createNativeQuery(mrkirtipur67).executeUpdate();
  			int cmrkir133 =entityManager.createNativeQuery(mrkirtipur133).executeUpdate();
  			int cmrkir250=entityManager.createNativeQuery(mrkirtipur250).executeUpdate();
  			int cmrkir500 =entityManager.createNativeQuery(mrkirtipur500).executeUpdate();
  			int cmrkir0 =entityManager.createNativeQuery(mrkirtipur0).executeUpdate();
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckirtipurPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmrkir0);

  			//DENOTED BY 
  			String kirbswDBYSA="UPDATE "+kirtipurLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String kirbswDBYTHA="UPDATE "+kirtipurLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String kirbswLDBYSA="UPDATE "+kirtipurLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String kirbswLDBYTHA="UPDATE "+kirtipurLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String kirbswPDBYSA="UPDATE "+kirtipurLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String kirbswDPBYTHA="UPDATE "+kirtipurLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(kirbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(kirbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(kirbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(kirbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(kirbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(kirbswDPBYTHA).executeUpdate();
  			//End DENOTED BY

  		}catch(Exception e){
			e.printStackTrace();
		}
  	    BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED KIRTIPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  	    
  	    //KIRTIPUR LOCATION END
    
		
  	    
  	    
  	    
  	    //KAMALADI LOCATION START
  		String kamalLedWard="UPDATE "+kamaladiLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+kamaladiLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kamaladiLocation+".BSW_LEDGER)";
  		String kamalLedRD="UPDATE "+kamaladiLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+kamaladiLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kamaladiLocation+".BSW_LEDGER)";
  		String kamalLedPS="UPDATE "+kamaladiLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+kamaladiLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kamaladiLocation+".BSW_LEDGER)";
  		
  		String kamalLedPCC="UPDATE "+kamaladiLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+kamaladiLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kamaladiLocation+".BSW_LEDGER)";
		String kamalLedPCT="UPDATE "+kamaladiLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+kamaladiLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+kamaladiLocation+".BSW_LEDGER)";
  		
  		String kamalLeddDBSA="UPDATE "+kamaladiLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String kamalLeddDBTHA="UPDATE "+kamaladiLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String kamalMasDBSA="UPDATE "+kamaladiLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String kamalMasDBTHA="UPDATE "+kamaladiLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String kamalPayDBSA="UPDATE "+kamaladiLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String kamalPayDBTHA="UPDATE "+kamaladiLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrkamal5="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrkamal17="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrkamal33="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrkamal67="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrkamal133="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrkamal250="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrkamal500="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrkamal0="UPDATE "+kamaladiLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED KAMALADI LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int ckamalLedWard = entityManager.createNativeQuery(kamalLedWard).executeUpdate();
  			int ckamalLedRD =   entityManager.createNativeQuery(kamalLedRD).executeUpdate();
  			int ckamalLedPS =   entityManager.createNativeQuery(kamalLedPS).executeUpdate();
  			int ckamalLeddDBSA= entityManager.createNativeQuery(kamalLeddDBSA).executeUpdate();
  			int ckamalLeddDBTHA=entityManager.createNativeQuery(kamalLeddDBTHA).executeUpdate();
  			int ckamalMasDBSA = entityManager.createNativeQuery(kamalMasDBSA).executeUpdate();
  			int ckamalMasDBTHA =entityManager.createNativeQuery(kamalMasDBTHA).executeUpdate();
  			int ckamalPayDBSA = entityManager.createNativeQuery(kamalPayDBSA).executeUpdate();
  			int ckamalPayDBTHA =entityManager.createNativeQuery(kamalPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(kamalLedPCC).executeUpdate();
  			entityManager.createNativeQuery(kamalLedPCT).executeUpdate();

  			int ckamal5 =entityManager.createNativeQuery(mrkamal5).executeUpdate();
  			int ckamal17 =entityManager.createNativeQuery(mrkamal17).executeUpdate();
  			int ckamal33 =entityManager.createNativeQuery(mrkamal33).executeUpdate();
  			int ckamal67 =entityManager.createNativeQuery(mrkamal67).executeUpdate();
  			int ckamal133 =entityManager.createNativeQuery(mrkamal133).executeUpdate();
  			int ckamal250=entityManager.createNativeQuery(mrkamal250).executeUpdate();
  			int ckamal500 =entityManager.createNativeQuery(mrkamal500).executeUpdate();
  			int ckamal0 =entityManager.createNativeQuery(mrkamal0).executeUpdate();

  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamalPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ckamal0);

  			//DENOTED BY 
  			String kamalbswDBYSA="UPDATE "+kamaladiLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String kamalbswDBYTHA="UPDATE "+kamaladiLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String kamalbswLDBYSA="UPDATE "+kamaladiLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String kamalbswLDBYTHA="UPDATE "+kamaladiLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String kamalbswPDBYSA="UPDATE "+kamaladiLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String kamalbswDPBYTHA="UPDATE "+kamaladiLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(kamalbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(kamalbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(kamalbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(kamalbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(kamalbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(kamalbswDPBYTHA).executeUpdate();
  			//End DENOTED BY


  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED KAMALADI LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

  			//KAMALADI LOCATION END
  		}catch(Exception e){
			e.printStackTrace();
		}
  	    
  	    //MAHANKAL LOCATION START
  		String mahankalLedWard="UPDATE "+mahankalLocation+".BSW_LEDGER c SET c.WARDNO =(SELECT k.WARD_NO from "+mahankalLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO) WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+mahankalLocation+".BSW_LEDGER)";
  		String mahankalLedRD="UPDATE "+mahankalLocation+".BSW_LEDGER c SET c.READING_DAY =(SELECT k.READING_DAY from "+mahankalLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+mahankalLocation+".BSW_LEDGER)";
  		String mahankalLedPS="UPDATE "+mahankalLocation+".BSW_LEDGER c SET c.PIPE_SIZE =(SELECT k.PIPE_SIZE from "+mahankalLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+mahankalLocation+".BSW_LEDGER)";
  		
  		String mahanLedPCC="UPDATE "+mahankalLocation+".BSW_LEDGER c SET c.CON_CATEGORY =(SELECT k.CON_CATEGORY from "+mahankalLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+mahankalLocation+".BSW_LEDGER)";
		String mahanLedPCT="UPDATE "+mahankalLocation+".BSW_LEDGER c SET c.CON_TYPE =(SELECT k.CON_TYPE from "+mahankalLocation+".BSW_MASTER k WHERE k.CONNECTION_NO=c.CONNECTION_NO)  WHERE c.MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+mahankalLocation+".BSW_LEDGER)";
  		
  		
  		String mahankalLeddDBSA="UPDATE "+mahankalLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String mahankalLeddDBTHA="UPDATE "+mahankalLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String mahankalMasDBSA="UPDATE "+mahankalLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String mahankalMasDBTHA="UPDATE "+mahankalLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		String mahankalPayDBSA="UPDATE "+mahankalLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY IS NULL";
  		String mahankalPayDBTHA="UPDATE "+mahankalLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY IS NULL";
  		
  		String mrmahankal5="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=5 WHERE PIPE_SIZE=0.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>5";
  		String mrmahankal17="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=17 WHERE PIPE_SIZE=0.75 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>17";
  		String mrmahankal33="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=33 WHERE PIPE_SIZE=1 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>33";
  		String mrmahankal67="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=67 WHERE PIPE_SIZE=1.5 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>67";
  		String mrmahankal133="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=133 WHERE PIPE_SIZE=2 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>133";
  		String mrmahankal250="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=250 WHERE PIPE_SIZE=3 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>250";
  		String mrmahankal500="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=500 WHERE PIPE_SIZE=4 AND CON_TYPE='Metered' AND METER_IN_HIRE='Yes' AND NVL(METER_RENT,0)<>500";
  		String mrmahankal0="UPDATE "+mahankalLocation+".BSW_MASTER SET METER_RENT=0 WHERE CON_TYPE='Metered' AND METER_IN_HIRE='No' AND NVL(METER_RENT,0)<>0";
  		
  		try{
  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>START UPDATED mahankalAPUR LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");
  			int cmahankalLedWard = entityManager.createNativeQuery(mahankalLedWard).executeUpdate();
  			int cmahankalLedRD =   entityManager.createNativeQuery(mahankalLedRD).executeUpdate();
  			int cmahankalLedPS =   entityManager.createNativeQuery(mahankalLedPS).executeUpdate();
  			int cmahankalLeddDBSA= entityManager.createNativeQuery(mahankalLeddDBSA).executeUpdate();
  			int cmahankalLeddDBTHA=entityManager.createNativeQuery(mahankalLeddDBTHA).executeUpdate();
  			int cmahankalMasDBSA = entityManager.createNativeQuery(mahankalMasDBSA).executeUpdate();
  			int cmahankalMasDBTHA =entityManager.createNativeQuery(mahankalMasDBTHA).executeUpdate();
  			int cmahankalPayDBSA = entityManager.createNativeQuery(mahankalPayDBSA).executeUpdate();
  			int cmahankalPayDBTHA =entityManager.createNativeQuery(mahankalPayDBTHA).executeUpdate();

  			entityManager.createNativeQuery(mahanLedPCC).executeUpdate();
  			entityManager.createNativeQuery(mahanLedPCT).executeUpdate();


  			int cmahankal5 =entityManager.createNativeQuery(mrmahankal5).executeUpdate();
  			int cmahankal17 =entityManager.createNativeQuery(mrmahankal17).executeUpdate();
  			int cmahankal33 =entityManager.createNativeQuery(mrmahankal33).executeUpdate();
  			int cmahankal67 =entityManager.createNativeQuery(mrmahankal67).executeUpdate();
  			int cmahankal133 =entityManager.createNativeQuery(mrmahankal133).executeUpdate();
  			int cmahankal250=entityManager.createNativeQuery(mrmahankal250).executeUpdate();
  			int cmahankal500 =entityManager.createNativeQuery(mrmahankal500).executeUpdate();
  			int cmahankal0 =entityManager.createNativeQuery(mrmahankal0).executeUpdate();

  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger WARDNO>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalLedWard);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger Reading Day>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalLedRD);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger PIPESISE>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalLedPS);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalLeddDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Ledger DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalLeddDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalMasDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Master DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalMasDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY SA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalPayDBSA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>Payments DENOTED BY THA>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankalPayDBTHA);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>MR5>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal5);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur17>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal17);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur33>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal33);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur67>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal67);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur133>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal133);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur250>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal250);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur500>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal500);
  			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>mrkirtipur0>>>>>>>>>>>>>>>>>>>>>>>>>>>"+cmahankal0);

  			//DENOTED BY 
  			String mahanbswDBYSA="UPDATE "+mahankalLocation+".BSW_MASTER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String mahanbswDBYTHA="UPDATE "+mahankalLocation+".BSW_MASTER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String mahanbswLDBYSA="UPDATE "+mahankalLocation+".BSW_LEDGER SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String mahanbswLDBYTHA="UPDATE "+mahankalLocation+".BSW_LEDGER SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";
  			String mahanbswPDBYSA="UPDATE "+mahankalLocation+".BSW_PAYMENTS SET DENOTED_BY='SA' WHERE PIPE_SIZE=0.5 AND DENOTED_BY NOT IN('SA')";
  			String mahanbswDPBYTHA="UPDATE "+mahankalLocation+".BSW_PAYMENTS SET DENOTED_BY='THA' WHERE PIPE_SIZE>0.5 AND DENOTED_BY NOT IN('THA')";

  			entityManager.createNativeQuery(mahanbswDBYSA).executeUpdate();
  			entityManager.createNativeQuery(mahanbswDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(mahanbswLDBYSA).executeUpdate();
  			entityManager.createNativeQuery(mahanbswLDBYTHA).executeUpdate();
  			entityManager.createNativeQuery(mahanbswPDBYSA).executeUpdate();
  			entityManager.createNativeQuery(mahanbswDPBYTHA).executeUpdate();
  			//End DENOTED BY


  			BsmartWaterLogger.logger.info("**************>>>>>>>>>>>>>END UPDATED MAHANKAL LOCATION>>>>>>>>>>>>>>>>>>>>>>>>>>>**************");

  			//MAHANKAL LOCATION END
  		}catch(Exception e){
			e.printStackTrace();
		}
			    
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateDailyPaymentByMonthSelectionBalanceDaily(String currentdate) {
		
		String testLocation="TESTLOC_2222";
		String tripurLocation="TRIPUR_1115";
		String baneshLocation="BANESH_1112";
		String lalitLocation="LALITPUR_1118";
		String maharLocation="MAHAR_1111";
		String bhaktLocation="BHAKTAPUR_1116";
		String chhetLocation="CHHETRA_1114";
		String thimiLocation="THIMI_1117";
		String kirtipurLocation="KIRTIPUR_1119";
		String kamaladiLocation="KAMALADI_1113";
		String mahankalLocation="MAHAN_1110";
		try{
			updatePaymentByMonthClosingBalance(currentdate, testLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, tripurLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, baneshLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, lalitLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, maharLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, bhaktLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, chhetLocation);
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			updatePaymentByMonthClosingBalance(currentdate, thimiLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, kirtipurLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, kamaladiLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updatePaymentByMonthClosingBalance(currentdate, mahankalLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Async
	public void updatePaymentByMonthClosingBalance(String currentdate,
			String testLocation) {
		String todayPaymentConnections="SELECT CONNECTION_NO FROM "+testLocation+".BSW_PAYMENTS WHERE TRUNC(EDATE)=TO_DATE('"+currentdate+"', 'dd-MM-yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT')";
		Session session = getCustomEntityManager(testLocation).unwrap(Session.class);
		List<String> list=entityManager.createNativeQuery(todayPaymentConnections).getResultList();
		BsmartWaterLogger.logger.info(">>>List Size >>>>"+list.size());
		
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String connectionno = (String) iterator.next();
			BsmartWaterLogger.logger.info(">>>Connection No>>>>"+connectionno);
			
			BillingLedgerEntity billingLedgerEntity=billingLedgerDao.findLatestLedgerBYCNSchema(connectionno,testLocation);
			
			if(billingLedgerEntity!=null){
				
				if(billingLedgerEntity.getLast_paid_amount()!=null && billingLedgerEntity.getReceipt_date()!=null){
					
					if(billingLedgerEntity.getReceipt_no()==null || "".equalsIgnoreCase(billingLedgerEntity.getReceipt_no())){
						
						BsmartWaterLogger.logger.info(billingLedgerEntity.getReceipt_no()+">>>>>>>>>>>>>>>"+billingLedgerEntity.getMonthyearnep());
						
						List<BillingLedgerEntity> list1=billingLedgerDao.findBillsFromMaxReceiptNo(connectionno,testLocation);
						
						if(!list1.isEmpty()){
							int i=1;
							double closebalance=0;
							double netamount=0;
							
							for (BillingLedgerEntity bill: list1) {
								
								BsmartWaterLogger.logger.info(">>>Inside List1"+bill.getMonthyearnep());
								if(i==1){
									closebalance=bill.getClose_balance()==null?0:bill.getClose_balance();
								}else{
									
									if(i==2){
										 bill.setArrears(closebalance);
										 netamount=bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+closebalance;
										 bill.setNet_amount(netamount);
										 session.merge(bill);
										 session.flush();
										 session.clear();
										 BsmartWaterLogger.logger.info( "Updated successful");
									}else{
										bill.setArrears(netamount);
										bill.setNet_amount(bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+bill.getArrears());
										session.merge(bill);
										session.flush();
										session.clear();
										BsmartWaterLogger.logger.info( "Updated successful");
										netamount=bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+bill.getArrears();
									}
								}
								
								
								if(list1.size()==i){
									
									if(bill.getLast_paid_amount()!=null && bill.getReceipt_date()!=null){
										
										if(bill.getReceipt_no()==null || "".equalsIgnoreCase(bill.getReceipt_no())){
											
											bill.setLast_paid_amount(null);
											bill.setReceipt_date(null);
											bill.setClose_balance(null);
											session.merge(bill);
											session.flush();
											session.clear();
											BsmartWaterLogger.logger.info( "Updated successful");
										}
									}
								}
								
								i=i+1;
								
							}
						}
						
					}
				}
				
			}
			
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateRecalculateArrears(String connectionno, String schemaName,String userName) {
		updateRecalculateArrearsAsync(connectionno,schemaName,userName);
	}

	
	@Async
	public void updateRecalculateArrearsAsync(String connectionno,String schemaName,String userName) {
		
			Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
			
			BsmartWaterLogger.logger.info(">>>Connection No>>>>"+connectionno);
			
			List<BillingLedgerEntity> list1=billingLedgerDao.findBillsFromMaxReceiptNo(connectionno,schemaName);
			
			if(!list1.isEmpty()){
				int i=1;
				double closebalance=0;
				double netamount=0;
				
				for (BillingLedgerEntity bill: list1) {
					
					BsmartWaterLogger.logger.info(">>>Inside List1"+bill.getMonthyearnep());
					if(i==1){
						closebalance=bill.getClose_balance()==null?0:bill.getClose_balance();
					}else{
						
						if(i==2){
							 bill.setArrears(closebalance);
							 netamount=bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+closebalance;
							 bill.setNet_amount(netamount);
							 bill.setUpdated_by(userName);
							 bill.setBank_due_date(new Date());
							 bill.setRemarks("Amount Recalculated(Manually)");
							 session.merge(bill);
							 session.flush();
							 session.clear();
							 BsmartWaterLogger.logger.info( "Updated successful");
						}else{
							bill.setArrears(netamount);
							bill.setNet_amount(bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+bill.getArrears());
							bill.setUpdated_by(userName);
							bill.setBank_due_date(new Date());
							bill.setRemarks("Amount Recalculated(Manually)");
							session.merge(bill);
							session.flush();
							session.clear();
							BsmartWaterLogger.logger.info( "Updated successful");
							netamount=bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+bill.getArrears();
						}
					}
					
					
					if(list1.size()==i){
						
						if(bill.getLast_paid_amount()!=null && bill.getReceipt_date()!=null){
							
							if(bill.getReceipt_no()==null || "".equalsIgnoreCase(bill.getReceipt_no())){
								
								bill.setLast_paid_amount(null);
								bill.setReceipt_date(null);
								bill.setClose_balance(null);
								session.merge(bill);
								session.flush();
								session.clear();
								BsmartWaterLogger.logger.info( "Updated successful");
							}
						}
					}
					
					i=i+1;
					
				}
			}
			
				
					
						
				
						
				
			
		
	}

	@Override
	public long pendingCount(String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillApproveEntity.pendingCount").setParameter("monthyearnep", monthyearnep).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public void recalculateAllLedger() {
		String testLocation="TESTLOC_2222";
		String tripurLocation="TRIPUR_1115";
		String baneshLocation="BANESH_1112";
		String lalitLocation="LALITPUR_1118";
		String maharLocation="MAHAR_1111";
		String bhaktLocation="BHAKTAPUR_1116";
		String chhetLocation="CHHETRA_1114";
		String thimiLocation="THIMI_1117";
		String kirtipurLocation="KIRTIPUR_1119";
		String kamaladiLocation="KAMALADI_1113";
		String mahankalLocation="MAHAN_1110";
		
		
		try{
			updateNotPostedClosingBalance(testLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(tripurLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(baneshLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(lalitLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(maharLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(bhaktLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(chhetLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(thimiLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(kirtipurLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(kamaladiLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			updateNotPostedClosingBalance(mahankalLocation);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void updateNotPostedClosingBalance(String testLocation) {
		Session session = getCustomEntityManager(testLocation).unwrap(Session.class);
		
		String getMaxMonthYear="SELECT MAX(MONTH_YEAR) FROM "+testLocation+".BSW_CLOSE_MONTH_END";
		java.math.BigDecimal monthYearB= (BigDecimal) entityManager.createNativeQuery(getMaxMonthYear).getSingleResult();
		String monthYear=""+monthYearB;
		String year=(monthYear).substring(0, 4);
		String month=(monthYear).substring(4, 6);
		String monthYearNext="";
		
		if("12".equals(month)){
			int yearN=(Integer.parseInt(year)+1);
			monthYearNext=yearN+"01";
		} else {
			int y=Integer.parseInt(monthYear)+1;
			monthYearNext=""+y;
		}
		System.out.println("monthYearNext============="+monthYearNext);
		
		
		String sitecode=testLocation.split("_")[1];
		
		String qry="SELECT A.CONNECTION_NO FROM "
				+ "(SELECT CONNECTION_NO,ARREARS,MONTHYEARNEP,SUSPENSE FROM "+testLocation+".BSW_LEDGER WHERE  "
				+ "MONTHYEARNEP="+monthYearNext+" AND RECEIPT_NO IS NULL AND BILLNO IS NOT NULL)A RIGHT OUTER JOIN "
				+ "(SELECT CONNECTION_NO,CLOSE_BALANCE,SERVICE_CHARGE FROM "+testLocation+".BSW_LEDGER WHERE "
				+ "RECEIPT_NO is NOT NULL  AND LAST_PAID_AMOUNT IS NOT NULL AND RECEIPT_NO LIKE '"+sitecode+"%' "
				+ "AND MONTHYEARNEP="+monthYear+")B ON A.CONNECTION_NO=B.CONNECTION_NO WHERE A.MONTHYEARNEP="+monthYearNext+" "
				+ "AND NVL(A.ARREARS,0)<>NVL(B.CLOSE_BALANCE,0)";
		
		List<String> list=entityManager.createNativeQuery(qry).getResultList();
		BsmartWaterLogger.logger.info(">>>List Size >>>>"+list.size());
		System.out.println(list.toString());
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String connectionno = (String) iterator.next();
			
			
			
			BsmartWaterLogger.logger.info(">>>Connection No>>>>"+connectionno);
			
			System.out.println("updateclosebal connectionno========"+connectionno);
			
			List<BillingLedgerEntity> list1=billingLedgerDao.findBillsFromMaxReceiptNo(connectionno,testLocation);
			
			if(!list1.isEmpty()){
				int i=1;
				double closebalance=0;
				double netamount=0;
				
				for (BillingLedgerEntity bill: list1) {
					
					BsmartWaterLogger.logger.info(">>>Inside List1"+bill.getMonthyearnep());
					if(i==1){
						closebalance=bill.getClose_balance()==null?0:bill.getClose_balance();
					}else{
						
						if(i==2){
							 bill.setArrears(closebalance);
							 netamount=bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+closebalance;
							 bill.setNet_amount(netamount);
							 bill.setBank_due_date(new Date());
							 bill.setLast_paid_amount(null);
							 bill.setReceipt_date(null);
							 bill.setClose_balance(null);
							 session.merge(bill);
							 session.flush();
							 session.clear();
							 BsmartWaterLogger.logger.info( "Updated successful");
						}else{
							bill.setArrears(netamount);
							bill.setNet_amount(bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+bill.getArrears());
							bill.setBank_due_date(new Date());
							session.merge(bill);
							session.flush();
							session.clear();
							BsmartWaterLogger.logger.info( "Updated successful");
							netamount=bill.getWater_charges()+bill.getSw_charges()+bill.getMtr_rent()+bill.getArrears();
						}
					}
					
					
					if(list1.size()==i){
						
						if(bill.getLast_paid_amount()!=null && bill.getReceipt_date()!=null){
							
							if(bill.getReceipt_no()==null || "".equalsIgnoreCase(bill.getReceipt_no())){
								
								bill.setLast_paid_amount(null);
								bill.setReceipt_date(null);
								bill.setClose_balance(null);
								session.merge(bill);
								session.flush();
								session.clear();
								BsmartWaterLogger.logger.info( "Updated successful");
							}
						}
					}
					
					i=i+1;
					
				}
			}
		}
		
		
	}

	@Override
	public void updateMasterDataMonthEnd(String schema) {

		String updtWard="UPDATE "+schema+".BSW_LEDGER L SET L.WARDNO=(SELECT M.WARD_NO FROM "+schema+".BSW_MASTER M WHERE L.CONNECTION_NO=M.CONNECTION_NO) WHERE L.WARDNO IS NULL AND MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+schema+".BSW_LEDGER)";
		String updtRdngDy="UPDATE "+schema+".BSW_LEDGER L SET L.READING_DAY=(SELECT M.READING_DAY FROM "+schema+".BSW_MASTER M WHERE L.CONNECTION_NO=M.CONNECTION_NO) WHERE L.READING_DAY IS NULL AND MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+schema+".BSW_LEDGER)";
		String updtPipe="UPDATE "+schema+".BSW_LEDGER L SET L.PIPE_SIZE=(SELECT M.PIPE_SIZE FROM "+schema+".BSW_MASTER M WHERE L.CONNECTION_NO=M.CONNECTION_NO) WHERE L.PIPE_SIZE IS NULL AND MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+schema+".BSW_LEDGER)";
		String updtDenoted="UPDATE "+schema+".BSW_LEDGER L SET L.DENOTED_BY=(SELECT M.DENOTED_BY FROM "+schema+".BSW_MASTER M WHERE L.CONNECTION_NO=M.CONNECTION_NO) WHERE L.DENOTED_BY IS NULL AND MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+schema+".BSW_LEDGER)";
		String updtCategory="UPDATE "+schema+".BSW_LEDGER L SET L.CON_CATEGORY=(SELECT M.CON_CATEGORY FROM "+schema+".BSW_MASTER M WHERE L.CONNECTION_NO=M.CONNECTION_NO) WHERE L.CON_CATEGORY IS NULL AND MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+schema+".BSW_LEDGER)";
		String updtType="UPDATE "+schema+".BSW_LEDGER L SET L.CON_TYPE=(SELECT M.CON_TYPE FROM "+schema+".BSW_MASTER M WHERE L.CONNECTION_NO=M.CONNECTION_NO) WHERE L.CON_TYPE IS NULL AND MONTHYEARNEP IN(SELECT MAX(MONTHYEARNEP) FROM "+schema+".BSW_LEDGER)";
		int updtWardc=0,updtRdngDyc=0,updtPipec=0,updtDenotedc=0,updtCategoryc=0,updtTypec=0;
		
		try {
			updtWardc=getCustomEntityManager().createNativeQuery(updtWard).executeUpdate();
			//System.out.println(updtWard);
		}catch (Exception e) {
			e.getMessage();
		}
		
		try {
			updtRdngDyc=entityManager.createNativeQuery(updtRdngDy).executeUpdate();
			//System.out.println(updtRdngDy);
		}catch (Exception e) {
			e.getMessage();
		}
		
		try {
			updtPipec=entityManager.createNativeQuery(updtPipe).executeUpdate();
			//System.out.println(updtPipe);
		}catch (Exception e) {
			e.getMessage();
		}
		
		try {
			updtDenotedc=entityManager.createNativeQuery(updtDenoted).executeUpdate();
			//System.out.println(updtDenoted);
		}catch (Exception e) {
			e.getMessage();
		}
		
		try {
			updtCategoryc=entityManager.createNativeQuery(updtCategory).executeUpdate();
			//System.out.println(updtCategory);
		}catch (Exception e) {
			e.getMessage();
		}
		
		try {
			updtTypec=entityManager.createNativeQuery(updtType).executeUpdate();
			//System.out.println(updtType);
		}catch (Exception e) {
			e.getMessage();
		}
		
		System.out.println("updated ward : "+updtWardc+", updated Rday : "+updtRdngDyc+", updated pipe : "+updtPipec+", updated DenotedBy : "+updtDenotedc+". updated Category : "+updtCategoryc+", updated type : "+updtTypec);
		BsmartWaterLogger.logger.info("updated ward : "+updtWardc+", updated Rday : "+updtRdngDyc+", updated pipe : "+updtPipec+", updated DenotedBy : "+updtDenotedc+". updated Category : "+updtCategoryc+", updated type : "+updtTypec);
	}
	
}
