<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script>
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
	
});
</script>
				<div class="panel panel-flat">
						<div class="panel-body">
						 <form class="form-horizontal" action="#">
							<fieldset class="content-group"> 
								<legend class="text-bold">Bulk Bill UnMetered</legend>
									
							<div class="row">
								<div class="col-md-3">
									  <div class="form-group">
										<label>Month Year</label><select data-placeholder="Select" class="select" id="reading_month" name="reading_month" required="required">
										    <option value="" data-icon="icon-git-branch">Select</option>
											<option value="${latestNepaliMonth}" data-icon="icon-git-branch">${monthDesc}</option>
											
										</select>
									 </div>
					   </div>
								
							</div>
									
									
									
								</fieldset>	
								<div class="text-right" id="buttondiv">
									<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="submitMonthEnd();"><span class="ladda-label" >Submit</span></button>
								</div>
							</form>
							
						</div>			
						</div>
						

 <div id="loading" hidden="true" style="text-align: center;">
                    <h3 id="loadingText">Loading..... Please wait. </h3> 
	 <img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
</div>
		
		
		

<script>
function submitMonthEnd(){
	
	
	var monthyear=$("#reading_month").val();
	
	 if(monthyear=="" || monthyear==null){
		
		alert("Please Select Month Year to generate the Bill");
		return false;
	}else{
		
	var desig="${userbdesignation}";
		
	if(desig=="Counter Incharge" || desig=="Revenue Incharge" || desig=="Billing Incharge" || desig=="Account Chief"|| desig=="Developer"){
		
		
	
		swal({
			  title: 'Are you sure you want to generate Unmetered Bill?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: "rgb(35, 164, 123)",
			  cancelButtonColor: "#fb8585",
			  confirmButtonText: "Yes!",
			  cancelButtonText: "Cancel",
			  closeOnConfirm: false,
			  closeOnCancel: true
			},
			function(isConfirm) {
			  if (isConfirm) {
				  $("#loading").show();
					 $("#buttondiv").hide();
					 
					 $.ajax({
						  type: "GET",
						  url: "./billing/updateBulkBillingUnmetered",
						  dataType: "text",
						  async   : false,
						   data:{
						     monthyear:monthyear,
						  },
						  
						  success: function(response){
						
								  swal({
						                title: response,
						                text: "",
						                confirmButtonColor: "#2196F3",
						            });
					       
							  
						  }
						});
					 $("#loading").hide();	
					 $("#buttondiv").show();
				  
			  } 
			});
		
		
		
		
		
		
	 
	
	
	}else{
		alert("Sorry You dont have Permission for Generating Unmetered Bills");
		return false;
	}
	 
	 }
	 
}
</script>

						
<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

</style>