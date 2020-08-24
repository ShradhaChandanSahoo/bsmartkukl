<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript">
var counterNo="";
var dayclosenepali="";
$(document).ready(function(){   
	$('#cashCounterScreen').show();
	$('#cashCounter').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Cash Counter";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	
 	 $('#closeDatenep').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true,
			onChange: function(){
				var rdngdtnep = $('#closeDatenep').val();
				getEngDate(rdngdtnep);
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
				   $('#closeDate').val(response);
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
								<legend class="text-bold">Day Close Report</legend>
									<div class="row">
											
										 <div class="form-group col-md-3" >
										<label>Counter No</label>
					                         <select class="select" id="counter_no" name="counter_no"
														required="required" data-placeholder="Select Counter No">
															<option value="" disabled="disabled">Select counter No</option>
														<c:forEach var="data" items="${counter}">
																<option value="${data.counterNumber}">${data.counterNumber}</option>
														</c:forEach>
													</select>
				                         </div> 
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
													<input type="text"  name="closeDate" id="closeDate" class="form-control" readonly="readonly" >
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
	
	
	var waterP="",drainageP="",otherP="",meterC="",meterChangeFine="",excuses="",totalP="",advanceP="",cashCollectionAmt="",amtInWords="",advancerebate="",oldbalance="",totalreceivable="",username="",userdesig="";
	function generateDayClosureReport()
	{
		var date = $("#closeDate").val();
		var counter_no = $("#counter_no").val();
		counterNo=counter_no;
		if(counter_no == "")
		{
			 swal({
	                title: "Please Select Counter No",
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });	
			 return false;
		}
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
			  url: "./generateDayClosureReportForAdmin/"+counter_no,
			  dataType: "Json",
			  data:{date : date},
			  cache       : true,
			  async       : false,
			  success: function(response){
				 
				  if(response[0] == "")
					 {
					  	response[0]="0.0";
					 } 
				  var data="";
				  for(var i=0;i<response[0].length;i++){
					  data = response[0][i];
					  
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
					  numberToWords(data[6]); 
				  }
				  
				 /*  $("#wP").text(response);
				  waterP = response;
				  $("#tP").text(response);
				  totalP = response;
				  $("#cCA").text(response);
				  cashCollectionAmt = response;
				  numberToWords(cashCollectionAmt); */
				  username=response[1];
				  userdesig=response[2];
				  
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
		
		var branchname="${BranchName}";
		var tcad=(parseFloat(advanceP))+(parseFloat(advanceP*(0.03)))+(parseFloat(totalP));
		
		
	var printW = window.open("");
	 var html = "<div>";
  
	 html += " <pre><span style='font-size: 17px;'><b>Kathmandu Upatyaka Khanepani Limited<br>Branch : "+branchname+"<br>Counter No.: "+counterNo+"<br>Date: "+dayclosenepali+"<br><u>Day Close Report</u></b></span><br/><br/>"+
											
  "<span style='font-size:16px;'>  Water Charges          <b>"+waterP+"</b><br/>"+
  "  Sewerage Charges       <b>"+drainageP+"<br/></b>"+
  "  Meter Charges	         <b>"+meterC+"<br/></b>"+
  "  Total Bill	         <b>"+(waterP+meterC+drainageP)+"<br/></b>"+
  "  Total Rebate           <b>"+excuses+"<br/></b>"+
  "  Net Bill		 <b>"+parseFloat(parseFloat(waterP+meterC+drainageP)-parseFloat(excuses)).toFixed(2)+"<br/></b>"+
  "  Total Penalty          <b>"+meterChangeFine+"<br/></b>"+
  "  Miscellaneous          <b>"+otherP+"<br/></b>"+
  "  Previous Month Arrear  <b>"+oldbalance+"<br/></b>"+
//  "  Rebate	    <b> "+parseFloat(excuses-parseFloat(advanceP*(0.03))).toFixed(2)+"<br/></b>"+
  "  Advance Payment        <b>"+parseFloat(advanceP).toFixed(2)+"<br/></b>"+
  "  Advance Rebate         <b>"+parseFloat(advanceP*(0.03)).toFixed(2)+"<br/></b>"+
  "  <b>Total Collection       <u>"+totalP+"<br/></u></b>"+
//  "  Total Receivable   <b>"+totalreceivable+"<br/></b>"+
//"  Total(TP+AP+AR)<b>        <b>"+parseFloat(tctp).toFixed(2)+"</b>"+
  "</span><br/><br/>"+/* cashCollectionAmt */											
  "  (Amount in word: "+amtInWords+")<br/><br/><br/>"+
											
 "<span style='font-size:17px;'><b> <u>Amount description:</u></b></span><br/><br/>"+
 
  "<span style='font-size:16px;'>  1000.......      50.......     5.......<br/><br/>"+
  "  500........      20.......     2.......<br/><br/>"+
  "  250........      10.......     1.......<br/><br/>"+
  "  100........      Total Amount:.......</span><br/><br/><br/>"+   
											
  "<span style='font-size:17px;'><b> <u>Cheque description:</u></b></span><br/><br/>"+
 
  "<span style='font-size:16px;'>  Cheque No.:                  	Amount:                    Grand total:.......<br/><br/><br/>"+                  	

  "  Hand over:                                               Take over:<br/><br/>"+
  "  Name:<b>"+username+"</b>                                    Name:<br/><br/>"+ 
  "  Designation:<b>"+userdesig+"</b>                               Designation:<br/><br/>"+  
  "  Signature:                                               Signature:<br/><br/></span>"+
	
	"</pre><br><br><br>";
		html += "</div>";

    printW.document.write(html);
    printW.document.close();
    printW.focus();
    printW.print();
    printW.close(); 

    
    }

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