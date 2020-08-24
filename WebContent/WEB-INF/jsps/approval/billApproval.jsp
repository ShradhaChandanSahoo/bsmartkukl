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
							
							<form action="./billApproveStatus" method="POST">
							
							    <input type="text" id="billId" name="billId" hidden="true">
								<input type="text" id="billStatus" name="billStatus" hidden="true">
								<input type="text" id="monthyear" name="monthyear" hidden="true">
								
							
							<fieldset class="content-group" > 
								<legend class="text-bold">Bill Correction Approval
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="submit" class="btn btn-success btn-ladda"  data-style="expand-right" data-spinner-size="20"  onclick="return billDetailsPopUp('CHECKBOXYES',1)"><span class="ladda-label" >Approve</span></button>
								<button type="submit" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return billDetailsPopUp('CHECKBOXYES',2)"><span class="ladda-label" >Reject</span></button>
								
								</legend>
							
							<div class="table-responsive">
								<table class="table datatable-basic table-bordered">
									<thead>
										<tr>
											<th> <input type="checkbox" class="control-primary" id="selectall" onClick="selectAll(this)" > </th>
											<th>Connection&nbsp;No</th>
											<th style="min-width: 180px;">Name&nbsp;in&nbsp;Eng</th>
											<th>Name in Nepali</th>
											<th>Connection Category</th>
											<th>Connection Type</th>
											<th>Mobile No</th>
											<th>Remarks</th>
										</tr>
									</thead>
									<tbody>
										
									<c:forEach var="app" items="${pendingBillsApproval}">
											<tr>
												
												<td><div id="docketNumDiv"><input type="checkbox" class="control-primary" autocomplete="off" placeholder="" name="billidkey" id="billidkey" value="${app.billid}" /></div></td>
												<td><a href="#" onclick="showApprovalRecord('${app.connection_no}',${app.billid});">${app.connection_no}</a></td>
												<td>${app.name_eng}</td>
												<td>${app.name_nep}</td>
												<td>${app.con_category}</td>
												<td>${app.con_type}</td>
												<td>${app.mobile_no}</td>
												<td>${app.remarks}</td>
											</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</fieldset>
					
						
						<div class="col-md-12" id="billApprovalId" style="display: none;">
							
					
							<fieldset class="content-group">
					
								<legend class="text-bold" style="font-size: 18px;">Bill Correction Approval - <font color="red"><span id="connectionidspan"></span></font></legend>
								
								
												
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection No &nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="connection_no" id="connection_no" placeholder="Connection No..." readonly="readonly">
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
								
								<div class="row">
								
								
								    <div class="col-md-2">
										<div class="form-group">
											<label>Previous Reading Date &nbsp;<font color="red">*</font></label> 
											<div class="input-group">
													<input type="text" class="form-control" name="previous_read_date" id="previous_read_date" readonly="readonly">
													
											</div>
										</div>
									</div>
									
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Reading Date in English</label> 
											<div class="input-group">
													<input type="text" class="form-control " name="rdng_date" id="rdng_date"  readonly="readonly">
													
											</div>
										</div>
									</div>


					

										<div class="col-md-2">
										<div class="form-group">
											<label>Reading Date in Nepali</label> 
											<input type="text" id="rdng_date_nep" name="rdng_date_nep" class="form-control nepali-calendar"  readonly="readonly"
												placeholder="Reading Date in Nepali..." name="">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Bill Period</label> <input type="text" class="form-control" name="bill_period" id="bill_period"  readonly="readonly"
												placeholder="Bill Period...">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Bill Date in Eng</label> 
											<div class="input-group">
													<input type="text" class="form-control" name="bill_date" id="bill_date"  readonly="readonly">
											</div>
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Bill Date in Nepali</label> 
											<input type="text" id="bill_date_nep" name="bill_date_nep" class="form-control nepali-calendar"  readonly="readonly"
												placeholder="Bill Date in Nepali...">
										</div>
									</div>
									
									
					
								</div>
								
								
								
								<div class="row">
									
								   <div class="col-md-2">
										<div class="form-group">
											<label>Previous Reading</label> <input type="text" class="form-control" name="previous_reading" id="previous_reading" readonly="readonly"
												placeholder="Previous Reading...">
										</div>
									</div>
									
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Present Reading</label> <input type="text" name="present_reading" id="present_reading"  readonly="readonly"
												class="form-control" placeholder="Present Reading...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Units Consumed</label> <input type="text" class="form-control" name="consumption" id="consumption" readonly="readonly"
												placeholder="Units Consumed...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Meter Status</label> <input type="text" class="form-control" name="mc_status" id="mc_status" readonly="readonly"
												placeholder="Units Consumed...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Minimum Charges</label> <input type="text" class="form-control"  readonly="readonly"
												placeholder="Minimum Charges..." name="minimum_charges" id="minimum_charges">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Additional Charges</label> <input type="text" class="form-control" name="additional_charges" readonly="readonly" id="additional_charges"
												placeholder="Additional Charges...">
										</div>
									</div>
									
					
									
									
									
									
									
					
								</div>
								
								<div class="row">
								
								
								   <div class="col-md-2">
										<div class="form-group">
											<label>Water Charges</label> <input type="text" class="form-control" name="water_charges" id="water_charges" readonly="readonly"
												placeholder="Water Charges...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Sewerage Charges</label> <input type="text" class="form-control" name="sw_charges" id="sw_charges" readonly="readonly"
												placeholder="Sewage...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Meter Rent</label> <input type="text" class="form-control" name="mtr_rent" readonly="readonly" id="mtr_rent"
												placeholder="Meter Rent...">
										</div>
									</div>
									
					
									
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Excess Charges</label> <input type="text" class="form-control" name="excess_charges"  readonly="readonly" id="excess_charges"
												placeholder="Excess Charges...">
										</div>
									</div>
									
									
									
									<div class="col-md-2">
										<div class="form-group"> 
											<label>Arrears</label> <input type="text" class="form-control" name="arrears" id="arrears" readonly="readonly"
												placeholder="Arrears...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Others</label> <input type="text" name="other" readonly="readonly" id="other"
												class="form-control" placeholder="Others...">
										</div>
									</div>
					
									
					
					
								</div>
								
								
								<div class="row">
								
								
								
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Average</label> <input type="text" class="form-control" name="avg_units" id="avg_units" readonly="readonly"
												placeholder="Average...">
										</div>
									</div>
									
									
									
					
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Penalty</label> <input type="text" class="form-control" name="penalty" id="penalty" readonly="readonly"
												placeholder="Penalty...">
										</div>
									</div>
									
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Rebate</label> <input type="text" class="form-control" name="rebate" id="rebate" readonly="readonly"
												placeholder="Rebate...">
										</div>
									</div>
					
									
									<div class="col-md-2" hidden="true">
										<div class="form-group">
											<label>Opening Balance</label> <input type="text" name="open_balance" readonly="readonly" id="open_balance"
												class="form-control" placeholder="Opening Balance...">
										</div>
									</div>
									
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Net Amount</label> <input type="text" name="net_amount" id="net_amount" readonly="readonly"
												class="form-control" placeholder="Net Amount...">
										</div>
									</div>
					
								</div>
								
								
								
								<div class="row">
									
									
									<div class="col-md-12">
										<div class="form-group">
											<label>Remarks</label> 
											<textarea placeholder="Enter your Remarks here" class="form-control" cols="1" rows="1" name="remarks" id="remarks"></textarea>
										</div>
									</div>
					
								</div>
					
							 <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />	
					
							</fieldset>
								<!-- <div class="text-right">
									<button type="submit" class="btn btn-primary btn-ladda btn-ladda-progress"  data-style="expand-right" data-spinner-size="20" onclick="setBillStatus(1);"><span class="ladda-label" >Approve</span></button>
									<button type="submit" class="btn btn-danger btn-ladda btn-ladda-progress"  data-style="expand-right" data-spinner-size="20" onclick="setBillStatus(2);"><span class="ladda-label" >Reject</span></button>
								</div> -->
						
						</div>
						</form>
						</div>
						
						</div>



