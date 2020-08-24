<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script>
$(document).ready(function(){
	document.getElementById("connectionNo").focus();
		$('#umpayments').show();
		$('#paymentsModule').addClass('active');
   $('#receiptdate_Nep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
	});
   
   $('#receiptdate_Nep1').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			onChange: function(){
				var receiptdate_Nep1 = $('#receiptdate_Nep1').val();
				getEngDate(receiptdate_Nep1);
			}
			
	});
   
   $('#inst_nepali_date').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			
	});
   
   $('#receiptdate_Nep2').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			onChange: function(){
				var receiptdate_Nep1 = $('#receiptdate_Nep2').val();
				getEngDate1(receiptdate_Nep1);
			}
			
	});
   
   
   $('#m_date').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			onChange: function(){
				var m_date = $('#m_date').val();
				getEngDate2(m_date);
			}
	});
   
   var branch="${branchcode}";
   /* if(branch=='1119' || branch=='1117' || branch=='2222' || branch=='1113' || branch=='1110'){
	   $('#rebatenew').attr('readonly', false);
	   $('#penalty').attr('readonly', false);
   } */
   $("#connectionNo").focus();
});

var bymonthyearv="";

function getEngDate(nepalidate){
	
	var date_nep=nepalidate;
	
	
	$.ajax({
		  type: "GET",
		  url: "./billing/onChangeNepaliDate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
			 date_nep:date_nep,
			  
		  },
		  
		  success: function(response){
				   $('#cddate').val(response);
		  } 
		});
	
}

function getEngDate2(nepalidate){
	
	var date_nep=nepalidate;
	
	
	$.ajax({
		  type: "GET",
		  url: "./billing/onChangeNepaliDate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
			 date_nep:date_nep,
			  
		  },
		  
		  success: function(response){
			  var currDate = new Date();
			  var endDate   = new Date(response);
			  if(currDate<endDate){
			    	alert("Manual Date cannot be future Date!!");
			    	$('#m_date').val("");
			    	return false;
			    }
		  } 
		});
}

var global_rebate=0;


function getEngDate1(nepalidate){
	
	var date_nep=nepalidate;
	
	
	$.ajax({
		  type: "GET",
		  url: "./billing/onChangeNepaliDate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
			 date_nep:date_nep,
			  
		  },
		  
		  success: function(response){
				   $('#cddate1').val(response);
		  } 
		});
	
}

var nclamt=0;
function setConsumerData(connectionno){
	if($('#connectionNo').val().trim()=='')
	{
	$('#alertDiv').show();
    $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter connection no.</span>');
      return false;
	}
	
	
	if (!/[^a-zA-Z0-9]/.test(connectionno)) {
		 
	}else{
		 alert("Connection No does not contain Special Characters");
		 $('#connectionNo').val("");
		 $('#customerName').val("");
		 $("#amountbill").val(0);
	     $("#balance_amount").val(0);
	     $("#rebatenew").val(0);
	     $("#water_charges1").val(0);
		 $("#sw_charges1").val(0);
		 $("#meter_rent1").val(0);
		 $("#frecamount").val(0);
		 $("#penalty").val(0);
		 
		 return false;
	}
	
	   
	
	$.ajax({
		  type: "GET",
		  url: "./getConsumerDataAndLedgerDataNew/"+connectionno,
		  dataType: "json",
		  async       : false,
		  cache       : false,
		  success: function(response){
			  if(response[0]!=null && response[0] !=""){
			  var data1=response[0];
			  var data2=response[1];
			  
			  if(data2==null){
				  alert("Please update the Ledger First and Collect the Payment for Connection No. : " +connectionno);
				  $('#connectionNo').val("");
				  $('#customerName').val("");
			  }
			  
			  $("#connectionNo").prop('readonly', true);
			  $("#name_eng").val(data1.name_eng);
			  $("#address_eng").val(data1.address_eng);
			  $("#ledgerno").val(data1.ledgerno);
			  $("#ward_no").val(data1.ward_no);
			  $("#area_no").val(data1.area_no);
			  $("#areaNumber").val(data1.area_no);
			  $("#pipesize").val(data1.pipe_size);
			  
			  $("#con_category").val(data1.con_category);
			  $("#con_type").val(data1.con_type);
			  
			  $("#amount").val("");
			  $("#rbalance").val(0);
			  $("#advance").val(0);
			  $("#advance_rebate").val(0);
			  
			  
			  $("#folio").val(data1.folio);
			  $("#customerName").val(data1.name_eng);
			  
			  $("#customer_id").val(data1.customer_id);
			  
			  $("#setward_no").val(data1.ward_no);
			  $("#setreading_day").val(data1.reading_day);
			  $("#setpipe_size").val(data1.pipe_size);
			  var board=parseFloat(data1.balance);
			  if(board>0){
				  alert(board+" Board amount is pending for this customer.");
			  }
			  var con_status=data1.con_satatus;
			  if(con_status=='Temporary'){
				  alert("Payment for Temporary Hole Block Connection is Blocked. Please make it normal and make the payment!");
				  window.location.reload();
			  }
			  
			  $("#rdng_date").val(response[2]);
			  $("#net_amount").val(parseFloat(response[11]).toFixed(2));
			  
			  /* $("#water_charges").val(parseFloat(response[6]).toFixed(2));
			  $("#sw_charges").val(parseFloat(response[7]).toFixed(2));
			  $("#mtr_rent").val(parseFloat(response[8]).toFixed(2));  */
			  
			  $("#water_charges").val(response[15]==null?parseFloat(0).toFixed(2):parseFloat(response[15]).toFixed(2));
			  $("#sw_charges").val(response[16]==null?parseFloat(0).toFixed(2):parseFloat(response[16]).toFixed(2));
			  $("#mtr_rent").val(response[17]==null?0:parseFloat(response[17]).toFixed(2));
			  
			  
			  
			  $("#additional_charges").val(0);
			  $("#rebate").val(parseFloat(response[10]).toFixed(2));
			  $("#penalty").val(parseFloat(response[9]).toFixed(2));
			  $("#other").val(0);
			  
			  
			  //$("#amountbill").val(parseFloat(response[11]).toFixed(2));

			  
			  //$("#amountbill").val(data2.net_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.net_amount).toFixed(2));
			  //$("#balance_amount").val(data2.arrears);
			  
			  
			  nclamt=data2.close_balance==null?parseFloat(0):parseFloat(data2.close_balance);
			  var nwtrc=parseFloat(response[15]);
			  var nswc=parseFloat(response[16]);
			  var nmtrc=parseFloat(response[17]==null?parseFloat(0):parseFloat(response[17]));
			  
		      var rec=data2.receipt_no;
			      
			    if(rec==null || rec==""){
					
			    	  if(nclamt>0){
			    		  
			    	     $("#amountbill").val(parseFloat(nclamt).toFixed(2));
			    	     $("#balance_amount").val(parseFloat((nclamt)-(nwtrc+nswc+nmtrc)).toFixed(2));
			    	     $("#old_balance").val(0);
			    	  
			    	      var frecamount=parseFloat(nclamt)+parseFloat(response[9])-parseFloat(response[10]);
						  $("#frecamount").val(parseFloat(frecamount).toFixed(2));
						  $("#totalpayable").val(parseFloat(frecamount).toFixed(2));
			    	  }else{
			    		  
			    		  $("#amountbill").val(data2.net_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.net_amount).toFixed(2));
						  $("#balance_amount").val(data2.arrears); 
						  
						  var frecamount=parseFloat(data2.net_amount)+parseFloat(response[9])-parseFloat(response[10])+parseFloat(response[12])+(data2.bill_adj_amount==null?parseFloat(0):parseFloat(data2.bill_adj_amount));
						  $("#frecamount").val(parseFloat(frecamount).toFixed(2));
						  $("#totalpayable").val(parseFloat(frecamount).toFixed(2));
						  $("#old_balance").val(0);
			    	  }
			    	
			    	  $("#water_charges1").val(parseFloat(response[15]).toFixed(2));
					  $("#sw_charges1").val(parseFloat(response[16]).toFixed(2));
					  $("#meter_rent1").val(response[17]==null?parseFloat(0).toFixed(2):parseFloat(response[17]).toFixed(2)); 
					  $("#bill_adj_amount").val(data2.bill_adj_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.bill_adj_amount).toFixed(2));
					  
					  
					  
					  var arrcoradj=data2.service_charge==null?parseFloat(0):parseFloat(data2.service_charge);
					  var billadj=data2.bill_adj_amount==null?parseFloat(0):parseFloat(data2.bill_adj_amount);
					  var pen_adj1=data2.penalty_adj_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.penalty_adj_amount).toFixed(2);
					  var pen_adj2=data2.totalamt==null?parseFloat(0).toFixed(2):parseFloat(data2.totalamt).toFixed(2);
					  var rebate_adj_amount=data2.latefee==null?parseFloat(0).toFixed(2):parseFloat(data2.latefee).toFixed(2);
					  //alert(pen_adj2);
					  var billarradj=parseFloat(arrcoradj)+parseFloat(billadj);
					  var penadj=parseFloat(pen_adj1)+parseFloat(pen_adj2);
					  //alert(penadj);
					  $("#bill_adj_amount").val(parseFloat(billarradj).toFixed(2));
					  $("#penalty_adj_amount").val(parseFloat(penadj).toFixed(2));
					  $("#rebate_adj_amount").val(parseFloat(rebate_adj_amount).toFixed(2));
					  
					  if(data2.adj_id != null && data2.corr_id == null){
						  $('#adjtype').val("BDJ");
						  $('#arrear_corr').val(0);
					  } else if(data2.corr_id != null && data2.adj_id == null){
						  $('#adjtype').val("BAC");
						  $('#arrear_corr').val(0);
					  } else if (data2.adj_id != null && data2.corr_id != null) {
						  $('#adjtype').val("BOTH");
						  $('#arrear_corr').val(arrcoradj);
					  } else {
						  $('#adjtype').val("");
						  $('#arrear_corr').val(0);
					  }/*  */
					  if(parseFloat(billarradj)!=0){
						  swal({
					            title: "Balance <span style='color:red;font-weight: bold;'>Adjusted/Corrected</span>,<br>Please Check Rebate and Penalty.",
					            text: "Do the Rebate Correction from Arrear Correction Screen if required.",
					            type: "warning",
					            timer: 10000,
					            confirmButtonColor: "#2196F3",
					            html: true,
					        });
						  
						  /* if(${usercdesignation=='Counter Incharge' || usercdesignation=='Revenue Incharge' || usercdesignation=='Developer'}){
						  $('#rebatenew').attr('readonly', false);
						  } */
					  }
					  var pending_adj_amt=data2.pending_adj_approve==null?0:data2.pending_adj_approve;
					  $('#pending_adj_approve').val(pending_adj_amt);
					  //alert($('#adjtype').val());
					  //alert($('#arrear_corr').val());
				      /* if(arrcoradj==0){
				    	  $("#bill_adj_amount").val(data2.bill_adj_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.bill_adj_amount).toFixed(2));
						  $('#adjtype').val("BDJ");
						  $("#penalty_adj_amount").val(data2.penalty_adj_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.penalty_adj_amount).toFixed(2));
					  
					  }else{
						  $('#bill_adj_amount').val(arrcoradj);
						  $('#adjtype').val("BAC");
						  $("#penalty_adj_amount").val(data2.penalty_adj_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.penalty_adj_amount).toFixed(2));
					  } */
				      
				      
					  
				} else{
					
					
					
					 $("#amountbill").val(0);
		    	     $("#balance_amount").val(0);
		    	     $("#old_balance").val(parseFloat(response[12]).toFixed(2));
		    	     $("#penalty").val(0);
		    	     $("#rebatenew").val(0);
		    	     $("#water_charges1").val(0);
					 $("#sw_charges1").val(0);
					 $("#meter_rent1").val(0);
					 var pending_adj_amt=data2.pending_adj_approve==null?0:data2.pending_adj_approve;
					  $('#pending_adj_approve').val(pending_adj_amt);
				    $("#frecamount").val(parseFloat(response[12]).toFixed(2));
				    //alert("Payment already received for Latest Month Ledger.You can take money when New Ledger is Generated..");
				    alert("Payment already received for Latest Month Ledger.You can take money for Next Month..");
					
				}
			    
			      
				  
				  
			  //var lastbalnce=response[5];
			  
			  //Newly Added Penalty Rebate
			  $("#penalty").val(parseFloat(response[9]).toFixed(2));
			  $("#rebatenew").val(parseFloat(response[10]).toFixed(2));
			  global_rebate=parseFloat(response[10]).toFixed(2);
			  
			  //alert("Actual Rebate==="+global_rebate);
			  /*$("#water_charges1").val(parseFloat(response[6]).toFixed(2));
			  $("#sw_charges1").val(parseFloat(response[7]).toFixed(2));
			  $("#meter_rent1").val(parseFloat(response[8]).toFixed(2)); */
			 
			  $("#from_mon_year").val(response[13]);
			  $("#to_mon_year").val(response[14]);
			  
		      if(parseFloat(response[15]).toFixed(0)==0){
			 
			      alert("Water Charges & Sewerage Charges is showing zero for Latest Month if wrong Please Correct Bill and Collect Money");

			  }
		
			  if(response[14]!=null){
			     getLastPaidUpto(response[14]);
			  }
			   bymonthyearv=""+response[14];
			   
			  $('#monthnep').val(response[18]).trigger("change");
			  
			  //alert(response[20]);
			  if(response[20]=='TA'){
				  alert("Arrears Transaction Approval Pending.After Approval Please Collect the Money!!");
				  window.location.reload();
			  } else if(response[20]=='ACP'){
				  alert("Arrears Correction Pending.After Approval Please Collect the Money!!");
				  window.location.reload();
			  }
			  getFinalReceivableAmount();
			  
			  
			  
			  } else{
				  
				  swal({
		                title: "Entered Connection No Doesn't Exist",
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
				  $("#connectionNo").val("");
				  return false;
			  }
			  
		  },
	    error: function (xhr) {
		 /* $('#alertDiv').show();
		    $('#alertDiv').html('<div class="alert alert-danger alert-bordered col-md-6"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Connection no. doesn\'t Exist</span>');
		    */   
		  },
		});
}
function validateFieldBoard(){

	var connection_no=$("#connectionNo1").val();
	var amount=$("#paidamount").val();
	var tottopaid=$("#total_to_be_paid").val();
	var inst_nepali_date=$("#inst_nepali_date").val();
	
	if(connection_no==0 || connection_no==""){
		alert("Please Enter Connection No");
		return false;
	}
	else if(inst_nepali_date==null || inst_nepali_date==""){
		alert("Please Select Installment Due Date(if Full Payment Select Today Date)");
		return false;
	}
	else if(amount==0 || amount==""){
		
		alert("Please Enter Valid Amount");
		$("#paidamount").val("");
		return false;
	}
	else if(parseFloat(tottopaid)<parseFloat(amount)){
		alert("Total Paid amount cannot be more that Total To be Paid amount.");
		$("#paidamount").val("");
		return false;
	} else if(tottopaid==0){
		alert("Total To be Paid should be greater that 0");
		return false;
	}
	
	if(confirm("Are you sure to Pay the Board Amount?")){
	     $('#submitId2').hide();
	}else{
		 $('#submitId2').show();
		 return false;
	}
	
}

