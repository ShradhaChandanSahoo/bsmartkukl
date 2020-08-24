 <%@include file="/WEB-INF/decorators/taglibs.jsp"%>

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
 
 

					<div class="panel panel-flat">
						<div class="panel-body">
							<form:form action="#" role="form"  modelAttribute="billApproveEntity" commandName="billApproveEntity" method="POST" id="billApproveEntity">
					
							<fieldset class="content-group">
					
								<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">Bill Correction / Prepare New Bill</legend>
								
								<legend class="text-bold" >Master Details</legend>
								
								<input type="text" name="mr_code" id="mr_code" hidden="true">
								<input type="text" name="area_no" id="area_no" hidden="true">
								<input type="text" name="monthyear" id="monthyear" hidden="true">
								<input type="text" name="monthyearnep" id="monthyearnep" hidden="true">
								<input type="text" name="rdng_mth" id="rdng_mth" hidden="true">
								<input type="text" name="mr_code" id="mr_code" hidden="true">
								<input type="text" name="billid" id="billid" hidden="true">
								
								
								
								
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection No &nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="connection_no" id="connection_no" placeholder="Connection No..." onchange="connectionDetails(this.value)" onkeyup="convertToUpperCase();">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Name in English</label> <input type="text" class="form-control" name="name_eng" id="name_eng" readonly="readonly"
												placeholder="Name in Eng...">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Ledger No</label> <input type="text" class="form-control" name="ledgerno" id="ledgerno" readonly="readonly"
												placeholder="Ledger No...">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Folio</label> <input type="text" class="form-control" name="folio" id="folio" readonly="readonly"
												placeholder="Folio...">
										</div>
									</div>
									
								
									
					
					
								</div>
								
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Name in Nepali</label> <input type="text" name="name_nep" id="name_nep" readonly="readonly"
												class="form-control" placeholder="Name in Nepali...">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection Category</label> <input type="text" class="form-control" name="con_category" id="con_category" readonly="readonly"
												placeholder="Connection Category...">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Connection Type</label> <input type="text" class="form-control" name="con_type" id="con_type" readonly="readonly"
												placeholder="Connection Type...">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Pipe Diameter</label> <input type="text" class="form-control" name="pipe_size" id="pipe_size" readonly="readonly"
												placeholder="Pipe Diameter...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Connection Status</label> <input type="text" class="form-control" name="con_satatus" id="con_satatus" readonly="readonly"
												placeholder="Connection Status...">
										</div>
									</div>
								</div>
								
								<legend class="text-bold" >Ledger Details</legend>
								
								
								
								<div class="row">
									
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Prev Read Date(Eng) &nbsp;<font color="red">*</font></label> 
											<div class="input-group">
													<form:input type="text" class="form-control" path="previous_read_date" id="previous_read_date" readonly="true"></form:input>
													
											</div>
										</div>
									</div>
									
									  <div class="col-md-2">
										<div class="form-group">
											<label>Reading Date(Nep)&nbsp;<font color="red">*</font></label> 
											<input type="text" id="rdng_date_nep" name="rdng_date_nep" class="form-control nepali-calendar"
												placeholder="Reading Date in Nepali...">
									</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Reading Date(Eng)&nbsp;<font color="red">*</font></label> 
											<div class="input-group">
													<form:input type="text" class="form-control" path="rdng_date" id="rdng_date" onchange="getBillPeriod(this.value);" readonly="true"></form:input>
													
											</div>
										</div>
									</div>
									
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Bill Period(Month(s))&nbsp;<font color="red">*</font></label> <form:input type="text" class="form-control" path="bill_period" id="bill_period" readonly="true"
												placeholder="Bill Period..."></form:input>
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Due Date(Nep)&nbsp;</label> 
											<input type="text" id="due_date_nep" name="due_date_nep" class="form-control nepali-calendar"
												placeholder="Due Date in Nepali...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Due Date(Eng)&nbsp;</label> 
											<div class="input-group">
													
													<form:input type="text" class="form-control" path="due_date" id="due_date" readonly="true"></form:input>
											</div>
										</div>
									</div>
									
									
									
									
					
								</div>
								
								
								
								<div class="row">
								
									<!-- <div class="col-md-2">
										<div class="form-group">
											<label>Bill Date in Nepali&nbsp;<font color="red">*</font></label> 
											<input type="text" id="bill_date_nep" name="bill_date_nep" class="form-control nepali-calendar"
												placeholder="Bill Date in Nepali...">
									</div>
									</div> -->
									
									
								
								   <div class="col-md-2" id="previous_readingdiv">
										<div class="form-group">
											<label>Previous Reading(KL)&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="previous_reading" id="previous_reading" onchange="getUnitsConsumed();"
												placeholder="Previous Reading..." >
										</div>
									</div>
									
									
									
									<div class="col-md-2" id="present_readingdiv">
										<div class="form-group">
											<label>Present Reading(KL)&nbsp;<font color="red">*</font></label> <form:input type="text" path="present_reading" id="present_reading" onchange="getUnitsConsumed();"
												class="form-control" placeholder="Present Reading..."></form:input>
										</div>
									</div>
									
									
									<div class="col-md-2" id="consumptiondiv">
										<div class="form-group">
											<label>Units Consumed(KL)&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="consumption" id="consumption" readonly="readonly"
												placeholder="Units Consumed...">
										</div>
									</div>
					
									<div class="col-md-2" id="mc_statusdiv">
										<div class="form-group">
											<label>Meter Status&nbsp;<font color="red">*</font></label>
												
												<select data-placeholder="Select" class="select" id="mc_status" name="mc_status" required="required" onchange="checkMCStatus()">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${observationList}" var="ob">
													<option value="${ob.id}">${ob.observationName}</option>
												   </c:forEach>
													
												</select>
												
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Minimum Charges&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" readonly="readonly"
												placeholder="Minimum Charges..." name="minimum_charges" id="minimum_charges">
										</div>
									</div>
									
									
									
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Additional Charges&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="additional_charges" id="additional_charges" readonly="readonly"
												placeholder="Additional Charges...">
										</div>
									</div>
									
									
									
									
					
								</div>
								
								<div class="row">
								
								    
								
								  <div class="col-md-2">
										<div class="form-group">
											<label>Water Charges&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="water_charges" id="water_charges" readonly="readonly"
												placeholder="Water Charges...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Sewerage Charges&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="sw_charges" id="sw_charges" readonly="readonly"
												placeholder="Sewage...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Meter Rent&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="mtr_rent" id="mtr_rent" readonly="readonly"
												placeholder="Meter Rent...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Miscellaneous&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="excess_charges" id="excess_charges" readonly="readonly"
												placeholder="Miscellaneous...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group"> 
											<label>Arrears&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="arrears" id="arrears" onchange="calculateNetAmount(this.value);"
												placeholder="Arrears..." readonly="readonly">
										</div>
									</div>
									
									
								     <div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Miscellaneous&nbsp;<font color="red">*</font></label> <input type="text" name="other" id="other" readonly="readonly"
												class="form-control" placeholder="Others...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label><b>Net Amount</b>&nbsp;<font color="red">*</font></label> <input type="text" name="net_amount" id="net_amount" readonly="readonly"
												class="form-control" placeholder="Net Amount...">
										</div>
									</div>
									
					
					
								</div>
								
								
								<div class="row">
								
									
									
								
								   <div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Average&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="avg_units" id="avg_units" readonly="readonly"
												placeholder="Average...">
										</div>
									</div>
									
					
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Penalty&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="penalty" id="penalty" readonly="readonly"
												placeholder="Penalty...">
										</div>
									</div>
									
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Rebate&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="rebate" id="rebate" readonly="readonly"
												placeholder="Rebate...">
										</div>
									</div>
					
									
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Opening Balance&nbsp;<font color="red">*</font></label> <input type="text" name="open_balance" id="open_balance" readonly="readonly"
												class="form-control" placeholder="Opening Balance...">
										</div>
									</div>
									
									
									
					
									<div class="col-md-12">
										<div class="form-group">
											<label>Remarks&nbsp;<font color="red">*</font></label> 
											<textarea placeholder="Enter your Remarks here" class="form-control" cols="1" rows="1" name="remarks" id="remarks" value="Bill Correction"></textarea>
										</div>
									</div>
					
					
									<!-- <div class="col-md-2">
										<div class="form-group">
											<label>Last Amout Paid Date Eng&nbsp;<font color="red">*</font></label> 
											<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar3"></i></span>
													<input type="text" class="form-control daterange-single" name="last_payable_date" id="last_payable_date">
											</div>
											
										</div>
									</div> -->
									<div hidden="true"><form:input type="text" path="previous_mc_status" id="previous_mc_status"/>
									<form:input type="text" path="previous_water_charge" id="previous_water_charge"/>
									<form:input type="text" path="previous_sw_charge" id="previous_sw_charge"/>
									<form:input type="text" path="previous_mtr_rent" id="previous_mtr_rent"/>
									<form:input type="text" path="previous_arrears" id="previous_arrears"/>
									<form:input type="text" path="previous_net_amt" id="previous_net_amt"/>
									
									</div>
									<%-- <div hidden="true"><form:input type="text" path="" id=""/></div> --%>
									
					
					
								</div>
								
								
								
								<!-- <div class="row">
									
									
									<div class="col-md-12">
										<div class="form-group">
											<label>Remarks&nbsp;<font color="red">*</font></label> 
											<textarea placeholder="Enter your Remarks here" class="form-control" cols="1" rows="1" name="remarks" id="remarks"></textarea>
										</div>
									</div>
					
								</div> -->
								
								
								
								
								
								<legend class="text-bold" >Slab Details 
								
								<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default" id="onshow_callback" style="float: right;" onclick='paymentHistoryPopUp()'>View Payment History</button></legend>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default1" id="onshow_callback" style="margin-top: -119px;margin-left:605px;" onclick='ledgerHistoryPopUp()'>View Ledger History</button>
								
								
								<div class="row">
									<div class="table-responsive">
					
										<table class="table table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue">
													<th>SL No</th>
													<th>Consumption in (ltr)</th>
													<th>Amount</th>
					
												</tr>
					
											</thead>
											<tbody>
												<tr>
													<td>1</td>
													<td><span id="key1"></span></td>
													<td><span id="key2"></span></td>
													
												</tr>
												
												<tr>
													<td>2</td>
													<td><span id="key3"></span></td>
													<td><span id="key4"></span></td>
													
												</tr>
					
											</tbody>
										</table>
										
									</div>
					
								</div>
					
							</fieldset>
							
							
								<div class="text-center">
									<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label" >Clear</span></button>
									<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="calculateBill();" id="caliddiv"><span class="ladda-label" >Calculate</span></button>
									<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return checkValid();" id="subiddiv"><span class="ladda-label">Submit</span></button>
									<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="billPrint();" id="billprintid" style="display: none;"><span class="ladda-label">Bill Print</span></button>
								</div>
						</form:form>
						</div>
					</div>
					
					
				<!-- Basic modal -->
				<div id="modal_default" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Payment History</h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment">
									<thead>
										<tr>
											<th></th>
											<th>REC Amount</th>
											<th>Receipt No</th>
											<th>Receipt Date</th>
											<th>Payment Mode</th>
											<th>Penalty</th>
											<th>Rebate</th>
											<th style="min-width: 300px;">Remarks</th>
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
				<!-- /basic modal -->	
					
			
					<div id="modal_default1" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title"><font color="red"><span id="connectionidspan"></span></font></h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment1">
									<thead>
										<tr>
											<!-- <th>CON No.</th> -->
											<th></th>
											<th>Bill No</th>
											<th>MonthYear</th>
											<th>Water Charge</th>
											<th>SW Charge</th>
											<th>MR</th>
											<th>Arrears</th>
											<th>NET</th>
											<th>LR(KL)</th>
											<th>CR(KL)</th>
											<th>Units</th>
											<th style="min-width: 170px;">MC Status</th>
											
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
					