<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">
		<fieldset class="content-group">
			<legend class="text-bold">
				Bill Correction List(Approved & Rejected)
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get Bill Correction List</span>
				</button>

			</legend>

			<div class="table-responsive">
				<table class="table datatable-basic table-bordered"
					id="consumerTable">
					<thead>
						<tr>
							<th>Sl&nbsp;No</th>
							<th>Con&nbsp;No</th>
							<th>Name</th>
							<th>Type</th>
							<th>Ward</th>
							<!-- <th>BillNo</th> -->
							<th>Arrears</th>
							<th>Water Charge</th>
							<th>SW Charge</th>
							<th>Meter Rent</th>
							<th>NET</th>
							<th>Status</th>
							<th>Remarks</th>

						</tr>
					</thead>
					<tbody id="tableBodyId">


					</tbody>
				</table>
			</div>
		</fieldset>



	</div>

</div>


<div id="loading" hidden="true" style="text-align: center;">
	<h3 id="loadingText">Loading..... Please wait.</h3>
	<img alt="image" src="./resources/images/loading.gif"
		style="width: 3%; height: 3%;">
</div>


<script>

$(document).ready(function(){   
	
	$('#bilapprovalModule').show();
	$('#bilapprovalModule').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Bill Approval";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
});

var billprimaryKeys="";

function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 

function selectAll(source) {
	 
	   var flagChecked = 0;
		checkboxes = document.getElementsByName('billidkey');
		for(var i =0;i<checkboxes.length;i++)
			{
			checkboxes[i].checked = source.checked;
			if(checkboxes[i].checked)
			 {
				flagChecked = 1;
			}
			}
		
		 if(flagChecked==0)
		{
			$('#docketNumDiv span:first-child').removeClass("checked");
		}
		else{
			$('#docketNumDiv span:first-child').addClass("checked");
		} 
		
		
}


