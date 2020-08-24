<%@include file="/WEB-INF/decorators/taglibs.jsp"%>	
<!-- <script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>
 -->
<script type="text/javascript">

$(document).ready(function(){   
	
	$('#administrationModule').addClass('active');
	$('#administrationScreens').show();
	
	
	var activeMod="${activeModulesNew}";
	var module="Administration";
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
	
	$('#effectingDateN').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
  	});
	
});



function finalSubmit() {
	
	/* $.ajax({
   	    type :"get", */
        /* dataType :'json', */
        /*  data :$('#disconnectionRules').serialize(),
   	    url :"./saveDisconnectionRules",
 		async:false,
    	cache:false,
 		success : function(response)
 		{
 			  alert(response); */
 			/*  for(var j=0;j<response.length;j++)
	    		{
 				$('#fromConnectionNo').val(response[j][0]);
 				$('#receiptNo').val(response[j][1]);
 				$('#engRdate').val(response[j][2]);
 				$('#receiptAmnt').val('-'+response[j][5]);
 				$('#adjustmentAmnt').val(response[j][5]);
 				$('#engAdate').val(currDate);
 				$('#adjustmentNo').val(response[j][8]);
 				$('#payMode').val(response[j][4]);
 				$('#towards').val(response[j][7]);
 				
 				$('#cdNo').val(response[j][3]);
 				$('#bankName').val(response[j][6]);
 				$('#cdDate').val(response[j][9]); */
 				
	    		/* } */
 	/* 	} */
/* }); */
 //	$("#disconnectionRules").attr("action", "./saveDisconnectionRules").submit(); 
 	return false;
	 /*  swal({
          title: "Employee Added Successfully",
          confirmButtonColor: "#2196F3"
      }); */
} 
</script>


<div class="content">


<!-- <form action="#"> -->
	<div class="panel panel-flat">
		<div class="panel-body">
					 <form:form action="./saveDisconnectionRules" modelAttribute="disconnectionRules" commandName="disconnectionRules" method="POST"
						id="disconnectionRules" role="form" class="form-horizontal form-validate-jquery">
							<fieldset class="content-group"> 
									<legend class="panel-title" style="text-align:center; font-size:21px;">Disconnection Rules</legend>
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label style="margin-top: 20px;">Branch</label>
					                                <input id ="branch" type="text" class="form-control"  placeholder="Branch..." >
				                                </div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Connection Not Paid From Last</label> 
					                                <input id="connectionNLastPaid" type="text" class="form-control" placeholder="Connection Not Paid From Last..." >
					                                <label>Months</label>
				                                </div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label>Cut Off Amount From<font color="red">&nbsp;*</font></label>
					                                <input id="cutOff" type="text" class="form-control" required="required" placeholder="Cut Off Amount From..." >
					                             </div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label>To<font color="red">&nbsp;*</font></label>
					                                <input id="to" type="text" class="form-control" required="required" placeholder="To..." >
					                            </div>
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-6">
												<div class="form-group">
													<label>Effecting Date in Nepali<font color="red">&nbsp;*</font></label>
														<div class="input-group">
														<span class="input-group-addon " ><i class="icon-calendar"></i></span>
					                                		<input type="text" id="effectingDateN" name="effectingDateN" class="form-control nepali-calendar" required="required"
															placeholder="Effecting Date in Nepali...">
														</div>
					                            </div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label>Effecting Date in English<font color="red">&nbsp;*</font></label>
					                                 <div class="input-group">
														<span class="input-group-addon"><i class="icon-calendar"></i></span>
														<input type="text" class="form-control daterange-single" id="effectingDateE" name="effectingDateE" required="required">
													</div>
					                            </div>
					                           
											</div>
										</div>
										
								</fieldset>
							<div class="text-center">
								<button type="submit"   class="btn btn-primary" onclick="finalSubmit(0)" id="addOption"> Submit <i class="icon-arrow-right14 position-right"></i></button>
							</div>
						
							</form:form>	
						<br/><br/>
								
										
								
				<!-- 3rd grid view -->
				<!-- <div id ="gridId2" hidden="true">
				<fieldset class="content-group" > 
									<legend class="text-bold">Customer Groups Details: <span style="background-color: #EFC4BA">&nbsp;Group ID:1&nbsp;</span></legend>
										<div class="panel panel-flat">
										<div class="col-md-12">
						<div class="panel-heading">
							<h5 class="panel-title">Bordered table</h5>
							<div class="heading-elements">
								<ul class="icons-list">
			                		<li><a data-action="collapse"></a></li>
			                		<li><a data-action="reload"></a></li>
			                		<li><a data-action="close"></a></li>
			                	</ul>
		                	</div>
						</div>
					<div  class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr>
									<th>Connection No.</th>
									<th hidden="true">Group ID</th>
									<th>Name</th>
									<th>Address in English</th>
									<th>Address in Nepali</th>
									<th>Ward No.</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td id="con1" ><a href="#" onclick="return getDetailsByConnNum()">00124578</a></td>
									<td id="gid1" hidden="true">1</td>
									<td id="cust1">Customer Name1</td>
									<td id="addr011">Address 01</td>
									<td id="addr021">Address 02</td>
									<td id="wNum1">001</td>
								</tr>
								<tr>
									<td>00124579</td>
									<td hidden="true">1</td>
									<td>Customer Name2</td>
									<td>Address 11</td>
									<td>Address 12</td>
									<td>001</td>
								</tr>
								<tr>
									<td>00124580</td>
									<td hidden="true">1</td>
									<td>Customer Name3</td>
									<td>Address 21</td>
									<td>Address 22</td>
									<td>001</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
								</fieldset>
						<div>		</ -->
								
							</div>
						</div>
	</form>

</div>


<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818	
}
</style>