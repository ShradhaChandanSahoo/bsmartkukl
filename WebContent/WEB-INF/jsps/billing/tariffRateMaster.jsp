<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript">

$(document).ready(function(){   
	$('#umconfig').show();
	$('#configModule').addClass('active');
	
	 $('#effectingDateN').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
  });
	
	/*  $("#id").val("0"); */
	/*  $("#branchCode1").val(""); */
	 $("#diameter_tap").val("");
	 $("#min_consumption").val("");
	 $("#meter_type").val("");
	 $("#minimum_charages").val("");
	 $("#rate_per_1000_ltr").val("");
	 $("#sw_charge_percent").val("");
	  $("#monthly_charges").val("");
	/*  $("#min_consumption").val("");
	  */ 
	 
	 var msg = '${msg}';
	 if(msg!="")
	 {
		 swal({
             title: msg,
             text: "",
             confirmButtonColor: "#2196F3",
         }); 
	 }
	 
	    var activeMod="${activeModulesNew}";
		var module="Administration";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		}
		
		
});

function editOption(val){
	
    var desig="${userbdesignation}";
	
	if(desig=="General Manager"){
			document.getElementById('updateOption').style.display='block';
			document.getElementById('addOption').style.display='none';
			$.ajax({
				type : "GET",
				url : "./getTariffDetailsForEditing/"+val,
				dataType : "json",
				async : false,
				cache : false,
				success : function(response)
				{
					document.getElementById("diameter_tap").value = response.diameter_tap;
					document.getElementById("min_consumption").value = response.min_consumption;
					//document.getElementById("meter_type").value = response.meter_type;
					document.getElementById("minimum_charages").value=response.minimum_charages;
					document.getElementById("rate_per_1000_ltr").value = response.rate_per_1000_ltr;
					document.getElementById("sw_charge_percent").value = response.sw_charge_percent;
					document.getElementById("monthly_charges").value=response.monthly_charges;
					$('#meter_type').val(response.meter_type).trigger("change");
					
				}
			});
			 $('#'+id).attr("data-toggle", "modal");
			 $('#'+id).attr("data-target","#stack1");
	 
	}else{
		alert("Sorry You dont have Permission for Adjustment Approval");
		return false;
	}
}


