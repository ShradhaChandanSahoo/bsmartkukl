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
							
							<form:form action="./meterApproveStatus" role="form" modelAttribute="meterChangeApproveEntity" commandName="meterChangeApproveEntity" method="POST" id="meterChangeApproveEntity">
							    <input type="text" id="id" name="id" hidden="true">
								<input type="text" id="meterStatus" name="meterStatus" hidden="true">
							
							<fieldset class="content-group" > 
								<legend class="text-bold">Metering Approval
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="submit" class="btn btn-success btn-ladda"  data-style="expand-right" data-spinner-size="20"  onclick="return meterDetailsPopUp('CHECKBOXYES',1)"><span class="ladda-label" >Approve</span></button>
								<button type="submit" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return meterDetailsPopUp('CHECKBOXYES',2)"><span class="ladda-label" >Reject</span></button>
								
								</legend>
							
							<div class="table-responsive">
								<table class="table datatable-basic table-bordered">
									<thead>
										<tr>
											<th> <input type="checkbox" class="control-primary" id="selectall" onClick="selectAll(this)" > </th>
											<th>Connection&nbsp;No</th>
											<th>Meter No</th>
										    <th>Meter Previous Reading</th>
											<th>Meter Present Reading</th>
											<th>Meter Change Units</th>
											<th>Installation Date in Nepali</th>
											<th>Installation Date in Englsih</th>
											
										</tr>
									</thead>
									<tbody>
										
									<c:forEach var="app" items="${pendingMetersApproval}">
											<tr>
												
												<td><div id="docketNumDiv"><input type="checkbox" class="control-primary" autocomplete="off" placeholder="" name="meteridkey" id="meteridkey" value="${app.id}" /></div></td>
												<td><a href="#" onclick="showApprovalRecord(${app.connection_no},${app.id});">${app.connection_no}</a></td>
												<td>${app.new_meter_no}</td>
												<td>${app.fr}</td>
												<td>${app.ir}</td>
												<td>${app.mcunits}</td>
												<td>${app.new_ins_date_nep}</td>
												<td><fmt:formatDate value="${app.new_ins_date_eng}" pattern="yyyy/MM/dd"/></td>
											</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</fieldset>
					
						
						<div class="col-md-12" id="meterApprovalId" style="display: none;">
							
					
							<fieldset class="content-group">
					
								<legend class="text-bold" style="font-size: 18px;">Meter Change Approval - <font color="red"><span id="connectionidspan"></span></font></legend>
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection No &nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="connection_no" id="connection_no" placeholder="Connection No..." readonly="readonly">
										</div>
									</div>
									
									<div class="col-md-3">
												<div class="form-group">
													<label>Meter No&nbsp;<font color="red">*</font></label>
					                                <form:input path="new_meter_no" id="new_meter_no" name="new_meter_no" type="text" class="form-control" placeholder="Initial Reading" />
				                                </div>
									</div>
									
									<div class="col-md-3">
												<div class="form-group">
													<label>Previous Reading&nbsp;<font color="red">*</font></label>
					                                <form:input path="fr" id="fr" name="fr" type="text" class="form-control" placeholder="Initial Reading" />
				                                </div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Present Reading&nbsp;<font color="red">*</font></label>
			                                <form:input path="ir" id="ir" name="ir" type="text" class="form-control" placeholder="Initial Reading" />
		                                </div>
									</div>
											
								</div>
								
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Meter Installation Date in Nepali&nbsp;<font color="red">*</font></label>
											<div class="input-group">
												<span class="input-group-addon"><i class="icon-calendar22"></i></span>
				                               <form:input path="new_ins_date_nep" type="text" id="new_ins_date_nep" name="new_ins_date_nep" class="form-control nepali-calendar" placeholder="Meter Installation Date in Nepali" readonly="readonly"/>
				                              </div>
				                           </div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
										<label>Meter Installation Date in English&nbsp;<font color="red">*</font></label>
										<div class="input-group">
											<span class="input-group-addon"><i class="icon-calendar22"></i></span>
			                               <form:input path="new_ins_date_eng" type="text" id="new_ins_date_eng" name="new_ins_date_eng" class="form-control" placeholder="Meter Installation Date in English" readonly="readonly" />
			                              </div>
			                           </div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>MC Units&nbsp;<font color="red">*</font></label>
			                                <form:input path="mcunits" id="mcunits" name="mcunits" type="text" class="form-control" placeholder="MC Units"/>
		                                </div>
									</div>
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Remarks/Reason</label> 
											<form:textarea path="remarks" id="remarks" name="remarks" placeholder="Enter your Remarks here" class="form-control" cols="2" rows="1"></form:textarea>
										</div>
									</div>
									
								</div>
								
								<!-- <div class="row">
									
								</div> -->
								
								 <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />	
					
							</fieldset>
								<!-- <div class="text-right">
									<button type="submit" class="btn btn-primary btn-ladda btn-ladda-progress"  data-style="expand-right" data-spinner-size="20" onclick="setBillStatus(1);"><span class="ladda-label" >Approve</span></button>
									<button type="submit" class="btn btn-danger btn-ladda btn-ladda-progress"  data-style="expand-right" data-spinner-size="20" onclick="setBillStatus(2);"><span class="ladda-label" >Reject</span></button>
								</div> -->
						
						</div>
						</form:form>
						</div>
						
						</div>