function changeRemBalance(){
	var amount=$("#paidamount").val();
	var tottopaid=$("#total_to_be_paid").val();
	//var rem_bal=$("#rem_balance").val();
	var board=$("#board_balance1").val();
	var board_adj_amt=$("#board_adj_amt").val();
	var penalty_adj_amount1=$("#penalty_adj_amount1").val();
	
	var paying=0;pen=0;rem=0;tobepaid=0;
	
	if(parseFloat($("#paidamount").val())<parseFloat(global_totaltobepaid)){
		paying=parseFloat(amount)/1.5;
		pen=(paying/2)+parseFloat(penalty_adj_amount1);
		tobepaid=parseFloat(parseFloat(board)+parseFloat(pen)+parseFloat(board_adj_amt)).toFixed(2);
		rem=parseFloat(tobepaid)-parseFloat(amount);
		//alert(paying);
		//alert(pen);
		//alert(rem);
		$("#inst_penalty").val(parseFloat(pen).toFixed(2));
		$("#total_to_be_paid").val(tobepaid);
		$("#rem_balance").val(parseFloat(rem).toFixed(2));
	} else{
		pen=parseFloat(board).toFixed(2)*0.5;
		$("#inst_penalty").val(parseFloat(pen).toFixed(2));
		$("#rem_balance").val(0);
		$("#total_to_be_paid").val(global_totaltobepaid);
	}
	
}


function validateField(){

var payMode=$("#payMode").val();
var amount=$("#amount").val();
var penalty=$("#penalty").val();
var penalty_adj_amount=$("#penalty_adj_amount").val();
var advance=$("#advance").val();
var customerName=$("#customerName").val();

var connection_no=$("#connectionNo").val();
var penaltyv=parseFloat(parseFloat(penalty==null?0:penalty)+parseFloat(penalty_adj_amount==null?0:penalty_adj_amount));
var arrear_corr=$("#arrear_corr").val();


//var wc=$("#water_charges1").val();
//var ba=$("#amountbill").val();

if(arrear_corr==null || arrear_corr==""){
	  $("#arrear_corr").val(0);
	  //alert($("#arrear_corr").val());
	}

if(connection_no==null || connection_no==""){
	alert("Please Enter Connection No");
	return false;
}

if(amount==0 || amount=="" || $.isNumeric(amount)==false){
	$("#amount").val("");
	$("#rbalance").val(0);
	document.getElementById("amount").focus();
	alert("Enter Total Paid amount");
	key.preventDefault();
	return false;
}

if($.isNumeric(amount)==false){
	    alert("Enter Total Paid amount in Numbers");
	   return false;
}

if(parseFloat(amount)<=0){
  	alert("Total to be Paid should be greater than NPR. 0");
  	$("#amount").val("");
    return false;
 }
 
if(connection_no==null || connection_no==""){
	alert("Please Enter Connection No");
	return false;
}

 if(parseFloat(advance).toFixed(2)>10000){
	alert("Advance amount is NPR."+parseFloat(advance)+"Please check whether it is correct or wrong");

 }
  
  if(penaltyv > parseFloat(amount)){
   	 alert("Total to be Paid should be greater than Penalty Amount");
   	$("#amount").val("");
     return false;
  }
   
  /*  if(wc==null || wc=="" || (wc==0 && ba==0)){
	   alert("Payment already done for Latest Ledger.Please generate New Ledger and Collect Money..");
	   return false;
   } */
   
  
   
   
	if(payMode!=1){
		var bankname=$("#bankname").val();
		var cdno=$("#cdno").val();
		var cddate=$("#cddate").val();
		if(bankname==""){
			swal({
                title: "Please Select Bank Name",
                text: "",
                confirmButtonColor: "#2196F3",
            });
			return false;
		}else if(cdno==""){
			swal({
                title: "Please Enter Cheque/DD No",
                text: "",
                confirmButtonColor: "#2196F3",
            });
			return false;
		}else if(cddate==""){
			swal({
                title: "Please Enter Cheque/DD Date",
                text: "",
                confirmButtonColor: "#2196F3",
            });
			return false;
		}
		return true;
	}
	
	if(confirm("Are you sure to Pay the Bill?")){
		     $('#submitId1').hide();
		     // $("#paymentEntity").attr("target", "");
		     //$("#paymentEntity").attr("action", "./addPaymentEntity").submit();
		     paymentByMonth="";
	 }else{
		 $('#submitId1').show();
		 return false;
	 }
}
/* jQuery.extend(jQuery.expr[':'], {
    focusable: function (el, index, selector) {
        return $(el).is('[tabindex]');
    }
});  */


function getLastPaidUpto(nepaliMonthYear){
	var str=nepaliMonthYear;
	if(str.length!=6){
	   alert("Please enter Valid Month Year");
	   return false;
	}else{
		
	    var yr = str.substring(0, 4);
	    var mnth = str.substring(4, 6);
	    if(mnth.length!=2){
	    	alert("Please enter Valid Month Year");
	    }else{
	    	if(mnth=="01"){
	    		$('#paidupto').val(yr+" Baisakh");
	    	}else if(mnth=="02"){
	    		$('#paidupto').val(yr+" Jestha");
	    	}else if(mnth=="03"){
	    		$('#paidupto').val(yr+" Asadh");
	    	}else if(mnth=="04"){
	    		$('#paidupto').val(yr+" Shrawan");
	    	}else if(mnth=="05"){
	    		$('#paidupto').val(yr+" Bhadra");
	    	}else if(mnth=="06"){
	    		$('#paidupto').val(yr+" Ashwin");
	    	}else if(mnth=="07"){
	    		$('#paidupto').val(yr+" Kartik");
	    	}else if(mnth=="08"){
	    		$('#paidupto').val(yr+" Mangshir");
	    	}else if(mnth=="09"){
	    		$('#paidupto').val(yr+" Poush");
	    	}else if(mnth=="10"){
	    		$('#paidupto').val(yr+" Magh");
	    	}else if(mnth=="11"){
	    		$('#paidupto').val(yr+" Falgun");
	    	}else if(mnth=="12"){
	    		$('#paidupto').val(yr+" Chaitra");
	    	}else{
	    		alert("Please enter valid Month Year");
	    	}
	    	
	    }
	}
	
}
 function onKeyPress(key,id)
    {	
         if(key.keyCode== 13){
        	 if(id=="amount"){
        		 document.getElementById("amount").focus();
        		 key.preventDefault();
        		 return false;
        	 }else if(id=="tender_cash"){
        		 document.getElementById("tender_cash").focus();
        		 key.preventDefault();
        		 return false;
        	 }
        	 else if(id=="submitId1"){
        		 document.getElementById("submitId1").focus();
        	 }
         }
    }  
 
 function adjustmentApproval(){
	 
	 var connection_no=$('#connectionNo').val();
	 var bill_adj_amount=$('#bill_adj_amount').val();
	 var penalty_adj_amount=$('#penalty_adj_amount').val();
	 var from_mon_year=$('#from_mon_year').val();
	 var to_mon_year=$('#to_mon_year').val();
	 var remarks=$('#remarks').val();
	 var adjTowards=$('#adjTowards').val();
	 
	 if(connection_no=="" || connection_no==null){
		 alert("Please enter Connection No");
		 return false;
	 }else if(bill_adj_amount=="" || bill_adj_amount==null){
		 alert("Please enter Bill Adjustment Amount");
		 return false;
	 }else if(penalty_adj_amount=="" || penalty_adj_amount==null){
		 alert("Please enter Penalty Adjustment Amount");
		 return false;
	 }else if(from_mon_year=="" || from_mon_year==null){
		 alert("From Month Year is Empty");
		 return false;
	 }else if(to_mon_year=="" || to_mon_year==null){
		 alert("Please select To Month Year");
		 return false;
	 }else if(remarks=="" || remarks==null){
			alert("Please enter remarks");
			return false;
		}
	 
	 
	else {
			if (adjTowards == 'Adjustment') {
				if (confirm("Are you sure to submit for Adjustment Approval?")) {

					$.ajax({
						type : "GET",
						url : "./billpenalty/sendToApprove",
						dataType : "text",
						async : false,
						data : {
							connection_no : connection_no,
							bill_adj_amount : bill_adj_amount,
							penalty_adj_amount : penalty_adj_amount,
							from_mon_year : from_mon_year,
							to_mon_year : to_mon_year,
							remarks : remarks,
						},

						success : function(response) {
							alert(response);
						}
					});

				} else {

					return false;
				}

			}
			
	else if (adjTowards == 'Correction') {
				if (confirm("Are you sure to submit for Correction Approval?")) {

					$.ajax({
						type : "GET",
						url : "./billpenalty/sendToCorrectionApprove",
						dataType : "text",
						async : false,
						data : {
							connection_no : connection_no,
							bill_adj_amount : bill_adj_amount,
							penalty_adj_amount : penalty_adj_amount,
							from_mon_year : from_mon_year,
							to_mon_year : to_mon_year,
							remarks : remarks,
						},

						success : function(response) {
							alert(response);
						}
					});

				} else {

					return false;
				}

			}
		}
	}

	function changeLabel() {
		if (kuklidcounter.checked) {
			$('#kuklcounterdiv').show();
			$('#boardcounterdiv').hide();

		} else if (boardcounter.checked) {
			$('#kuklcounterdiv').hide();
			$('#boardcounterdiv').show();
		}

	}

	function adjustment() {

		var connection_no = $('#connectionNo').val();

		if (connection_no == "" || connection_no == null) {
			alert("Please enter Connection No");
			return false;
		} else {
			$('#remarks_div').show();
			document.getElementById('bill_adj_amount').readOnly = false;
			document.getElementById('penalty_adj_amount').readOnly = false;
			$('#adjTowards_div').show();

		}
	}

	function boardadjustment() {
		var connection_no = $('#connectionNo1').val();

		if (connection_no == "" || connection_no == null) {
			alert("Please enter Connection No");
			return false;
		} else {

			//document.getElementById('board_balance1').readOnly = false;
			document.getElementById('no_of_installments').readOnly = false;
		}

	}
</script>


<c:if test="${not empty holidayExist}">
<script>
var holidayExist="${holidayExist}";
if(holidayExist=='Yes'){
	alert("Today is Holiday.You cannot able to take Payment for the day..!!");
	window.location.href="./cashCounterLogin";
}
</script>
</c:if>

<c:if test="${not empty timeoutcounter}">
<script>
var timeoutcounter="${timeoutcounter}";
if(timeoutcounter=='Yes'){
	alert("Cash Counter Working Timings from 9AM-5PM only!!");
	window.location.href="./cashCounterLogin";
}
</script>
</c:if>