function finalSubmit(param) {
	

	if(document.getElementById("diameter_tap").value == "" || document.getElementById("diameter_tap").value == null)
	{
	
		 swal({
             title: "Please Enter Diameter Tap",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	if(document.getElementById("min_consumption").value == "" || document.getElementById("min_consumption").value == null)
	{
	
		 swal({
             title: "Please Enter Minimum Consumption",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	if(document.getElementById("meter_type").value == "" || document.getElementById("meter_type").value == null)
	{
	
		 swal({
             title: "Please Enter Meter Type",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	
	if(document.getElementById("minimum_charages").value == "" || document.getElementById("minimum_charages").value == null)
	{
	
		 swal({
             title: "Please Enter Minimum Charges",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	if(document.getElementById("rate_per_1000_ltr").value == "" || document.getElementById("rate_per_1000_ltr").value == null)
	{
	
		 swal({
             title: "Please Enter Rate Per 1000 Litres",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	if(document.getElementById("sw_charge_percent").value == "" || document.getElementById("sw_charge_percent").value == null)
	{
	
		 swal({
             title: "Please Enter Sewage Charge Percentage",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	
	if(document.getElementById("monthly_charges").value == "" || document.getElementById("monthly_charges").value == null)
	{
	
		 swal({
             title: "Please Enter Monthly Charges",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}

	
	if(param =='0')
	{
		$("#tariffRateMasterId").attr("action", "./addTariffMasterDetails").submit();
		
	}
	else if(param == '1')
	{
		$("#tariffRateMasterId").attr("action", "./updateTariffRateDetails").submit();
	} 
	else
		{
		return false;
		}
	
}

</script>

<div class="panel panel-flat">
	<div class="panel-body">
			<div class="panel-flat">
					<fieldset class="content-group">
						<form:form action="#" modelAttribute="tariffRateMaster" commandName="tariffRateMaster" method="POST"
							 id="tariffRateMasterId" role="form" >
								<fieldset class="content-group"> 
									 <legend class="text-bold">Tariff Master Details</legend>
										<div class="row"> 
											
											<div hidden="true" class="col-md-3">
												<div class="form-group">
													<label>ID</label>
					                                <form:input path="id" id="id" type="text" class="form-control" ></form:input>
					                             </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Diameter Tap</label>
					                                <form:input path="diameter_tap" id="diameter_tap" name="diameter_tap" type="text" class="form-control" placeholder="Diameter Tap..." ></form:input>
					                             </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Minimum Consumption</label>
					                                <form:input path="min_consumption" id="min_consumption"  name="min_consumption" type="text"  class="form-control" placeholder="Minimum Consumption..." ></form:input>
					                             </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Meter Type</label>
													
														<form:select class="select" id="meter_type" name="meter_type" path="meter_type" data-placeholder="Select Meter Type">
																	 <form:option value="">Select Meter Type</form:option>
																 <form:option value="METERED">METERED</form:option>
																  <form:option value="UNMETERED">UNMETERED</form:option>
														</form:select>
												
												</div>
											</div>
									
									</div>
									
									<div class="row"> 
									
											<div class="col-md-3">
												<div class="form-group" >
													<label>Minimum Charges</label>
					                                <form:input path ="minimum_charages" id="minimum_charages" name="minimum_charages" type="text" class="form-control" placeholder="Minimum Charges..." ></form:input>
												</div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group" >
													<label>Rate Per 1000 Ltr</label>
					                                <form:input path ="rate_per_1000_ltr" id="rate_per_1000_ltr" name="rate_per_1000_ltr" type="text" class="form-control" placeholder="Rate Per 1000 ltr..." ></form:input>
												</div>
											</div>
											
											
											<div class="col-md-3">
												<div class="form-group" >
													<label>Monthly Charges</label>
					                                <form:input path ="monthly_charges" id="monthly_charges" name="monthly_charges" type="text" class="form-control" placeholder="Monthly Charges..." ></form:input>
												</div>
											</div>
											
											
											<div class="col-md-3">
												<div class="form-group" >
													<label>Sewage Charge Percentage</label>
					                                <form:input path ="sw_charge_percent" id="sw_charge_percent" name="sw_charge_percent" type="text" class="form-control" placeholder="Sewage Charge Percentage..." ></form:input>
												</div>
											</div>
										</div>
										
										<div class="row"> 	
								
										 <div class="text-center">
									<!-- <button type="submit" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20"> <span class="ladda-label">Add Tariff Master</span></button>    --> 

									<button type="submit" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20" onclick="return finalSubmit(0);" id="addOption">Add Tariff Master<i class="icon-arrow-right14 position-centre"></i></button>
											<button type="submit" class="btn bg-teal btn-ladda" data-style="expand-right"  data-spinner-size="20" onclick="return finalSubmit(1);" id="updateOption" style="display: none;"><i class="glyphicon glyphicon-user"></i> &nbsp;&nbsp;Update Tariff Rates<i class="icon-arrow-right14 position-right"></i></button> 
										<!-- 	<button type="submit" class="btn btn-primary" onclick="finalSubmit(2)" id="deleteOption" style="display: none;"> Delete <i class="icon-arrow-right14 position-right"></i></button>  --> 
										</div>
										
										</div>
								</fieldset>
							</form:form> 
				
						
								<table class="table datatable-basic table-bordered">
									<thead>
									<tr>
									<th>Diameter Tap</th>
									<th>Minimum Consumption</th>
									<th>Meter Type</th>
									<th>Minimum Charges</th>
									<th>Rate Per 1000 Ltr</th>
									<th>Monthly Charges</th>
									<th>Sewage Charge Percentage</th>
									<th>Edit</th>
									</tr>
							<!-- 			<tr>
											<th>Pipe&nbsp;Line<br>(in inch)
											</th>
											<th style="min-width: 180px;">Minimum Consumption<br>(in
												liter)
											</th>
											<th colspan="2">Taps with meter installed.
											<th>Taps without meter installed. (Rs.)</th>

										</tr>
										<tr>
											<th colspan="2"></th>
											<th>(Rs.)Minimum&nbsp;Maintain&nbsp;Price</th>
											<th>Rs.Increment in Consumption according to volume
												(1000 per ltr)</th>
												
											<th></th>
										</tr>
										<tr><th>Sewage Charge Percentage</th></tr>  -->
									</thead>
									<tbody>
										<c:forEach var="app" items="${tarifRates}">
											<tr>
												<td>${app.diameter_tap}</td>
												<td>${app.min_consumption}</td>
												<td>${app.meter_type}</td>
												<td>${app.minimum_charages}</td>
												<td>${app.rate_per_1000_ltr}</td>
												<td>${app.monthly_charges}</td>
											    <td>${app.sw_charge_percent}</td>
												<td><a href="#" onclick="editOption(${app.id});" id="editData${app.id}">Edit</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
					</fieldset>
				</div>
			</div>
	</div>
	

<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
	padding: 0px 0px 0 0px;
}

.dataTables_filter {
	display: none;
	float: left;
	margin: 0 0 20px 20px;
	position: relative;
}

.dataTables_length>label {
	margin-bottom: 0;
	display: none;
}
</style>