<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">



		<fieldset class="content-group">
			<legend class="text-bold">
				Meter Approval List(Approved & Rejected)
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get Meter Approval List</span>
				</button>

			</legend>

			<div class="table-responsive">
				<table class="table datatable-basic table-bordered"
					id="consumerTable">
					<thead>
						<tr>
							<th>Sl&nbsp;No</th>
							<th>Connection No</th>
							<th>Meter No</th>
							<th>Meter Previous Reading</th>
							<th>Meter Present Reading</th>
							<th>Meter Change Units</th>
							<th>Installation Date in Nepali</th>
							<th>Installation Date in Englsih</th>
							<th>Status</th>

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
	$('#masterApprovalScreen').show();
	$('#masterApproval').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Master Approval";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
});

var meterprimaryKeys="";

function selectAll(source) {
	 
	   var flagChecked = 0;
		checkboxes = document.getElementsByName('meteridkey');
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
	
function meterDetailsPopUp(meterprimaryKeys,status)
{
	if(meterprimaryKeys=='CHECKBOXYES')//This is for Assign Complaint CheckBox
	{
	var checkboxes = document.getElementsByName('meteridkey');
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
	
	meterprimaryKeys=checks;
	
	$('#meterStatus').val(status);
	$('#meteridkey').val(meterprimaryKeys);
	
	
	}
}

function showApprovalRecord(connectionNo,id){
	
	$('#connectionidspan').html(connectionNo);
	$('#id').val(id);
	$('#meterApprovalId').show();
	 
	$.ajax({
		  type: "GET",
		  url: "./metering/approveMeterDetails/"+connectionNo,
		  dataType: "JSON",
		  async       : false,
		  
		  success: function(response){
			 var consumer=response[0];
			 
			 $.each(consumer, function(index, data){
				 
				 $('#connection_no').val(data.connection_no);
				 $('#new_meter_no').val(data.new_meter_no);
				 $('#fr').val(data.fr);
				 $('#ir').val(data.ir);
				 $('#new_ins_date_nep').val(data.new_ins_date_nep);
				 $('#new_ins_date_eng').val(moment(data.new_ins_date_nep).format('YYYY/MM/DD'));
				 $('#mcunits').val(data.mcunits);
				 $('#remarks').val(data.remarks);
			 
			 });
		  }
		});
	
	
	
}


function getPendingList(){
	
	
	$("#loading").show();
	 $.ajax({
			type : "GET",
			url : "./getMeterApproveList",	
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
							  		if(data.new_meter_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_meter_no+"</td>"; }
							  		if(data.fr == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.fr+"</td>";}
							  		if(data.ir == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ir+"</td>";}
							  		if(data.mcunits == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.mcunits+"</td>";}
							  		if(data.new_ins_date_nep == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_ins_date_nep+"</td>";}
							  		if(data.new_ins_date_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+moment(data.new_ins_date_eng).format('YYYY-MM-DD')+"</td>";}
							  		if(data.approve_status == null ){ConnectionData+="<td></td>";}
							  		else
							  		{ 
							  			if(data.approve_status == '1'){ConnectionData+="<td>Approved</td>";}
							  			else{ConnectionData+="<td>Rejected</td>";}
							  		}
							  		
							  		ConnectionData+="</tr>";	
								} 
			$("#tableBodyId").html(ConnectionData);
			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
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




</script>

<style>

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
}

.datatable-header, .datatable-footer {
    padding: 8px 0px 0 0px;
}

.dataTables_filter {
    display: none;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}
</style>