<script>
var billno="";
var address_eng="";

var bill_date_nep="";
var due_date_nep="";

function checkMCStatus(){
	
	var conType=$("#con_type").val();
	var mc_status=$("#mc_status").val();
	
	 var check="Unmetered".indexOf(conType) > -1;
	 
	 if(check){
		 
		 if(mc_status==8){
			 
		 }else if(mc_status==11){
			 
		 }else if(mc_status==12){
			 
		 }else if(mc_status==13){
			 
		 }else if(mc_status==""){
			 
		 }else if(mc_status==15){
			 
		 }else if(mc_status==18){
			 
		 }else if(mc_status==19){
			 
		 }else{
			 alert("Selected Observation is not applicable for Unmetered Conections...");
			 $('#mc_status').val("").trigger("change");
			 return false;
		 }
	 }
	 
	 if(mc_status==16 || mc_status==56){
		 
	 } else{
			var previous_reading=$("#previous_reading").val();
			var present_reading=$("#present_reading").val();
			$('#present_reading').val(previous_reading);
			
			$('#consumption').val(parseFloat($("#present_reading").val())-parseFloat($("#previous_reading").val()));
	 }
	 
	 calculateBill();
	
}

function convertToUpperCase(){
	$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
}

function ledgerHistoryPopUp(){
	$("#viewPayHistotytbody1").empty();
	
	var connectionNo=$("#connection_no").val();
	
	var psize="";
	var nameeng="";
	var namenep="";
	var areanum="";

	var tableData = "";
	$.ajax
	({			
		type : "GET",
		url : "./billing/viewBillLedgertHistoryForReading/"+connectionNo,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	    
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	
		              	tableData += "<tr>"
								+"<td>"+(s+1)+"</td>" 
								+"<td>"+obj.billno+"</td>"
								+"<td>"+obj.monthyearnep+"</td>"
								+"<td>"+obj.water_charges+"</td>"
								+"<td>"+obj.sw_charges+"</td>"
								+"<td>"+obj.mtr_rent+"</td>"
								+"<td>"+obj.lastarrears+"</td>"
								+"<td>"+obj.net_amount+"</td>"
								+"<td>"+obj.previous_reading+"</td>"
								+"<td>"+obj.present_reading+"</td>"
								+"<td>"+obj.units+"</td>"
								+"<td>"+obj.obname+"</td>"
								+"</tr>";
								
								if(s==0){
									nameeng=obj.name_eng;
									namenep=obj.name_nep;
									psize=obj.pipe_size;
									areanum=obj.area_no;
								}
				                
				     }
				
			    $('#connectionidspan').html("Bill Ledger History &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;            Name : "+nameeng+"   &nbsp;&nbsp;/&nbsp;&nbsp;   "+namenep+"    &nbsp;&nbsp;&nbsp;Area No : "+areanum+"&nbsp;&nbsp;&nbsp;&nbsp;  Pipe Size(inch):"+psize);
				$('#viewPayHistotytbody1').append(tableData);
				loadSearchFilter1('popupPayment1',tableData,'viewPayHistotytbody1');
		}
	

	});

	
}

