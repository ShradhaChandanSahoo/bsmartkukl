package com.bcits.bsmartwater.asyncupdate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.DoubleLedgerValidation;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.FiscalYearAdvanceBalanceService;
import com.bcits.bsmartwater.service.FiscalYearBalService;


@Component
public class UpdateAsynchronous {
	
	
	
	
    @Async
    public Future<Boolean> cancelReceiptAsynchronousUpdate(PaymentEntity paymentdata,List<BillingLedgerEntity> billlist,BillingLedgerService billingLedgerService, String schemaName) throws InterruptedException {
    	 System.out.println("Started Updating..");
    	 try{
         int updatedcount=billingLedgerService.custombatchUpdateBillingLedgerEntity(billlist,paymentdata,schemaName);
         System.out.println(updatedcount+"==========================="+billlist.size());
		 System.out.println("Finished Updating");
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
        return new AsyncResult<Boolean>(true);
    }
    
    @Async
	public Future<Boolean> addPaymentAsynchronous(List<BillingLedgerEntity> list, Double bPenalty, Double bRebate,Double bOther, String bReceiptNo, Double bReceivedAmount,Double bFrecamount, Double bAdvanceRebate, Timestamp today,BillingLedgerDao billingLedgerDao, String schemaName, Double bill_adj_amount, Double penalty_adj_amount, Date rdate, String adjtype) {
		 System.out.println("Started Updating..");
		 try{
		 int count=billingLedgerDao.custombatchUpdateBillingLedgerEntity(list,bPenalty, bRebate, bOther, bReceiptNo,bReceivedAmount, bFrecamount,bAdvanceRebate,today,schemaName,bill_adj_amount,penalty_adj_amount,rdate,adjtype);
		 System.out.println(count+"========================="+list.size());
		 System.out.println("Finished Updating");
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return new AsyncResult<Boolean>(true);
	}
    
    @Async
	public Future<Boolean> saveAsyncBulkBillUnmetered(String userName,
			String monthyearnep, List<ConsumerMaster> consumerMasters,
			int cmonth, int cyear, String nepalidate, String schemaName, BillingLedgerDao billingLedgerDao,String sitecode, DoubleLedgerValidationDao doubleLedgerValidationDao) {
		
		
		System.out.println("Started Updating..");
		 try{
		 int count=billingLedgerDao.saveAsyncBulkBillUnmetered(userName,monthyearnep, consumerMasters, cmonth, cyear,nepalidate, schemaName,sitecode);
		 System.out.println(count+"========================="+consumerMasters.size());
		 
		
		 
		 DoubleLedgerValidation doubleLedgerValidation1=doubleLedgerValidationDao.getRecordByWardAndRdngDay1("UNMETERED",0,schemaName);
			
		 if(doubleLedgerValidation1!=null) {
			System.out.println("Found doubleLedgerValidation1 Not Null!!");
			doubleLedgerValidation1.setFlag(0);
			try {
				System.out.println("Going to update doubleLedgerValidation1");
				doubleLedgerValidationDao.customupdate(doubleLedgerValidation1,schemaName);
				System.out.println("doubleLedgerValidation1 updated successfully");
			}catch (Exception e) {
			e.printStackTrace();
			}
		 } else {
			System.out.println("Not Found doubleLedgerValidation1 == Null!!");
		 }
		
	
		 
		 System.out.println("Finished Updating");
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return new AsyncResult<Boolean>(true);
		
	}

	public Future<Boolean> submitMonthEndAsynchronousUpdate(List<?> monthEndSummary, FiscalYearBalService fiscalYearBalService, String schemaName, String monthyearnep, String sitecode, String userName) {
		 System.out.println("Started Updating..");
		 try{
		 int count=fiscalYearBalService.submitAsyncMonthEndAsynchronousUpdate(monthEndSummary,schemaName,monthyearnep,sitecode,userName);
		 System.out.println(count+"========================="+monthEndSummary.size());
		 System.out.println("Finished Updating");
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return new AsyncResult<Boolean>(true);
	}

	public Future<Boolean> submitMonthEndAsynchronousUpdateManual(
			List<?> monthEndSummary, FiscalYearBalService fiscalYearBalService,
			String schemaName, String monthyearnep, String sitecode,
			String userName) {
		
		 System.out.println("Started Updating..");
		 try{
		 int count=fiscalYearBalService.submitMonthEndAsynchronousUpdateManual(monthEndSummary,schemaName,monthyearnep,sitecode,userName);
		 System.out.println(count+"========================="+monthEndSummary.size());
		 System.out.println("Finished Updating");
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return new AsyncResult<Boolean>(true);
	}

	public Future<Boolean> updateDataFromFiscalBalance(List<?> monthEndSummary,
			FiscalYearAdvanceBalanceService advanceBalanceService, String schemaName, String monthyearnep, String sitecode,
			String userName) 
	{
		
		 System.out.println("Started Updating..");
		 try{
		 int count=advanceBalanceService.submitAsyncAdvanceReportUpdate(monthEndSummary,schemaName,monthyearnep,sitecode,userName);
		 System.out.println(count+"========================="+monthEndSummary.size());
		 System.out.println("Finished Updating");
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return new AsyncResult<Boolean>(true);
		
	}
}
