<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<%-- <script type="text/javascript" src="resources/layout_3/assets/js/plugins/notifications/jgrowl.min.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/ui/moment/moment.min.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/pickers/daterangepicker.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/pickers/anytime.min.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/pickers/pickadate/picker.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/pickers/pickadate/picker.date.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/pickers/pickadate/picker.time.js"></script>
	<script type="text/javascript" src="resources/layout_3/assets/js/plugins/pickers/pickadate/legacy.js"></script> --%>


<%-- 	<script type="text/javascript" src="resources/layout_3/assets/js/pages/picker_date.js"></script>
 --%>

<script type="text/javascript"
	src="resources/layout_3/assets/js/plugins/notifications/bootbox.min.js"></script>

<script type="text/javascript">

var userRoleType="${userRoleType}";

	$(document).ready(function() {
		
		var cust_id="${customer_id}";
		$('#connection_no').val(cust_id);
		$('#connection_no').prop("disabled", false);
		$('#modifyConsumerId').hide();
		
		$('#ummdentry').show();
		$('#masterDataEntry').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Master Data Entry";
		var module1="Customer History";
		var check=activeMod.indexOf(module) > -1;
		var check1=activeMod.indexOf(module1) > -1;
		
		if(check){
		}else if(check1){
		}else{
			window.location.href="./accessDenied";
		}
		
		

		$('#ent_date_nep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true
		});

		$('#conc_date_nep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true
		});
	
		
		
	});

	function finalSubmit(val) {

		var connecn_no = $('#connection_no').val();
		var name_eng = $('#name_eng').val();
		var fname_eng = $('#fname_eng').val();
		var address_eng = $('#address_eng').val();
		var fixedCharges = $('#fixedCharges').val();

		var ward_no = $('#ward_no').val();
		var reading_day = $('#reading_day').val();
		
		
		
		var seq_no = $('#seq_no').val();
		
		var con_category = $('#con_category').val();
		var con_type = $('#con_type').val();
		var pipe_size = $('#pipe_size').val();
		var meterHired = $('#meterHired').val();
		var con_satatus = $('#con_satatus').val();
		var conc_date_nep = $('#conc_date_nep').val();

		if(con_type == 'Unmetered'){
			reading_day=0;
		}
		
		
		if (connecn_no.trim() == '' || connecn_no == null) {
			bootbox.alert('Please Enter Connection No.');
			return false;
		}
		else if (name_eng.trim() == '' || name_eng == null) {
			bootbox.alert('Please Enter Name in English ');
			return false;
		}
		else if (address_eng.trim() == '' || address_eng == null) {
			bootbox.alert('Please Enter Address in English');
			return false;
		}
		else if (con_type == '')
		{
			bootbox.alert('Please Select Connection Type');
			return false;
		}
		
		else if (ward_no.trim() == '' || ward_no == null) {
			bootbox.alert('Please Select Ward No.');
			return false;
		}

		else if ((reading_day == '0' && con_type == 'Metered') || (reading_day ==null && con_type == 'Metered')) {
			bootbox.alert('Please Select Reading Day');
			return false;
		}

		else if ((seq_no.trim() == '' && con_type == 'Metered') || (seq_no == null && con_type == 'Metered')) {
			bootbox.alert('Please Enter Seq No.');
			return false;
		}
		else if (fixedCharges.trim() == '' || fixedCharges == null) {
			$('#fixedCharges').val(0);	
			
		}
		else if (con_category == '')
		{
			bootbox.alert('Please Select Connection Category');
			return false;
		}
		else if ($("#sewage_used").val() == '')
		{
			bootbox.alert('Please Select Sewage Used');
			return false;
		}
		else  if (pipe_size =='' || pipe_size ==null)
		{
			bootbox.alert('Please Select Pipe Size');
			return false;
		}
		
		else if (meterHired == '')
		{
			bootbox.alert('Please Select Meter Hired Status');
			return false;
		}
		else if (con_satatus == '')
		{
			bootbox.alert('Please Select Connection Status');
			return false;
		}
		
		$('#area_no').val(ward_no + "-" + reading_day + "-" + seq_no);
		
		if (val == '0') {
			
			 if(confirm("Are you sure to add New Consumer?")){
				 $('#consumerMaster').attr('action',
							'./addConsumerMaster?area_no1=' + $('#area_no').val())
							.submit();
			 }else{
				 return false;
			 }
			 
			
			
		} else {
			
			if(confirm("Are you sure to Modify the Data?")){
				$('#consumerMaster').attr('action',
						'./updateConsumerMaster?area_no1=' + $('#area_no').val())
						.submit();
			 }else{
				 return false;
			 }
			
			
			
		}
	}

	function checkConStatus(){
		var status=$('#con_satatus').val();
		if(status!="Normal")
			{
			$('#name_eng').css("color","red");
			$('#name_nep').css("color","red");
			}
		else
			{
			$('#name_eng').css("color","black");
			$('#name_nep').css("color","black");
			}
		
	}
	
	function checkConStatusOnLoad(status){
		if(status!="Normal")
			{
			$('#name_eng').css("color","red");
			$('#name_nep').css("color","red");
			}
		else
			{
			$('#name_eng').css("color","black");
			$('#name_nep').css("color","black");
			}
		
	}
	
	
	function checkConnectionNo() {

		
		var s = $('#connection_no').val();
		$
				.ajax({
					type : "GET",
					url : "./getConsumerMasterDetails/"
							+ $('#connection_no').val(),
					dataType : "JSON",
					async : false,
					cache : false,
					success : function(response) {

						if ($.trim(response)) {
							
							
							$('#connection_no').prop("disabled", true);

							//if (response.approve_status == '0') {
								$('#id').val(response.id);
								$('#branch').val(response.branch);
								$('#connection_no').val(response.connection_no);
								$('#name_eng').val(response.name_eng);
								$('#name_nep').val(response.name_nep);
								$('#fname_eng').val(response.fname_eng);
								$('#fname_nep').val(response.fname_nep);
								$('#gfname_eng').val(response.gfname_eng);
								$('#gfname_nep').val(response.gfname_nep);
								$('#address_eng').val(response.address_eng);
								$('#address_nep').val(response.address_nep);
								$('#tole_name_eng').val(response.tole_name_eng);
								$('#tole_name_nep').val(response.tole_name_nep);
								$('#road_street_eng').val(
										response.road_street_eng);
								$('#road_street_nep').val(
										response.road_street_nep);
								$('#mpc_name_eng').val(response.mpc_name_eng);
								$('#mpc_name_nep').val(response.mpc_name_nep);
								//$('#ward_no').val(response.ward_no).trigger("change");
								$('#ward_no').find('option').remove().end().append('<option value="'+ response.ward_no +'">'+ response.ward_no +'</option>');
								$('#ward_no').val(response.ward_no);
									
								$('#customer_id').val(response.customer_id);
								
								$('#phone_no').val(response.phone_no);
								$('#mobile_no').val(response.mobile_no);
								$('#citizenship_no').val(
										response.citizenship_no);
								$('#ent_date_eng').val(
										response.ent_trans_endDate);
								$('#ent_date_nep').val(response.ent_date_nep);
								$('#mtr_ser_no').val(response.mtr_ser_no);
								$('#mtr_rdng').val(response.mtr_rdng);
								$('#mtr_reader').val(response.mtr_reader);
								var rday=response.reading_day;
								
								if(rday<10){
									var reday=0+""+rday;
									$('#reading_day').find('option').remove().end().append('<option value="'+ reday +'">'+ reday +'</option>');
									$('#reading_day').val(reday);
									
								}
								else{
								//$('#reading_day').val(response.reading_day);
									$('#reading_day').find('option').remove().end().append('<option value="'+ response.reading_day +'">'+ response.reading_day +'</option>');
									$('#reading_day').val(response.reading_day);
								}
								var area=response.area_no;
								
								var arears=area.split("-");
								
								if(arears[1]<10 &&arears[1].length==1)
									{
									var newAr=arears[0]+"-0"+arears[1]+"-"+arears[2];
									$('#area_no').val(newAr);
									}
								else{
								$('#area_no').val(response.area_no);
								}
								$('#con_category').val(response.con_category);
								
								if(response.con_type=='')
								$('#con_type').val(response.con_type);
								
								
								$('#con_satatus').val(response.con_satatus);
								$('#average').val(response.average);
								//$('#sewage_used').val(response.sewage_used);
								$('#sewage_used').find('option').remove().end().append('<option value="'+ response.sewage_used +'">'+ response.sewage_used +'</option>');
								$('#plot_no').val(response.plot_no);
								$('#loc_no').val(response.loc_no);
								$('#area_type').val(response.area_type);
								$('#remarks').val("New Connection"+response.remarks==null?"":response.remarks);
								$('#balance').val(response.balance);
								$('#ledgerno').val(response.ledgerno);
								$('#folio').val(response.folio);
								
								$('#seq_no').val(response.seq_no);
								$('#fixedCharges').val(response.fixedCharges);
								
							//	$('#connc_date_eng').val(response.con_trans_date);
							    if(response.connc_date_eng =='' || response.connc_date_eng==null){
							    	
							    }else{
								$('#connc_date_eng').val(format(response.connc_date_eng, 'dd/MM/yyyy'));
							    }
								
								$('#conc_date_nep').val(response.conc_date_nep);
								$('#costumerGroup').val(response.costumerGroup);
								$('#addConsumerId').hide();
								$('#modifyConsumerId').show();
								$('#oldconnectionno').val(response.oldconnectionno);
								
								$('#meterHired').val(response.meterHired);
								$('#fixedCharges').val(0);
								$('#balance').val(response.balance);
								$('#consumer_title').val(response.consumer_title).trigger("change");
								
								if(response.con_type=='Unmetered'){
									$('#billremid').show();
									$('#bill_remarksum').val(response.bill_remarksum).trigger("change");
								}
								
								if (response.meterHired == 'Yes'||response.meterHired == 'Y') {
									$('#meterRentChargesid').show();
								} else {
									$('#meterRentChargesid').hide();
								}



var conT=response.connection_no;                   
								if(response.branch==conT.substring(0,4) && conT.length== 10){
									
									if(response.con_satatus == 'Processing in New Connection' || response.con_satatus == 'Permanent'){
										alert("You can Modify the details after New Connection Approval");
										$('#alertDiv').hide();
										
										window.location.href="./consumerMaster";
									}else{
									$('#alertDiv').show();
									$('#alertDiv')
											.html(
													'<div class="alert alert-success alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">'
															+ s
															+ ' Connection No Exist. You can Modify the details</span>');
									checkConStatusOnLoad(response.con_satatus);
								}
								}
								else{

								$('#alertDiv').show();
								$('#alertDiv')
										.html(
												'<div class="alert alert-success alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">'
														+ s
														+ ' Connection No Exist. You can Modify the details</span>');
								checkConStatusOnLoad(response.con_satatus);
								}
								 if(userRoleType=='Account Chief'){
									 
								 } else {
									 $('#con_type').find('option').remove().end().append('<option value="'+ response.con_type +'">'+ response.con_type +'</option>');
									 $("#con_type").val(response.con_type).trigger("change");
									 $('#con_satatus').find('option').remove().end().append('<option value="'+ response.con_satatus +'">'+ response.con_satatus +'</option>');
									 $("#con_satatus").val(response.con_satatus).trigger("change");
									 $('#pipe_size').find('option').remove().end().append('<option value="'+ response.pipe_size +'">'+ response.pipe_size +'</option>');
									 $("#pipe_size").val(response.pipe_size).trigger("change");
									 
									// $('#reading_day').find('option').remove().end().append('<option value="'+ response.reading_day +'">'+ response.reading_day +'</option>');
									// $("#reading_day").val(response.reading_day).trigger("change");
									 
									//$("#reading_day").prop("readonly",true);
									 
									 $('#citizenship_no').prop("readonly", true);
									 $('#name_eng').prop("readonly", true);
									 $('#name_nep').prop("readonly", true);
									 $('#fname_eng').prop("readonly", true);
									 $('#fname_nep').prop("readonly", true);
									 $('#gfname_eng').prop("readonly", true);
									 $('#gfname_nep').prop("readonly", true);
									 $('#seq_no').prop("readonly", true);
									
								 }
							//} else {
								/* $('#addConsumerId').show();
								$('#modifyConsumerId').hide();
								$('#alertDiv').show();
								$('#alertDiv')
										.html(
												'<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">'
														+ s
														+ ' Details are Already Approved .You cannot Modify</span>');
								$('#consumerMaster')[0].reset();
								$('#connection_no').val(''); */

							//}
								
							//$('#connection_no').attr(dis)

						}
						if (response.id == 0) {
							$('#connection_no').prop("disabled", false);
							$('#addConsumerId').show();
							$('#modifyConsumerId').hide();
							$('#alertDiv').hide();
							$('#consumerMaster')[0].reset();
							$('#connection_no').val(s);
						}

					}
				});

	}

	function checkMeterHired() {
		
		var meterHired=$('#meterHired').val();
		var pipe_size = $('#pipe_size').val();
		if (meterHired == 'Yes') {
			    
			    $('#meterRentChargesid').show();
				if(pipe_size=='0.5'){
					$("#meterRentCharges").val(5);	
				}else if(pipe_size=='0.75'){
					$("#meterRentCharges").val(17);
				}else if(pipe_size=='1'){
					$("#meterRentCharges").val(33);
				}else if(pipe_size=='1.5'){
					$("#meterRentCharges").val(67);
				}else if(pipe_size=='2'){
					$("#meterRentCharges").val(133);
				}else if(pipe_size=='3'){
					$("#meterRentCharges").val(250);
				}else if(pipe_size=='4'){
					$("#meterRentCharges").val(500);
				} 
	}else {
		$('#meterRentChargesid').hide();
		$("#meterRentCharges").val(0);
	}
	}
	
	function checkMunicipalityId(muncipal_Id) {

		$.ajax({
					type : "GET",
					url : "./getWardDetails/"
							+ muncipal_Id,
					dataType : "JSON",
					async : false,
					cache : false,
					success : function(response) {
						
						var res=response;
						var html="<option value='' selected='selected'>Select Ward No</option>";
						for(var i=0;i<res.length;i++){
							var result=res[i];
								html+='<option value='+result.wardNo+'>'+result.wardNo+'</option>';
						}
						$('#ward_no').html(html);
					}
				});
	}

	function updateAreaNo() {

		var ward_no = $('#ward_no').val();
		var reading_day = $('#reading_day').val();
		var seq_no = $('#seq_no').val();
		
		var con_type = $('#con_type').val();

		if(con_type=="Metered"){
			$('#area_no').val(ward_no + "-" + reading_day + "-" + seq_no);
		}else{
			
			$('#ward_no').val(ward_no);
			$('#area_no').val(0);
			$('#reading_day').val(0);
			$('#seq_no').val("");
		}

	}

			  
			  
