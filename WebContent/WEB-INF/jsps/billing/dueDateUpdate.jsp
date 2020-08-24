<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript" src="./resources/layout_3/assets/js/core/libraries/jquery_ui/interactions.min.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/selects/select2.min.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/inputs/touchspin.min.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/styling/switch.min.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_select2.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>


<script>
$(document).ready(function(){   
	$('#billingscreens').show();
	$('#billingManagement').addClass('active');
	
	  $('#rdng_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#rdng_date_nep').val();
			 		getEngDate(rdngdtnep,1);
			 	}
	  });
	  
	  
	  $('#due_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#due_date_nep').val();
			 		getEngDate(rdngdtnep,2);
			 	}
	  });
	  
	  $("#id1111").validate({
		    ignore: 'input[type=hidden], .select2-search__field', // ignore hidden fields
		    errorClass: 'validation-error-label',
		    successClass: 'validation-valid-label',
		    highlight: function(element, errorClass) {
		        $(element).removeClass(errorClass);
		    },
		    unhighlight: function(element, errorClass) {
		        $(element).removeClass(errorClass);
		    },

		    // Different components require proper error label placement
		    errorPlacement: function(error, element) {

		        // Styled checkboxes, radios, bootstrap switch
		        if (element.parents('div').hasClass("checker") || element.parents('div').hasClass("choice") || element.parent().hasClass('bootstrap-switch-container') ) {
		            if(element.parents('label').hasClass('checkbox-inline') || element.parents('label').hasClass('radio-inline')) {
		                error.appendTo( element.parent().parent().parent().parent() );
		            }
		             else {
		                error.appendTo( element.parent().parent().parent().parent().parent() );
		            }
		        }

		        // Unstyled checkboxes, radios
		        else if (element.parents('div').hasClass('checkbox') || element.parents('div').hasClass('radio')) {
		            error.appendTo( element.parent().parent().parent() );
		        }

		        // Input with icons and Select2
		        else if (element.parents('div').hasClass('has-feedback') || element.hasClass('select2-hidden-accessible')) {
		            error.appendTo( element.parent() );
		        }

		        // Inline checkboxes, radios
		        else if (element.parents('label').hasClass('checkbox-inline') || element.parents('label').hasClass('radio-inline')) {
		            error.appendTo( element.parent().parent() );
		        }

		        // Input group, styled file input
		        else if (element.parent().hasClass('uploader') || element.parents().hasClass('input-group')) {
		            error.appendTo( element.parent().parent() );
		        }

		        else {
		            error.insertAfter(element);
		        }
		    },
		    validClass: "validation-valid-label",
		    success: function(label) {
		        label.addClass("validation-valid-label").text("Success.")
		    },
		    rules: {
		        password: {
		            minlength: 5
		        },
		        repeat_password: {
		            equalTo: "#password"
		        },
		        email_id: {
		            email: true
		        },
		        mobileno: {
		        	minlength: 10,
		            maxlength: 10,
		            digits: true
		        },
		        minimum_number: {
		            min: 10
		        },
		        maximum_number: {
		            max: 10
		        },
		        number_range: {
		            range: [10, 20]
		        },
		        url: {
		            url: true
		        },
		        date: {
		            date: true
		        },
		        date_iso: {
		            dateISO: true
		        },
		        numbers: {
		            number: true
		        },
		        digits: {
		            digits: true
		        },
		        creditcard: {
		            creditcard: true
		        },
		        basic_checkbox: {
		            minlength: 2
		        },
		        styled_checkbox: {
		            minlength: 2
		        },
		        switchery_group: {
		            minlength: 2
		        },
		        switch_group: {
		            minlength: 2
		        }
		    },
		    messages: {
		        custom: {
		            required: "This is a custom error message",
		        },
		        mobileno : "Please Enter 10 Digit Mobile Number",
		        agree: "Please accept our policy"
		    }
		});
	  
	  
});


