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
					<div class="panel-body" style="overflow: scroll;">
							
							<form action="./newConnApproveStatus" method="POST">
							
							    <input type="text" id="connectId" name="connectId" hidden="true">
								<input type="text" id="conStatus" name="conStatus" hidden="true">
							
							<fieldset class="content-group" > 
								<legend class="text-bold">New Connection Approval To Start Billing
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<!-- <button type="submit" class="btn btn-success btn-ladda"  data-style="expand-right" data-spinner-size="20"  onclick="return billDetailsPopUp('CHECKBOXYES',1)" style="display: none;"><span class="ladda-label">Approve</span></button> -->
								<!-- <button type="submit" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return billDetailsPopUp('CHECKBOXYES',2)"><span class="ladda-label" >Reject</span></button> -->
								
								</legend>
							
							<div class="table-responsive" >
								<table class="table datatable-basic table-bordered" >
									<thead>
										<tr>
											<!-- <th> <input type="checkbox" class="control-primary" id="selectall" onClick="selectAll(this)" > </th> -->
											<th></th>
											<th>Con&nbsp;No</th>
											<th style="min-width: 180px;">Name</th>
											<th>WardNo</th>
											<th>AreaNo</th>
											<th>Pipe(inch)</th>
											<th>Con Type</th>
											<th>Con Category</th>
											<th>Sewage Used</th>
											<th>Meter Hired</th>
											
										</tr>
									</thead>
									<tbody>
										
									<c:forEach var="app" items="${masterListApproval}">
											<tr>
												
												<%-- <td><div id="docketNumDiv"><input type="checkbox" class="control-primary" autocomplete="off" placeholder="" name="connectIdkey" id="connectIdkey" value="${app.connection_no}" /></div></td> --%>
												<%-- <td><button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default3" onclick="showConnNo(${app.connection_no});" id="onshow_callback2" style="float: right;">Approve</button></td> --%>
												<td><button type="button" class="btn bg-teal btn-sm"  onclick="newCalculate(${app.connection_no});" id="onshow_callback2" style="float: right;">Approve</button></td>
												<td>${app.connection_no}</td>
												<td>${app.name_eng}</td>
												<td>${app.ward_no}</td>
												<td>${app.area_no}</td>
												<td>${app.pipe_size}</td>
												<td>${app.con_type}</td>
												<td>${app.con_category}</td>
												<td>${app.sewage_used}</td>
												<td>${app.meterHired}</td>
											</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</fieldset>
					
						
						
						</form>
						</div>
						
						</div>
					
					
					
					<!-- Basic modal -->
				<div id="modal_default3" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">Enter Reading</h5>
							</div>

							<div class="modal-body">
								
								<div class="col-md-12">
								
									<div class="col-md-3" id="mc_statusdivmcc">
										<div class="form-group">
											<label>Meter Status&nbsp;<font color="red">*</font></label>
												
												<select data-placeholder="Select" class="select" id="mc_status3m" name="mc_status3m" required="required">
												    <option value="" data-icon="icon-git-branch">Select</option>
													
													<c:forEach items="${observationList}" var="ob">
													<option value="${ob.id}-${ob.observationName}">${ob.observationName}</option>
												   </c:forEach>
													
												</select>
												
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Last&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="previous_reading3m" id="previous_reading3m" placeholder="Previous Reading" onchange="calculateConsumption2();" value="0">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Present&nbsp;Read(KL)&nbsp;<font color="red">*</font></label> <input type="text" class="form-control" name="present_reading3m" id="present_reading3m" onchange="calculateConsumption2();" value="0"
												placeholder="Present Reading">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Consumption(KL)</label> <input type="text" class="form-control" name="consumption3m" id="consumption3m" readonly="readonly" value="0"
												placeholder="Consumption">
										</div>
									</div>
									
									
									
									
									<div class="col-md-1">
										<div class="form-group">
											<label>&nbsp;</label> 
									     <button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="calculateBill2();"><span class="ladda-label" >Approve</span></button>
										</div>
									</div>
					
								</div>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				<!-- /basic modal -->
			<!-- New connection apporval list -->
			
<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">
			   <fieldset class="content-group">
				<legend class="text-bold">
				New Connection Approval List
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda" data-style="expand-right" data-spinner-size="20" onclick="return getApprovalList();">
	            <span class="ladda-label">Get New Connection Approval List</span>
				</button>

			</legend>
				<div class="row">
					<div class="table-responsive">
						<div class="col-md-16">
							<div class="panel-body" style="margin-top: -3px; padding: 6px;">
								<table class="table datatable-basic table-bordered"
									id="tableForm">
									<thead style="width: 100px">

										<tr class="bg-blue">
											<th></th>
							<th style="min-width:220px;">Name</th>
							<th>Con&nbsp;No</th>
							<th>Area No</th>
							<th style="min-width:50px;">Connection Type</th>
							<th style="min-width:200px;">Approved By</th>
							<th>Approved Date</th>
										</tr>

									</thead>
								
					                <tbody id="tableBodyId1">
					                </tbody>
								</table>

							</div>

						</div>
					</div>

				</div>

			</fieldset>
	
