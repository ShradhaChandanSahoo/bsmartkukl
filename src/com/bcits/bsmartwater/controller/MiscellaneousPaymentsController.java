package com.bcits.bsmartwater.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bcits.bsmartwater.model.MiscellaneousPayment;
import com.bcits.bsmartwater.service.MiscellaneousPaymentService;

@Controller
public class MiscellaneousPaymentsController {

	@Autowired
	private MiscellaneousPaymentService miscellaneousPaymentService;
	
	
	public void syncNewConnectionPayments(){
   		
   		Connection connection = null;
		try{
			//step1 load the driver class  
		    Class.forName("org.postgresql.Driver");  
		    //step2 create  the connection object 
		    String databaseURL="jdbc:postgresql://192.168.20.2:5432/bsmartgrievance";
		    connection=  DriverManager.getConnection(databaseURL,"postgres","post123");
		    
		    if(connection!=null){
		    	System.out.println("Connection Established");
		    }
		    
		    //Save Data
		    
		    int status=0;
	   		List<MiscellaneousPayment> miscellaneousPayments=miscellaneousPaymentService.getSyncToPGRS(status);
	   		
	   		for(MiscellaneousPayment miscellaneousPayment:miscellaneousPayments){
	   			
	   			Statement stmt=connection.createStatement(); 
	   			
	   			
	   			if(miscellaneousPayment.getApplication_id()!=null){
	   				String insertSQL="INSERT INTO kuklgrievance.ncpt_depositpaid (id,sitecode,applicationid,amount,recno,recdate,towards,paymentchanal,"
				    		+ "username,datestamp)"
				    		+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
			
						//System.out.println("Inside Miscellaneous PAyments----");
					    ResultSet rs = stmt.executeQuery("select nextval('kuklgrievance.ncpt_depositpaid_id_seq')");
					    int seqval=0;
					    if ( rs!=null && rs.next() ) {
					    	seqval = rs.getInt(1);
					    	rs.close();
					    }
					    
					    String towards="";
					    
					    if((miscellaneousPayment.getNctap()==null?0:miscellaneousPayment.getNctap())>0){
					    	towards="New Connection Tap";
					    }
					    else if((miscellaneousPayment.getNcdeposit()==null?0:miscellaneousPayment.getNcdeposit())>0){
					    	towards="New Connection Deposit";
					    }
					    else if((miscellaneousPayment.getMvalue()==null?0:miscellaneousPayment.getMvalue())>0){
					    	towards="Meter Value";
					    }
					    else if((miscellaneousPayment.getTempholeamt()==null?0:miscellaneousPayment.getTempholeamt())>0){
					    	towards="Temparary Hole Bloack";
					    }
					    else if((miscellaneousPayment.getNameChangeAmt()==null?0:miscellaneousPayment.getNameChangeAmt())>0){
					    	towards="Name Change Amount";
					    }
					    else if((miscellaneousPayment.getIlg_con_amt()==null?0:miscellaneousPayment.getIlg_con_amt())>0){
					    	towards="Illegal Connection Amount";
					    }
					    PreparedStatement statement = connection.prepareStatement(insertSQL);
					    statement.setInt(1,seqval);
					    statement.setString(2,miscellaneousPayment.getSitecode());
					    statement.setLong(3,miscellaneousPayment.getApplication_id());
					    statement.setDouble(4,miscellaneousPayment.getAmount());
					    statement.setString(5,miscellaneousPayment.getRecno());
					    statement.setTimestamp(6,new Timestamp(miscellaneousPayment.getCreated_date().getTime()));
					    statement.setString(7,towards);
					    statement.setString(8,"Cash Counter");
					    statement.setString(9,miscellaneousPayment.getUserid());
					    statement.setTimestamp(10,new Timestamp(new java.util.Date().getTime()));
					    
					    //System.out.println("-------------Start----");
					    statement.executeUpdate();
					    //System.out.println("Record Inserted");
					    
					    
					    miscellaneousPayment.setStatus(1);
						miscellaneousPaymentService.update(miscellaneousPayment);
						String updateSQL="UPDATE kuklgrievance.ncpt_application set recno='"+miscellaneousPayment.getRecno()+"',amount="+miscellaneousPayment.getAmount()+" where applicationid="+miscellaneousPayment.getApplication_id()+" and sitecode='"+miscellaneousPayment.getSitecode()+"'";
						PreparedStatement statement1 = connection.prepareStatement(updateSQL);
						statement1.executeUpdate();
						statement1.close();
						statement.close();
						rs.close();
						

	   			}else{
	   				
	   			    miscellaneousPayment.setStatus(1);
					miscellaneousPaymentService.update(miscellaneousPayment);
	   			}
	   			
	   			if(stmt!=null){
	   			   stmt.close();
	   			}
			    
			}

		    				  

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			if(connection!=null){
				try {
					connection.close();
					//System.out.println("Connection Closed");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