</script>
	
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
						
						 <form  action="./billing/dueDateUpdate" method="POST"  id="id1111">
							<fieldset class="content-group"> 
								<legend class="text-bold">Update Due Date</legend>
									
							  <div class="row">
			
			
											<div class="col-md-4">
												<div class="form-group">
													<label>Month Year</label> <input type="text" class="form-control" name="monthyear" id="monthyear" readonly="readonly" value="${maxMonthYear}"
														placeholder="Month Year...">
												</div>
										    </div>
								
									
											<div class="col-md-4">
												<div class="form-group">
													<label>Ward Number&nbsp;<font color="red">*</font></label><select class="selectbox input-group" id="wardNo" name="wardNo" required="required">
													    <option value="" data-icon="icon-git-branch">Select</option>
														
														<c:forEach items="${wardNoList}" var="ward">
														<option value="${ward.wardNo}">${ward.wardNo}</option>
													   </c:forEach>
														
													</select>
												</div>
											</div>
											
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Bill To Date in Nepali&nbsp;<font color="red">*</font></label> 
													<input type="text" id="rdng_date_nep" name="rdng_date_nep" class="form-control nepali-calendar" required="required" 
														placeholder="Bill To Date in Nepali..." >
												</div>
											</div>
								
										 	
							   </div>
									
									
									<div class="row">
									
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Bill To Date in Eng&nbsp;<font color="red">*</font></label> 
													<div class="input-group">
															<span class="input-group-addon"><i class="icon-calendar22"></i></span>
															<input type="text" class="form-control" name="rdng_date" id="rdng_date" required="required" readonly="readonly">
													</div>
												</div>
											</div>
										
										
										 	
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Due Date in Nepali&nbsp;<font color="red">*</font></label> 
													<input type="text" name="due_date_nep" id="due_date_nep" class="form-control nepali-calendar" required="required"
														placeholder="Due Date in Nepali..." >
												</div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>Due Date in Eng&nbsp;<font color="red">*</font></label> 
													<div class="input-group">
															<span class="input-group-addon"><i class="icon-calendar22"></i></span>
															
															 <input type="text" class="form-control" name="due_date" id="due_date" required="required" readonly="readonly">
													</div>
												</div>
											</div>
										
										
										<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
										
									</div>
									
									
								</fieldset>	
								<div class="text-right">
									<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20"><span class="ladda-label">Submit</span></button>
								</div>
							</form>
							
						</div>			
						</div>
						
						
						
<script>


function getEngDate(nepalidate,value){
	
	var date_nep=nepalidate;
	
	$.ajax({
		  type: "GET",
		  url: "./billing/onChangeNepaliDate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
			 date_nep:date_nep,
			  
		  },
		  
		  success: function(response){
	         
			  if(value==1){
				  $('#rdng_date').val(response);
			  }else{
				  
				    var rdng_date=$("#rdng_date").val();
				  	var startDate = new Date(rdng_date);
				 	var endDate = new Date(response);
				  
				 	if(startDate>=endDate){
				 		alert("Due date Nepali should be greater than Bill To Date in Nepali");
				    	$('#due_date_nep').val("");
				    	$('#due_date').val("");
				    }else{
				     $('#due_date').val(response);
				    }
			  }
		  }
		  
		  
		  
		  
		});
	
}

function updateDueDate(){
	
	
	/* var monthyear=$("#monthyear").val();
	var wardNo=$("#wardNo").val();
	var rdng_date=$("#rdng_date").val();
	var rdng_date_nep=$("#rdng_date_nep").val();
	var due_date=$("#due_date").val();
	var due_date_nep=$("#due_date_nep").val(); */
	
/* 	if(monthyear!="" && wardNo!="" && rdng_date!="" && rdng_date_nep!="" && due_date!="" && due_date_nep!=""){
	
	 $.ajax({
		  type: "GET",
		  url: "./billing/dueDateUpdate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
		     monthyear:monthyear,
		     wardNo:wardNo,
			 rdng_date:rdng_date,
			 rdng_date_nep:rdng_date_nep,
			 due_date:due_date,
			 due_date_nep:due_date_nep
			  
		  },
		  
		  success: function(response){
	            
			   swal({
	                title: response,
	                text: "",
	                confirmButtonColor: "#2196F3",
	            });
			   
	           window.location.reload();
			  
		  }
		}); 
	}*/
	
}

</script>
						
<style>
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}
</style>