/* function updateAreaNo(){
	var ward_no1=$('#ward_no').val();
	var reading_day=$('#reading_day').val();
	var seq_no=$('#seq_no').val();
	var ward_no="";
	if(ward_no1!="" && reading_day!="" && seq_no!="")
	{
		$.ajax({
			type: "GET",
			url: "./getWardNoById/"+ward_no1,
			dataType: "text",
			async       : false,
			success: function(response){
				ward_no=response;
			}
		});
		$('#area_no').val(ward_no1+"-"+reading_day+"-"+seq_no);
	}
} */


function convertToUpperCase(){
	$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
}

</script>


<div class="panel panel-flat">
	<!-- <div class="panel-heading">
							<h5 class="panel-title">Consumer Master</h5>
							<div class="heading-elements">
								<ul class="icons-list">
			                		<li><a data-action="collapse"></a></li>
			                		<li><a data-action="reload"></a></li>
			                		<li><a data-action="close"></a></li>
			                	</ul>
		                	</div>
						</div> -->


	<div class="panel-body">
		<c:if test="${not empty consumerMaterOp}">
			<div class="alert alert-success	 alert-bordered">
				<button type="button" class="close" data-dismiss="alert">
					<span>&times;</span><span class="sr-only">Close</span>
				</button>
				<span class="text-semibold" id="alert">${consumerMaterOp}</span>
			</div>
			<c:remove var="consumerMaterOp" scope="session" />
		</c:if>
		<div class="row" hidden="true" id="alertDiv"></div>
		<!-- <form class="form-horizontal" action="#">-->
		<form:form action="#" modelAttribute="consumerMaster"
			commandName="consumerMaster" method="POST" id="consumerMaster"
			role="form" class="form-horizontal form-validate-jquery">
			<form:input id="id" path="id" name="id" type="text" hidden="true" />
			
			<input type="text" name="oldconnectionno" id="oldconnectionno" hidden="true">
			
			<fieldset class="content-group">
				<legend class="text-bold">Consumer Master Details</legend>
				<!-- <legend ></legend> -->
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<label>Connection No <font color="red">*</font>.
							</label>
							<form:input type="text" required="required" path="connection_no"
								name="connection_no" id="connection_no"
								onchange="checkConnectionNo(this.values)" class="form-control" onkeyup="convertToUpperCase();"
								placeholder=""  />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Consumer Id <font color="red">*</font>.
							</label>
							<input type="text" required="required" value="${customer_id}"
								name="customer_id" id="customer_id" readonly="readonly"
								class="form-control" placeholder="" />
						</div>
					</div>
					
					
					<div class="col-md-2">
						<div class="form-group">
							<label>Ledger No.</label>
							<form:input type="text" path="ledgerno" name="ledgerno"
								id="ledgerno" class="form-control" placeholder="Ledger No..." />
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Folio No.</label>
							<form:input type="text" path="folio" name="folio" id="folio"
								class="form-control" placeholder="Follo No..." />
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Citizenship No.</label>
							<form:input type="text" path="citizenship_no"
								name="citizenship_no" id="citizenship_no" class="form-control"
								placeholder="Citizenship No..." />
						</div>
					</div>
				</div>


				
										
										<div class="row">
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Plot No.</label>
					                                <form:input type="text" path="plot_no" name="plot_no" id="plot_no" class="form-control" placeholder="Plot No..." />
				                                </div>
											</div>
											
											<div class="col-md-2">
												<div class="form-group">
													<label>Locality No.</label>
					                                <form:input type="text" path="loc_no" name="loc_no" id="loc_no" class="form-control" placeholder="Locality No..."/>
				                                </div>
											</div>
											
											<div class="form-group col-md-2">
												<label>Consumer Title<font color="red">*</font></label>
												<form:select class="selectbox input-group" path="consumer_title"
													name="consumer_title" id="consumer_title">
													<option value="" data-icon="icon-git-branch">Select
														</option>
													<option value="Campus" data-icon="icon-git-branch">Campus</option>
													<option value="Comp" data-icon="icon-git-commit">Comp</option>
													<option value="Corp" data-icon="icon-git-commit">Corp</option>
													
													<option value="Dr." data-icon="icon-git-commit">Dr.</option>
													<option value="Eng." data-icon="icon-git-commit">Eng.</option>
													<option value="HMG" data-icon="icon-git-commit">HMG</option>
													
													
													<option value="Hospital" data-icon="icon-git-commit">Hospital</option>
													<option value="Hotel" data-icon="icon-git-commit">Hotel</option>
													<option value="Miss." data-icon="icon-git-commit">Miss.</option>
													
													<option value="Mr." data-icon="icon-git-commit">Mr.</option>
													<option value="Mrs." data-icon="icon-git-commit">Mrs.</option>
													<option value="Ms" data-icon="icon-git-commit">Ms</option>
													
													<option value="Mun." data-icon="icon-git-commit">Mun.</option>
													<option value="Hotel" data-icon="icon-git-commit">Hotel</option>
													
													<option value="NG" data-icon="icon-git-commit">NG</option>
													<option value="Office" data-icon="icon-git-commit">Office</option>
													<option value="Park" data-icon="icon-git-commit">Park</option>
													
													<option value="School" data-icon="icon-git-commit">School</option>

													
												</form:select>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Name in English <font color="red">*</font></label>
					                                <form:input type="text" required="required" path="name_eng" name="name_eng" id="name_eng" class="form-control" placeholder="Name in English..." />
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group" >
													<label>Name in Nepali</label>
					                                <form:input type="text" path="name_nep" name="name_nep" id="name_nep" class="form-control" placeholder="Name in Nepali..." />
				                                </div>
											</div>
											
										</div>
										
										<div class="row">
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Father Name in English <!-- <font color="red">*</font> --></label>
					                                <form:input type="text"  path="fname_eng" name="fname_eng" id="fname_eng" class="form-control" placeholder="Father Name in English..."/>
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Father Name in Nepali</label>
					                                <form:input type="text" path="fname_nep" name="fname_nep" id="fname_nep" class="form-control" placeholder="Father Name in Nepali..." />
				                                </div>
											</div>
											
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Grand Father Name(Eng)</label>
					                                <form:input type="text" path="gfname_eng" name="gfname_eng" id="gfname_eng" class="form-control" placeholder="GF Name in English..." />
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Grand Father Name(Nep)</label>
					                                <form:input type="text" path="gfname_nep" name="gfname_nep" id="gfname_nep" class="form-control" placeholder="GF Name in Nepali..." />
				                                </div>
											</div>
										</div>
										
										
										
										<div class="row">
											<div class="col-md-3 form-group">
													<label>Address in English <font color="red">*</font></label>
					                                <form:textarea class="form-control" required="required" path="address_eng" name="address_eng" id="address_eng" placeholder="Address in English..."  style="height: 37px;"></form:textarea>
				                                
											</div>
										
											
											<div class="col-md-3">
												<div class="form-group" >
													<label>Address in Nepali</label>
					                                <form:textarea class="form-control" path="address_nep" name="address_nep" id="address_nep" placeholder="Address in Nepali..."  style="height: 37px;"></form:textarea>
				                                </div>
											</div>
									
										<div class="col-md-2">
											<div class="form-group">
												<label>Municipality</label>
												<form:select class="selectbox input-group" id="municipality_Id" onchange="checkMunicipalityId(this.value)"
													name="municipality_Id" path="municipality_Id"
													data-placeholder="Select Municipality Name...">
													<form:option value="0" disabled="disabled">Select</form:option>
													<c:forEach var="data" items="${municipal}">
														<form:option value="${data.id}">${data.muncipalityName}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										
										<div class="form-group col-md-2">
											<label>Connection Type <font color="red">*</font></label>
											<form:select class="selectbox input-group" path="con_type"
												name="con_type" id="con_type" onchange="getFixedCharge(this.value);">
												<option value="" data-icon="icon-git-branch">Select
													</option>
												<option value="Metered" data-icon="icon-git-branch">Metered</option>
												<option value="Unmetered" data-icon="icon-git-commit">Unmetered</option>
											</form:select>
										</div>
					
					
										<div class="col-md-2">
											<div class="form-group">
												<label>Ward No. <font color="red">*</font></label>
												<form:select class="selectbox input-group" id="ward_no" name="ward_no"  onchange="updateAreaNo()"
													path="ward_no" data-placeholder="Select Ward No...">
													<form:option value="">Select Ward No</form:option>
													<c:forEach var="data" items="${wardList}">
														<form:option value="${data.wardNo}">${data.wardNo}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										
										
										
										
									</div>
									
									<div class="row">
											
										<div class="form-group col-md-3" id="rdaydiv">
										<label>Reading Day <font color="red">*</font></label>
											
											<form:select class="selectbox input-group" path="reading_day" name="reading_day" id="reading_day" onchange="updateAreaNo()"> 
												<option value="0"  data-icon="icon-git-branch">Select Reading Day</option>
												<option value="01" data-icon="icon-git-branch">01</option>
												<option value="02" data-icon="icon-git-branch">02</option>
												<option value="03" data-icon="icon-git-branch">03</option>
												<option value="04" data-icon="icon-git-branch">04</option>
												<option value="05" data-icon="icon-git-branch">05</option>
												<option value="06" data-icon="icon-git-branch">06</option>
												<option value="07" data-icon="icon-git-branch">07</option>
												<option value="08" data-icon="icon-git-branch">08</option>
												<option value="09" data-icon="icon-git-branch">09</option>
												<option value="10" data-icon="icon-git-branch">10</option>
												<option value="11" data-icon="icon-git-branch">11</option>
												<option value="12" data-icon="icon-git-branch">12</option>
												<option value="13" data-icon="icon-git-branch">13</option>
												<option value="14" data-icon="icon-git-branch">14</option>
												<option value="15" data-icon="icon-git-branch">15</option>
												<option value="16" data-icon="icon-git-branch">16</option>
												<option value="17" data-icon="icon-git-branch">17</option>
												<option value="18" data-icon="icon-git-branch">18</option>
												<option value="19" data-icon="icon-git-branch">19</option>
												<option value="20" data-icon="icon-git-branch">20</option>
												<option value="21" data-icon="icon-git-branch">21</option>
												<option value="22" data-icon="icon-git-branch">22</option>
												<option value="23" data-icon="icon-git-branch">23</option>
												<option value="24" data-icon="icon-git-branch">24</option>
												<option value="25" data-icon="icon-git-branch">25</option>
												<option value="26" data-icon="icon-git-branch">26</option>
												<option value="27" data-icon="icon-git-branch">27</option>
												<option value="28" data-icon="icon-git-branch">28</option>
												<option value="29" data-icon="icon-git-branch">29</option>
												<option value="30" data-icon="icon-git-branch">30</option>
											</form:select>
										
										</div>
											
											<div class="col-md-3" id="seqdivid">
												<div class="form-group">
													<label>Sequence No.<font color="red">*</font></label>
					                                <form:input path="seq_no" onchange="updateAreaNo()" name="seq_no" id="seq_no" type="text" class="form-control" placeholder="Sequence No..." />
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Area No.</label>
					                                <form:input disabled="true" type="text" path="area_no" name="area_no" id="area_no" class="form-control" placeholder="Area No..." />
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group" >
													<label>Mobile No.</label>
					                                <form:input type="text" path="mobile_no" name="mobile_no" id="mobile_no" class="form-control" placeholder="Mobile No..." />
				                                </div>
											</div>
											
											 <!--   Billing Remarks added 20-12-2018 by ram -->
                   <%--   <div class="form-group col-md-3" style="display: none;" id="meterremoveid">
						<label>Meter Remove</label>
						<form:select class="selectbox input-group" path="meterRemove"  name="meterRemove"
							id="meterRemove" placeholder="Meter Remove...">
							<option value="Yes" data-icon="icon-git-branch">Yes</option>
							<option value="No" data-icon="icon-git-branch">No</option>
						</form:select>
					</div>
							 --%>				
					</div>

				<div class="row">
				
				<div class="col-md-3">
						<div class="form-group">
							<label>Phone No.</label>
							<form:input type="text" path="phone_no" name="phone_no"
								id="phone_no" class="form-control" placeholder="Phone No..." />
						</div>
					</div>
				
					<div class="col-md-3" hidden="true">
						<div class="form-group">
							<label>Meter Reader</label>
							<!-- <div class="col-lg-3">  -->
							<form:select class="selectbox input-group" id="mtr_reader"
								name="mtr_reader" path="mtr_reader"
								data-placeholder="Select Meter Reader...">
								<form:option value="" disabled="disabled">Select Meter Reader</form:option>
								<c:forEach var="data" items="${mtrReaderList}">
									<form:option value="${data.id}">${data.mrCode} - ${data.mrName }</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Tole Name in English</label>
							<form:input type="text" path="tole_name_eng" name="tole_name_eng"
								id="tole_name_eng" class="form-control"
								placeholder="Tole Name in English..." />
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Tole Name in Nepali</label>
							<form:input type="text" path="tole_name_nep" name="tole_name_nep"
								id="tole_name_nep" class="form-control"
								placeholder="Tole Name in Nepali..." />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Road Street in English</label>
							<form:input type="text" path="road_street_eng"
								name="road_street_eng" id="road_street_eng" class="form-control"
								placeholder="Road Street in English..." />
						</div>
					</div>

					
				</div>

				<div class="row">

					
					
					<div class="col-md-3">
						<div class="form-group">
							<label>Road Street in Nepali</label>
							<form:input type="text" path="road_street_nep"
								name="road_street_nep" id="road_street_nep" class="form-control"
								placeholder="Road Street in Nepali..." />
						</div>
					</div>
					<div class="form-group col-md-3">
						<label>Area Type</label>
						<form:select class="selectbox input-group" path="area_type"
							name="area_type" id="area_type">
							<option value="" data-icon="icon-git-branch">Select Area
								Type</option>
							<option value="Urban" data-icon="icon-git-branch">Urban</option>
							<option value="Rural" data-icon="icon-git-commit">Rural</option>
						</form:select>
					</div>

					<div class="form-group col-md-3" hidden="true">
						<label>Average</label>
						<form:select class="selectbox input-group" path="average"
							name="average" id="average">
							<option value="" data-icon="icon-git-branch">Select
								Average</option>
							<option value="1" data-icon="icon-git-branch">Yes</option>
							<option value="0" data-icon="icon-git-commit">No</option>
						</form:select>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Entered Date in Nepali</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="icon-calendar3"></i></span>
								<form:input path="ent_date_nep" name="ent_date_nep"
									placeholder="Entered Date in Nepali..." id="ent_date_nep"
									class="form-control nepali-calendar" />
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Entered Date in English</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="icon-calendar3"></i></span>
								<input type="text" type="text" name="ent_date_eng"
									id="ent_date_eng" class="form-control daterange-single">
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Connection Date in Nepali <!-- <font color="red">*</font> --></label>

							<div class="input-group">
								<span class="input-group-addon"><i class="icon-calendar3"></i></span>
								<form:input path="conc_date_nep" name="conc_date_nep"
									placeholder="Connection Date in Nepali..." id="conc_date_nep"
									class="form-control nepali-calendar" />
							</div>

						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label>Connection Date in English</label>

							<div class="input-group">
								<span class="input-group-addon"><i class="icon-calendar3"></i></span>
								<input type="text" type="text" name="connc_date_eng"
									id="connc_date_eng" class="form-control daterange-single">
							</div>
						</div>
					</div>
					<div class="form-group col-md-3">
						<label>Connection Category <font color="red">*</font></label>
						<form:select class="selectbox input-group" path="con_category"
							name="con_category" id="con_category">
							<option value="" data-icon="icon-git-branch">Select
								Connection Category</option>
							<option value="Domestic" data-icon="icon-git-branch">Domestic</option>
							<option value="Government" data-icon="icon-git-commit">Government</option>
							<option value="Industry and Company" data-icon="icon-git-commit">Industry and Company</option>
						</form:select>
					</div>
					<div class="form-group col-md-3">
						<label>Connection Status <font color="red">*</font></label>
						<form:select class="selectbox input-group" path="con_satatus"
							name="con_satatus" id="con_satatus" onchange="checkConStatus()">
							<!-- <option value="" data-icon="icon-git-branch">Select Connection Status</option> -->
							<option value="Normal" data-icon="icon-git-branch">Normal</option>
							<option value="Temporary" data-icon="icon-git-commit">Temporary</option>
							<option value="Defaulter"
								data-icon="icon-git-commit">Defaulter</option>
							<option value="Permanent"
								data-icon="icon-git-commit">Permanent</option>
						</form:select>
					</div>
					
				</div>

				<div class="row">
					
					<div class="col-md-3" hidden="true">
						<div class="form-group">
							<label>MPC Name in English</label>
							<form:input type="text" path="mpc_name_eng" name="mpc_name_eng"
								id="mpc_name_eng" class="form-control"
								placeholder="MPC Name in English..." />
						</div>
					</div>
					<div class="col-md-3" hidden="true">
						<div class="form-group">
							<label>MPC Name in Nepali</label>
							<form:input type="text" path="mpc_name_nep" name="mpc_name_nep"
								id="mpc_name_nep" class="form-control"
								placeholder="MPC Name in Nepali..." />
						</div>
					</div>
					
					
					

					
				</div>

				<div class="row">
				
				
				
					<div class="form-group col-md-3">
						<label>Pipe Size(inch) <font color="red">*</font></label>
						<form:select class="selectbox input-group" path="pipe_size"
							name="pipe_size" id="pipe_size" onchange="checkMeterHired()">
							
							<option value="0.5" data-icon="icon-git-branch">0.5</option>
							<option value="0.75" data-icon="icon-git-branch">0.75</option>
							<option value="1" data-icon="icon-git-commit">1</option>
							<option value="1.5" data-icon="icon-git-compare">1.5</option>
							<option value="2" data-icon="icon-git-compare">2</option>
							<option value="3" data-icon="icon-git-compare">3</option>
							<option value="4" data-icon="icon-git-compare">4</option>
						</form:select>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Fixed Charges <font color="red">*</font></label>
							<form:input type="text" required="required" path="fixedCharges"
								name="fixedCharges" id="fixedCharges" class="form-control"
								placeholder="Fixed Charges..." />
						</div>
					</div>

					<div class="form-group col-md-3">
						<label>Sewage Used <font color="red">*</font></label>
						<form:select class="selectbox input-group" path="sewage_used"
							name="sewage_used" id="sewage_used">
							<option value="Yes" data-icon="icon-git-branch">Yes</option>
							<option value="No" data-icon="icon-git-commit">No</option>
						</form:select>
					</div>
					
					<div class="col-md-3" hidden="true">
						<div class="form-group">
							<label>Customer Group <font color="red"></font></label>
							<form:input type="text" path="costumerGroup" name="costumerGroup"
								id="costumerGroup" class="form-control"
								placeholder="Consumer Group..." />
						</div>
					</div>
					<div class="form-group col-md-3">
						<label>Meter Hired</label>
						<form:select class="selectbox input-group" path="meterHired"
							onchange="checkMeterHired()" name="meterHired"
							id="meterHired" placeholder="Meter Hired...">
							<option value="Yes" data-icon="icon-git-branch">Yes</option>
							<option value="No" data-icon="icon-git-branch">No</option>
						</form:select>
					</div>

					
				</div>
				<div class="row">

				
					
					

					<div hidden="true" class="col-md-3" id="meterRentChargesid">
						<div class="form-group">

							<label>Meter Rent Charges</label>
							<form:input type="text" path="meterRentCharges"
								name="meterRentCharges" id="meterRentCharges" value="0"
								class="form-control" placeholder="Meter Rent Charges..." />
						</div>
					</div>
					
					<div class="form-group col-md-2" style="display: none;" id="billremid">
						<label>Bill Remarks</label>
						<select class="selectbox input-group" 
							name="bill_remarksum" id="bill_remarksum">
							<option value="" data-icon="icon-git-branch">Select
								</option>
							<option value="LWS" data-icon="icon-git-branch">Low Water Supply</option>
							<option value="LWS" data-icon="icon-git-branch">Meter Remove</option>
						</select>
					</div>
        
         
         
         

					<div class="form-group col-md-4">
						<label>Remarks</label>
						<form:textarea class="form-control" path="remarks" name="remarks" 
							id="remarks" placeholder="Remarks..." rows="1" style="width: 333px;"></form:textarea>
					</div>
					
					<div class="col-md-3" hidden="true">
						<div class="form-group">
							<label>Board Balance <font color="red"></font></label>
							<form:input type="text" path="balance" name="balance"
								id="balance" class="form-control"
								placeholder="Board Balance"/>
						</div>
					</div>
					
				</div>








				<div class="text-center">
					
					<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label" >Clear</span></button>
					
					<button type="submit" hidden="true" onclick="return finalSubmit(1)"
						id="modifyConsumerId" class="btn btn-primary">
						<i class="glyphicon glyphicon-user"></i> &nbsp;&nbsp;Modify<i
							class="icon-arrow-right14 position-right"></i>
					</button>
					
					<button type="submit" onclick="return finalSubmit(0)"
						id="addConsumerId" class="btn btn-primary">
						<i class="glyphicon glyphicon-user"></i> &nbsp;&nbsp;Add Consumer<i
							class="icon-arrow-right14 position-right"></i>
					</button>

				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</fieldset>
		</form:form>


	</div>