<c:if test="${not empty msg}">
	<script>		        
		            
	
					var lastpaiduptomonth="";
					var receiptno="${peEntity.receiptNo}";
		            var counterNo="${peEntity.counterno}";
		            var branch="${CBranchName}";
		            var branchcode="${branchcode}";
		            var userOfficialName="${userOfficialName}";
					
		            var counterno="${counterNo}";
		            var cno="${peEntity.counterno}";
		            
		           if(counterno==cno){
		            
		            
		            var date="${date}";
		            var nepdate="${nepdate}";

		            var consumerName="${consumerMaster.name_eng}";
		            if(consumerName=="" || consumerName==null){
		            	consumerName="${consumerMaster.name_nep}";
		            }else{
		            	consumerName=consumerName.charAt(0).toUpperCase()+ consumerName.toLowerCase().slice(1);
		            }
		            var consumeraddress="${consumerMaster.address_nep}";
		            if(consumeraddress=="" || consumeraddress==null){
		            	consumeraddress="${consumerMaster.address_nep}";
		            }
		            var connection_no="${consumerMaster.connection_no}";
		            var customer_id="${consumerMaster.customer_id}";
		            var areano="${consumerMaster.area_no}";
		            var type="${peEntity.payMode}";
		            var cheqDDcash="";
		            if(type==2){
		            	cheqDDcash="Cheque";
		            }else if(type==3){
		            	cheqDDcash="DD";
		            }else{
		            	cheqDDcash="Cash";
		            }
		            
		            // var wc="${billLedger.water_charges}";
		            // var sc="${billLedger.sw_charges}";
		            // var mtr_rent="${billLedger.mtr_rent}";
		            //var amount=parseFloat(tender_cash).toFixed(2)-parseFloat(changeamt).toFixed(2);
		            //var penalty="${billLedger.penalty}";
		            //var rebate="${billLedger.rebate}";
		            
		            if(connection_no==null || connection_no==""){
		            	
		            }else{
		            
		            var chequeno="${peEntity.cdno}";
		            var other="${billLedger.other}";
		            //var oc="${billLedger.additional_charges}";
		            var arrears="${peEntity.balance_amount}";
		            
		            var penal="${peEntity.penalty}";
		            var penaltyadj="${peEntity.penalty_adj_amount}";
		            var penalty=parseFloat(parseFloat(penal==null?0:penal)+parseFloat(penaltyadj==null?0:penaltyadj)).toFixed(2);
		            
		            var rebate="${peEntity.rebate}";
		            var frecamount="${peEntity.frecamount}";
		            var amount="${peEntity.amount}";
		            
		            var advanceamt="${peEntity.advance}";
		            var advance_rebate="${peEntity.advance_rebate}";
		            var old_balance="${peEntity.old_balance}";
		            var miscellaneous_cost="${peEntity.miscellaneous_cost}";
		            var nepaliMonthYear="${peEntity.to_mon_year}";
		            
		            var rembalance="${peEntity.rbalance}";
		            var rebateAdj="${peEntity.rebate_adj_amt}";

		                var str="";
		                str= ""+nepaliMonthYear;
		    	         var yr = str.substring(0, 4);
		    	         var mnth = str.substring(4, 6);
		    		
		    	    	if(mnth=="01"){
		    	    		lastpaiduptomonth=yr+" Baisakh";
		    	    	}else if(mnth=="02"){
		    	    		lastpaiduptomonth=yr+" Jestha";
		    	    	}else if(mnth=="03"){
		    	    		lastpaiduptomonth=yr+" Asadh";
		    	    	}else if(mnth=="04"){
		    	    		lastpaiduptomonth=yr+" Shrawan";
		    	    	}else if(mnth=="05"){
		    	    		lastpaiduptomonth=yr+" Bhadra";
		    	    	}else if(mnth=="06"){
		    	    		lastpaiduptomonth=yr+" Ashwin";
		    	    	}else if(mnth=="07"){
		    	    		lastpaiduptomonth=yr+" Kartik";
		    	    	}else if(mnth=="08"){
		    	    		lastpaiduptomonth=yr+" Mangshir";
		    	    	}else if(mnth=="09"){
		    	    		lastpaiduptomonth=yr+" Poush";
		    	    	}else if(mnth=="10"){
		    	    		lastpaiduptomonth=yr+" Magh";
		    	    	}else if(mnth=="11"){
		    	    		lastpaiduptomonth=yr+" Falgun";
		    	    	}else if(mnth=="12"){
		    	    		lastpaiduptomonth=yr+" Chaitra";
		    	    	}
		            
		            


		            var tender_cash="${peEntity.tender_cash}";
		            var changeamt="${peEntity.change}";
		            var username="${counterUserName}";
		            
		            //Newly Added
     		        var wc="${peEntity.water_charges}";
     		        var sc="${peEntity.sw_charges}";
     		        var mtr_rent="${peEntity.meter_rent}";
  		            var bill_amount="${peEntity.bill_amount}";
  		            var from_mon_yr="${peEntity.from_mon_year}";
					var from_mon_year1=""+from_mon_yr;
  		            var from_mon_year="";
	    	    	
  		            if(from_mon_year1!=null && from_mon_year1!='' && from_mon_year1!='null'){
				        
						var yr1 = from_mon_year1.substring(0, 4);
						var mnth1 = from_mon_year1.substring(4, 6);

						if (mnth1 == "01") {
							from_mon_year = yr1 + " Baisakh";
						} else if (mnth1 == "02") {
							from_mon_year = yr1 + " Jestha";
						} else if (mnth1 == "03") {
							from_mon_year = yr1 + " Asadh";
						} else if (mnth1 == "04") {
							from_mon_year = yr1 + " Shrawan";
						} else if (mnth1 == "05") {
							from_mon_year = yr1 + " Bhadra";
						} else if (mnth1 == "06") {
							from_mon_year = yr1 + " Ashwin";
						} else if (mnth1 == "07") {
							from_mon_year = yr1 + " Kartik";
						} else if (mnth1 == "08") {
							from_mon_year = yr1 + " Mangshir";
						} else if (mnth1 == "09") {
							from_mon_year = yr1 + " Poush";
						} else if (mnth1 == "10") {
							from_mon_year = yr1 + " Magh";
						} else if (mnth1 == "11") {
							from_mon_year = yr1 + " Falgun";
						} else if (mnth1 == "12") {
							from_mon_year = yr1 + " Chaitra";
						}

					}
	    	    	
	    	    	
		            var printW = window.open("");
				    var html = "<div>";
				   
				    html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"
				   	   
					   +" <colgroup>"
					   +" <col style='width: 145px'>"
					   +" <col style='width: 185px'>"
					   +"</colgroup>"
					   
					   +"  <tr>"
					   +"    <th><br>Kathmandu Upatyaka Khanepani Limited<br><br>Branch:"+branch+" <br>PAN NO. :600041601</th>"
					   +"    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					  
					   
					   +"    <th colspan='2'><hr style='height: 2px; background-color: black;'></th>"
					   +"  </tr>"
					   +"  <tr>"
					   
					   +"  <tr>"
					   +"  <th colspan='2' align='center'><u>PAYMENT RECEIPT</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Receipt No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+receiptno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Date<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+nepdate+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Customer ID<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+customer_id+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Area No.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+areano+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+consumerName+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Payment Mode<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+cheqDDcash+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Cheque No<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+chequeno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Arrears<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+arrears+"</b></td>"
					   +"  </tr>"
					   
					  
					   +"  <tr>"
					   +"    <td>Water Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+wc+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Sewerage Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+sc+"</td>"
					   +"  </tr>"
					   
					  
					   +"   <tr>"
					   +"    <td>Meter Rent Charges<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+mtr_rent+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Miscellaneous<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+miscellaneous_cost+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Penalty<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+penalty+"</td>"
					   +"  </tr>"

					   +"  <tr>"
					   +"    <td>Rebate<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+(parseFloat(rebate)+parseFloat(rebateAdj))+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Previous Balance<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+parseFloat(old_balance).toFixed(2)+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Bill Amount<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+bill_amount+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Total To Be Paid<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+frecamount+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Total Amount Paid Rs.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b><u>"+amount+"</u></b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Payment Period<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+from_mon_year+" - "+lastpaiduptomonth+"</td>"
					   +"  </tr>"
					   
					   /* +"  <tr>"
					   +"    <td>Last Paid UPTO:<br></td>"
					   +"    <td><b>"+lastpaiduptomonth+"</b></td>"
					   +"  </tr>" */
						
					   +"  <tr>"
					   +"    <td>Advance<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+advanceamt+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Advance Rebate<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+parseFloat(advance_rebate).toFixed(2)+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Balance(After Paid)<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+rembalance+"</b></td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Received By<br></td>"
					   +"    <td>:&nbsp;&nbsp;("+userOfficialName+" - Counter No. :"+counterNo+")</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td colspan='2'><center>*****Computer Generated Receipt*****</center></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td>&nbsp;</td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td><center>.</center></td>"
					   +"  </tr>"
					   +"</table><br><br><br>";
				   
				    html += "</div>";

				    printW.document.write(html);
				    printW.document.close();
				    printW.focus();
				    printW.print();
				    printW.close();
				    
		            }

		            }
				    
				    
</script>
</c:if>




<c:if test="${not empty msg1}">
	<script>		        
	
					var nepdate="${nepdate}";
		            var connection_no="${connection_no}";
		            var customer_id="${customer_id}";
		            var paid_amount="${paid_amount}";
		            var install_amount="${install_amount}";
		            //var penalty="${penalty}";
		            var penal="${peEntity.penalty}";
		            var penaltyadj="${peEntity.penalty_adj_amount}";
		            var penalty=parseFloat(parseFloat(penal==null?0:penal)+parseFloat(penaltyadj==null?0:penaltyadj)).toFixed(2);
		            var receiptNo="${peEntity.receiptNo}";
		            var chequeno="${peEntity.cdno}";
		            var branch="${CBranchName}";
		            var type="${peEntity.payMode}";
		            var cheqDDcash="";
		            if(type==2){
		            	cheqDDcash="Cheque";
		            }else if(type==3){
		            	cheqDDcash="DD";
		            }else{
		            	cheqDDcash="Cash";
		            }
		            
		            var amount="${peEntity.amount}";
		            var rem_balance="${rem_balance}";
		            var name_eng="${name_eng}";
		            
		            if(name_eng=="" || name_eng==null){
		            	
		            }else{
		            	name_eng=name_eng.charAt(0).toUpperCase()+ name_eng.toLowerCase().slice(1);
		            }
		            var area_no="${area_no}";
		            var install_due_date="${install_due_date}";
		            var userOfficialName="${userOfficialName}";
		            var counterNo="${peEntity.counterno}";
		            var username="${counterUserName}";
		            
		            
		            var counterno="${counterNo}";
		            
		            if(counterno==counterNo){
		            
					if(connection_no==null || connection_no==""){
		            	
		            }else{

		            var printW = window.open("");
				    var html = "<div>";
				   
				    html += "  <table style='undefined;table-layout: fixed; width: 325px;font-size:11pt;line-height:16px;'>"
				   	   
					   +" <colgroup>"
					   +" <col style='width: 145px'>"
					   +" <col style='width: 185px'>"
					   +"</colgroup>"
					   
					   +"  <tr>"
					   +"    <th><br>Kathmandu Upatyaka Khanepani Limited<br><br>Branch:"+branch+" <br>PAN NO. :600041601</th>"
					   +"    <th><img src='./resources/images/kukl_new.png' width='130px' height='130px' alt='' align='right'></th>"
					   +"  </tr>"
					   +"  <tr>"
					   
					   +"    <th colspan='2'><hr style='height: 2px; background-color: black;'></th>"
					   +"  </tr>"
					   +"  <tr>"
					   
					   +"  <tr>"
					   +"  <th colspan='2' align='center'><u>BOARD PAYMENT RECEIPT</u></th>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Receipt No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+receiptNo+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Date.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+nepdate+"</b></td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Customer ID.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+customer_id+"</b></td>"
					   +"  </tr>"
					   
					   +"    <td>Connection No.<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+connection_no+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td><b>Area No.</b><br></td>"
					   +"    <td>:&nbsp;&nbsp;"+area_no+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Name<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+name_eng+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Payment Mode<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+cheqDDcash+"</td>"
					   +"  </tr>"
					   
					   
					   +"  <tr>"
					   +"    <td>Cheque No<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+chequeno+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Installment Amount<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+install_amount+"</b></td>"
					   +"  </tr>"
					   
					  
					   +"  <tr>"
					   +"    <td>Penalty<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+penalty+"</b></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Total Paid<br></td>"
					   +"    <td>:&nbsp;&nbsp;<u><b>"+paid_amount+"</b></u></td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Remaining Balance<br></td>"
					   +"    <td>:&nbsp;&nbsp;<b>"+rem_balance+"</b></td>"
					   +"  </tr>"
					   
					   +"   <tr>"
					   +"    <td>Due Date<br></td>"
					   +"    <td>:&nbsp;&nbsp;"+install_due_date+"</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td>Received By<br></td>"
					   +"    <td>:&nbsp;&nbsp;("+userOfficialName+" - Counter No. :"+counterNo+")</td>"
					   +"  </tr>"
					   
					   +"  <tr>"
					   +"    <td colspan='2'><center>*****Computer Generated Receipt*****</center></td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td>&nbsp;</td>"
					   +"  </tr>"
					   +"  <tr>"
					   +"    <td>&nbsp;</td>"
					   +"    <td><center>.</center></td>"
					   +"  </tr>"
					   +"</table><br><br><br>";
				   
				    html += "</div>";
				   
				    printW.document.write(html);
				    printW.document.close();
				    printW.focus();
				    printW.print();
				    printW.close();
				    
		           }
		          }

				    
				    
</script>
</c:if>
<c:if test = "${not empty expirydate}"> 			        
      <script>		        
          var msg = "${expirydate}";
            alert(msg);
      </script>
     
</c:if>
	