function calculateNetAmount(arrears){
	var water_charges=parseFloat($("#water_charges").val());
	var sw_charges=parseFloat($("#sw_charges").val());
	var mtr_rent=parseFloat($("#mtr_rent").val());
	var arrears=parseFloat($("#arrears").val());
	var excess_charges=parseFloat($("#excess_charges").val());
	$('#net_amount').val(water_charges+sw_charges+mtr_rent+arrears+excess_charges)
	
}

function billPrint(){
	 
	var connection_no=$("#connection_no").val();
	$.ajax({
	  type: "GET",
	  url: "./billing/printBillByConNo/"+connection_no,
	  dataType: "JSON",
	  async   : false,
	  cache:false,
	  success: function(response){
		  var result=response;
		  
		  var printW = window.open("");
		  
		  var branch="${BranchName}";
		  
		  for(var i=0;i<result.length;i++)
		  {
			 
			 var data=result[i];
			 
			 
			 var wcc=data.water_charges==null?parseFloat(0):data.water_charges;
			 var scc=data.sw_charges==null?parseFloat(0):data.sw_charges;
			 var mrc=data.mtr_rent==null?parseFloat(0):data.mtr_rent;
			 
			 var cmt=parseFloat(parseFloat(wcc)+parseFloat(scc)+parseFloat(mrc)).toFixed(2);
			 
			 var board=data.boardbalance==null?parseFloat(0):data.boardbalance;
			 
		     var tolArrs= parseFloat(parseFloat(data.arrears)+parseFloat(board)).toFixed(2);
			 var netAmt= parseFloat(parseFloat(tolArrs)+parseFloat(cmt)).toFixed(2);
			 
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
			  
			   
			   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
			   +"  </tr>"
			   +"  <tr>"
			   
			   +"  <tr>"
			   +"  <th colspan='2' align='center'><u>METER READING BILL: "+data.monthDesc+"</u></th>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Bill No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.billno+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Customer No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.customer_id+"</b></td>"
			   +"  </tr>"
			   
			   +"    <td>Connection No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.connection_no+"</b></td>"
			   +"  </tr>"
			   
			   +"    <td>Area No.<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.area_no+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td><b>Name</b><br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.name_eng+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Address<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.address_eng+"</td>"
			   +"  </tr>"
			   
			   
			   +"  <tr>"
			   +"    <td>Connection Type<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.con_type+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Tap Size<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.pipe_size+"</td>"
			   +"  </tr>"
			   
			   
			  /*  +"  <tr>"
			   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
			   +"  </tr>" */
			   
			   +"  <tr>"
			   +"    <td>Reading Date<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.rdng_date_nep+"</b></td>"
			   +"  </tr>"
			   
			   /* +"  <tr>"
			   +"    <td>DUE DATE :<br></td>"
			   +"    <td><b>"+data.due_date_nep+"</b></td>"
			   +"  </tr>" */
			   
			  
			   +"  <tr>"
			   +"    <td>Present Reading(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.present_reading+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Previous Reading(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.previous_reading+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Consumption(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.consumption+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Additinal Units(KL)<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.additionalUnits+"</td>"
			   +"  </tr>"
			   
			  /*  +"  <tr>"
			   +"    <th colspan='2'><hr style='height: 3px; background-color: black;'></th>"
			   +"  </tr>" */
			   
			   +"  <tr>"
			   +"    <td>Minimum Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.minimum_charges+"</b></td>"
			   +"  </tr>"
			   +"   <tr>"
			   +"    <td>Additional Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.additional_charges+"</b></td>"
			   +"  </tr>"
			   +"  <tr>"
			   +"    <td>Total Water Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.water_charges+"</b></td>"
			   +"  </tr>"
			   +"  <tr>"
			   +"    <td>Sewerage Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.sw_charges+"</b></td>"
			   +"  </tr>"
			   +"  <tr>"
			   +"    <td>Meter Rent Charges<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+data.mtr_rent+"</b></td>"
			   +"  </tr>"
			   
			   /* +"  <tr>"
			   +"    <td>Penalty :<br></td>"
			   +"    <td>"+data.dpenalty+"</td>"
			   +"  </tr>"
			   +"  <tr>"
			   
			   +"    <td>Rebate :<br></td>"
			   +"    <td>"+data.rebate+"</td>"
			   +"  </tr>" */
			   
			   +"  <tr>"
			   +"    <td>Miscellaneous<br></td>"
			   +"    <td>:&nbsp;&nbsp;0</td>"/*"+data.other+"  */
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Current Month Total<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+cmt+"</b></td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Arrears<br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+tolArrs+"</b></td>"
			   +"  </tr>"
			   
			   /* +"  <tr>"
			   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
			   +"  </tr>" */
			   
			   +"  <tr>"
			   +"    <td><b>Total Amount Rs.</b><br></td>"
			   +"    <td>:&nbsp;&nbsp;<b>"+netAmt+"</b></td>"
			   +"  </tr>"
			   
			   
			   +"  <tr>"
			   +"    <td>Observation<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.observation+"</td>"
			   +"  </tr>"
			   
			   +"  <tr>"
			   +"    <td>Meter Reader'S Name<br></td>"
			   +"    <td>:&nbsp;&nbsp;"+data.mrname+"</td>"
			   +"  </tr>"
			   
			   
			   
			  /*  +"  <tr>"
			   +"    <th colspan='2'><hr style='height: 5px; background-color: black;'></th>"
			   +"  </tr>" */
			   
			   
			   +"  <tr>"
			   
			   +"    <td colspan='2'><center>*****Computer Generated Bill*****</center></td>"
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
			    
			   
			   
			    
		  }	
		  
		    printW.document.close();
		    printW.focus();
		    printW.print();
		    printW.close(); 
	  }
	
	   
	    
	});
}


