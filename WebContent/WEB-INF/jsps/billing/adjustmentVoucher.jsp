
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
							<form:form action="./createAdjustment" role="form"  modelAttribute="adjustmentVoucherEntity" commandName="adjustmentVoucherEntity" method="POST" id="adjustmentVoucherEntity">
					
							<fieldset class="content-group">
					
								<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">Adjustment Voucher</legend>
								<br>
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection No &nbsp;<font color="red">*</font></label> <form:input type="text"
												class="form-control" name="connection_no" path="connection_no" id="connection_no" placeholder="Connection No..." onchange="getConnectionDetails(this.value)" onkeyup="convertToUpperCase()"></form:input>
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Name</label> <input type="text"  class="form-control" name="name_eng" id="name_eng" readonly="readonly"
												placeholder="Name">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection Category</label> <input type="text" class="form-control" name="con_category" id="con_category" readonly="readonly"
												placeholder="Connection Category...">
										</div>
									</div>
					
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Area No</label> <input type="text" class="form-control" name="area_no"  id="area_no" readonly="readonly"
												placeholder="Area No...">
										</div>
									</div>
									
									
									
								
									
					
					
								</div>
								
								
								<div class="row">
								
									<div class="col-md-3">
										<div class="form-group">
											<label>Pipe Diameter</label> <input type="text" class="form-control" name="pipe_size" id="pipe_size" readonly="readonly"
												placeholder="Pipe Diameter...">
										</div>
									</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Adjustment No&nbsp;<font color="red">*</font></label> <form:input type="text" class="form-control" name="adjustment_no" id="adjustment_no" path="adjustment_no"
											readonly="true"></form:input>
										</div>
									</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Adjustment Date&nbsp;<font color="red">*</font></label> 
											<input type="text" id="adjust_date" name="adjust_date" class="form-control nepali-calendar"
												placeholder="Adjustment Date">
									</div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Adjustment Amount&nbsp;<font color="red">*</font></label> <form:input type="text" class="form-control" name="adjustment_amount" id="adjustment_amount" path="adjustment_amount"
												placeholder="Adjustment Amount"></form:input>
										</div>
									</div>
									
									<div class="col-md-12">
										<div class="form-group">
											<label>Remarks&nbsp;<font color="red">*</font></label> 
											<textarea placeholder="Enter your Remarks here in English" class="form-control" cols="1" rows="1" name="remarks" id="remarks"></textarea>
										</div>
									</div>
					
								</div>
								
							</fieldset>
							
								<div class="text-center">
									<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label" >Clear</span></button>
									<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return checkValid();"><span class="ladda-label">Submit</span></button>
								</div>
							
						</form:form>
						</div>
					</div>
					
					
					
					
					<div class="panel panel-flat">
					<div class="panel-body" style="overflow: scroll;">
							
							
							
							<fieldset class="content-group" > 
								<legend class="text-bold">Get Adjustment List
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return getPendingList();"><span class="ladda-label" >Get Adjustment List</span></button>
								
								</legend>
							
							<div class="table-responsive" >
								<table class="table datatable-basic table-bordered" id="adjustTable">
									<thead>
										<tr>
											<th>Sl&nbsp;No</th>
											<th>Con&nbsp;No</th>
											<th style="min-width: 180px;">Name</th>
											<th>AreaNo</th>
											<th>Pipe(inch)</th>
											<th>Con Type</th>
											<th>Adjustment No</th>
											<th>Adjustment Amount</th>
											<th>Adjustment Date</th>
											<th>Prepared By</th>
											<th style="min-width: 250px;">Remarks</th>
											
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
                         	<h3 id="loadingText">Loading..... Please wait. </h3> 
						 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
						</div>
					
<script>

$(document).ready(function(){   
	
	$('#adjvocApprovalScreen').show();
	$('#adjvocApproval').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Adjustment Voucher";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	
	
	  $('#adjust_date').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				
	  });
	  
	var adjustmentNo="${adjustmentNo}";   
	$('#adjustment_no').val(adjustmentNo);  
});


function getPendingList(){
	
	
	$("#loading").show();
	 $.ajax({
			type : "GET",
			url : "./getAdjustmentList",	
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
							  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
							  		if(data.pipe_size == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.pipe_size+"</td>";}
							  		if(data.con_type == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.con_type+"</td>";}
							  		if(data.adjustment_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adjustment_no+"</td>";}
							  		if(data.adjustment_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adjustment_amount+"</td>";}
							  		if(data.adjustment_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adjustment_date+"</td>";}
							  		if(data.prepared_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.prepared_by+"</td>";}
							  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
							  		ConnectionData+="</tr>";	
								} 
			$("#tableBodyId").html(ConnectionData);
			loadSearchFilter1('adjustTable',ConnectionData,'tableBodyId');
			$("#loading").hide(); 
	
	
	
}
	 })
}

	 function loadSearchFilter1(param,tableData,temp)
	 {
	     $('#'+param).dataTable().fnClearTable();
	     $('#'+param).dataTable().fnDestroy();
	     $('#'+temp).html(tableData);
	     $('#'+param).dataTable();

	 } 
	 
	 function convertToUpperCase(){
			
			$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
			
		}
	 
function clearButton(){
	
	window.location.href="./adjustmentVoucher";
}
function checkValid(){
	
	var desig="${userbdesignation}";
	
	if(desig=="Revenue Incharge"){
	
	var connectionNo=$("#connection_no").val();
	var adjustment_no=$("#adjustment_no").val();
	var adjustment_amount=$("#adjustment_amount").val();
	var adjust_date=$('#adjust_date').val();
	var remarks=$('#remarks').val();
	
	if(connectionNo=="" || connectionNo==null){
		alert("Please Enter Connection No");
		return false;
	}else if(adjustment_no=="" || adjustment_no==null){
		alert("Please enter Adjustment No");
		return false;
	}
	else if(adjust_date=="" || adjust_date==null){
		alert("Please select Adjustment date");
		return false;
	}
	else if(adjustment_amount=="" || adjustment_amount==null || $.isNumeric(adjustment_amount)==false){
		alert("Please enter Adjustment Amount in Numeric");
		$("#adjustment_amount").val("");
		return false;
	}else if(remarks=="" || remarks==null){
		alert("Please enter remarks");
		return false;
	}
	else{
    	
    	 if(confirm("Are you sure to submit the Application?")){
		 }else{
			 return false;
		 }
		    
    }
	}else{
		alert("Sorry You dont have Permission for Adjustment Approval");
		return false;
	} 
}





function getConnectionDetails(connectionno){

$.ajax({
	type : "GET",
	url : "./getConsumerMasterDetails1/"+connectionno,	
	dataType : "json",
	cache : false,
	async : false,
	success : function(response)
	{	
					if(response.length==0)
					{
						alert("Entered Connection Number is not available");
					}	
					else{
		
						
					   for( var i=0;i<response.length;i++)
						{
		 					data = response[i];
		 					$('#name_eng').val(data.name_eng);
		 					$('#con_category').val(data.con_category);
		 					$('#exist_con_type').val(data.con_type);
		 					$('#area_no').val(data.area_no);
		 					$('#pipe_size').val(data.pipe_size);
		 					$('#exist_con_status').val(data.con_satatus);
						} 
						
					}
			
		
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

.modal.fade .modal-dialog {
width: 1200px;
}

</style>