<div id="viewPayements">

	<div class="panel panel-flat">

		<div class="form-group">
			<div hidden="true" id="alertDiv"></div>
			
			
			
			
		</div>
		
		<c:if test = "${branchcode == '1112' || branchcode == '1116' || branchcode == '1114' || branchcode == '1119' || branchcode == '1118' || branchcode == '1111' || branchcode == '1117' || branchcode == '2222'}"> 
		    <label class="radio-inline" style="color: red;margin-left: 25px;">
				<input type="radio" id="kuklidcounter" name="radio-inline-left" class="styled" checked="checked" onclick="return changeLabel();">
				KUKL
			</label>
			<label class="radio-inline" style="color: blue;">
				<input type="radio" id="boardcounter" name="radio-inline-left" class="styled" onclick="return changeLabel();">
				BOARD
			</label>
		</c:if>
		
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12">
						<legend class="text-semibold" ><!--style="margin-bottom: -12px;" -->
							<i class="icon-reading position-left"></i> Counter
							no:${counterNo} Counter Name:${counterName} Date:${currDate}   Receipts: <b><font color="red">${datalist[0].cashcount}</font></b>  Receipt Amount: <b><font color="red">${datalist[0].cashamount}</font></b>   Cheques: <b><font color="red">${datalist[0].cdcount}</font></b>    Cheque Amount: <b><font color="red">${datalist[0].cdamount}</font></b>
						</legend>

					<div id="kuklcounterdiv">
					<fieldset>
						<form:form action="./addPaymentEntity" modelAttribute="paymentEntity"
							commandName="paymentEntity" method="POST" id="paymentEntity"
							role="form" class="form-horizontal form-validate-jquery">

							<!-- <h5 align="center" style="color: black;">Payment Entry</h5> -->
							
							<form:input type="text" name="ward_no" id="setward_no" path="ward_no" hidden="true"></form:input>
							<form:input type="text" name="reading_day" id="setreading_day" path="reading_day" hidden="true"></form:input>
							<form:input type="text" name="pipe_size" id="setpipe_size" path="pipe_size" hidden="true"></form:input>
							<form:input type="text" name="to_mon_year" id="to_mon_year" path="to_mon_year" hidden="true"></form:input>
							<form:input type="text" name="cddate" id="cddate" path="cddate" hidden="true"></form:input>
							
							<form:input type="text" name="con_type" id="con_type" path="con_type" hidden="true"></form:input>
							<form:input type="text" name="con_category" id="con_category" path="con_category" hidden="true"></form:input>
							
							
							
							<input type="text" name="adjtype" id="adjtype" hidden="true">
							<input type="text" name="arrear_corr" id="arrear_corr" hidden="true">
							<div class="form-group" hidden="true">
								<form:input type="text" class="form-control" name="id" id="id"
									path="id" placeholder="id"></form:input>
									
									
									
									
							</div>
							<div class="col-md-2">


								<label>Connection No</label>

								<form:input type="text" class="form-control" name="connectionNo"
									tabindex="1" onkeypress="onKeyPress(event,'amount')"
									onchange="setConsumerData(this.value)" id="connectionNo"
									path="connectionNo" placeholder="Connection No"
									required="required" onkeyup="convertToUpperCase();"></form:input>
							</div>
							
							<div class="col-md-2" style="color: red;">
								<label>Customer ID</label>
								<input type="text" name="customer_id" id="customer_id"
									 readonly="readonly" class="form-control"
									placeholder="Customer Id">
							</div>
							
							<div class="col-md-3">
								<label>Customer Name</label>
								<input type="text" name="customerName" id="customerName"
									 readonly="readonly" class="form-control"
									placeholder="Customer Name">
							</div>
							<div class="col-md-1">
								<label>Area No</label>
								<input type="text" name="areaNumber" id="areaNumber"
									 readonly="readonly" class="form-control"
									placeholder="Area No">
							</div>
							<div class="col-md-2">
								<label>Receipt No</label>
								<form:input type="text" name="receiptNo" id="receiptNo"
									path="receiptNo" readonly="true" class="form-control"
									placeholder="Receipt No"></form:input>
							</div>
							
							<div class="col-md-2" >
								<label>Arrears/Open Balance</label>
								<form:input type="text" name="balance_amount" id="balance_amount" 
									 path="balance_amount"
									required="required" class="form-control"
									placeholder="Balance Amount" value="0" readonly="true"></form:input>
							</div>
							
							<div class="col-md-1">
								<label>Water Charges</label>
								<form:input type="text" name="water_charges"  id="water_charges1" path="water_charges" placeholder="Water charges" onchange="getFinalReceivableAmount()"
									required="required"  class="form-control" value="0" readonly="true"></form:input>
							</div>
							
							<div class="col-md-1">
								<label>SW Charges</label>
								<form:input type="text" name="sw_charges"  id="sw_charges1" path="sw_charges" placeholder="SW Charges" onchange="getFinalReceivableAmount()"
									required="required" class="form-control" value="0" readonly="true"></form:input>
							</div>
							
							<div class="col-md-1">
								<label>Meter Rent</label>
								<form:input type="text" name="meter_rent"  id="meter_rent1" path="meter_rent" placeholder="Meter Charges" onchange="getFinalReceivableAmount()"
									required="required" class="form-control" value="0" readonly="true"></form:input>
							</div>
							
							<div class="col-md-2">
								<label>NET Amount(OB+W+SC+MR)</label>
								<form:input type="text" name="bill_amount"  id="amountbill" path="bill_amount" onchange="getFinalReceivableAmount();"
									required="required" readonly="true" class="form-control" value="0"></form:input>
							</div>
							


							<div class="col-md-1">
								<label>Penalty</label>
								<form:input type="text" name="penalty" id="penalty" onchange="getFinalReceivableAmount()"
									path="penalty" required="required"
									class="form-control" placeholder="Penalty" value="0" readonly="true"></form:input><!--readonly="true"  -->
							</div>

							<div class="col-md-1">
								<label>Rebate</label>
								<form:input type="text" name="rebate" id="rebatenew" onchange="getFinalReceivableAmount()"
									path="rebate" required="required"
									class="form-control" placeholder="Rebate" value="0" readonly="true"></form:input><!-- readonly="true" -->
							</div>
							<div class="col-md-2" hidden="true" id="adjTowards_div">
								<label>Adjustment Towards</label>
								<select data-placeholder="Select" class="select"  id="adjTowards" name="adjTowards">
								<option value="Correction">Arrear Correction</option>
								<option value="Adjustment">Arrear Adjustment</option>
								</select>
							</div>
							<div class="col-md-2">
								<label class="blink_text"><b style="color:#cb3092;">Arrears Adjustment Amount</b></label>
								<form:input type="text" name="bill_adj_amount" path="bill_adj_amount" id="bill_adj_amount" onchange="getFinalReceivableAmount()" readonly="true"
									 required="required"
									class="form-control" placeholder="Arrears Adjustment" value="0"></form:input>
							</div>
							
							<div class="col-md-2">
								<label class="blink_text"><b style="color:#cb3092;">Penalty Adjustment Amount</b></label>
								<form:input type="text" name="penalty_adj_amount" path="penalty_adj_amount" id="penalty_adj_amount" onchange="getFinalReceivableAmount()" readonly="true"
									 required="required" class="form-control" placeholder="Penalty Adjustment" value="0"></form:input>
							</div>
							<div class="col-md-1">
								<label class="blink_text"><b style="color:#cb3092;">Rebate Corr</b></label>
								<form:input type="text" name="rebate_adj_amount" path="rebate_adj_amt" id="rebate_adj_amount" readonly="true"
									 required="required"
									class="form-control" placeholder="Rebate Corr" value="0"></form:input>
							</div>
							
							<div class="col-md-2">
								<label>Closing Balance</label>
								<form:input type="text" name="old_balance"  id="old_balance" path="old_balance" placeholder="Old Balance"
									required="required" class="form-control" onchange="getFinalReceivableAmount()" value="0" readonly="true"></form:input>
							</div>
							
							  <div class="col-md-1" >
								<label>Miscellaneous</label>
								<form:input type="text" name="miscellaneous_cost" id="miscellaneous_cost" onchange="getFinalReceivableAmount()"
									path="miscellaneous_cost" value="0"
									required="required" class="form-control"
									placeholder="Miscellaneous Amount"></form:input>
							   </div>

							<div class="col-md-2">
								<label><b style="color: red;">Total To be Paid</b></label>
								<form:input type="text" name="frecamount" id="frecamount"
									path="frecamount" required="required"
									class="form-control" placeholder="Final Receivable Amount" value="0" readonly="true"></form:input>
							</div>
							
							<div class="col-md-2" >
								<label><b style="color:green;">Total Paid Amount</b>&nbsp;<font color="red">*</font></label>
								<form:input type="text" name="amount" tabindex="2" id="amount"
									onkeypress="onKeyPress(event,'submitId1')" path="amount" onchange="getAdvanceAmount(this.value);"
									required="required" class="form-control"
									placeholder="Amount"></form:input>
							</div>
							
							
							<div class="col-md-2" >
								<label>Remaining Balance</label>
								<input type="text" name="rbalance" id="rbalance"
									required="required" class="form-control" value="0"  readonly="readonly"
									placeholder="Remaining Bal">
							</div>
							
							
							<div class="col-md-1" >
								<label>Advance</label>
								<form:input type="text" name="advance" id="advance"
									 path="advance"
									required="required" class="form-control" value="0" readonly="true"
									placeholder="Advance"></form:input>
							</div>
							
							<div class="col-md-2">
								<label>Advance Rebate</label>
								<form:input type="text" name="advance_rebate" id="advance_rebate" value="0" readonly="true"
									path="advance_rebate"
									class="form-control" placeholder="Rebate"></form:input>
							</div>
							
							<div class="col-md-3" hidden="true">
								<label>Tender Cash&nbsp;</label>
								<form:input type="text" name="tender_cash" id="tender_cash" value="0"
									path="tender_cash" onchange="getChangeAmount(this.value);"
									required="required" class="form-control"
									placeholder="Tender Cash"></form:input>
							</div>
							
							<div class="col-md-3" hidden="true">
								<label>Change</label>
								<form:input type="text" name="change" id="change" value="0"
									path="change"
									required="required" class="form-control"
									placeholder="Change"></form:input>
							</div>
							
							

							<div class="col-md-2">
								<label>Pay Towards:</label>
								<form:select data-placeholder="Pay Towards" name="towards"
									id="towards" path="towards" class="select">
									<form:option value="BILL PAYMENT">BILL PAYMENT</form:option>
									<%-- <form:option value="NEW CONNECTION CHARGE">NEW CONNECTION CHARGE</form:option> --%>
								</form:select>
							</div>

							<div class="col-md-2">
								<label>Payment Mode:</label>
								<form:select data-placeholder="Payment Mode" name="payMode"
									id="payMode" path="payMode" class="select">
									<form:option value="1">Cash</form:option>
									<form:option value="2">Cheque</form:option>
									<form:option value="3">DD</form:option>
								</form:select>
							</div>

							<%-- <div class="col-md-3">
									<label>Bank Name</label>
									<form:select class="select" id="bankname" name="bankname"
										path="bankname" data-placeholder="Select Bank Name">
										<form:option value="" disabled="disabled">Select Bank</form:option>
										<c:forEach var="data" items="${bankData}">
											<form:option value="${data.bankName}">${data.bankName}</form:option>
										</c:forEach>
									</form:select>
							</div> --%>
							
							<div class="col-md-3">
								<label>Bank Name</label>
								<form:input type="text" class="form-control" name="bankname"
									id="bankname" path="bankname" placeholder="Bank Name"></form:input>
							</div>

							<div class="col-md-3">
								<label>Cheque/DD No</label>
								<form:input type="text" class="form-control" name="cdno"
									id="cdno" path="cdno" placeholder="Cheq/DD No"></form:input>
							</div>


							<div class="row">


								<div class="col-md-2">
									<label>Cheq/DD Date Nepali</label>
									<div class="input-group">
										<span class="input-group-addon"><i
											class="icon-calendar3"></i></span>
										<form:input name="nepali_date" path="nepali_date"
											placeholder="Cheq/DD Date" id="receiptdate_Nep1"
											class="form-control nepali-calendar" />
									</div>
								</div>
								
								<c:if test = "${branchcode=='2222'}"> 			        
		   							<div class="col-md-2">
										<label>Manual Date</label>
										<div class="input-group">
											
											<input name="m_date" 
												placeholder="" id="m_date"
												class="form-control nepali-calendar" />
										</div>
								   </div>
    							</c:if> 
								
								
							   
							   <div class="col-md-1" >
								<label>From</label>
								<form:input type="text" name="from_mon_year" id="from_mon_year" readonly="true"
									path="from_mon_year" 
									 class="form-control"
									></form:input>
							   </div>
							   
							   <%-- <div class="col-md-1" >
								<label>To</label>
								<form:input type="text" name="to_mon_year" id="to_mon_year" onchange="getLastPaidUpto(this.value)"
									path="to_mon_year"
									 class="form-control"></form:input>
							   </div> --%>
							   
							   <div class="col-md-2" >
								<label>To Year</label>
								<%-- <form:input type="text" name="to_mon_year" id="to_mon_year" onchange="getLastPaidUpto(this.value)"
									path="to_mon_year"
									 class="form-control"></form:input> --%>
									 
								<select data-placeholder="Select" class="select" id="yearnep" name="yearnep" required="required">
									<c:forEach items="${yearList}" var="ward">
									<option value="${ward.year}">${ward.year}</option>
								   </c:forEach>
								</select>
									 
							   </div>
							   
							   <div class="col-md-2" >
								<label>To Month</label>
								<select data-placeholder="Select" class="select"  name="monthnep" id="monthnep"> 
									<option value="01" data-icon="icon-git-branch">Baisakh</option>
									<option value="02" data-icon="icon-git-branch">Jestha</option>
									<option value="03" data-icon="icon-git-branch">Asadh</option>
									<option value="04" data-icon="icon-git-branch">Shrawan</option>
									<option value="05" data-icon="icon-git-branch">Bhadra</option>
									<option value="06" data-icon="icon-git-branch">Ashwin</option>
									<option value="07" data-icon="icon-git-branch">Kartik</option>
									<option value="08" data-icon="icon-git-branch">Mangshir</option>
									<option value="09" data-icon="icon-git-branch">Poush</option>
									<option value="10" data-icon="icon-git-branch">Magh</option>
									<option value="11" data-icon="icon-git-branch">Falgun</option>
									<option value="12" data-icon="icon-git-branch">Chaitra</option>
									
						       </select>
							   </div>
							   
							   
							   
							   <div class="col-md-2" >
								<label>Last Paid Upto</label>
								<input type="text" name="paidupto" id="paidupto" placeholder="Last Paid Upto" class="form-control" readonly="readonly">
							   </div>
							   <div class="col-md-2">
								<label class="blink_text"><b style="color: #0015ff;">Pending Adj Amt in HO</b></label>
								<input type="text" name="pending_adj_approve" id="pending_adj_approve" readonly="readonly"
									class="form-control" placeholder="Pendind Approve" value="0">
							</div>
							   <div class="col-md-3" id="remarks_div" hidden="true">
								<label>Remarks</label>
								<textarea placeholder="Enter your Remarks here" class="form-control"  rows="1" name="remarks" id="remarks"></textarea>
							   </div>
							   
								
								<div class="col-md-9" style="float: right;">
								<div class="input-group">
								
									<%-- <c:if test="${usercdesignation=='Counter Incharge' || usercdesignation=='Revenue Incharge' || usercdesignation=='Developer'}">
										<button class="btn btn-warning" type="button" style="margin-top: 25px;" 
										id="adjustmentbutton" onclick="return adjustment()">Need Adjustment</button>
										
										<button class="btn btn-success" type="button" style="margin-top: 25px;" 
										id="submitAdjustbutton" onclick="return adjustmentApproval()">Request for Adjustment Approval</button>
									</c:if> --%>
									
								
									
									<button class="btn btn-primary" type="button" style="margin-top: 25px;" 
									id="clearbutton" onclick="return calculatePenaltyR()">Calculate</button>
									
									<button class="btn btn-info" type="button" style="margin-top: 25px;" 
									id="clearbutton" onclick="return clearField()">Clear</button>
								
							
								    <button class="btn btn-primary" type="submit" tabindex="3" style="margin-top: 25px;margin-left: 12px;" 
									id="submitId1" onclick="return validateField()">Submit</button>
									
									
									<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" 
									data-target="#modal_default" id="onshow_callback" style="margin-top: 25px;" 
									onclick='ledgerHistoryPopUp()'>View Ledger</button>
									
									</div>
									
									
								</div>
								
								
								
							</div><br>
							

						</form:form>
					</fieldset>
						
						</div>
						
						
						
				<div id="boardcounterdiv" style="display: none;">
						<fieldset>
						<form:form action="./addBoardPaymentEntity"  id="boardpaymentEntity" 
						class="form-horizontal form-validate-jquery"
						modelAttribute="paymentEntity"
							commandName="paymentEntity" method="POST" >

							<form:input type="text" name="cddate" path="cddate" id="cddate1" hidden="true"></form:input>
							<form:input type="text" name="ward_no" id="setward_no1" path="ward_no" hidden="true"></form:input>
							<form:input type="text" name="reading_day" id="setreading_day1" path="reading_day" hidden="true"></form:input>
							<form:input type="text" name="pipe_size" id="setpipe_size1" path="pipe_size" hidden="true"></form:input>
							
							<form:input type="text" name="con_type" id="con_type1" path="con_type" hidden="true"></form:input>
							<form:input type="text" name="con_category" id="con_category1" path="con_category" hidden="true"></form:input>
							
							<div class="col-md-2">
								<label>Connection No</label>

								<form:input type="text" class="form-control" name="connectionNo" path="connectionNo"
									onchange="setConsumerDataBoard(this.value)" id="connectionNo1"
									 placeholder="Connection No" onkeyup="convertToUpperCase1();"
									required="required"></form:input>
							</div>
							
							<div class="col-md-2" style="color: red;">
								<label>Customer ID</label>
								<input type="text" name="customer_id" id="customerid1"
									 readonly="readonly" class="form-control"
									placeholder="Customer Id">
							</div>
							
							<div class="col-md-2">
								<label>Receipt No</label>
								<form:input type="text" name="receiptNo" id="receiptNo1"
									path="receiptNo" readonly="true" class="form-control"
									placeholder="Receipt No"></form:input>
							</div>
							
							<div class="col-md-2">
								<label>Customer Name</label>
								<input type="text" name="customerName" id="customerName1"
									 readonly="readonly" class="form-control"
									placeholder="Customer Name">
							</div>

							<div class="col-md-2">
								<label>Area No</label>
								<input type="text" name="area_no" id="area_num1"
									 readonly="readonly" class="form-control"
									placeholder="Area No">
							</div>
							
							<div class="col-md-2">
								<label>Total Board Balance</label>
								<input type="text" name="board_balance" id="board_balance1" onchange="getInstallmentAmount();"
									 readonly="readonly" class="form-control"
									placeholder="Board Balance">
							</div>
							
							<div class="col-md-2">
								<label>Installments</label>
								<input type="text" name="no_of_installments" id="no_of_installments" onchange="getInstallmentAmount();"
									 readonly="readonly" class="form-control" value="1"
									placeholder="No of Installations">
							</div>
							
							<div class="col-md-2">
								<label>Installment Due Date</label>
								<div class="input-group">
									<span class="input-group-addon"><i
										class="icon-calendar3"></i></span>
									<input name="inst_nepali_date" 
										placeholder="Due Date" id="inst_nepali_date"
										class="form-control nepali-calendar" />
								</div>
							</div>
							
							<div class="col-md-2">
								<label>Installment Board Balance</label>
								<input type="text" name="inst_board_balance" id="inst_board_balance"
									 readonly="readonly" class="form-control" value="0"
									placeholder="Inst Board Balance">
							</div>
							
							<div class="col-md-2">
								<label>Installment Penalty</label>
								<input type="text" name="inst_penalty" id="inst_penalty" value="0"
									 readonly="readonly" class="form-control"
									placeholder="Installment Penalty">
							</div>
							
							<div class="col-md-2">
								<label>Board Adjustment Amount</label>
								<form:input type="text" name="board_adj_amt" id="board_adj_amt" value="0"
									 readonly="true" class="form-control" path="bill_adj_amount"
									placeholder="Board Amount"></form:input>
							</div>
							<div hidden="true"><input type="text" id="board_adj_id" name="board_adj_id" value="0"></div>
							<div class="col-md-2">
								<label>Penalty Adjustment Amount</label>
								<form:input type="text" name="penalty_adj_amount" path="penalty_adj_amount" id="penalty_adj_amount1" onchange="getInstallmentAmount();" 
									 required="required" readonly="true"
									class="form-control" placeholder="Penalty Adjustment" value="0"></form:input>
							</div>
							
							<div class="col-md-2">
								<label>Total To be Paid</label>
								<input type="text" name="total_to_be_paid" id="total_to_be_paid" value="0"
									 readonly="readonly" class="form-control"
									placeholder="Total To be Paid">
							</div>
							<div class="col-md-2">
								<label>Total Paid</label>
								<form:input type="text" name="amount" path="amount" id="paidamount" required="required"
									 class="form-control"
									placeholder="Total Paid" onchange="changeRemBalance();"></form:input>
							</div>
							
							<div class="col-md-2">
								<label>REM BAL</label>
								<input type="text" name="rem_balance" id="rem_balance" value="0"
									 readonly="readonly" class="form-control"
									placeholder="REM BAL">
							</div>
							
							
							
							<div class="col-md-2">
								<label>Pay Towards:</label>
								<form:select data-placeholder="Pay Towards" name="towards" path="towards"
									id="towards1" class="select">
									<form:option value="BOARD PAYMENT">BOARD PAYMENT</form:option>
								</form:select>
							</div>

							<div class="col-md-2">
								<label>Payment Mode:</label>
								<form:select data-placeholder="Payment Mode" name="payMode" path="payMode"
									id="payMode1"  class="select">
									<form:option value="1">Cash</form:option>
									<form:option value="2">Cheque</form:option>
									<form:option value="3">DD</form:option>
									
								</form:select>
							</div>

							
							<div class="col-md-2">
								<label>Bank Name</label>
								<form:input type="text" class="form-control" name="bankname" path="bankname"
									id="bankname1"  placeholder="Bank Name"></form:input>
							</div>

							<div class="col-md-2">
								<label>Cheque/DD No</label>
								<form:input type="text" class="form-control" name="cdno" path="cdno"
									id="cdno1" placeholder="Cheq/DD No"></form:input>
							</div>

							<div class="col-md-2">
								<label>Cheq/DD Date Nepali</label>
								<div class="input-group">
									<span class="input-group-addon"><i
										class="icon-calendar3"></i></span>
									<input name="nepali_date" 
										placeholder="Cheq/DD Date" id="receiptdate_Nep2"
										class="form-control nepali-calendar" />
								</div>
							</div>
							
							<div class="col-md-1">
								<label></label>
								<div class="input-group">
									
								</div>
							</div>
							
							
							
							
							<div class="col-md-2" style="margin-top:7px;">
								<label></label>
								<div class="input-group">
									<button class="btn btn-warning" type="button" 
										id="boardadjustmentbutton" onclick="return boardadjustment()">Edit Board Balance</button>
								</div>
							</div>
							
							<div class="col-md-1" style="margin-left: -62px; margin-top:7px;">
								<label></label>
								<div class="input-group">
									<button class="btn btn-info" type="button"
									id="clearbutton" onclick="return clearField()">Clear</button>
								</div>
							</div>
							
							<div class="col-md-1" style="margin-left: -40px; margin-top:7px;">
								<label></label>
								<div class="input-group">
									<button class="btn btn-primary" type="submit" tabindex="3" 
							id="submitId2" onclick="return validateFieldBoard()">Submit</button>
								</div>
							</div>
							
							<div class="col-md-1" style="margin-left: -30px; margin-top:7px;">
								<label></label>
								<div class="input-group">
									<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" 
							data-target="#modal_default1" id="onshow_callback" 
							onclick='ledgerBoardHistoryPopUp()'>View Board Ledger</button>
								</div>
							</div>
							
							<div class="col-md-2" style="margin-left: 94px;margin-top:20px;">
								<label style="color: red;">Note: Cancel Receipt option is not there for Board Collection please check it before post</label>
							</div>
					
																
									
							

						</form:form>
					</fieldset>
						
						</div>
						
						
						
						
						
						<legend class="text-semibold" style="margin-bottom: 4px;">
						</legend>
					<fieldset>
					
					
						<legend class="text-semibold">
							<div class="form-group col-md-3">
							<i class="icon-cabinet"></i>&nbsp;&nbsp;&nbsp;Today's Payment History 
							</div>
							<div class="form-group col-md-2 input-xs">
							<button type="button" class="btn btn-warning" data-toggle="modal" style="text-decoration: blink; float: right;"
									data-target="#modal_default2" id="onshow_callback1" 
							 onclick='myPaymentHistoryPopUp()'>View Today's Payment History </button>
							</div>	
									
						</legend>
						
						
						<legend class="text-semibold">
							<i class="icon-reading position-left"></i>Customer Details
						</legend>

						<div class="form-group col-md-2 input-xs">
							<label>Name:</label> <input type="text" class="form-control"
								id="name_eng" placeholder="Name" readonly="readonly">
						</div>
						
						<div class="form-group col-md-2 input-xs" hidden="true">
							<label>Pipe Size:</label> <input type="text" class="form-control"
								id="pipesize" placeholder="" readonly="readonly">
						</div>

						<div class="form-group col-md-2 input-xs">
							<label>Ledger No:</label> <input type="text" id="ledgerno"
								readonly="readonly" placeholder="Ledger No" class="form-control">
						</div>

						<div class="form-group col-md-2 input-xs">
							<label>Folio No:</label> <input type="text" id="folio"
								readonly="readonly" placeholder="Folio No" class="form-control">
						</div>
						<div class="form-group col-md-2 input-xs">
							<label>Address:</label>
							<textarea rows="1" cols="3" id="address_eng" class="form-control"
								readonly="readonly" placeholder="Enter your address here"></textarea>
						</div>

						<div class="form-group col-md-2 input-xs">
							<label>Ward No</label> <input type="text" id="ward_no"
								class="form-control" readonly="readonly" placeholder="Ward No">
						</div>

						<div class="form-group col-md-2 input-xs">
							<label>Area</label> <input type="text" id="area_no"
								class="form-control" readonly="readonly" placeholder="Area">
						</div>
					</fieldset>
					<fieldset>
						<legend class="text-semibold" style="margin-bottom: 10px;">
							<i class="icon-reading position-left"></i> Bill Summary
						</legend>
						<div class="col-md-2">
							<div class="form-group">

								<label>Bill Date:</label>
								<div class="input-group">
									<span class="input-group-addon"><i
										class="icon-calendar3"></i></span> <input type="text" id="rdng_date"
										class="form-control daterange-single">
								</div>
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Total Payable:</label> <input type="text"
									placeholder="Total Payable" id="totalpayable" class="form-control">
							</div>
						</div>



						<div class="col-md-2">
							<div class="form-group">
								<label>Water Charge:</label> <input type="text"
									placeholder="Water Charge" id="water_charges"
									class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Sewage Charge:</label> <input type="text"
									placeholder="Sewage Charge" id="sw_charges"
									class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Meter Rent:</label> <input type="text"
									placeholder="Meter Rent" id="mtr_rent" class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Additional Charges:</label> <input type="text"
									placeholder="Additional Charges" id="additional_charges"
									class="form-control">
							</div>
						</div>



						<div class="col-md-2">
							<div class="form-group">
								<label>Rebate:</label> <input type="text" placeholder="Rebate"
									id="rebate" class="form-control">
							</div>
						</div>


						<div class="col-md-2">
							<div class="form-group">
								<label>Penality:</label> <input type="text"
									placeholder="Penality" id="penalty_10" class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Others:</label> <input type="text" placeholder="Others"
									id="other" class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Net Amount:</label> <input type="text"
									placeholder="Net Amount" id="net_amount" class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>KuklPayable:</label> <input type="text"
									placeholder="KuklPayable" class="form-control">
							</div>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>BoardPayable:</label> <input type="text" id="boardpayable"
									placeholder="BoardPayable" class="form-control">
							</div>
						</div>

					</fieldset>
					<%-- <fieldset>
						<legend class="text-semibold">
							<i class="icon-reading position-left"></i> Cash Summary
						</legend>

						<div class="form-group col-md-2">
							<label>No of Cash Receipts:</label> <input type="text"
								value="${cashsummery[0].cashcount}" class="form-control"
								placeholder="No of Cash Receipts">
						</div>


						<div class="form-group col-md-2">
							<label>No of CH/DD Receipts:</label> <input type="text"
								value="${cashsummery[0].cdcount}" class="form-control"
								placeholder="No of CH/DD Receipts">
						</div>

						<div class="form-group col-md-2">
							<label>Total Receipts:</label> <input type="text"
								value="${cashsummery[0].totalcount}" class="form-control"
								placeholder="Total Receipts">
						</div>

						<div class="form-group col-md-2">
							<label>Amount By Cash:</label> <input type="text"
								value="${cashsummery[0].cashamount}" class="form-control"
								placeholder="Amount By Cash">
						</div>

						<div class="form-group col-md-2">
							<label>Amount By CH/DD:</label> <input type="text"
								value="${cashsummery[0].cdamount}" class="form-control"
								placeholder="Amount By CH/DD">
						</div>

						<div class="form-group col-md-2">
							<label><b>Total Amount</b>:</label> <input type="text"
								value="${cashsummery[0].totalAmount}" class="form-control"
								placeholder="Total Amount">
						</div>
					</fieldset>
					<fieldset>
						<legend class="text-semibold"
							style="margin-bottom: 8px; margin-top: -23px;">
							<i class="icon-reading position-left"></i>Last Receipts Drawn
						</legend>

						<div class="form-group col-md-2">
							<label>Connection No:</label> <input type="text"
								value="${prevPayment.connectionNo}" readonly="readonly"
								class="form-control" placeholder="Connection No">
						</div>


						<div class="form-group col-md-2">
							<label>Receipt No:</label> <input type="text"
								value="${prevPayment.receiptNo}" readonly="readonly"
								class="form-control" placeholder="Receipt No">
						</div>

						<div class="form-group col-md-2">
							<label>Amount:</label> <input type="text"
								value="${prevPayment.amount}" readonly="readonly"
								class="form-control" placeholder="Amount">
						</div>

					</fieldset>
 --%>
				</div>
			</div>


		</div>
	</div>