function clearButton(){
	
	window.location.href="./billCorrection";
}
function checkValid(){
	
	var connectionNo=$("#connection_no").val();
	var pipeSize=$("#pipe_size").val();
	var previous_reading=$("#previous_reading").val();
	var present_reading=$("#present_reading").val();
	var rdng_date_nep=$("#rdng_date_nep").val();
	var mc_status=$("#mc_status").val();
	var due_date_nep=$("#due_date_nep").val();
	
	var additional_charges=$("#additional_charges").val();
	var water_charges=$("#water_charges").val();
	var mtr_rent=$("#mtr_rent").val();
	var excess_charges=$("#excess_charges").val();
	var other=$("#other").val();
	var avg_units=$("#avg_units").val();
	var rebate=$("#rebate").val();
	var arrears=$("#arrears").val();
	var open_balance=$("#open_balance").val();
	var net_amount=$("#net_amount").val();
	var numberregex = new RegExp("^(?:0|[0-9]*)\.[0-9]+$");
	var remarks=$("#remarks").val();
	
	
	
	 if(connectionNo=="" || connectionNo==null){
			
			alert("Please enter connection number");
			return false;
     }else if(pipeSize=="" || pipeSize==null){
			
			alert("Pipe size is not available");
			return false;
     }else if(present_reading=="" || present_reading==null){
			
    	 $("#present_reading").val(0);
  	 }else if(previous_reading=="" || previous_reading==null){
			
    	 $("#previous_reading").val(0);
  	 }else if(rdng_date_nep=="" || rdng_date_nep==null){
			
			alert("Please select Reading date Nepali");
			return false;
  	 }/* else if(due_date_nep=="" || due_date_nep==null){
			
			alert("Please select Due date Nepali");
			return false;
     } */
      else if(parseFloat(present_reading)<parseFloat(previous_reading)){
			
			alert("Present Reading should be greater than or equal to previous Reading");
			return false;
     }else if(rdng_date_nep=="" || rdng_date_nep==null){
			
			alert("Please select Reading date nepali");
			return false;
     }else if(mc_status=="" || mc_status==null){
			
			alert("Please select Meter Status");
			return false;
    } /* else if(additional_charges=="" || additional_charges==null || water_charges=="" || water_charges==null 
    		 ||mtr_rent=="" || mtr_rent==null || excess_charges=="" || excess_charges==null || other=="" || other==null ||
    		 avg_units=="" || avg_units==null || rebate=="" || rebate==null || open_balance=="" || open_balance==null || net_amount=="" || net_amount==null){
		
		alert("Please Calculate Bill and then Submit");
		return false;
	} */else if(arrears=="" || arrears==null){
		
		alert("Please enter arrears");
		return false;
	} /* else if(!numberregex.test(arrears)){
		
		alert("Please enter arrears amount in numbers only");
		return false;
	} */ else if(remarks=="" || remarks==null){
		alert("Please enter remarks");
		return false;
	}else{
    	
    	 if(confirm("Are you sure to Submit the Bill for Approval?")){
			   $("#billApproveEntity").attr("target", "");
			   $("#billApproveEntity").attr("action", "./createBillApprove").submit();
		 }else{
			 return false;
		 }
		    
    }
}

