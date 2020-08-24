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
			
			var activeMod="${activeModulesNew}";
			var module="Master Modification";
			var check=activeMod.indexOf(module) > -1;
			
			if(check){
			}else{
				window.location.href="./accessDenied";
			}
			
			$('#ir').val('');
			$('#mcunits').val('');
			$('#fr').val('');
			
			$('#new_metcon_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var nmcdtnep = $('#new_metcon_date_nep').val();
				 		getEngDate(nmcdtnep,1);
				 	}
		  	});
			
			$('#new_ins_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var ninsdtnep = $('#new_ins_date_nep').val();
				 		getEngDate(ninsdtnep,2);
				 	}
					
		  	});
			
			$('#new_cal_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var ncldtnep = $('#new_cal_date_nep').val();
				 		getEngDate(ncldtnep,3);
				 	}
		  	});
			
			$('#release_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var rldtnep = $('#release_date_nep').val();
				 		getEngDate(rldtnep,4);
				 	}
		  	});
			
			$('#given_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var gvdtnep = $('#given_date_nep').val();
				 		getEngDate(gvdtnep,5);
				 	}
		  	});
			
			$('#new_ent_date_nep').nepaliDatePicker({
				 dateFormat: "%D, %M %d, %y",
					npdMonth: true,
					npdYear: true,
					onChange: function(){
				 		var netdtnep = $('#new_ent_date_nep').val();
				 		getEngDate(netdtnep,6);
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
								  $('#new_metcon_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else if(value==2)
							  {
								  $('#new_ins_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else if(value==3)
							  {
								  $('#new_cal_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else if(value==4)
							  {
								  $('#release_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else if(value==5)
							  {
								  $('#given_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
							  else 
							  {
								  $('#new_ent_date_eng').val(moment(response).format('YYYY/MM/DD'));
							  }
						  }
				});
		}
</script>

<script type="text/javascript">	
var connectionno=null;
var presentReading="";
var previousReading="";

function checkMeterConnNo() 
{
	connectionno=$("#connectionno").val();
	if($('#connectionno').val().trim()=='')
	{
		swal({
            title: "Please Enter Connection No",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	
	var datavalue="";
	
	//alert(connectionno);
	$.ajax({
		  type: "GET",
		  url: "./checkConnMeterDetails/"+connectionno,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="Exist")
			  {
				  $.ajax({
					  type: "GET",
					  url: "./viewConnMeterDetails/"+connectionno,
					  dataType: "json",
					  cache       : false,
					  async       : false,
					  success: function(response)
					  {
						  swal({
					            title: "Connection No. "+connectionno+"  Details Exist, You can Enter Meter Change details...",
					            text: "",
					            confirmButtonColor: "#2196F3",
					        });
						  
						  var consumer=response[0];
						  var ledVal=response[1];
						  $.each(consumer, function(index, data)
						  {
							 $('#oldmtrconndate_Nep').val(data.metcon_date_nep);
							 $("#oldmtrconndate_Eng").val(moment(data.metcon_date_eng).format('YYYY/MM/DD'));
							 $('#oldmtrNo').val(data.meter_no);
							 $('#oldmtrCapacity').val(data.meter_capacity);
							 /* $("#oldmtrMake").hide();
							 $("#oldmtrOwnership").hide(); */
							 $("#oldmtrMake").val(data.meter_make);
							 $("#oldmtrOwnership").val(data.meter_own);
							 /* var html2 = '<select path="" id="oldmtrMake" name="oldmtrMake" class="select-style">';
			    	         var idName=data.meter_make;
					    		     html2 += "<option value="+idName+">"+idName+"</option>";
					    		
					    	html2+='</select>';		    	
					    	$('#placeId').html(html2);
					    	
					    	var html2 = '<select path="" id="oldmtrOwnership" name="oldmtrOwnership" class="select-style">';
			    	        var idName=data.meter_own;
					    		     html2 += "<option value="+idName+">"+idName+"</option>";
					    		
					    	html2+='</select>';		    	
					    	$('#placeId2').html(html2); */
							 //$("#oldmtrMake").val(data.meter_make).trigger("change");;
							 //$('#oldmtrOwnership').val(data.meter_own);
							 $('#finalReading').val(data.ir);
							 $('#oldinstndate_Nep').val(data.ins_date_nep);
							 $('#oldinstndate_Eng').val(moment(data.ins_date_eng).format('YYYY/MM/DD'));
							 $('#oldcaldate_Nep').val(data.cal_date_nep);
							 $('#oldcaldate_Eng').val(moment(data.cal_date_eng).format('YYYY/MM/DD'));
							 $('#oldcalOfficer').val(data.calibrated_officer);
						 });
						 
						  $.each(ledVal, function(index, data)
							{
							  presentReading=data.presentReading;
							  previousReading=data.prevReading;
							  //alert(presentReading+"====="+previousReading);
							});
						  
					  }
					}); 
			  }
			  else
			  {
			  	datavalue="Doesnot Exist";
			  }
		  }
		});
	if(datavalue=='Doesnot Exist')
	{
		 $('#oldmtrconndate_Nep').val('');
		 $("#oldmtrconndate_Eng").val('');
		 $('#oldmtrNo').val('');
		 $('#oldmtrCapacity').val('');
		 $("#oldmtrMake").val('');
		 $("#oldmtrOwnership").val('');
		 $('#finalReading').val('');
		 $('#oldinstndate_Nep').val('');
		 $('#oldinstndate_Eng').val('');
		 $('#oldcaldate_Nep').val('');
		 $('#oldcaldate_Eng').val('');
		 $('#oldcalOfficer').val('');
		 
		swal({
            title: "Meter Details Does't Exist for Connection No: "+connectionno+" ",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}else{
		return true;
	}
}
</script>

<style>
.form-horizontal .form-group{
	margin-left: 0px !important;
	margin-right: 0px !important;
	
}

.col-lg-2 {
    width: 18.6667%;
}

.col-lg-4 {
    width: 30.6667%;
}

.col-lg-9 {
    width: 80%;
}

.col-lg-7 {
    width: 55%;
}

.form-control{
	width: 170%
	
}

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
	margin-bottom: 13px;
	margin-top: 13px;
    padding-bottom: 1px;
    padding-top: 1px;
}

.select-style {
    
    width: 225px;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}
</style>		

				<div class="panel panel-flat">
						<div class="panel-body">
							
							<form:form action="./addNewMtrChangeDetails" role="form" modelAttribute="meterChangeApproveEntity" commandName="meterChangeApproveEntity" method="POST" id="meterChangeApproveEntity">
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Meter Change</legend><br>
										
								<div class="form-group">
									<label class="control-label col-lg-2">Connection No&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="connectionno" id="connectionno" name="connectionno" type="text" class="form-control" placeholder="Connection No" />
											</div>

										</div>
									</div>
									
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return checkMeterConnNo();"><span class="ladda-label">View</span></button>
											</div>

										</div>
									</div>
								</div><br><br>
									
					<legend class="text-bold"></legend>
								
					<legend class="text-bold" style="text-align: center;">Old Meter Details 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					New Meter Details</legend>
					
									     <br>
						 <div class="form-group">
									<label class="control-label col-lg-2">Meter Conn Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-7">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" readonly="true" style="width: 170%" type="text" id="oldmtrconndate_Nep" name="oldmtrconndate_Nep" class="form-control " placeholder="Old Meter Conn Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Meter Conn Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_metcon_date_nep"  type="text" id="new_metcon_date_nep" name="new_metcon_date_nep" class="form-control nepali-calendar" placeholder="New Meter Conn Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
				
							<div class="form-group">
									<label class="control-label col-lg-2">Meter Conn Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" id="oldmtrconndate_Eng" readonly="true" name="oldmtrconndate_Eng" type="text" class="form-control" placeholder="Old Meter Conn Date in English"/>
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Meter Conn Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_metcon_date_eng" id="new_metcon_date_eng" name="new_metcon_date_eng" type="text" class="form-control" placeholder="New Meter Conn Date in English" readonly="true"/>
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
								
								<div class="form-group">
									<label class="control-label col-lg-2">Meter No&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="oldmtrNo" readonly="true" name="oldmtrNo" type="text" class="form-control" placeholder="Meter No" />
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Meter No&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="new_meter_no" id="new_meter_no" name="new_meter_no" type="text" class="form-control" placeholder="Meter No" />
											</div>

										</div>
									</div>
									
								</div><br><br>
								
								<div class="form-group">
									<label class="control-label col-lg-2">Meter Capacity&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="" id="oldmtrCapacity" readonly="true" name="oldmtrCapacity" type="text" class="form-control" placeholder="Meter Capacity" />
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Meter Capacity&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="new_meter_capacity" id="new_meter_capacity" name="new_meter_capacity" type="text" class="form-control" placeholder="Meter Capacity" />
											</div>

										</div>
									</div>
									
								</div><br><br>
								
							<div class="form-group">
									<label class="control-label col-lg-2">Meter Make&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6" id="placeId">
												<input id="oldmtrMake" name="oldmtrMake" type="text" class="form-control" placeholder="Meter Make" readonly="readonly"/>
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Meter Make&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:select path="new_meter_make" id="new_meter_make" name="new_meter_make" class="select-style">
										   				 <form:option  value="" data-icon="icon-git-branch">Select Meter Make</form:option>
										   				 <form:option  value="L&T" data-icon="icon-git-branch">L&T</form:option>
										   				 <form:option  value="Visiontek" data-icon="icon-git-branch">Visiontek</form:option>
												</form:select>
											</div>

										</div>
									</div>
									
								</div><br><br>
					
								<div class="form-group">
									<label class="control-label col-lg-2">Meter Ownership&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6" id="placeId2">
											<input  id="oldmtrOwnership" name="oldmtrOwnership" type="text" class="form-control" placeholder="Meter Ownership" readonly="readonly"/>
											
												<%-- <form:select path="" id="oldmtrOwnership" readonly="true" name="oldmtrOwnership" class="select-style">
							   					 <form:option value="" data-icon="icon-git-branch">Select Meter Ownership</form:option>
												</form:select> --%>
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Meter Ownership&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:select path="new_meter_own" id="new_meter_own" name="new_meter_own" class="select-style">
							   			 			<form:option  value="" data-icon="icon-git-branch">Select Meter Ownership</form:option>
										   			 <form:option  value="YES" data-icon="icon-git-branch">YES</form:option>
										   			 <form:option  value="NO" data-icon="icon-git-branch">NO</form:option>
												</form:select>
											</div>

										</div>
									</div>
								</div><br><br>
								
								
					
						<div class="form-group">
									<label class="control-label col-lg-2">Final Reading&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="fr" id="fr" name="fr" type="text" class="form-control" placeholder="Final Reading" onchange="getMcUnit()"/>
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Initial Reading&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="ir" id="ir" name="ir" type="text" class="form-control" placeholder="Initial Reading" />
											</div>

										</div>
									</div>
								</div><br><br>
					
						<div class="form-group">
									<label class="control-label col-lg-2">Old Installation Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" type="text"  readonly="true" id="oldinstndate_Nep" name="oldinstndate_Nep" class="form-control" placeholder="Old Installation Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">New Installation Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_ins_date_nep" type="text" id="new_ins_date_nep" name="new_ins_date_nep" class="form-control nepali-calendar" placeholder="New Installation Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
					
							<div class="form-group">
									<label class="control-label col-lg-2">Old Installation Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" id="oldinstndate_Eng" readonly="true" name="oldinstndate_Eng" type="text" class="form-control" placeholder="Old Installation Date in English"/>
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">New Installation Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_ins_date_eng" id="new_ins_date_eng" name="new_ins_date_eng" type="text" class="form-control" placeholder="New Installation Date in English" readonly="true"/>
												</div>
													
											</div>

										</div>
									</div>
									
								</div><br><br>
					
							<div class="form-group">
									<label class="control-label col-lg-2">Old Calibrated Date in Nepali</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" type="text" readonly="true" id="oldcaldate_Nep" name="oldcaldate_Nep" class="form-control" placeholder="Old Calibrated Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">New Calibrated Date in Nepali</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_cal_date_nep" type="text" id="new_cal_date_nep" name="new_cal_date_nep" class="form-control nepali-calendar" placeholder="New Calibrated Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
					
							<div class="form-group">
									<label class="control-label col-lg-2">Old Calibrated Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="" id="oldcaldate_Eng" readonly="true" name="oldcaldate_Eng" type="text" class="form-control" placeholder="Old Calibrated Date in English"/>
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">New Calibrated Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
													<form:input path="new_cal_date_eng" id="new_cal_date_eng" name="new_cal_date_eng" type="text" class="form-control" placeholder="New Calibrated Date in English" readonly="true"/>
												</div>
													
											</div>

										</div>
									</div>
									
								</div><br><br>
					
							<div class="form-group">
									<label class="control-label col-lg-2">Calibrated Officer</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												 <form:input path="" id="oldcalOfficer" readonly="true" name="oldcalOfficer" type="text" class="form-control" placeholder="Calibrated Officer" />
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Calibrated Officer</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												 <form:input path="new_calibrated_officer" id="new_calibrated_officer" name="new_calibrated_officer" type="text" class="form-control" placeholder="Calibrated Officer" />
											</div>

										</div>
									</div>
									
								</div><br><br>
					
								<div class="form-group">
									<label class="control-label col-lg-2">Release Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="release_date_nep" type="text" id="release_date_nep" name="release_date_nep" class="form-control nepali-calendar" placeholder="Release Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Given Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="given_date_nep" type="text" id="given_date_nep" name="given_date_nep" class="form-control nepali-calendar" placeholder="Given Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
					
								<div class="form-group">
									<label class="control-label col-lg-2">Release Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="release_date_eng" id="release_date_eng" name="release_date_eng" type="text" class="form-control" placeholder="Release Date in English" readonly="true"/>
												</div>
													
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Given Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="given_date_eng" id="given_date_eng" name="given_date_eng" type="text" class="form-control" placeholder="Given Date in English" readonly="true"/>
												</div>
													
											</div>

										</div>
									</div>
									
								</div><br><br>
					
							<div class="form-group">
									<label class="control-label col-lg-2">MC Units&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:input path="mcunits" id="mcunits" name="mcunits" type="text" class="form-control" placeholder="MC Units" readonly="true"/>
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Entered Date in Nepali&nbsp;<font color="red">*</font></label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_ent_date_nep" type="text" id="new_ent_date_nep" name="new_ent_date_nep" class="form-control nepali-calendar" placeholder="Entered Date in Nepali" />
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
					
							<div class="form-group">
									<label class="control-label col-lg-2">Remarks/Reason</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-6">
												<form:textarea path="remarks" id="remarks" name="remarks" placeholder="Enter your Remarks here" class="form-control" cols="2" rows="1"></form:textarea>
											</div>

										</div>
									</div>
									
									<label class="control-label col-lg-2">Entered Date in English</label>
									<div class="col-lg-4">
										<div class="row">
											<div class="col-lg-9">
												<div class="input-group">
													<span class="input-group-addon"><i class="icon-calendar22"></i></span>
														<form:input path="new_ent_date_eng" id="new_ent_date_eng" name="new_ent_date_eng" type="text" class="form-control" placeholder="Entered Date in English" readonly="true"/>
												</div>
													
											</div>

										</div>
									</div>
								</div><br><br>
					
								<div hidden="true">
									<form:input path="id" id="id" name="id" type="text" class="form-control" placeholder="Meter ID" />
								</div>
							
					<div class="text-center" id="addId">
						<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return validateFields(0)"><span class="ladda-label">Submit</span></button>
					</div>
				
					<div class="text-center" id="editId" hidden="true">
						<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return validateFields(1)"><span class="ladda-label">Modify</span></button>
					</div>
					</fieldset>	
						
				</form:form>
				</div>			
			</div>		

			
			
			
<script>

function getMcUnit(){
	//alert(presentReading+"====="+previousReading);
	var fr=$('#fr').val();
	if(fr==null || fr==""){
		$('#mcunits').val(0);
	} else{
		var mcUnit=parseInt(fr)-parseInt(previousReading);
		$('#mcunits').val(mcUnit);
	}
}

function validateFields(st){
	
	var fr=$('#fr').val();
	var release_date_nep=$('#release_date_nep').val();
	var new_metcon_date_nep=$('#new_metcon_date_nep').val();
	var new_meter_no=$('#new_meter_no').val();
	var new_meter_capacity=$('#new_meter_capacity').val();
	var new_meter_own=$('#new_meter_own').val();
	var ir=$('#ir').val();
	var new_ins_date_nep=$('#new_ins_date_nep').val();
	var remarks=$('#remarks').val();
	
	if(fr == null||fr == ""){
		alert("Select Final Reading of Old Meter");
		return false;
	} 
	else if(release_date_nep == null||release_date_nep == ""){
		alert("Please Select Release Date");
		return false;
	}
	else if(new_metcon_date_nep == null||new_metcon_date_nep == ""){
		alert("Please Select New Meter Connection Date");
		return false;
	}
	else if(new_meter_no == null||new_meter_no == ""){
		alert("Please Select New Meter No");
		return false;
	}
	else if(new_meter_capacity == null||new_meter_capacity == ""){
		alert("Please Select New Meter capacity");
		return false;
	}
	else if(new_meter_own == null||new_meter_own == ""){
		alert("Please Select Meter Ownership");
		return false;
	}
	else if(ir == null||ir == ""){
		alert("Please Enter New Meter Initiaql Reading");
		return false;
	}
	else if(new_ins_date_nep == null||new_ins_date_nep == ""){
		alert("Please Select New Meter Installation Date");
		return false;
	}
	else if(remarks == null||remarks == ""){
		alert("Please Enter Remarks");
		return false;
	}
	
	
	if(st=='0'){
		$("#meterChangeApproveEntity").attr("action", "./addNewMtrChangeDetails").submit();
	} else if(st=='1'){
		
	} else{
		return false;
	}
	
	
	
}

</script>