</div>
<script>



	$(document).ready(function() {

		$('#openUl').show();

		$('#secUl').show();
		$('#genericLibrary').addClass('active');
		$('#dataTables1').addClass('active');
		
		$('#remarks').val("New Connection");
	});
	
	
	function getFixedCharge(value){
		
		var ward_no=$('#ward_no').val();
		
		if(value=="Metered"){
			
			$('#fixedCharges').val(0);
			$('#rdaydiv').show();
			$('#seqdivid').show();
			$('#billremid').hide();
			$('#bill_remarksum').val("");
			//$('#meterremoveid').hide();
			
		}else{
			
			$('#fixedCharges').val(0);
			$('#reading_day').val(0);
			$('#seq_no').val("");
			$('#area_no').val(0);
			$('#rdaydiv').hide();
			$('#seqdivid').hide();
			$('#ward_no').val(ward_no);
			$('#billremid').show();
			//$('#meterremoveid').show();
			$("#meterHired").val("No");
		}
			checkMeterHired();
	}
	
	function clearButton(){
		
		window.location.href="./consumerMaster";
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
	font-size: 13px;
}
</style>
<script>
var format = function (time, format) {
    var t = new Date(time);
    var tf = function (i) { return (i < 10 ? '0' : '') + i };
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
        switch (a) {
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}
//alert(new Date().getTime());
//alert(format(new Date().getTime(), 'MM/dd/yyyy'))

</script>