function getBillPeriod(){
	
	var previous_read_date=$("#previous_read_date").val();
	var present_read_date=$("#rdng_date").val();
	
	if(previous_read_date!="" && present_read_date!=""){
	
	 $.ajax({
		  type: "GET",
		  url: "./billing/getBillPeriod",
		  dataType: "text",
		  async   : false,
		  data:{
			  previous_read_date:previous_read_date,
			  present_read_date:present_read_date,
			  
		  },
		  success: function(response){
			  $('#bill_period').val(response);
		  }
		});
	}
}

function getEngDate(nepalidate,value){
	
	var date_nep=nepalidate;
	var previous_read_date=$("#previous_read_date").val();
	
	
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
			 
			  if(value==1){
				 
				    var startDate = new Date(previous_read_date);
				    var endDate   = new Date(response);
				   
				    
				    if(endDate<=startDate){
				    	alert("Reading date should be Greater than Previous Reading Date");
				    	$('#rdng_date_nep').val("");
				    	$('#rdng_date').val("");
				    	$('#bill_period').val("");
				    	return false;
				    }else if(currDate<endDate){
				    	alert("Reading date Nepali cannot be future date");
				    	$('#rdng_date_nep').val("");
				    	$('#rdng_date').val("");
				    	$('#bill_period').val("");
				    	
				    	return false;
				    }else{
				     	$('#rdng_date').val(response);
				    }
				  
			  }else if(value==2){
				  
				  
				    var rdng_date=$("#rdng_date").val();
				  	var startDate = new Date(rdng_date);
				 	var endDate = new Date(response);
				  
				 	if(startDate>=endDate){
				 		alert("Due date Nepali should be greater than Reading Date");
				    	$('#due_date_nep').val("");
				    	$('#due_date').val("");
				    }else{
				     $('#due_date').val(response);
				    }
				  
				  
				  
			  }else{
				  
				    var startDate = new Date(response);
				    var today = new Date();
				    
				    var dd = today.getDate();
				    var mm = today.getMonth()+1; //January is 0!

				    var yyyy = today.getFullYear();
				    if(dd<10){
				        dd='0'+dd;
				    } 
				    if(mm<10){
				        mm='0'+mm;
				    } 
				    var today1 = mm+'/'+dd+'/'+yyyy;
				    
				    var dd1 = startDate.getDate();
				    var mm1 = startDate.getMonth()+1; //January is 0!

				    var yyyy1 = startDate.getFullYear();
				    if(dd1<10){
				        dd1='0'+dd1;
				    } 
				    if(mm1<10){
				        mm1='0'+mm1;
				    } 
				    var today2 = mm1+'/'+dd1+'/'+yyyy1;
				  
				    if(billno!=null){
					 	if(today2!=today1){
					 		alert("Please select Bill Date as Today Date");
					    	$('#bill_date_nep').val("");
					    	$('#bill_date_nep').val("");
					    }else{
					    	$('#bill_date_nep').val(date_nep);
					    }
				    }else{
				    	
				    	    var startDate = new Date($("#rdng_date").val());
						    var endDate   = new Date(response);
						   
						    
						    if(endDate<startDate){
						    	alert("Bill date should be Greater than or equal to Reading Date");
						    	$('#bill_date_nep').val("");
						    	
						    	return false;
						    }else if(currDate<endDate){
						    	alert("Bill date Nepali cannot be future date");
						    	$('#bill_date_nep').val("");
						    	return false;
						    }else{
						     	$('#bill_date_nep').val(response);
						    }
				    	
				    }
				  
			  }
		  }
		});
	
}