function getPendingList(){
	
	
	$("#loading").show();
	 $.ajax({
			type : "GET",
			url : "./getBillCorrectionChangeList",	
			dataType : "json",
			cache : false,
			async : false,
			success : function(response)
			{	
				if(response.length==0)
				{
					$("#tableBodyId").empty();
					swal({
			             title: "No Data Found.",
			             text: "",
			             confirmButtonColor: "#2196F3",
			         }); 
					$("#loading").hide(); 
					return false;
				}	
				
				var ConnectionData="";
								
							    var count;
								for( var i=0;i<response.length;i++)
								{
				 					data = response[i];
				 					count=i+1;
				 					ConnectionData+="<tr>"+
									"<td>"+count+"</td>";
							  		if(data.connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connection_no+"</td>"; }
							  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
							  		if(data.con_type == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.con_type+"</td>";}
							  		if(data.wardno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.wardno+"</td>";}
							  		/* if(data.billno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.billno+"</td>";} */
							  		if(data.arrears == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.arrears+"</td>";}
							  		if(data.water_charges == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.water_charges+"</td>";}
							  		if(data.sw_charges == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.sw_charges+"</td>";}
							  		if(data.mtr_rent == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.mtr_rent+"</td>";}
							  		if(data.net_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.net_amount+"</td>";}
							  		if(data.approve_status == null ){ConnectionData+="<td></td>";}
							  		else
							  		{ 
							  			if(data.approve_status == '1'){ConnectionData+="<td>Approved</td>";}
							  			else{ConnectionData+="<td>Rejected</td>";}
							  		}
							  		
							  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
							  		
							  		ConnectionData+="</tr>";	
								} 
			$("#tableBodyId").html(ConnectionData);
			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
			$("#loading").hide(); 
	
	
	
}
	 })
}


	
function billDetailsPopUp(billprimaryKeys,status)
{
	if(billprimaryKeys=='CHECKBOXYES')//This is for Assign Complaint CheckBox
	{
	var checkboxes = document.getElementsByName('billidkey');
	var checks = "";
	for(var i =0;i<checkboxes.length;i++)
	{
		
		if(checkboxes[i].checked)
		 {
			checks = checks+checkboxes[i].value+",";
			
		}
	}
	
	if(checks == "")
	{
		alert("Please Select the Connection Number ...");
		return false;
	}
	
	checks = checks.substring(0,checks.length-1);
	
	billprimaryKeys=checks;
	
	
	$('#billStatus').val(status);
	$('#billId').val(billprimaryKeys);
	
	
	
	
	}
}
	
	
/* function setBillStatus(billStatus){
	
	$('#billStatus').val(billStatus);
	
} */

function showApprovalRecord(connectionNo,billId){
	
	$('#connectionidspan').html(connectionNo);
	$('#billId').val(billId);
	$('#billApprovalId').show();
	 
	$.ajax({
		  type: "GET",
		  url: "./billing/approveBillDetails/"+connectionNo,
		  dataType: "JSON",
		  async       : false,
		  
		  success: function(response){
			 var consumer=response[0];
			 var ledger=response[1];
			 
			 $.each(consumer, function(index, data){
				 
				 $('#connection_no').val(data.connection_no);
				 $('#name_eng').val(data.name_eng);
				 $('#ledgerno').val(data.ledgerno);
				 $('#folio').val(data.folio);
				 $('#con_category').val(data.con_category);
				 $('#con_type').val(data.con_type);
				 $('#pipe_size').val(data.pipe_size);
				 $('#con_satatus').val(data.con_satatus);
				 $('#name_nep').val(data.name_nep);
			 
			 });
			 
			 $.each(ledger, function(index, data){
				 
				  $('#previous_reading').val(data.previous_reading);
				  $('#present_reading').val(data.present_reading);
				  $('#consumption').val(data.consumption);
				 
				  $('#rdng_date').val(data.rdng_date);
				  $('#previous_read_date').val(data.previous_read_date);
				  $('#rdng_date_nep').val(data.rdng_date_nep);
				  $('#bill_date').val(data.bill_date);
				  $('#bill_date_nep').val(data.bill_date_nep);
				 
				  $('#mc_status').val(data.mc_status);
				 
				 
				  $('#water_charges').val(data.water_charges);
				  $('#sw_charges').val(data.sw_charges);
				  $('#net_amount').val(data.net_amount);
				  $('#additional_charges').val(data.additional_charges); 
				  $('#minimum_charges').val(data.minimum_charges); 
				  
				  $('#excess_charges').val(data.excess_charges);
				  $('#mtr_rent').val(data.mtr_rent);
				  $('#arrears').val(data.arrears);
				  $('#other').val(data.other);
				  $('#avg_units').val(data.avg_units);
				  $('#penalty').val(data.penalty);
				  $('#rebate').val(data.rebate);
				  $('#open_balance').val(data.open_balance);
				  $('#bill_period').val(data.bill_period);
				  $('#remarks').val(data.remarks);
				 
				  $('#monthyear').val(data.monthyear);
				  
		     });
			  
		  }
		});
	
	
	
}


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

.dataTables_filter {
    /* display: none; */
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}
</style>