</div>

				<!-- Basic modal -->
				<div id="modal_default" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title"><font color="red"><span id="connectionidspan"></span></font></h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment">
									<thead>
										<tr>
											<!-- <th>CON No.</th> -->
											<th></th>
											<th>MY</th>
											<th>CR</th>
											<th>LR</th>
											<th>U</th>
											<th>OB</th>
											<th>WC</th>
											<th>SC</th>
											<th>MR</th>
											<th>NT</th>
											<th style="color: red;">PE</th>
											<th style="color: green;">RB</th>
											<th>Misc</th>
											<th>Badj</th>
											<th>Padj</th>
											<th>Acorr</th>
											<th>Pcorr</th>
											<th>paid</th>
										    <th>CB</th>
											<th>RNo</th>
											<th>RDate</th>
											<th>BillNo</th>
											
										</tr>
									</thead>
									<tbody id="viewPayHistotytbody">
										
									</tbody>
								</table>
								
								
							</div>
							
							
							
							
							

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				
				
				
				
				<!-- Basic modal -->
				<div id="modal_default1" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Board Ledger History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment1">
									<thead>
										<tr>
											<th></th>
											<th style="color: red;">Installment&nbsp;Amount</th>
											<th style="color: red;">Penalty</th>
											<th style="color: red;">Remaining&nbsp;Balance</th>
											<th style="color: red;">Submit&nbsp;By</th>
											<th style="color: red;">Submit&nbsp;Date</th>
										</tr>
									</thead>
									<tbody id="viewPayHistotytbody1">
										
									</tbody>
								</table>
								
								
							</div>
							
							
							
							
							

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				
				
				
				<!-- Basic modal -->
				<div id="modal_default2" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">My Payment History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment2">
									<thead>
										<tr>
											<th></th>
											<th style="color: blue;">Con&nbsp;No</th>
											<th style="color: blue;min-width: 180px;">Name</th>
											<th style="color: blue;">Ward</th>
											<th style="color: blue;">Arrears</th>
											<th style="color: blue;">WC</th>
											<th style="color: blue;">SWC</th>
											<th style="color: blue;">MR</th>
											<th style="color: blue;">NET</th>
											<th style="color: blue;">MISC</th>
											<th style="color: blue;">PE</th>
											<th style="color: blue;">REB</th>
											<th style="color: blue;">BAdj</th>
											<th style="color: blue;">PAdj</th>
											<th style="color: blue;">PAID</th>
											<th style="color: blue;min-width: 200px;">Date</th>
											<th style="color: blue;">RNO</th>
										</tr>
									</thead>
									<tbody id="viewPayHistotytbody2">
										
									</tbody>
								</table>
								
								
							</div>
							
							
							
							
							

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				
				
				
				
				

