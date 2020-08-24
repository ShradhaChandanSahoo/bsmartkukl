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
							<form:form action="#" role="form"  modelAttribute="billLedgerEntity" commandName="billLedgerEntity" method="POST" id="billLedgerEntity">
					
							<fieldset class="content-group">
					
								<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">Blank Record</legend>
								
								<legend class="text-bold" >Master Details</legend>
								
								<form:input type="text" name="wardno" path="wardno" id="wardno" hidden="true"></form:input>
								<form:input type="text" name="reading_day" path="reading_day" id="reading_day" hidden="true"></form:input>
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection No &nbsp;<font color="red">*</font></label> <input type="text"
												class="form-control" name="connection_no" id="connection_no" placeholder="Connection No..." onchange="connectionDetails(this.value)" onkeyup="convertToUpperCase();">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Name in English</label> <input type="text" class="form-control" name="name_eng" id="name_eng" readonly="readonly"
												placeholder="Name in Eng...">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Ledger No</label> <input type="text" class="form-control" name="ledgerno" id="ledgerno" readonly="readonly"
												placeholder="Ledger No...">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Folio</label> <input type="text" class="form-control" name="folio" id="folio" readonly="readonly"
												placeholder="Folio...">
										</div>
									</div>
									
								
									
					
					
								</div>
								
								
								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<label>Name in Nepali</label> <input type="text" name="name_nep" id="name_nep" readonly="readonly"
												class="form-control" placeholder="Name in Nepali...">
										</div>
									</div>
					
									<div class="col-md-3">
										<div class="form-group">
											<label>Connection Category</label> <input type="text" class="form-control" name="con_category" id="con_category" readonly="readonly"
												placeholder="Connection Category...">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Connection Type</label> <input type="text" class="form-control" name="con_type" id="con_type" readonly="readonly"
												placeholder="Connection Type...">
										</div>
									</div>
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Pipe Diameter</label> <input type="text" class="form-control" name="pipe_size" id="pipe_size" readonly="readonly"
												placeholder="Pipe Diameter...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Connection Status</label> <input type="text" class="form-control" name="con_satatus" id="con_satatus" readonly="readonly"
												placeholder="Connection Status...">
										</div>
									</div>
								</div>
								
								<legend class="text-bold" >Ledger Details</legend>
								
								
								
								<div class="row">
									
									
									
									 <div class="col-md-3">
										<div class="form-group">
											<label>Reading Date(Nep)&nbsp;<font color="red">*</font></label> 
											<input type="text" id="rdng_date_nep" name="rdng_date_nep" class="form-control nepali-calendar"
												placeholder="Reading Date in Nepali...">
									</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Reading Date(Eng)&nbsp;</label> 
											<div class="input-group">
													<form:input type="text" class="form-control" path="rdng_date" id="rdng_date"  readonly="true"></form:input>
													
											</div>
										</div>
									</div>
									
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Bill Period(Month(s))&nbsp;</label> <form:input type="text" class="form-control" path="bill_period" id="bill_period" readonly="true" value="1"
												placeholder="Bill Period..."></form:input>
										</div>
									</div>
									
									
									<div class="col-md-3">
										<div class="form-group">
											<label>Due Date(Nep)&nbsp;</label> 
											<input type="text" id="due_date_nep" name="due_date_nep" class="form-control nepali-calendar"
												placeholder="Due Date in Nepali...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Due Date(Eng)&nbsp;</label> 
											<div class="input-group">
													
													<form:input type="text" class="form-control" path="due_date" id="due_date" readonly="true"></form:input>
											</div>
										</div>
									</div>
									
									
									
									
					
								</div>
								
								
								
								<div class="row">
								
									
									
								
								   <div class="col-md-2" id="previous_readingdiv">
										<div class="form-group">
											<label>Previous Reading(KL)&nbsp;</label> <input type="text" class="form-control" value="0" name="previous_reading" id="previous_reading"  readonly="readonly"
												placeholder="Previous Reading...">
										</div>
									</div>
									
									
									
									<div class="col-md-2" id="present_readingdiv">
										<div class="form-group">
											<label>Present Reading(KL)&nbsp;</label> <form:input type="text" path="present_reading" value="0" id="present_reading"  readonly="true"
												class="form-control" placeholder="Present Reading..."></form:input>
										</div>
									</div>
									
									
									<div class="col-md-2" id="consumptiondiv">
										<div class="form-group">
											<label>Units Consumed(KL)&nbsp;</label> <input type="text" class="form-control" name="consumption" id="consumption" readonly="readonly" value="0"
												placeholder="Units Consumed...">
										</div>
									</div>
					
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Minimum Charges&nbsp;</label> <input type="text" class="form-control" readonly="readonly" value="0"
												placeholder="Minimum Charges..." name="minimum_charges" id="minimum_charges">
										</div>
									</div>
									
									
									
					
									<div class="col-md-2">
										<div class="form-group">
											<label>Additional Charges&nbsp;</label> <input type="text" class="form-control" name="additional_charges" id="additional_charges" readonly="readonly" value="0"
												placeholder="Additional Charges...">
										</div>
									</div>
									
									
									
									
					
								</div>
								
								<div class="row">
								
								    
								
								  <div class="col-md-2">
										<div class="form-group">
											<label>Water Charges&nbsp;</label> <input type="text" class="form-control" name="water_charges" id="water_charges" readonly="readonly" value="0"
												placeholder="Water Charges...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Sewerage Charges&nbsp;</label> <input type="text" class="form-control" name="sw_charges" id="sw_charges" readonly="readonly" value="0"
												placeholder="Sewage...">
										</div>
									</div>
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Meter Rent&nbsp;</label> <input type="text" class="form-control" name="mtr_rent" id="mtr_rent" readonly="readonly" value="0"
												placeholder="Meter Rent...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group">
											<label>Miscellaneous&nbsp;</label> <input type="text" class="form-control" name="excess_charges" id="excess_charges" readonly="readonly" value="0"
												placeholder="Miscellaneous...">
										</div>
									</div>
									
									
									<div class="col-md-2">
										<div class="form-group"> 
											<label>Arrears&nbsp;</label> <input type="text" class="form-control" value="0" name="arrears" id="arrears"  readonly="readonly"
												placeholder="Arrears...">
										</div>
									</div>
									
									
								     
									
									<div class="col-md-2">
										<div class="form-group">
											<label><b>Net Amount</b>&nbsp;</label> <input type="text" value="0" name="net_amount" id="net_amount" readonly="readonly"
												class="form-control" placeholder="Net Amount...">
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
					
					
				
					
			
					
					