</div></div>

 <div id="loading" hidden="true" style="text-align: center;">
                         	<h3 id="loadingText">Loading..... Please wait. </h3> 
						 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
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
//------------------------ new connection approval list --------------------------
function getApprovalList(){
		
		
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getNewConnectionApproveList",	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					 if(response.length==0)
					{
						$("#tableBodyId1").empty();
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
								  		if(data.name == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name+"</td>"; }
								  		if(data.conn == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.conn+"</td>"; }
								  		if(data.areaNo == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.areaNo+"</td>";}
								  		if(data.connType == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connType+"</td>";}
								  		if(data.lastUpdatedBy == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.lastUpdatedBy+"</td>";}
								  		if(data.lastUpdatedDate == "" ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.lastUpdatedDate+"</td>";
								  	}
								  		
								  		
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId1").html(ConnectionData);
				loadSearchFilter1('tableForm',ConnectionData,'tableBodyId1');
				$("#loading").hide(); 
		
		
		
	}
		 })
	}





















//------------------------ new connection approval list --------------------------
function calculateConsumption2(){
	 var lr=$('#previous_reading3m').val();
	 var cr=$('#present_reading3m').val();
	
	if(lr=="" || lr==null){
		alert("Please enter Last Reading");
		return false;
	}else if(cr=="" || cr==null){
		alert("Please enter Present Reading");
		return false;
	}else{
		var lr1=parseInt(lr);
		var cr1=parseInt(cr);
		
		if(lr1 > cr1){
			alert("Current Reading should be grater than Last Reading");
			$("#present_reading3m").val("");
			
			return false;
		}else{
			$("#consumption3m").val(cr1-lr1);
		}
		
	}  
	

	
}

var connectionno="";

function showConnNo(conno){
	
	connectionno=conno;
}
function calculateBill2(){
	
	var prev=$('#previous_reading3m').val();
	var pr=$('#present_reading3m').val();
	var unitsConsume=$('#consumption3m').val();
	var mc=$('#mc_status3m').val();
	
	if(prev=="" || $.isNumeric(prev)==false)
	{
		prev=0;
	}
	
	if(pr=="" || $.isNumeric(pr)==false)
	{
		pr=0;
	}
	
	$.ajax({
		type : "GET",
		url : "./approveNewConnection",
		dataType : "text",
		data:{
			'connectionno':connectionno,
			'prev':prev,
			'pr':pr,
			'mc':mc,
			'unitsConsume':unitsConsume
		},
		async : false,
		cache : false,
		success : function(response) {
			
			alert(response);
			window.location.reload();
			
		}
	});
	
	
}

function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 



function newCalculate(conno){
	
	/* swal({
		  title: 'Are you sure to Approve this?',
		  text: "You won't be able to revert this!",
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes, Approve it!'
		}).then(function (result) {
		  if (result.value) {
			  $.ajax({
					type : "GET",
					url : "./approveNewConnection",
					dataType : "text",
					data:{
						'connectionno':conno,
					},
					async : false,
					cache : false,
					success : function(response) {
						
						swal({
							type: 'success',
							title: response,
								
						});
						//alert(response);
						window.location.reload();
						
					}
				});
		    
		  }
		}); */
	
	swal({
		  title: 'Are you sure to Approve this?',
		  text: "You won't be able to revert this!",
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: "rgb(35, 164, 123)",
		  cancelButtonColor: "#fb8585",
		  confirmButtonText: "Yes, Approve it!",
		  cancelButtonText: "Cancel",
		  closeOnConfirm: false,
		  closeOnCancel: true
		},
		function(isConfirm) {
		  if (isConfirm) {
			  $.ajax({
					type : "GET",
					url : "./approveNewConnection",
					dataType : "text",
					data:{
						'connectionno':conno,
					},
					async : false,
					cache : false,
					success : function(response) {
						
						swal({
							  position: 'top-end',
							  type: 'success',
							  title: response,
							  showConfirmButton: false,
							  timer: 2000
							},function(){ 
							       location.reload();
							   });
						/* swal({
							type: 'success',
							title: response,
							showConfirmButton: false,	
						});
						//alert(response);
						window.location.reload(); */
						
					}
				});
		  } 
		});
	
	
	
	
	
}

var billprimaryKeys="";

/* function selectAll(source) {
	 
	   var flagChecked = 0;
		checkboxes = document.getElementsByName('connectIdkey');
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
	
function billDetailsPopUp(billprimaryKeys,status)
{
	if(billprimaryKeys=='CHECKBOXYES')//This is for Assign Complaint CheckBox
	{
	var checkboxes = document.getElementsByName('connectIdkey');
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
	
	
	$('#conStatus').val(status);
	$('#connectId').val(billprimaryKeys);
	
	
	
	
	}
} */
	




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
    display: block;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}
.modal.fade .modal-dialog {
width: 900px;
}
.modal-body {
    position: relative;
    padding: 0px;
}

.sweet-alert button.cancel {
    background-color: #db6c6c !important;
    color: #fff;
}

</style>