function paymentHistoryPopUp(){
	$("#viewPayHistotytbody").empty();
	var connectionNo=$("#connection_no").val();
	
	var tableData = "";
	$.ajax
	({			
		type : "GET",
		url : "./billing/viewPaymentHistory/"+connectionNo,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	    
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	
		              	tableData += "<tr>"
								+"<td>"+(s+1)+"</td>"
								+"<td>"+obj.amount+"</td>"
								+"<td>"+obj.receiptNo+"</td>"
								+"<td>"+obj.pdateeng+"</td>"
								+"<td>"+obj.paymode+"</td>"
								+"<td>"+obj.penalty+"</td>"
								+"<td>"+obj.rebate+"</td>"
								+"<td>"+obj.remarks+"</td>"
								+"</tr>";
				                
				     }
				$('#viewPayHistotytbody').append(tableData);
				loadSearchFilter1('popupPayment',tableData,'viewPayHistotytbody');
		}
	

	});
	
}
function calculateBill(){
	var connectionNo=$("#connection_no").val();
	var pipeSize=$("#pipe_size").val();
	var conType=$("#con_type").val();
	var units=$("#consumption").val();
	var billperiod=$("#bill_period").val();
	var prevr=$("#previous_reading").val();
	var pr=$("#present_reading").val();
	
	if(prevr==null || prevr==""){
		$('#previous_reading').val(0);
	}
	
	if(pr==null || pr==""){
		$('#present_reading').val(0);
	}
	
	
	var previous_reading=parseFloat($("#previous_reading").val());
	var present_reading=parseFloat($("#present_reading").val());
	
	
	
	var rdng_date_nep=$("#rdng_date_nep").val();
	var mc_status=$("#mc_status").val();
	
	 if(connectionNo=="" || connectionNo==null){
			
			alert("Please enter connection number");
			return false;
     }else if(pipeSize=="" || pipeSize==null){
			
			alert("Pipe size is not available");
			return false;
     }/* else if(present_reading=="" || present_reading==null){
			
    	 $('#present_reading').val(0);
     }  */else if(present_reading < previous_reading){
			
    	    $('#present_reading').val(0);
			alert("Present Reading should be greater than or equal to previous Reading");
			return false;
     } else if(rdng_date_nep=="" || rdng_date_nep==null){
			
			alert("Please select Reading date nepali");
			return false;
     }else if(mc_status=="" || mc_status==null){
			
			alert("Please select Meter Status");
			return false;
     }else{
	
    	 
	 $.ajax({
		  type: "GET",
		  url: "./billing/calculateBill/"+connectionNo+"/"+pipeSize+"/"+conType+"/"+units+"/"+mc_status,
		  dataType: "JSON",
		  data:{
			  billperiod:billperiod  
			  
		  },
		  async       : false,
		  
		  success: function(response){
		
			       $.each(response, function(index, data){
						
		    	      $('#water_charges').val(data.waterCharges);
					  $('#sw_charges').val(data.sewageCharges);
					  $('#net_amount').val(parseFloat(data.netAmount).toFixed(2));
					  $('#additional_charges').val(data.additionalCharges); 
					  $('#minimum_charges').val(data.minimum_charges); 
					  
					  $('#excess_charges').val(data.excess_charges);
					  $('#mtr_rent').val(data.meterRent);
					  $('#arrears').val(data.arrears);
					  $('#excess_charges').val(data.others);
					  $('#avg_units').val(data.average);
					  $('#penalty').val(data.penalty);
					  $('#rebate').val(data.rebate);
					  $('#open_balance').val(data.openingBalance);
					  
					  if(data.remarksothers==null){
						  $('#remarks').val("Bill Correction");
					  }else{
						  
					 	 $('#remarks').val(data.remarksothers);
					  }

					  $('#key1').html(data.key1);
					  $('#key2').html(data.key2);
					  $('#key3').html(data.key3);
					  $('#key4').html(data.key4);
					  
					  

						 var result="No bill";
						 var statuscheck=result.indexOf(data.disconnectedstage) > -1;
					  if(statuscheck){
						  alert("No bill for the current month for the Selected Meter Status");
						  $('#remarks').val("No Bill for the Current Month");
					  }
				 
				 });
			  
			  
			  
		  }
		});
	 
     }
	
}
function getUnitsConsumed(){
	
	var previous_reading=$("#previous_reading").val();
	var present_reading=$("#present_reading").val();
	
	if(previous_reading==null || previous_reading==''){
		$('#previous_reading').val(0);
		previous_reading=0;
	}
	if(present_reading==null || present_reading==''){
		$('#present_reading').val(0);
		present_reading=0;
	}
	
/* 	if(parseInt(present_reading)<parseInt(previous_reading)){
		alert("Present Reading should be greater than or equal to previous Reading");
		$('#present_reading').val("");
		$('#consumption').val("");
		return false;
   }else{ */
		$('#consumption').val(parseFloat(present_reading)-parseFloat(previous_reading));
  // }
		calculateBill();
		if(parseFloat($('#consumption').val())>0){
			$('#mc_status').val(16).trigger("change");
		}
}
var con_type="";
function connectionDetails(connectionNo){
	var approveCount=0;
	var monthyearLM="${monthyearNepLMY}";
	var branchcode="${branchcode}";
	$.ajax({
		  type: "GET",
		  url: "./billing/connectionDetails/"+connectionNo,
		  dataType: "JSON",
		  async       : false,
		  
		  success: function(response){
			 var consumer=response[0];
			 var ledger=response[1];
			 var billAppCount=response[2];
			 
			 var result="undefined";
			 var check=result.indexOf(consumer) > -1;
			 var ledgercheck=result.indexOf(ledger) > -1;
			 
			 $.each(billAppCount, function(index, data){
				approveCount=data.connectionNoCount;
             });
				 
			 if(check){
				 alert("Connection No.: "+connectionNo+" does not exists");
				 $('#connection_no').val("");
			 }else if(ledgercheck){
				 alert("Connection No.: "+connectionNo+" is not available in Ledger");
			 }else if(approveCount>0){
				 alert("Bill details sent for approval for Connection No: "+connectionNo+".");
				 $('#connection_no').val("");
			 }else{
			 
			 $.each(consumer, function(index, data){
				 
				 $('#name_eng').val(data.name_eng);
				 $('#ledgerno').val(data.ledgerno);
				 $('#folio').val(data.folio);
				 $('#con_category').val(data.con_category);
				 $('#con_type').val(data.con_type);
				 $('#pipe_size').val(data.pipe_size);
				 $('#con_satatus').val(data.con_satatus);
				 $('#minimum_charges').val(data.minimum_charges);
				 $('#area_no').val(data.area_no);
				 $('#mr_code').val(data.mtr_reader);
				 $('#name_nep').val(data.name_nep);
				 $('#mtr_rent').val(data.mtr_rent);
				 if(data.address_eng==null){
					 address_eng="";
				 }else{
					 
				    address_eng=data.address_eng;
				 }
				 
				 con_type=data.con_type;
			 
			 });
			 
			 $.each(ledger, function(index, data){
	//			 alert(data.net_amount);
				 $('#previous_reading').val(data.previous_reading);
				 $('#present_reading').val(data.present_reading);
				 $('#consumption').val(data.consumption);
				 $('#rdng_date').val(data.rdng_date);
				 $('#rdng_date_nep').val(data.rdng_date_nep);
				 $('#previous_read_date').val(data.previous_read_date);
				 $('#bill_period').val(data.bill_period);
				 $('#monthyear').val(data.monthyear);
				 $('#monthyearnep').val(data.monthyearnep);
				 $('#mr_code').val(data.mr_code);
				 $('#rdng_mth').val(data.rdng_mth);
				 $('#due_date').val(data.due_date);
				 $('#due_date_nep').val(data.due_date_nep);
				 $('#billid').val(data.billid);
				 
				 $('#minimum_charges').val(data.minimum_charges);
				 $('#additional_charges').val(data.additional_charges);
				 $('#water_charges').val(data.water_charges);
				 $('#sw_charges').val(data.sw_charges);
				// $('#mtr_rent').val(data.mtr_rent);
				 $('#excess_charges').val(data.excess_charges);
				 $('#arrears').val(data.arrears);
				 $('#other').val(data.other);
				 $('#avg_units').val(data.avg_units);
				 $('#penalty').val(data.penalty);
				 $('#open_balance').val(data.open_balance);
				 $('#net_amount').val(data.net_amount);
				 $('#rebate').val(data.rebate);
				 $('#remarks').val("Bill Correction"+data.remarks==null?"":data.remarks);
				 $('#previous_mc_status').val(data.mc_status);
				 $('#previous_water_charge').val(data.water_charges);
				 $('#previous_sw_charge').val(data.sw_charges);
				 $('#previous_mtr_rent').val(data.mtr_rent);
				 $('#previous_arrears').val(data.arrears);
				 $('#previous_net_amt').val(data.net_amount);
				 
				 if(data.mc_status==null || data.mc_status==""){
					    $('#mc_status').val(16).trigger("change");
				 }else{
					 
				    $('#mc_status').val(data.mc_status).trigger("change");
				 }
				 
				 
				 
				 
				 /*  $('#mc_status').append($('<option>',{
						value:data.mc_status,
						text:data.mc_statusname
				 
				  })); */
				 
				  
				 if(data.billnumber==null){
					  alert("Bill was not generated for this connection number")
					 $('#billprintid').hide();
				 }else{
					 
					 billno=data.billnumber;
					 $('#billprintid').show();
				 }
				 
				 var checkum="Unmetered".indexOf(con_type) > -1;
				
				 if(checkum){
					 
					 $('#previous_readingdiv').hide();
					 $('#present_readingdiv').hide();
					 $('#consumptiondiv').hide();
					 //$('#mc_statusdiv').hide();
					 //$('#mc_status').val(15);
				 }else{
					 $('#previous_readingdiv').show();
					 $('#present_readingdiv').show();
					 $('#consumptiondiv').show();
					 $('#mc_statusdiv').show();
				 }
				 
				 bill_date_nep=data.bill_date_nep;
				 due_date_nep=data.due_date_nep;
				 if(data.receipt_no==null || data.receipt_no==""){
					 
				 }else{
					 alert("Payment already done for this Latest Bill Now You cannot do the Bill Correction...");
					 $('#caliddiv').hide(); 
					 $('#subiddiv').hide(); 
				 }
				 
				 
				 
				   if(data.monthyearnep==monthyearLM){
					   
				   }else{
					   alert("Month Year : "+data.monthyearnep+" already Closed.You can't do the Bill Correction..");
					   $('#caliddiv').hide(); 
					   $('#subiddiv').hide(); 
				   }
					 
				 
				 
				 
		     });
			}
			  
		  }
		});
}

$(document).ready(function(){   
	
	$('#billingscreens').show();
	$('#billingManagement').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Monthly Billing";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	
	
	
	  $('#rdng_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#rdng_date_nep').val();
			 		getEngDate(rdngdtnep,1);
			 		getBillPeriod();
			 	}
	  });
	  
	  $('#bill_date_nep').nepaliDatePicker({
			    dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var billdtdtnep = $('#bill_date_nep').val();
			 		getEngDate(billdtdtnep,3);
			 		
			 	}
				
	  });
	  
	  $('#due_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#due_date_nep').val();
			 		getEngDate(rdngdtnep,2);
			 		
			 	}
		});
	  
	  
	  
});

function previousPaymentsPopUp(){
	
	$('#modal_default').show();
}

function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 

$(document).bind("contextmenu",function(e) {
	 e.preventDefault();
	});


</script>


<style>

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.modal.fade .modal-dialog {
width: 1200px;
}

</style>