<script>
var billno="";
var address_eng="";

var bill_date_nep="";
var due_date_nep="";

function convertToUpperCase(){
	$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
}








function clearButton(){
	
	window.location.href="./blankRecordBill";
}
function checkValid(){
	
	var connectionNo=$("#connection_no").val();
	var rdng_date_nep=$("#rdng_date_nep").val();

	
	
	 if(connectionNo=="" || connectionNo==null){
			alert("Please enter connection number");
			return false;
     }else if(rdng_date_nep=="" || rdng_date_nep==null){
			
			alert("Please select Reading date Nepali");
			return false;
  	 }else{
    	
    	 if(confirm("Are you sure to Submit the Blank Record?")){
			   $("#billLedgerEntity").attr("target", "");
			   $("#billLedgerEntity").attr("action", "./createblankRecord").submit();
		 }else{
			 return false;
		 }
		    
    }
}



function getEngDate(nepalidate,value){
	
	var date_nep=nepalidate;
	var previous_read_date=$("#previous_read_date").val();
	
	
	$.ajax({
		  type: "GET",
		  url: "./billing/onChangeNepaliDate",
		  dataType: "text",
		  async   : false,
		  data:{
			  
			 date_nep:date_nep,
			  
		  },
		  
		  success: function(response){
			  var currDate = new Date();
			 
			  if(value==1){
				 
				    var startDate = new Date(previous_read_date);
				    var endDate   = new Date(response);
				   
				    
				    if(endDate<=startDate){
				    	alert("Reading date should be Greater than Previous Reading Date");
				    	$('#rdng_date_nep').val("");
				    	$('#rdng_date').val("");
				    	$('#bill_period').val("");
				    	return false;
				    }else if(currDate<endDate){
				    	alert("Reading date Nepali cannot be future date");
				    	$('#rdng_date_nep').val("");
				    	$('#rdng_date').val("");
				    	$('#bill_period').val("");
				    	
				    	return false;
				    }else{
				     	$('#rdng_date').val(response);
				    }
				  
			  }else if(value==2){
				  
				  
				    var rdng_date=$("#rdng_date").val();
				  	var startDate = new Date(rdng_date);
				 	var endDate = new Date(response);
				  
				 	if(startDate>=endDate){
				 		alert("Due date Nepali should be greater than Reading Date");
				    	$('#due_date_nep').val("");
				    	$('#due_date').val("");
				    }else{
				     $('#due_date').val(response);
				    }
				  
				  
				  
			  }else{
				  
				    var startDate = new Date(response);
				    var today = new Date();
				    
				    var dd = today.getDate();
				    var mm = today.getMonth()+1; //January is 0!

				    var yyyy = today.getFullYear();
				    if(dd<10){
				        dd='0'+dd;
				    } 
				    if(mm<10){
				        mm='0'+mm;
				    } 
				    var today1 = mm+'/'+dd+'/'+yyyy;
				    
				    var dd1 = startDate.getDate();
				    var mm1 = startDate.getMonth()+1; //January is 0!

				    var yyyy1 = startDate.getFullYear();
				    if(dd1<10){
				        dd1='0'+dd1;
				    } 
				    if(mm1<10){
				        mm1='0'+mm1;
				    } 
				    var today2 = mm1+'/'+dd1+'/'+yyyy1;
				  
				    if(billno!=null){
					 	if(today2!=today1){
					 		alert("Please select Bill Date as Today Date");
					    	$('#bill_date_nep').val("");
					    	$('#bill_date_nep').val("");
					    }else{
					    	$('#bill_date_nep').val(date_nep);
					    }
				    }else{
				    	
				    	    var startDate = new Date($("#rdng_date").val());
						    var endDate   = new Date(response);
						   
						    
						    if(endDate<startDate){
						    	alert("Bill date should be Greater than or equal to Reading Date");
						    	$('#bill_date_nep').val("");
						    	
						    	return false;
						    }else if(currDate<endDate){
						    	alert("Bill date Nepali cannot be future date");
						    	$('#bill_date_nep').val("");
						    	return false;
						    }else{
						     	$('#bill_date_nep').val(response);
						    }
				    	
				    }
				  
			  }
		  }
		});
	
}


