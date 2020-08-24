<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<c:if test="${not empty msg}">
	<script>		        
		            var msg = "${msg}";
		            swal({
		                title: msg,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
		        </script>
</c:if>
<script>
		$(document).ready(function(){   
			$('#meteringscreens').show();
			$('#meterManagement').addClass('active');
			
			var activeMod="${activeModulesNew}";
			var module="Metering";
			var check=activeMod.indexOf(module) > -1;
			
			if(check){
			}else{
				window.location.href="./accessDenied";
			}
			
			 $('#ir').val('');
			 $('#ins_date_eng').val('');
			 $('#metcon_date_eng').val('');
			 $('#cal_date_eng').val('');
			 $('#ent_date_eng').val('');
			
			$('#ins_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var insdtnep = $('#ins_date_nep').val();
				 		getEngDate(insdtnep,1);
				 	}
		  	});
		
			$('#metcon_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var mcdtnep = $('#metcon_date_nep').val();
				 		getEngDate(mcdtnep,2);
				 	}
		  	});
		
			$('#cal_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var caldtnep = $('#cal_date_nep').val();
				 		getEngDate(caldtnep,3);
				 	}
		  	});
		
			$('#ent_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var etdtnep = $('#ent_date_nep').val();
				 		getEngDate(etdtnep,4);
				 	}
		  	});
		
		}); 
		
		function getEngDate(nepalidate,value)
		{
			var date_nep=nepalidate;
			$.ajax({
						  type: "GET",
						  url: "./billing/onChangeNepaliDate",
						  dataType: "text",
						  async   : false,
						  data:{
							 	date_nep:date_nep,
						  		},
						  success: function(response)
						  {
							  if(value==1)
							  {
								  $('#ins_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else if(value==2)
							  {
								  $('#metcon_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else if(value==3)
							  {
								  $('#cal_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else 
							  {
								  $('#ent_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
						  }
				});
		}
		
</script>
<script type="text/javascript">		
function checkMeterDetails() 
{
	if($('#connectionno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Connection No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#ir').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Initial Reading</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	    
	/* if($('#meter_make').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Make</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	} */
	
	if($('#meter_no').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#meter_capacity').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Capacity</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#meter_own').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Meter Ownership</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#ins_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Installation Nepali Date</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#metcon_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Conn Nepali Date </span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#ent_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Entered Nepali Date </span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}

	var datavalue="";
	var connectionno=$("#connectionno").val();
	$.ajax({
		  type: "GET",
		  url: "./uniqueMeterDetails/"+connectionno,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "Meter Details Already Exist For : "+connectionno+" Connection No.",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		resetButton();
		return false;
		
	}else{
		return true;
	}
}

function convertToUpperCase(){
	
	$("#connectionno").val($("#connectionno").val().toUpperCase().trim());
	
}
function uniqueMeterNoChk(meterNo){
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueMeterNoChk/"+meterNo,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "Meter No : "+meterNo+" Already exist.",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		resetButton();
		return false;
		
	}else{
		return true;
	}
	
}


function editMeterDetails(id,connectionno,ir,meter_make,meter_no,meter_capacity,meter_own,ins_date_eng,ins_date_nep,metcon_date_eng,metcon_date_nep,cal_date_eng,cal_date_nep,ent_date_eng,ent_date_nep,calibrated_officer,remarks,status)
{
	//alert(ins_date_eng)
	$("#id").val(id);
	$("#connectionno").val(connectionno);
	$("#ir").val(ir);
	$("#meter_make").val(meter_make).trigger("change");
	$("#meter_no").val(meter_no);
	$("#meter_capacity").val(meter_capacity);
	$("#meter_own").val(meter_own);
	$("#ins_date_eng").val(moment(ins_date_eng).format('YYYY/MM/DD'));
	$("#ins_date_nep").val(ins_date_nep);
	$("#metcon_date_eng").val(moment(metcon_date_eng).format('YYYY/MM/DD'));
	$("#metcon_date_nep").val(metcon_date_nep);
	$("#cal_date_eng").val(moment(cal_date_eng).format('YYYY/MM/DD'));
	$("#cal_date_nep").val(cal_date_nep);
	$("#ent_date_eng").val(moment(ent_date_eng).format('YYYY/MM/DD'));
	$("#ent_date_nep").val(ent_date_nep);
	$("#calibrated_officer").val(calibrated_officer);
	$("#remarks").val(remarks);
	$('#status').val(status);
	
	// added by ram
	
		$.ajax({
		  type: "GET",
		  url: "./metering/connectionDetails/"+connectionno,
		  dataType: "json",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
		  	if(response!=null)
		  	{
			  for(var i=0;i<response.length;i++)
			  {
				  data=response[i];
				  $('#ledgno').val(data.ledgerno);
				  $('#folio').val(data.folio);
				  $('#area_no').val(data.area_no);
				  $('#customer_name').val(data.name_eng);
			  }
		  	}
		  }
		});
	
	// for name and area no
	$("#addId").hide();
	$("#editId").show();
}

function modifyButton()
{
	if($('#connectionno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Connection No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#ir').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Initial Reading</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	    
	/* if($('#meter_make').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Make</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	} */
	
	if($('#meter_no').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#meter_capacity').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Capacity</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#meter_own').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please Select Meter Ownership</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#ins_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Installation Nepali Date</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#metcon_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Conn Nepali Date </span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	if($('#ent_date_nep').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Entered Nepali Date </span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	var id=$("#id").val();
	var connectionno=$("#connectionno").val();
	
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueMeterDetailsForEdit/"+connectionno+"/"+id,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		
		$("#meterDetailsEntity").attr("action", "./editMeterDetails").submit();
	}
	else{
		swal({
        title: "Connection No Not Exist",
        text: "",
        confirmButtonColor: "#2196F3",
    });
	return false; 
	}
	
}


function resetButton()
{
	$('#meterDetailsEntity')[0].reset();
	$('#ir').val('');
	$("#addId").show();
	$("#editId").hide();
}

function connectionDetails()
{
	if($('#connectionno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Connection No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	connectionno=$("#connectionno").val();
	//alert(connectionno);
	$.ajax({
		  type: "GET",
		  url: "./metering/connectionDetails/"+connectionno,
		  dataType: "json",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
		  	if(response!=null)
		  	{
			  for(var i=0;i<response.length;i++)
			  {
				  data=response[i];
				  $('#ledgno').val(data.ledgerno);
				  $('#folio').val(data.folio);
				  $('#area_no').val(data.area_no);
				  $('#customer_name').val(data.name_eng);
			  }
		  	}
		  	if(response=='')
		  	{
		  		swal({
		            title: "Connection No : "+connectionno+" Does't Exist",
		            text: "",
		            confirmButtonColor: "#2196F3",
		        });
		  		$("#connectionno").val("");
		  		$('#area_no').val("");
				  $('#customer_name').val("");
				return false;
		  	}
		  		
	  	}
	});
}

</script>

<style>
.form-horizontal .form-group {
	margin-left: 0px !important;
	margin-right: 0px !important;
}

select {
	width: 100%;
	border: 1px solid #DDD;
	border-radius: 3px;
	height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
}

.datatable-header, .datatable-footer {
	padding: 8px 0px 0 0px;
}
</style>

<div class="panel panel-flat">
	<div class="panel-body">
		<div class="row" hidden="true" id="alertDiv"></div>
		<!-- <form class="form-horizontal" action="#">-->
		<form:form action="./addMeterDetails" role="form"
			modelAttribute="meterDetailsEntity" commandName="meterDetailsEntity"
			method="POST" id="meterDetailsEntity">
			<fieldset class="content-group">
				<legend class="text-bold"
					style="text-align: center; font-size: 18px;">Meter Details</legend>

				<div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<label>Connection No&nbsp;<font color="red">*</font></label>
							<form:input path="connectionno" id="connectionno"
								name="connectionno" type="text" class="form-control"
								placeholder="Connection No..."
								onchange="connectionDetails(this.value)"
								onkeyup="convertToUpperCase()" />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Name&nbsp;</label> <input id="customer_name"
								name="customer_name" type="text" class="form-control"
								placeholder="Customer Name" readonly="readonly" />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Area No&nbsp;</label> <input id="area_no" name="area_no"
								type="text" class="form-control" placeholder="Area No"
								readonly="readonly" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Initial Reading&nbsp;<font color="red">*</font></label>
							<form:input path="ir" id="ir" name="ir" type="text"
								class="form-control" placeholder="Initial Reading" />
						</div>
					</div>

					<%-- <div class="col-md-4">
												<div class="form-group">
													<label>Meter Make&nbsp;</label>
					                                <!-- <input id="mtrMake" name="mtrMake" type="text" class="form-control" placeholder="Meter Make" > -->
					                                <form:select path="meter_make" id="meter_make" name="meter_make" class="selectbox input-group">
										   				 <form:option  value="" data-icon="icon-git-branch">Select Meter Make</form:option>
										   				 <form:option  value="L&T" data-icon="icon-git-branch">L&T</form:option>
										   				 <form:option  value="Visiontek" data-icon="icon-git-branch">Visiontek</form:option>
													</form:select>
				                                </div>
											</div> --%>

				</div>

				<div class="row">

					<div class="col-md-3">
						<div class="form-group">
							<label>Meter Make&nbsp;</label>
							<!-- <input id="mtrMake" name="mtrMake" type="text" class="form-control" placeholder="Meter Make" > -->
							<form:select path="meter_make" id="meter_make" name="meter_make"
								class="selectbox input-group">
								<form:option value="" data-icon="icon-git-branch">Select Meter Make</form:option>
								<form:option value="L&T" data-icon="icon-git-branch">L&T</form:option>
								<form:option value="Visiontek" data-icon="icon-git-branch">Visiontek</form:option>
								<form:option value="KUKL" data-icon="icon-git-branch">KUKL</form:option>
							</form:select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Meter No&nbsp;<font color="red">*</font></label>
							<form:input path="meter_no" id="meter_no" name="meter_no"
								type="text" class="form-control" placeholder="Meter No"
								onchange="uniqueMeterNoChk(this.value)" />
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Meter Capacity&nbsp;<font color="red">*</font></label>
							<form:input path="meter_capacity" id="meter_capacity"
								name="meter_capacity" type="text" class="form-control"
								placeholder="Meter Capacity" />
						</div>
					</div>

					<div class="form-group col-md-3">
						<label>Meter Ownership&nbsp;<font color="red">*</font></label>
						<form:select path="meter_own" id="meter_own" name="meter_own"
							class="selectbox input-group">
							<form:option value="" data-icon="icon-git-branch">Select Meter Ownership</form:option>
							<form:option value="YES" data-icon="icon-git-branch">YES</form:option>
							<form:option value="NO" data-icon="icon-git-branch">NO</form:option>
						</form:select>
					</div>
				</div>


				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Installation Date in Nepali&nbsp;<font color="red">*</font></label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="ins_date_nep" type="text" id="ins_date_nep"
									name="ins_date_nep" class="form-control nepali-calendar"
									placeholder="Installation Date in Nepali" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Meter Conn Date in Nepali&nbsp;<font color="red">*</font></label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="metcon_date_nep" type="text"
									id="metcon_date_nep" name="metcon_date_nep"
									class="form-control nepali-calendar"
									placeholder="Meter Conn Date in Nepali" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Calibrated Date in Nepali</label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="cal_date_nep" type="text" id="cal_date_nep"
									name="cal_date_nep" class="form-control nepali-calendar"
									placeholder="Calibrated Date in Nepali" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Installation Date in English</label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="ins_date_eng" id="ins_date_eng"
									name="ins_date_eng" type="text" class="form-control"
									placeholder="Installation Date in English" readonly="true" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Meter Conn Date in English</label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="metcon_date_eng" id="metcon_date_eng"
									name="metcon_date_eng" type="text" class="form-control"
									placeholder="Meter Conn Date in English" readonly="true" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Calibrated Date in English</label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="cal_date_eng" id="cal_date_eng"
									name="cal_date_eng" type="text" class="form-control"
									placeholder="Calibrated Date in English" readonly="true" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Entered Date in Nepali&nbsp;<font color="red">*</font></label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="ent_date_nep" type="text" id="ent_date_nep"
									name="ent_date_nep" class="form-control nepali-calendar"
									placeholder="Entered Date in Nepali" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Calibrated Officer</label>
							<form:input path="calibrated_officer" id="calibrated_officer"
								name="calibrated_officer" type="text" class="form-control"
								placeholder="Calibrated Officer" />
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Entered Date in English</label>
							<div class="input-group">
								<span class="input-group-addon"><i
									class="icon-calendar22"></i></span>
								<form:input path="ent_date_eng" id="ent_date_eng"
									name="ent_date_eng" type="text" class="form-control"
									placeholder="Entered Date in English" readonly="true" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" hidden="true">
						<div class="form-group">
							<label>Status</label>
							<form:input path="status" id="status" value="0" name="status"
								type="number" class="form-control" />
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Remarks/Reason</label>
							<form:textarea path="remarks" id="remarks" name="remarks"
								placeholder="Enter your Remarks here" class="form-control"
								cols="2" rows="2"></form:textarea>
						</div>
					</div>

					<div class="col-md-4" id="addId">
						<div class="form-group">
							<label>&nbsp;</label>
							<div class="input-group">
								<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="submit" class="btn bg-blue"
									data-style="expand-right" data-spinner-size="20"
									onclick="return checkMeterDetails();">
									<span class="ladda-label">Submit</span>
								</button>
							</div>
						</div>
					</div>

					<div class="col-md-4" id="editId" hidden="true">
						<div class="form-group">
							<label>&nbsp;</label>
							<div class="input-group">
								<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn bg-blue"
									data-style="expand-right" data-spinner-size="20"
									onclick="modifyButton();">
									<span class="ladda-label">Modify</span>
								</button>
								&nbsp;&nbsp;
								<button type="button" class="btn bg-blue"
									data-style="expand-right" data-spinner-size="20"
									onclick="resetButton();">
									<span class="ladda-label">Reset</span>
								</button>
							</div>
						</div>
					</div>

					<div hidden="true">
						<form:input path="id" id="id" name="id" type="text"
							class="form-control" placeholder="Meter ID" />
					</div>

				</div>

				<!-- <div class="text-center" id="addId">
						<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return checkMeterDetails();"><span class="ladda-label">Submit</span></button>
					</div>
				
					<div class="text-center" id="editId" hidden="true">
						<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="modifyButton();"><span class="ladda-label">Modify</span></button>
					</div> -->
				<br>

			</fieldset>
		</form:form>

	</div>
</div>

<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">
		<fieldset class="content-group">
			<legend class="text-bold">Meter Details</legend>
			<div class="table-responsive">
				<div class="col-md-12">
					<div class="panel-body" style="margin-top: -3px; padding: 6px;">
						<table class="table datatable-basic table-bordered" id="tableForm">
							<thead>

								<tr class="bg-blue">
									<th>CONNECTIONNO</th>
									<th>METER_NO</th>
									<th>METER_MAKE</th>
									<th>IR</th>
									<th>METER_CAPACITY</th>
									<!-- <th width="8%">METER_OWN</th> -->
									<th>INS_DATE_ENG</th>
									<!-- <th width="8%">INS_DATE_NEP</th> -->
									<th>METCON_DATE_ENG</th>
									<!--<th width="8%">METCON_DATE_NEP</th>
													<th width="5%">CAL_DATE_ENG</th>
													<th width="5%">CAL_DATE_NEP</th> 
													<th width="8%">ENT_DATE_ENG</th>
													<th width="5%">ENT_DATE_NEP</th>
													<th width="5%">CALIBRATED_OFFICER</th> -->
									<th>Edit</th>
									<!-- <th></th> -->
								</tr>

							</thead>
							<tbody>
								<c:forEach var="data" items="${mtrDetailsList}">
									<tr>
										<td>${data.connectionno}</td>
										<td>${data.meter_no}</td>
										<td>${data.meter_make}</td>
										<td>${data.ir}</td>
										<td>${data.meter_capacity}</td>
										<td><fmt:formatDate value="${data.ins_date_eng}"
												pattern="yyyy/MM/dd" /></td>
										<td><fmt:formatDate value="${data.metcon_date_eng}"
												pattern="yyyy/MM/dd" /></td>

										<td class="text-center"><a href="#"
											onclick="editMeterDetails('${data.id}','${data.connectionno}',
													'${data.ir}','${data.meter_make}','${data.meter_no}','${data.meter_capacity}','${data.meter_own}',
													'${data.ins_date_eng}','${data.ins_date_nep}','${data.metcon_date_eng}','${data.metcon_date_nep}',
													'${data.cal_date_eng}','${data.cal_date_nep}','${data.ent_date_eng}','${data.ent_date_nep}',
													'${data.calibrated_officer}','${data.remarks}','${data.status}')">[Edit]</a></td>

										<%-- <c:choose>
											<c:when test="${data.status=='0'}">
												<td><button type="button" class="btn bg-teal btn-ladda"
														data-style="expand-right" data-spinner-size="20"
														onclick="statusChange('Inactive','${data.id}');">
														<span class="ladda-label">Activate</span>
													</button></td>
											</c:when>
											<c:otherwise>
												<td><button type="button"
														class="btn bg-orange btn-ladda" data-style="expand-right"
														data-spinner-size="20"
														onclick="statusChange('Active','${data.id}');">
														<span class="ladda-label">DeActivate</span>
													</button></td>
											</c:otherwise>
										</c:choose> --%>

									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
				</div>
			</div>


		</fieldset>
	</div>

</div>




