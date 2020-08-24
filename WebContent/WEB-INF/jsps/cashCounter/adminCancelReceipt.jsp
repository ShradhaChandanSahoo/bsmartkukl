<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/core/libraries/jquery_ui/interactions.min.js"></script>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/plugins/forms/selects/select2.min.js"></script>

<script type="text/javascript"
	src="./resources/layout_3/assets/js/pages/form_select2.js"></script>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>

<script>
	$(document).ready(function() {
		$('#cashCounterScreen').show();
		$('#cashCounter').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Cash Counter";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		}
	});
</script>


<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
								    
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Cancel Receipt</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Counter No</label> 
													<select class="select" id="counter_no" name="counter_no"
														required="required" data-placeholder="Select Counter No">
															<option value="" disabled="disabled">Select counter No</option>
														<c:forEach var="data" items="${counter}">
																<option value="${data.counterNumber}">${data.counterNumber}</option>
														</c:forEach>
													</select>
					</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label>Date</label>
					                                <div class="input-group">
										<span class="input-group-addon"><i class="icon-calendar3"></i></span>
										<input type="text" class="form-control" name="searchdate" id="searchdate" value="${defaultDate}" readonly="readonly"></input>
									</div>
				                                </div>
											</div>
											
											<div class="col-md-4">
												<div class="form-group">
													<label>&nbsp;</label>
													<br>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="searchObsDetails();"><span class="ladda-label">Search</span></button>
				                                </div>
											</div>
								</div>
							</fieldset>
							<fieldset>
							<div id="observnDetails">
							<br> <legend class="text-bold" >Receipt Details</legend>
								<div class="row">
									<div class="table-responsive">
										<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue" width="100%">
													<th width="18%">Connection No</th>
													<th width="18%">Consumer Name</th>
													<th width="18%">Receipt No</th>
													<th width="18%">Receipt Date</th>
													<th width="18%">Bill Amount</th>
													<th width="18%">Total Paid Amount</th>
													<th width="18%">Towards</th>
													<th width="18%">Payment Mode</th>
													<th width="18%">Action</th>
												</tr>
					
											</thead>
											<tbody id="tableid123">
												 
											</tbody>
										</table>
					
									</div>
					
									</div>
									</div> 
								</div>
								</div> 
							
							
							</fieldset>
				</div>
		</div>
		
<script>
function searchObsDetails()
{
	  if($('#counter_no').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Receipt No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	} 
	 
	  
	//alert('hio');
	 var searchdate=$('#searchdate').val();
	 var counter_no=$('#counter_no').val();
	 
	 $.ajax({
			  type: "GET",
			  url: "./adminSearchReceiptNoNew/"+counter_no,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  data:{searchdate : searchdate},
			  success: function(response)
			  {
				  if(response[0]=="Already Day Closed")
				  	{
				  		swal({
				            title: "You Cannnot Cancel Receipt After Day Close",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  else{
				  var html="";
				  
				  for(var i=0;i<response[1].length;i++){
					  var data=response[1][i];
					  html+="<tr>"
							+" <td>"+data.connectionNo+"</td>"
							+" <td>"+data.name_eng+"</td>"
							+" <td>"+data.receiptNo+"</td>"
							+" <td>"+data.nepalimonthyear+"</td>"
							+" <td>"+data.bill_amount+"</td>"
							+" <td>"+data.amount+"</td>"
							+" <td>"+data.towards+"</td>";
							if(data.payMode=='1'){
								html+=" <td>Cash</td>";
							}else if(data.payMode=='2'){
								html+=" <td>Cheque</td>";
							}else if(data.payMode=='3'){
								html+=" <td>DD</td>";
							}else{
								html+=" <td></td>";
							}
							var cancelledremarks="Not Cancelled";
							if(data.cancelledremarks=="" || data.cancelledremarks==null){
								cancelledremarks="Not Cancelled";
							}else{
								cancelledremarks="Cancelled";
							}
							html+=" <td><a href='#' onclick=\"cancelReceipt('"+data.connectionNo+"','"+data.receiptNo+"','"+cancelledremarks+"')\">[Cancel Receipt]</a></td>"  
							+" </tr>";
				  }
				  
				  $("#tableid123").html(html);	
				  $("#observnDetails").show();
				  loadSearchFilter1('tableForm',html,'tableid123');
				  }     
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
function cancelReceipt(connectionno,receiptno,cancelledremarks){
	if(cancelledremarks=="Not Cancelled"){
		$.ajax({
			  type: "GET",
			  url: "./cancelReceiptNew/"+connectionno+"/"+receiptno,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  success: function(response)
			  {
				  alert(response[0]);
				  /* swal({
			            title: response[0],
			            text: "",
			            confirmButtonColor: "#2196F3",
			        }); */
			  }
		}); 
	}else{
		swal({
            title: "Record With Receipt No: "+receiptno+" Already Cancelled",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	$('#receiptno').val();
	location.reload();
}
</script>					