var con_type="";
function connectionDetails(connectionNo){

	var approveCount=0;
	$.ajax({
		  type: "GET",
		  url: "./billing/connectionDetailsBR/"+connectionNo,
		  dataType: "JSON",
		  async       : false,
		  
		  success: function(response){
			 
			 var consumer=response[0];
			 var billAppCount=response[1];
			 
			     $.each(billAppCount, function(index, data){
					approveCount=data.connectionNoCount;
	             });
			 
			 
			 var result="undefined";
			 var check=result.indexOf(consumer) > -1;
			 if(check){
				 alert("Connection No.: "+connectionNo+" does not exists");
				 $('#connection_no').val("");
			 }
			  else if(approveCount>0){
				 alert("Latest Month Bill already Created for Connection No : "+connectionNo);
				 $('#connection_no').val("");
			 }else{
			 
			 $.each(consumer, function(index, data){
				 
				 $('#name_eng').val(data.name_eng);
				 $('#ledgerno').val(data.ledgerno);
				 $('#folio').val(data.folio);
				 $('#con_category').val(data.con_category);
				 $('#con_type').val(data.con_type);
				 $('#pipe_size').val(data.pipe_size);
				 $('#con_satatus').val(data.con_satatus);
				 $('#area_no').val(data.area_no);
				 $('#mr_code').val(data.mtr_reader);
				 $('#name_nep').val(data.name_nep);
				 $('#reading_day').val(data.reading_day);
				 $('#wardno').val(data.ward_no);
				 
				 
				 
				 
				 
				 if(data.address_eng==null){
					 address_eng="";
				 }else{
					 
				    address_eng=data.address_eng;
				 }
				 
				 con_type=data.con_type;
			 
			 });
			 
			 
			}
			  
		  }
		});
}

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
	
	
	
	  $('#rdng_date_nep').nepaliDatePicker({
			 dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var rdngdtnep = $('#rdng_date_nep').val();
			 		getEngDate(rdngdtnep,1);
			 	
			 	}
	  });
	  
	  $('#bill_date_nep').nepaliDatePicker({
			    dateFormat: "%D, %M %d, %y",
				npdMonth: true,
				npdYear: true,
				onChange: function(){
			 		var billdtdtnep = $('#bill_date_nep').val();
			 		getEngDate(billdtdtnep,3);
			 		
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
	  
	  
	  
});

function previousPaymentsPopUp(){
	
	$('#modal_default').show();
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
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.modal.fade .modal-dialog {
width: 1200px;
}

</style>