<script>
var global_totaltobepaid=0;


function getInstallmentAmount(){
	
	var board_balance1=$('#board_balance1').val();
	var no_of_installments=$('#no_of_installments').val();
	var penalty_adj_amount1=$('#penalty_adj_amount1').val();
	var board_adj_amt=$('#board_adj_amt').val(); 
	if(board_balance1=="" || board_balance1==null || $.isNumeric(board_balance1)==false){
		$('#board_balance1').val(0);
		board_balance1=0;
	}
	if(no_of_installments=="" || no_of_installments==null || $.isNumeric(no_of_installments)==false){
		no_of_installments=1;
		$('#no_of_installments').val(1);
	}
	if(penalty_adj_amount1=="" || penalty_adj_amount1==null || $.isNumeric(penalty_adj_amount1)==false){
		penalty_adj_amount1=0;
		$('#penalty_adj_amount1').val(0);
	}
	if(board_adj_amt=="" || board_adj_amt==null || $.isNumeric(board_adj_amt)==false){
		board_adj_amt=0;
		$('#board_adj_amt').val(0);
	}
	
	var board=parseFloat(board_balance1);
	var noins=parseFloat(no_of_installments);
	
	var inst=board/noins;
	var penaltyamount=inst*0.5;
	
	$('#inst_board_balance').val(parseFloat(inst).toFixed(2));
	$('#inst_penalty').val(parseFloat(penaltyamount).toFixed(2));
	$('#total_to_be_paid').val(parseFloat(parseFloat(penaltyamount)+parseFloat(inst)+parseFloat(penalty_adj_amount1)+parseFloat(board_adj_amt)).toFixed(2));
	$('#rem_balance').val(parseFloat(parseFloat(board)-parseFloat(inst)).toFixed(2));
	
	if(noins==1){
		$("#paidamount").val("");
		$("#paidamount").prop("readonly", false);
	} else{
		$("#paidamount").val(parseFloat(parseFloat(penaltyamount)+parseFloat(inst)+parseFloat(penalty_adj_amount1)+parseFloat(board_adj_amt)).toFixed(2));
		$("#paidamount").prop("readonly", true);
		
	}
	
	
}

 var paymentByMonth="";
 function calculatePenaltyR(){
	
	var con_no=$('#connectionNo').val();
	var yearnep=$('#yearnep').val();
	var monthnep=$('#monthnep').val();
	
	
	if(bymonthyearv!=null && bymonthyearv!=''){
		
	    var to_mon_year1y=yearnep+""+monthnep;
		
		var bill_adj_amount = $('#bill_adj_amount').val();
		var penalty_adj_amount = $('#penalty_adj_amount').val();

		if (bill_adj_amount == "" || bill_adj_amount == null) {
			bill_adj_amount = 0;
			
		}
		if (penalty_adj_amount == "" || penalty_adj_amount == null) {
			penalty_adj_amount = 0;
			
		}
		
		if(bill_adj_amount==0 && penalty_adj_amount==0){
			
			
		}else{
			
			if(parseInt(bymonthyearv)!=parseInt(to_mon_year1y)){
				
				alert("Payment By Month Selection is not applicable if Adjustments applicable!!");
				getLastPaidUpto(bymonthyearv);
				return false;
			}
		}
		
	}
	

	
	if(con_no=="" || con_no==null){
		alert("Please enter Connection No then Calculate");
		return false;
	}else if(yearnep=="" || yearnep==null){
		alert("Please Select To Year then Calculate");
		return false;
	}else if(monthnep=="" || monthnep==null){
		alert("Please Select To Month then Calculate");
		return false;
	}else{
		
		var yearmntfr=parseInt($('#from_mon_year').val());
		var yearmntto=parseInt(yearnep+""+monthnep);
		var latestNepaliMonthS="${latestNepaliMonthS}";
		var latestNepaliMonth=parseInt("${latestNepaliMonthS}");
		var mnth ="";
		
		var bill_adj_amount=$('#bill_adj_amount').val();
		var penalty_adj_amount=$('#penalty_adj_amount').val();
		
		
		$('#amount').val("");
		
		if(bill_adj_amount=="" || bill_adj_amount==null){
			bill_adj_amount=0;
			$('#bill_adj_amount').val(0);
		}
		if(penalty_adj_amount=="" || penalty_adj_amount==null){
			  penalty_adj_amount=0;
			$('#penalty_adj_amount').val(0);
		}
	   
		if(yearmntto>latestNepaliMonth){
  		   
			mnth =latestNepaliMonthS.substring(4, 6);
			$('#monthnep').val(mnth).trigger("change");
			alert("Sorry You can't select Future Ledger Month & Latest Ledger Month Code is : "+mnth);
			return false;
  	    }
		
		
		if(yearmntfr<=yearmntto){
			$.ajax({
				  type: "GET",
				  url: "./getPenaltyAndRebateByMonthYear/"+con_no,
				  dataType: "json",
				  data:{
					  yearmntfr:yearmntfr,
					  yearmntto:yearmntto,
					  
				  },
				  async       : false,
				  cache       : false,
				  success: function(response){
					  
					  if(response[0]!=null && response[0]!=""){
					 
						  var data2=response[0];
						  
						  if(data2==null){
							  alert("Please update the Ledger First and Collect the Payment for Connection No. : " +connectionno);
							  $('#connectionNo').val("");
							  $('#customerName').val("");
						  }else{
						    var rec=data2.receipt_no;
						    if(rec==null || rec==""){
								
						    	  if(nclamt>0){
						    	     
						    		 alert("Sorry Payment By Month Selection is applicable only one time with in a month(Partial Payment)");
						    		 
						    		 $("#connectionNo").val("");
						    		 $("#customer_id").val("");
						    		 $("#customerName").val("");
						    	     $("#balance_amount").val(0);
						    	     $("#old_balance").val(0);
						    	     $("#water_charges1").val(0);
						    	     $("#sw_charges1").val(0);
						    	     $("#miscellaneous_cost").val(0);
									 $("#frecamount").val(0);
									 $("#totalpayable").val(0);
									 $("#amountbill").val(0);
									 $("#penalty").val(0);
									 $("#rebatenew").val(0);
									 return false;
						    		  
									 
						    	  }else{
						    		  
						    		  $("#amountbill").val(data2.net_amount==null?parseFloat(0).toFixed(2):parseFloat(data2.net_amount).toFixed(2));
						    		  $("#balance_amount").val(data2.arrears==null?0:parseFloat(data2.arrears).toFixed(2));
									  
									  var frecamount=parseFloat(data2.net_amount)+parseFloat(response[1])+parseFloat(bill_adj_amount)+parseFloat(penalty_adj_amount)-parseFloat(response[2]);
									  $("#frecamount").val(parseFloat(frecamount).toFixed(2));
									  $("#totalpayable").val(parseFloat(frecamount).toFixed(2));
									  $("#old_balance").val(0);
						    	  }
						    	
						    	  $("#water_charges1").val(data2.water_charges==null?parseFloat(0).toFixed(2):parseFloat(data2.water_charges).toFixed(2));
								  $("#sw_charges1").val(data2.sw_charges==null?parseFloat(0).toFixed(2):parseFloat(data2.sw_charges).toFixed(2));
								  $("#mtr_rent1").val(data2.mtr_rent==null?0:parseFloat(data2.mtr_rent).toFixed(2));
								  
								  alert("Amount is Calculated");
								  $("#penalty").val(parseFloat(response[1]).toFixed(2));
								  $("#rebatenew").val(parseFloat(response[2]).toFixed(2));
								  global_rebate=parseFloat(response[2]).toFixed(2);
								  //alert("Global Reabate in payment by month selection=="+global_rebate);
								  
							      paymentByMonth="PBM";
							      $('#amount').val("");
								  $('#rbalance').val(0);
								  $('#advance').val(0);
								  $('#advance_rebate').val(0);
							}else{
								
								 alert("Payment already received for the Latest Month Ledger");
								
								 $("#amountbill").val(0);
					    	     $("#balance_amount").val(0);
					    	     $("#old_balance").val(parseFloat(response[3]).toFixed(2));
					    	     $("#penalty").val(0);
					    	     $("#rebatenew").val(0);
					    	     $("#water_charges1").val(0);
								 $("#sw_charges1").val(0);
								 $("#meter_rent1").val(0);
								 $("#frecamount").val(parseFloat(response[3]).toFixed(2));
								
							}
							  
						  	  $('#to_mon_year').val(yearnep+""+monthnep);
							  getLastPaidUpto(yearnep+""+monthnep);
						  }
					  
					  }else{
						  
						  swal({
				                title: "Ledger is not Generated for Connection No:"+con_no+" in the Selected To Month ",
				                text: "",
				                confirmButtonColor: "#2196F3",
				            });
						  $("#connectionNo").val("");
						  return false;
					  }
					  
				  },
			     error: function (xhr) {
			 
				  },
				});
			
			
			$('#rebatenew').attr('readonly', true);
			var branch="${branchcode}";
			   /* if(branch=='1119' || branch=='1117' || branch=='2222'){
				   $('#rebatenew').attr('readonly', false);
				   $('#penalty').attr('readonly', false);
			   } */
			
		}else{
			
			alert("To Month Year should be greater than or equal to From Month Year");
			return false;
		}
		
	}
	
} 


 function calculatePenaltyRNew(){
	
	var con_no=$('#connectionNo').val();
	var yearnep=$('#yearnep').val();
	var monthnep=$('#monthnep').val();

	
	if(con_no=="" || con_no==null){
		alert("Please enter Connection No then Calculate");
		return false;
	}else if(yearnep=="" || yearnep==null){
		alert("Please Select To Year then Calculate");
		return false;
	}else if(monthnep=="" || monthnep==null){
		alert("Please Select To Month then Calculate");
		return false;
	}else{
		
		var yearmntfr=parseInt($('#from_mon_year').val());
		var yearmntto=parseInt(yearnep+""+monthnep);
		var latestNepaliMonthS="${latestNepaliMonthS}";
		var latestNepaliMonth=parseInt("${latestNepaliMonthS}");
		var mnth ="";
		
		var bill_adj_amount=$('#bill_adj_amount').val();
		var penalty_adj_amount=$('#penalty_adj_amount').val();
		
		
		$('#amount').val("");
		
		if(bill_adj_amount=="" || bill_adj_amount==null){
			bill_adj_amount=0;
			$('#bill_adj_amount').val(0);
		}
		if(penalty_adj_amount=="" || penalty_adj_amount==null){
			  penalty_adj_amount=0;
			$('#penalty_adj_amount').val(0);
		}
	   
		if(yearmntto>latestNepaliMonth){
  		   
			mnth =latestNepaliMonthS.substring(4, 6);
			$('#monthnep').val(mnth).trigger("change");
			alert("Sorry You can't select Future Ledger Month & Latest Ledger Month Code is : "+mnth);
			return false;
  	    }
		
		
		if(yearmntfr<=yearmntto){
			$.ajax({
				  type: "GET",
				  url: "./getPenaltyAndRebateByMonthYearNew/"+con_no,
				  dataType: "json",
				  data:{
					  yearmntfr:yearmntfr,
					  yearmntto:yearmntto,
					  
				  },
				  async       : false,
				  cache       : false,
				  success: function(response){
					  
					  if(response[0]!=null && response[0]!=""){
					 
						  var data2=response[0];
						  
						  if(data2==null){
							  alert("Please update the Ledger First and Collect the Payment for Connection No. : " +connectionno);
							  $('#connectionNo').val("");
							  $('#customerName').val("");
						  }else{
						    var rec=data2.receipt_no;
						    if(rec==null || rec==""){
								
						    	  if(nclamt>0){
						    		 
						    		 alert("Sorry Payment By Month Selection is applicable only one time with in a month(Partial Payment)");
						    		 $("#connectionNo").val("");
						    		 $("#customer_id").val("");
						    		 $("#customerName").val("");
						    	     $("#balance_amount").val(0);
						    	     $("#old_balance").val(0);
						    	     $("#water_charges1").val(0);
						    	     $("#sw_charges1").val(0);
						    	     $("#miscellaneous_cost").val(0);
									 $("#frecamount").val(0);
									 $("#totalpayable").val(0);
									 $("#amountbill").val(0);
									 $("#penalty").val(0);
									 $("#rebatenew").val(0);
									 return false;
						    		  
									 
						    	  }else{
						    		  
						    		  $("#amountbill").val(response[6]);
						    		  $("#balance_amount").val(response[7]);
									  var frecamount=parseFloat(response[6])+parseFloat(response[1])+parseFloat(bill_adj_amount)+parseFloat(penalty_adj_amount)-parseFloat(response[2]);
									  $("#frecamount").val(parseFloat(frecamount).toFixed(2));
									  $("#totalpayable").val(parseFloat(frecamount).toFixed(2));
									  $("#old_balance").val(0);
						    	  }
						    	
						    	  $("#water_charges1").val(response[8]);
								  $("#sw_charges1").val(response[9]);
								  $("#mtr_rent1").val(response[10]);
								  
								  alert("Amount is Calculated");
								  $("#penalty").val(parseFloat(response[1]).toFixed(2));
								  $("#rebatenew").val(parseFloat(response[2]).toFixed(2));
								  
							      paymentByMonth="PBM";
							      $('#amount').val("");
								  $('#rbalance').val(0);
								  $('#advance').val(0);
								  $('#advance_rebate').val(0);
							}else{
								
								 alert("Payment already received for the Latest Month Ledger");
								
								 $("#amountbill").val(0);
					    	     $("#balance_amount").val(0);
					    	     $("#old_balance").val(parseFloat(response[3]).toFixed(2));
					    	     $("#penalty").val(0);
					    	     $("#rebatenew").val(0);
					    	     $("#water_charges1").val(0);
								 $("#sw_charges1").val(0);
								 $("#meter_rent1").val(0);
								 $("#frecamount").val(parseFloat(response[3]).toFixed(2));
								
							}
							  
						  	  $('#to_mon_year').val(yearnep+""+monthnep);
							  getLastPaidUpto(yearnep+""+monthnep);
						  }
					  
					  }else{
						  
						  swal({
				                title: "Ledger is not Generated for Connection No:"+con_no+" in the Selected To Month ",
				                text: "",
				                confirmButtonColor: "#2196F3",
				            });
						  $("#connectionNo").val("");
						  return false;
					  }
					  
				  },
			     error: function (xhr) {
			 
				  },
				});
			
			
			
			
		}else{
			
			alert("To Month Year should be greater than or equal to From Month Year");
			return false;
		}
		
	}
	
}







