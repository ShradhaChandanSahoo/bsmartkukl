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
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Cancel Miscellaneous Receipt</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Receipt No<font color="red">*</font></label> 
													<input type="text" name="receiptNo" id="receiptNo"   class="form-control"/>
					</div>
											</div>
										
											<div class="col-md-5">
												<div class="form-group">
													<label>&nbsp;</label>
													<br>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="getReceiptNo();"><span class="ladda-label">Search</span></button>
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
													
													<th width="18%">Receipt No</th>
													<th width="18%">Receipt Date</th>
													<th width="18%">Bill Amount</th>
													<th width="18%">Total Paid Amount</th>
													<th width="18%">Towards</th>
													<th width="18%">Payment Mode</th>
													<th style="min-width: 150px">Action</th>
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
							<div><small><font color="red">* Only Today's Receipt which are paid through New Connection Counter can be Cancelled. </font></small></div>
										<div><small><font color="red">** Only Miscellaneous Receipt which are <b>Not</b> towards any <b>New Connection Charge</b> or <b>New Connection Installation Charge</b>, can be cancelled. </font></small></div>
										<br>
							
							</fieldset>
				</div>
		</div>
		<style>
.form-group {
    margin-bottom: 10px;
    position: relative;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #0DB7A5;
}
.form-horizontal .form-group {
    margin-left: 20px;
    margin-right: 40px;
}
</style>

<script>
function getReceiptNo()
{
  var receiptNo=$('#receiptNo').val();
	
  $.ajax({
 	    type :"get",
        dataType :'json',
 	    url :"./getMisReceiptDetails",
		async:false,
  	    cache:false,
  	    data:{receiptNo : receiptNo},
	    success : function(response)
		  {
	        //alert(response.length);
	        if(response==null || response=="")
		  	{
		  		swal({
		            title: "Receipt NO doesn't exist !",
		            text: "",
		            confirmButtonColor: "#2196F3",
		        });
				return false;
		  	}
		  else{
		  var html="";
		  
		  	
			  var data=response;
			  //alert(moment(data.rdate).format("YYYY-MM-DD"));
			  html+="<tr>"
					+" <td>"+data.connectionNo+"</td>"
					
					+" <td>"+data.receiptNo+"</td>"
					+" <td>"+moment(data.rdate).format("YYYY-MM-DD")+"</td>"
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
		 
		  
		  $("#tableid123").html(html);	
		  $("#observnDetails").show();
		  loadSearchFilter1('tableForm',html,'tableid123');
		  }
	        
	        
	        
	        
	      },
	      error : function(xhr){
	    	  swal({
		            title: "Receipt NO doesn't exist !",
		            text: "",
		            confirmButtonColor: "#2196F3",
		        });
	    	  $("#tableid123").empty();
				return false;
	      }
  });
}

function cancelReceipt(connectionno,receiptno,cancelledremarks){
	if(confirm("Are you sure you want to cancel the receipt? ")){
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
			        });  */
			  }
		}); 
		$('#receiptno').val();
		location.reload();
	}
	
	
}

</script>

