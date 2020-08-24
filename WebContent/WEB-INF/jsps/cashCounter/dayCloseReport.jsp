<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">
var counterNo="";
var dayclosenepali="";
$(document).ready(function(){   
	$('#umpayments').show();
	$('#paymentsModule').addClass('active');
	
 	 $('#closeDatenep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			onChange: function(){
				var rdngdtnep = $('#closeDatenep').val();
				getEngDate(rdngdtnep,1);
			}
	}); 
 	 
 	$('#closeDatenep1').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			onChange: function(){
				var rdngdtnep = $('#closeDatenep1').val();
				getEngDate(rdngdtnep,2);
			}
	}); 
	
	 counterNo ='<%=(Integer)session.getAttribute("counterNo") %>';			
});


function getEngDate(nepalidate,value){
	
	var date_nep=nepalidate;
	dayclosenepali=nepalidate;
	
	$.ajax({
		  type: "GET",
		  url: "./billing/onChangeNepaliDate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
			 date_nep:date_nep,
			  
		  },
		  
		  success: function(response){
			  
			       if(value==1){
				   $('#closeDate').val(response);
			    	   
			       }else{
			    	   $('#closeDate1').val(response); 
			       }
		  } 
		});
	
}




	</script>
<c:if test = "${not empty msg}"> 			        
		        <script>		        
		            var msg = "${msg}";
		            swal({
		                title: msg,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
		        </script>
 </c:if>
<c:if test = "${not empty msgdayclose}"> 			        
		        <script>		        
		            var msg = "${msgdayclose}";
		            swal({
		                title: msg,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
		        </script>
 </c:if>
<div class="panel panel-flat">
						<div class="panel-body">
							<fieldset class="content-group"> 
								<legend class="text-bold">Day Close Report - [BILLING]</legend>
									<div class="row">
											
										 <div class="form-group col-md-3" >
										<label>Day Close (Nepali)</label>
					                          <div class="input-group"> 
				         						<span class="input-group-addon"><i class="icon-calendar3"></i></span>
									     		<input name="closeDatenep"  placeholder="Select Close Date..." id="closeDatenep"  class="form-control nepali-calendar" />
											</div>
				                         </div> 
											
										<div class="col-md-3">
												<div class="form-group" >
													<label>Day Close</label>
					                                <div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar3"></i></span>
													<input type="text"  name="closeDate" id="closeDate" class="form-control" readonly="readonly">
												</div>
				                                </div>
											</div>	
											<div class="col-md-3" id="addId" >
												<div class="form-group text-center" >
													<label>&nbsp;</label>
					                                <button type="button" onclick="return generateDayClosureReport();" class="btn bg-teal btn-ladda"  style="width: 200px; margin-top: 28px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Print Report</span></button>
				                                </div>
											</div>
										</div>
								</fieldset>	
								
						</div>	
						
						
								</div>
								
								
								
								<!-- New Connection Start -->
								
								
								
								
								<div class="panel panel-flat">
						<div class="panel-body">
							<fieldset class="content-group"> 
								<legend class="text-bold">Day Close Report - [MISCELLANEOUS PAYMENTS-NEW CONNECTION]</legend>
									<div class="row">
											
										 <div class="form-group col-md-3" >
										<label>Day Close (Nepali)</label>
					                          <div class="input-group"> 
				         						<span class="input-group-addon"><i class="icon-calendar3"></i></span>
									     		<input name="closeDatenep1"  placeholder="Select Close Date..." id="closeDatenep1"  class="form-control nepali-calendar" />
											</div>
				                         </div> 
											
										<div class="col-md-3">
												<div class="form-group" >
													<label>Day Close</label>
					                                <div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar3"></i></span>
													<input type="text"  name="closeDate1" id="closeDate1" class="form-control" readonly="readonly">
												</div>
				                                </div>
											</div>	
											<div class="col-md-3" id="addId" >
												<div class="form-group text-center" >
													<label>&nbsp;</label>
					                                <button type="button" onclick="return generateDayClosureReport1();" class="btn bg-teal btn-ladda"  style="width: 200px; margin-top: 28px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Print Report</span></button>
				                                </div>
											</div>
										</div>
								</fieldset>	
								
						</div>	
						
						
								</div>
								
								<!-- New Connection End -->

	<script>
	function calculateDiffrence(val){
		var totalAmount= $("#totalAmount").val();
		var diff=totalAmount-val;
		$("#difference").val(diff);
	}
	function getDatafordayclose(){   
		 $("#totalAmount").val("");
		 $("#cddate").val("");
		 $("#difference").val("");
		 $("#amount_in_hand").val("");
		var counterno=$("#counter_no").val();
		//var countername=$("#counter_name").val();
		var counterdate=$("#counterdate").val();
		var counterdatenep=$("#receiptdate_Nep").val();
		$.ajax({
			  type: "GET",
			  url: "./getdataforDayClose",
			  dataType: "JSON",
			  data:{
				  counterno : counterno,
				  counterdate : counterdate,
			  },
			  cache       : false,
			  async       : false,
			  success: function(response){
				  if(response!=null && response!=""){
				 $("#totalAmount").val(response[0].totalAmount);
				 $("#cddate").val(counterdate);
				 $("#finalsummary").show();
				  }else{
					  $("#totalAmount").val(0);
						 $("#cddate").val(counterdate);
						 $("#finalsummary").show();
				  }
			  },
		 error: function (xhr) {
			 swal({
	                title: "Unable To Get The Data",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });     
			  },
			});
		
		
	}
	
	
	var waterP="",drainageP="",otherP="",meterC="",meterChangeFine="",excuses="",totalP="",advanceP="",cashCollectionAmt="",amtInWords="",advancerebate="",oldbalance="",totalreceivable="",arrears=0;
	function generateDayClosureReport()
	{
		var date = $("#closeDate").val();
	
		if(date == "")
		{
			 swal({
	                title: "Please Select Closing Day",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });	
			 return false;
		}
		$.ajax({
			  type: "GET",
			  url: "./generateDayClosureReport",
			  dataType: "Json",
			  data:{date : date},
			  cache       : true,
			  async       : false,
			  success: function(response){
				 
				  if(response == "")
					 {
					  	response="0.0";
					 } 
				  var data="";
				  for(var i=0;i<response.length;i++){
					  data = response[i];
					  
					  waterP=data[0];
					  drainageP= data[1];
					  otherP=data[2];
					  meterC=data[3];
					  meterChangeFine=data[4];
					  excuses=data[5];
					  totalP=data[6];
					  advanceP=data[7];
					  cashCollectionAmt=data[8];
					  advancerebate=data[9];
					  oldbalance=data[10];
					  totalreceivable=data[11];
					  arrears=data[12];
					  numberToWords(data[6]); 
				  }
				  
				 /*  $("#wP").text(response);
				  waterP = response;
				  $("#tP").text(response);
				  totalP = response;
				  $("#cCA").text(response);
				  cashCollectionAmt = response;
				  numberToWords(cashCollectionAmt); */
				 test();
			},
		 	error: function (xhr) {
			},
			}); 
	}
	
	
	function numberToWords(amt)
	{
		$.ajax({
			  type: "GET",
			  url: "./numberToWords",
			  dataType: "text",
			  data:{date : amt},
			  cache       : true,
			  async       : false,
			  success: function(response){
				  if(response == "")
					 {
					  	response="--";
					 } 
				  amtInWords = response;
			},
		 	error: function (xhr) {
			},
			}); 
	}
	
	
	
	
	function test()
 {
	 
		
		 var branchcode="${branchcode}";
	
		
		 var branchname="${CBranchName}";
		 var nameOfficail="${userOfficialName}";
		 var userdesig="${usercdesignation}";
	
		var tcad=(parseFloat(advanceP))+(parseFloat(advanceP*(0.03)))+(parseFloat(totalP));
		var advpay=parseFloat(advanceP).toFixed(2);
		var advReb=parseFloat(advanceP*(0.03)).toFixed(2);
		
		
		if(waterP==0 && drainageP==0 && meterChangeFine==0){
			tcad=0;
			advpay=0;
			advReb=0;
			oldbalance=0;
		}
		
		totalreceivable=parseFloat(totalreceivable);
		arrears=parseFloat(arrears);
	 var printW = window.open("");
	 var html = "<div>";
  
	 html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "+branchname+"<br>Counter No.: "+counterNo+"<br>Date: "+dayclosenepali+"<br><u>Day Close Report</u></b></span><br/><br/>"+
											
  "<span style='font-size:16px;'>  Water Charges       	 <b>"+parseFloat(waterP).toFixed(2)+"</b><br/>"+
  "  Sewerage Charges    	 <b>"+parseFloat(drainageP).toFixed(2)+"<br/></b>"+
  "  Meter Charges	      	 <b>"+parseFloat(meterC).toFixed(2)+"<br/></b>"+
  "  Total Bill	      	 <b>"+parseFloat(waterP+meterC+drainageP).toFixed(2)+"<br/></b>"+
  "  Total Rebate        	 <b>"+parseFloat(excuses).toFixed(2)+"<br/></b>"+
  "  Net Bill		 <b>"+parseFloat(parseFloat(waterP+meterC+drainageP)-parseFloat(excuses)).toFixed(2)+"<br/></b>"+
  "  Total Penalty       	 <b>"+parseFloat(meterChangeFine).toFixed(2)+"<br/></b>"+
  "  Miscellaneous       	 <b>"+parseFloat(otherP).toFixed(2)+"<br/></b>"+
  "  Previous Month Arrear  <b>"+parseFloat(oldbalance).toFixed(2)+"<br/></b>"+
//  "  Total Receivable    <b>"+parseFloat(totalreceivable+arrears).toFixed(2)+"<br/></b>"+
  "  <b>Total Collection    	 <u>"+parseFloat(totalP).toFixed(2)+"<br/></u></b>"+
  "  Advance Payment     	 <b>"+parseFloat(advpay).toFixed(2)+"<br/></b>"+
  "  Advance Rebate      	 <b>"+parseFloat(advReb).toFixed(2)+"<br/></b>"+
//  "  Total(TP+AP+AR)<b>     "+parseFloat(tcad).toFixed(2)+"</b></span><br/><br/>"+/* cashCollectionAmt */
											
  "  (Amount in word: "+amtInWords+")<br/><br/><br/>"+
											
 "<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"+
 
  "<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"+
  "  500........      20.......     2.......<br/><br/>"+
  "  250........      10.......     1.......<br/><br/>"+
  "  100........      Total Amount:.......</span><br/><br/><br/>"+   
											
  "<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"+
 
  "<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"+                  	

  "  Hand over:                                               Take over:<br/><br/>"+
  "  Name:<b>"+nameOfficail+"</b>                                    Name:<br/><br/>"+ 
  "  Designation:"+userdesig+"                              Designation:<br/><br/>"+  
  "  Signature:                                                Signature:<br/><br/></span>"+
	
	"</pre><br><br><br>";
		html += "</div>";

    printW.document.write(html);
    printW.document.close();
    printW.focus();
    printW.print();
    printW.close(); 

    
    }
	
	
	/*  */

	var nct="",ncd="",ncinst="",mval="",th="",namec="",illg="",misc="",adv="",tot="",amtInWords="";
	function generateDayClosureReport1()
	{
		var date = $("#closeDate1").val();
	
		if(date == "")
		{
			 swal({
	                title: "Please Select Closing Day",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });	
			 return false;
		}
		$.ajax({
			  type: "GET",
			  url: "./generateDayCloseMisc",
			  dataType: "Json",
			  data:{date : date},
			  cache       : true,
			  async       : false,
			  success: function(response){
				 
				  if(response == "")
					 {
					  	response="0.0";
					 } 
				  var data="";
				  for(var i=0;i<response.length;i++){
					   data = response[i];
					  
					   nct=data[0];
					   ncd=data[1];
					   ncinst=data[2];
					   mval=data[3];
					   th=data[4];
					   namec=data[5];
					   illg=data[6];
					   misc=data[7];
					   adv=data[8];
					   tot=data[9];
					   counterNo=data[10];
					   holec=data[11];
					   reamain=data[12];
					   appch=data[13];
					   tsq=data[14];
					   crdch=data[15];
					   oth=data[16];
					   testN();
				  }
				 
			},
		 	error: function (xhr) {
			},
			}); 
	}
	
	function testN()
	 {
		 
			
		 var branchname="${CBranchName}";
		 var printW = window.open("");
		 var html = "<div>";
	  
		 html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "+branchname+"<br>Counter No.: "+counterNo+"<br>Date: "+dayclosenepali+"<br><u>Miscellaneous Day Close Report</u></b></span><br/><br/>"+
												
			  "<span style='font-size:16px;'>  New Connection Reg          <b>"+parseFloat(nct).toFixed(2)+"</b><br/>"+
			  "  New Connection Inst.        <b>"+parseFloat(ncinst).toFixed(2)+"<br/></b>"+
			  "  New Connection Deposit      <b>"+parseFloat(ncd).toFixed(2)+"<br/></b>"+
			  "  Meter Value	              <b>"+parseFloat(mval).toFixed(2)+"<br/></b>"+
			  "  Advance                     <b>"+parseFloat(adv).toFixed(2)+"<br/></b>"+
			  "  Temporary Hole Block	      <b>"+parseFloat(th).toFixed(2)+"<br/></b>"+
			  "  Miscellaneous	 	      <b>"+parseFloat(misc).toFixed(2)+"<br/></b>"+
			  "  Name Change	 	      <b>"+parseFloat(namec).toFixed(2)+"<br/></b>"+
			  "  Illegal Conn Penalty        <b>"+parseFloat(illg).toFixed(2)+"<br/></b>"+
			  
			  "  Hole Change        	      <b>"+parseFloat(holec).toFixed(2)+"<br/></b>"+
			  "  Hole Maintainence           <b>"+parseFloat(reamain).toFixed(2)+"<br/></b>"+
			  "  Application Charge          <b>"+parseFloat(appch).toFixed(2)+"<br/></b>"+
			  "  Tender/Sealed Quotation     <b>"+parseFloat(tsq).toFixed(2)+"<br/></b>"+
			  "  Card Charge       	      <b>"+parseFloat(crdch).toFixed(2)+"<br/></b>"+
			  "  Others       		      <b>"+parseFloat(oth).toFixed(2)+"<br/></b>"+
			  
			  "  <b>Total Collection            <u>"+parseFloat(tot).toFixed(2)+"<br/></u></b>"+
			  "	</span><br/><br/>"+
												
	  "  (Amount in word: "+amtInWords+")<br/><br/>"+
												
	 "<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"+
	 
	  "<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"+
	  "  500........      20.......     2.......<br/><br/>"+
	  "  250........      10.......     1.......<br/><br/>"+
	  "  100........      Total Amount:.......</span><br/><br/><br/>"+   
												
	  "<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"+
	 
	  "<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"+                  	

	  "  Hand over:                                               Take over:<br/><br/>"+
	  "  Name:                                                    Name:<br/><br/>"+ 
	  "  Designation:                                             Designation:<br/><br/>"+  
	  "  Signature:                                               Signature:<br/><br/></span>"+
		
		"</pre><br><br>";
		html += "</div>";

	    printW.document.write(html);
	    printW.document.close();
	    printW.focus();
	    printW.print();
	    printW.close(); 

	    
	    }
	
	/*  */
	</script>
<style>
.form-group {
    margin-bottom: 10px;
    position: relative;
}
legend {
	text-trans none;
	font-size: 16px;
	border-color: #0DB7A5;
}
.form-horizontal .form-group {
    margin-left: 20px;
    margin-right: 40px;
}
</style>