function getAdvanceAmount(paidAmount){
	
	var frecamount=parseFloat($("#frecamount").val());
    var paidamt=parseFloat(paidAmount);
	
	$("#tender_cash").val(paidAmount);
	$("#change").val(0);
	
	
	 var to_mon_year1=parseInt($("#to_mon_year").val());
	 var latestNepaliMonthS=parseInt("${latestNepaliMonthS}");
	 var payByMonth=paymentByMonth;
	 var crmnth="${currentNMonth}";

	    
	    if(frecamount<paidamt){
	    	var advanceamount=0;
	    	
	    	if(frecamount>0){
	    		
	    	   advanceamount=parseFloat((paidamt-frecamount)).toFixed(2);
	    	   
	    	   $("#advance").val(advanceamount);
				 var closebal=$("#old_balance").val();
		      	 $("#advance_rebate").val(parseFloat((advanceamount)*3/100).toFixed(2));
		   	 	 
				 var advanceamt=$("#advance").val();
				 var advanceramt=$("#advance_rebate").val();
				 $("#rbalance").val(-(parseFloat(parseFloat(advanceamt)+parseFloat(advanceramt)).toFixed(2)));
				 
	    	}else{
	    		advanceamount=parseFloat(paidamt).toFixed(2);
	    		$("#advance").val(advanceamount);
			    $("#advance_rebate").val(parseFloat((paidamt)*3/100).toFixed(2));
				 var advanceamt=$("#advance").val();
				 var advanceramt=$("#advance_rebate").val();
				 $("#rbalance").val(-(parseFloat(parseFloat(advanceamt)+parseFloat(advanceramt)-parseFloat(frecamount)).toFixed(2)));
	    	}
			 
			
		}else{
		   	 $("#advance").val(0);
		   	 $("#advance_rebate").val(0);
		   	 $("#rbalance").val(parseFloat(frecamount-paidamt).toFixed(2));
		}
	    
	    
	    
	    
	    /* Advance Payment By Month Selection Start*/
	    
	    //Newly Added

		   
	if (payByMonth == "PBM") {

			var diff = paidamt - frecamount;

			if (parseFloat(diff) < -1) {

				alert("Less Payment cannot be accepted for Payment By Month Selection & Total Paid to be NPR."
						+ frecamount);
				$('#amount').val("");
				$('#rbalance').val(0);
				$('#advance').val(0);
				$('#advance_rebate').val(0);
				return false;
			}

			if (parseFloat(diff) > 1) {

				var tomy = ("" + to_mon_year1).substr(0, 4);
				var cmy = ("" + crmnth).substr(0, 4);
				var advanceamount = parseFloat((paidamt - frecamount)).toFixed(
						2);
				$("#advance").val(advanceamount);

				var tom = ("" + to_mon_year1).substr(4, 6);
				var cm = ("" + crmnth).substr(4, 6);

				if (tomy == cmy) {
					var di = parseInt(cm) - parseInt(tom);

					if (di > 4) {
						$('#advance_rebate').val(0);
					} else {
						$("#advance_rebate").val(
								parseFloat((advanceamount) * 3 / 100)
										.toFixed(2));

					}

				} else {

					var chmnthy = "";
					if (cm == '01') {
						var nmonthYearNep = (parseInt(cmy) - 1) + "12";
						chmnthy = "" + (parseInt(nmonthYearNep) - 2);
					} else if (cm == '02') {
						var nmonthYearNep = (parseInt(cmy) - 1) + "12";
						chmnthy = "" + (parseInt(nmonthYearNep) - 1);
					} else if (cm == '03') {
						var nmonthYearNep = (parseInt(cmy) - 1) + "12";
						chmnthy = "" + (parseInt(nmonthYearNep));
					} else {
						chmnthy = "" + (parseInt(crmnth) - 3);
					}

					var dif = parseInt(chmnthy) > parseInt(to_mon_year1);

					if (dif) {
						$('#advance_rebate').val(0);
					} else {
						$("#advance_rebate").val(
								parseFloat((advanceamount) * 3 / 100)
										.toFixed(2));
					}

				}
			}
			if (to_mon_year1 > latestNepaliMonthS) {
				$('#to_mon_year').val(latestNepaliMonthS);
				getLastPaidUpto("" + latestNepaliMonthS);
			}
		}

		/* Advance Payment By Month Selection End */

		// NEW REBATE ENABLE FUNCTION
		
		

		// END OF NEW REBATE ENABLE

		//End Newly Added

		//Old Logic
		/* var amountbill=parseFloat($("#amountbill").val()).toFixed(2);
		var penalty=parseFloat($("#penalty").val()).toFixed(2);

		if((paidamt-frecamount)>0){
			
			if(penalty>0 && (paidamt-amountbill)>0){
		   	 $("#rebatenew").val(((paidamt-frecamount)*3/100));
			 //$("#advance").val((paidamt-frecamount-penalty)+((paidamt-frecamount)*3/100));
			 $("#advance").val(parseFloat((paidamt-frecamount)).toFixed(2));

			}else{
			 //$("#rebatenew").val((paidAmount*3/100));
			 //$("#advance").val((paidamt-frecamount-penalty)+(paidAmount*3/100));
			 $("#advance").val((paidamt-frecamount).toFixed(2));

			}

		}else{
			$("#advance").val(0)
		} */

		//End Old Logic
	}

	function getChangeAmount(takenAmount) {
		var paidAmount = parseFloat($("#amount").val()).toFixed(2);
		var takenamt = parseFloat(takenAmount);
		if ((takenamt - paidAmount) > 0) {
			$("#change").val((takenamt - paidAmount));
		} else {
			$("#change").val(0);
		}
	}

	function getFinalReceivableAmount() {

		var bill_adj_amount = $('#bill_adj_amount').val();
		var penalty_adj_amount = $('#penalty_adj_amount').val();
		var rebate_adj_amount = $('#rebate_adj_amount').val();

		if (bill_adj_amount == "" || bill_adj_amount == null) {
			bill_adj_amount = 0;
			$('#bill_adj_amount').val(0);
		}
		if (penalty_adj_amount == "" || penalty_adj_amount == null) {
			penalty_adj_amount = 0;
			$('#penalty_adj_amount').val(0);
		}
		if (rebate_adj_amount == "" || penalty_adj_amount == null) {
			rebate_adj_amount = 0;
			$('#rebate_adj_amount').val(0);
		}

		var branchcode = "${branchcode}";

		var bcodecheck = branchcode.indexOf("1112") > -1;
/* 		if (bcodecheck) {

			var amountbill = $("#amountbill").val();
			var penalty = $("#penalty").val();
			var rebatenew = $("#rebatenew").val();
			var old_balance = $("#old_balance").val();
			var miscellaneous_cost = $("#miscellaneous_cost").val();
			
			if($.isNumeric(miscellaneous_cost)==false){
				miscellaneous_cost=0;
				$("#miscellaneous_cost").val(0);
			}
			
			if(parseFloat(miscellaneous_cost)<0){
				alert("Miscellaneous Cost Should be greater than zero");
				$("#miscellaneous_cost").val(0);
			}

			if (parseFloat(old_balance) > 0 || parseFloat(old_balance) < 0) {
				$("#frecamount").val(
						parseFloat(
								parseFloat(old_balance) + parseFloat(penalty)
										- parseFloat(rebatenew)
										+ parseFloat(miscellaneous_cost)
										+ parseFloat(bill_adj_amount)
										+ parseFloat(penalty_adj_amount))
								.toFixed(2));
			} else {
				$("#frecamount").val(
						parseFloat(
								parseFloat(amountbill)
										+ parseFloat(old_balance)
										+ parseFloat(penalty)
										- parseFloat(rebatenew)
										+ parseFloat(miscellaneous_cost)
										+ parseFloat(bill_adj_amount)
										+ parseFloat(penalty_adj_amount))
								.toFixed(2));
			}

		} else { */

			var amountbill = $("#amountbill").val();
			var old_balance = $("#old_balance").val();
			var penalty = $("#penalty").val();
			var rebatenew = $("#rebatenew").val();
			var miscellaneous_cost = $("#miscellaneous_cost").val();
			
			
			if($.isNumeric(miscellaneous_cost)==false){
				miscellaneous_cost=0;
				$("#miscellaneous_cost").val(0);
			}
			
			if(parseFloat(miscellaneous_cost)<0){
				
				alert("Miscellaneous Cost Should be greater than zero");
				$("#miscellaneous_cost").val(0);
			}
			if(parseFloat(miscellaneous_cost)>50){
				
				alert("Miscellaneous Cost is too much! Please check.");
				$("#miscellaneous_cost").val(0);
			}

			if (parseFloat(old_balance) > 0 || parseFloat(old_balance) < 0) {
				$("#frecamount").val(
						parseFloat(
								parseFloat(old_balance) + parseFloat(penalty)
										- parseFloat(rebatenew)
										+ parseFloat($("#miscellaneous_cost").val())
										+ parseFloat(bill_adj_amount)
										+ parseFloat(penalty_adj_amount))
								.toFixed(2));
			} else {
				$("#frecamount").val(
						parseFloat(
								parseFloat(amountbill)
										+ parseFloat(old_balance)
										+ parseFloat(penalty)
										- parseFloat(rebatenew)
										+ parseFloat($("#miscellaneous_cost").val())
										+ parseFloat(bill_adj_amount)
										+ parseFloat(penalty_adj_amount)
										+ parseFloat(rebate_adj_amount))
								.toFixed(2));
			}
		//}

		$('#amount').val("");
		$('#rbalance').val(0);
		$('#advance').val(0);
		$('#advance_rebate').val(0);

		//Rebate Control
		/* if (parseFloat(rebatenew) > parseFloat(global_rebate)) {
			alert("You Cannot give Rebate more than " + global_rebate);
			$("#rebatenew").val(global_rebate);
			getFinalReceivableAmount();
		} else if (parseFloat(rebatenew) < 0) {
			alert("You Cannot give Negative Rebate.");
			$("#rebatenew").val(global_rebate);
			getFinalReceivableAmount();
		} */
		//End Rebate Control

	}

	 function clearField(){
			window.location.href="./cashCounterPayments";
		}
	 
	
	function ledgerBoardHistoryPopUp() {

		$("#viewPayHistotytbody1").empty();
		var connectionNo = $("#connectionNo1").val();

		if (connectionNo == null || connectionNo == "") {

		} else {

			var tableData = "";
			$.ajax({
				type : "GET",
				url : "./billing/viewBoardLedgertHistory/" + connectionNo,
				async : false,
				dataType : "JSON",
				success : function(response) {
					for (var s = 0, len = response.length; s < len; ++s) {
						var obj = response[s];

						tableData += "<tr>" + "<td>" + (s + 1) + "</td>"
								+ "<td>" + obj.install_amount + "</td>"
								+ "<td>" + obj.penalty + "</td>" + "<td>"
								+ obj.rem_balance + "</td>" + "<td>"
								+ obj.submit_by + "</td>" + "<td>"
								+ obj.submit_date + "</td>" + "</tr>";

					}
					$('#viewPayHistotytbody1').append(tableData);
					loadSearchFilter1('popupPayment1', tableData,
							'viewPayHistotytbody1');
				}

			});
		}
	}

	function myPaymentHistoryPopUp() {

		$("#viewPayHistotytbody2").empty();
		var counterno = "${counterNo}";
		var tableData = "";
		$.ajax({
			type : "GET",
			url : "./billing/viewMyPaymentHistory/" + counterno,
			async : false,
			dataType : "JSON",
			success : function(response) {
				for (var s = 0, len = response.length; s < len; ++s) {
					var obj = response[s];

					tableData += "<tr>" + "<td>" + (s + 1) + "</td>" + "<td>"
							+ obj.connection_no + "</td>" + "<td>"
							+ obj.name_eng + "</td>" + "<td>" + obj.ward_no
							+ "</td>" + "<td>" + obj.balance_amount + "</td>"
							+ "<td>" + obj.water_charges + "</td>" + "<td>"
							+ obj.sw_charges + "</td>" + "<td>"
							+ obj.meter_rent + "</td>" + "<td>"
							+ obj.bill_amount + "</td>" + "<td>"
							+ obj.miscellaneous_cost + "</td>" + "<td>"
							+ obj.penalty + "</td>" + "<td>" + obj.rebate
							+ "</td>" + "<td>" + obj.bill_adj_amount + "</td>"
							+ "<td>" + obj.penalty_adj_amount + "</td>"
							+ "<td>" + obj.amount + "</td>" + "<td>"
							+ obj.rdate + "</td>" + "<td>" + obj.receiptNo
							+ "</td>" + "</tr>";

				}

				$('#viewPayHistotytbody2').append(tableData);
				loadSearchFilter1('popupPayment2', tableData,
						'viewPayHistotytbody2');
			}

		});

	}

	function ledgerHistoryPopUp() {

		$("#viewPayHistotytbody").empty();
		var connectionNo = $("#connectionNo").val();
		var customer_id = $("#customer_id").val();
		var nameeng = $("#customerName").val();
		var area_no = $("#area_no").val();
		var pipesize = $("#pipesize").val();

		if (connectionNo == null || connectionNo == "") {

		} else {

			var tableData = "";
			$.ajax({
				type : "GET",
				url : "./billing/viewBillLedgertHistory/" + connectionNo,
				async : false,
				dataType : "JSON",
				success : function(response) {
					for (var s = 0, len = response.length; s < len; ++s) {
						var obj = response[s];

						tableData += "<tr>" + "<td>" + (s + 1) + "</td>"
								+ "<td>" + obj.monthyearnep + "</td>" + "<td>"
								+ obj.cr + "</td>" + "<td>" + obj.lr + "</td>"
								+ "<td>" + obj.units + "</td>" + "<td>"
								+ obj.lastarrears + "</td>" + "<td>"
								+ obj.water_charges + "</td>" + "<td>"
								+ obj.sw_charges + "</td>" + "<td>"
								+ obj.mtr_rent + "</td>" + "<td>"
								+ obj.net_amount + "</td>" + "<td>"
								+ obj.penalty + "</td>" + "<td>" + obj.rebate
								+ "</td>" + "<td>" + obj.misc + "</td>"
								+ "<td>" + obj.bill_adj + "</td>" + "<td>"
								+ obj.penalty_adj + "</td>" + "<td>"
								+ obj.arrear_correction + "</td>" + "<td>"
								+ obj.penalty_correction + "</td>" + "<td>"
								+ obj.receipt_amount + "</td>" + "<td>"
								+ obj.close_balnce + "</td>" + "<td>"
								+ obj.receipt_no + "</td>" + "<td>"
								+ obj.receipt_date + "</td>" + "<td>"
								+ obj.billno + "</td>" + "</tr>";

					}

					$('#connectionidspan').html(
							" Connection No : " + connectionNo
									+ " &nbsp;&nbsp;Customer ID: "
									+ customer_id
									+ "&nbsp;&nbsp;            Name : "
									+ nameeng + "   &nbsp;&nbsp;  Area No:"
									+ area_no
									+ "   &nbsp;&nbsp;  Pipe Size :  "
									+ pipesize);
					$('#viewPayHistotytbody').append(tableData);
					loadSearchFilter1('popupPayment', tableData,
							'viewPayHistotytbody');
				}

			});
		}

	}

	function setConsumerDataBoard(connectionno) {
		var board_adj = 0;
		var bamt = 0;

		$
				.ajax({
					type : "GET",
					url : "./getConsumerBoardAdjAmt/" + connectionno,
					dataType : "json",
					async : false,
					cache : false,
					success : function(response) {
						//alert(response);
						if (response.length == 0) {
							$("#board_adj_amt").val(0);
							//$("#board_adj_id").val(0);
							//alert($("#board_adj_id").val());
							return false;
						} else {
							if (response == 'PENDING') {
								alert("Board Adjustment is pending for Transaction Approval. After aproval you can pay!");
								window.location.reload();

							} else {
								$("#board_adj_amt").val(response.board_adj);
								$('#penalty_adj_amount1').val(response.penalty_adj_amount);
								board_adj = parseFloat(response.board_adj);
								$("#board_adj_id").val(response.id);
								//alert(response.id);
								if (board_adj > 0 || board_adj < 0) {
									$('#boardadjustmentbutton').hide();
								}
							}

						}

					}
				});

		$.ajax({
			type : "GET",
			url : "./getConsumerMasterDetails1/" + connectionno,
			dataType : "json",
			async : false,
			cache : false,
			success : function(response) {

				if (response.length == 0) {
					alert("Connection No does not Exist");
					$("#connectionNo1").val("");
					return false;
				} else {

					var data1 = response[0];
					$("#connectionNo1").prop("readonly",true);
					$("#customerName1").val(data1.name_eng);
					$("#customerid1").val(data1.customer_id);
					$("#area_num1").val(data1.area_no);
					$("#inst_nepali_date").val(data1.install_due_date);

					$("#con_type1").val(data1.con_type);
					$("#con_category1").val(data1.con_category);

					bamt=data1.balance==null?0:data1.balance;
					  
					$("#board_balance1").val(data1.balance==null?0:data1.balance);
					$('#inst_board_balance').val(parseFloat(bamt).toFixed(2));
					var inspen=parseFloat(bamt*0.5);
					var pen_adj=$('#penalty_adj_amount1').val();
					pen_adj=parseFloat(pen_adj==null?0:pen_adj);
					$('#inst_penalty').val(parseFloat(bamt*0.5).toFixed(2));
					$('#total_to_be_paid').val(parseFloat(bamt+inspen+board_adj+pen_adj).toFixed(2));
					global_totaltobepaid=parseFloat(bamt+inspen+board_adj+pen_adj).toFixed(2);
					$("#setward_no1").val(data1.ward_no);
					$("#setreading_day1").val(data1.reading_day);
					$("#setpipe_size1").val(data1.pipe_size);
				}

			}

		});
		if (board_adj == null || board_adj == "") {

		} else {
			if (bamt == (-(board_adj))) {
				alert("BOARD Adjustment Approval is pending in Head Office.");
				$('#total_to_be_paid').val(0);
				return false;
			}
		}

	}

	function loadSearchFilter1(param, tableData, temp) {
		$('#' + param).dataTable().fnClearTable();
		$('#' + param).dataTable().fnDestroy();
		$('#' + temp).html(tableData);
		$('#' + param).dataTable();

	}

	function convertToUpperCase() {

		$("#connectionNo").val($("#connectionNo").val().toUpperCase().trim());

	}

	function convertToUpperCase1() {

		$("#connectionNo1").val($("#connectionNo1").val().toUpperCase().trim());

	}

	/* function blinker() {
	 $('#onshow_callback1').fadeOut(300);
	 $('#onshow_callback1').fadeIn(300);
	 }
	 setInterval(blinker, 1000); */
	 
		 
</script>		
				
<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.row {
	margin-left: 0px;
	margin-right: -10px;
}

.input-xs {
	padding: 1px 15px;
}


.modal.fade .modal-dialog {
width: 1260px;
}

.modal-body {
    position: relative;
    padding: 0px;
}

.datatable-header, .datatable-footer {
    padding: 5px 20px 0 20px;
}

body {
    font-family: "Roboto", Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 12px;
    line-height: 1.5384616;
    color: #333333;
}

.blink_text {

    animation:1s blinker linear infinite;
    -webkit-animation:1s blinker linear infinite;
    -moz-animation:3s blinker linear infinite;

     color: red;
    }

    @-moz-keyframes blinker {  
     0% { opacity: 1.0; }
     50% { opacity: 0.0; }
     100% { opacity: 1.0; }
     }

    @-webkit-keyframes blinker {  
     0% { opacity: 1.0; }
     50% { opacity: 0.0; }
     100% { opacity: 1.0; }
     }

    @keyframes blinker {  
     0% { opacity: 1.0; }
     50% { opacity: 0.0; }
     100% { opacity: 1.0; }
     }

</style>


