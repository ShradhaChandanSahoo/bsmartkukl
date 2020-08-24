<%@include file="/WEB-INF/decorators/taglibs.jsp"%>



<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
								    
							<fieldset class="content-group"> 
								<legend class="text-bold" style="text-align: center;font-size: 18px;">Cancel Receipt</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Receipt No</label>
					                                <input id="receiptno" name="receiptno" type="text" class="form-control" placeholder="Enter Receipt No" onkeyup="convertToUpperCase();"/>
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

function convertToUpperCase(){
	$("#receiptno").val($("#receiptno").val().toUpperCase().trim());
}


function searchObsDetails()
{
	
	var counterno="${counterNo}";
	if($('#receiptno').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Receipt No</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}  
	 var receiptno=$('#receiptno').val();
	$.ajax({
			  type: "GET",
			  url: "./searchReceiptNo/"+receiptno,
			  dataType: "json",
			  cache       : false,
			  async       : false,
			  success: function(response)
			  {
				  if(response[0]=="Receipt Doesn't Exist")
				  	{
				  		swal({
				            title: "Receipt No : "+receiptno+" Does't Exist / Unable to Cancel this Receipt",
				            text: "",
				            confirmButtonColor: "#2196F3",
				        });
						return false;
				  	}
				  else{
				  
				    var cno=response[1].counterno;
					
					if(counterno!=cno){
						alert("Sorry..You cannot Cancel this Receipt.This Receipt is done from Counter No : "+cno);
						return false;
					}else{
					  
					var branchcode="${branchcode}";
					var bcodecheck=branchcode.indexOf("2222") > -1;
					//var bcodecheck1=branchcode.indexOf("1119") > -1;
					//var bcodecheck2=branchcode.indexOf("2222") > -1;
					if(bcodecheck)
					{
						
					}
					
					else{
					  	var recdate =moment(response[1].rdate).format('DD-MM-YYYY');
					  	var tdate =moment(new Date()).format('DD-MM-YYYY');
					  	if(tdate==recdate){
	
					  	}else{
						    alert("Sorry..You cannot Cancel this Receipt.This Receipt is Posted on : "+recdate+". Today Posted Receipts only you can cancel");
							return false;
					  	}
					}
				  
			      var html="";
				  
				  html+="<tr>"
						+" <td>"+response[1].connectionNo+"</td>"
						+" <td>"+response[2].name_eng+"</td>"
						+" <td>"+response[1].receiptNo+"</td>"
						+" <td>"+response[3]+"</td>"
						+" <td>"+response[1].bill_amount+"</td>"
						+" <td>"+response[1].amount+"</td>"
						+" <td>"+response[1].towards+"</td>";
						if(response[1].payMode=='1'){
							html+=" <td>Cash</td>";
						}else if(response[1].payMode=='2'){
							html+=" <td>Cheque</td>";
						}else if(response[1].payMode=='3'){
							html+=" <td>DD</td>";
						}else{
							html+=" <td></td>";
						}
						var cancelledremarks="Not Cancelled";
						if(response[1].cancelledremarks=="" || response[1].cancelledremarks==null){
							cancelledremarks="Not Cancelled";
						}else{
							cancelledremarks="Cancelled";
						}
						html+=" <td><a href='#' onclick=\"cancelReceipt('"+response[1].connectionNo+"','"+response[1].receiptNo+"','"+cancelledremarks+"')\">[Cancel Receipt]</a></td>"  
						//+" <td><a href='#' onclick='editMeterObsDetails("+data.remarks+")'>[Edit]</a></td>"  
						+" </tr>";
				  
				  
				 
				  $("#tableid123").html(html);	
				  $("#observnDetails").show();
				  loadSearchFilter1('tableForm',html,'tableid123');
				  
				}
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
	var branchcode="${branchcode}";
	//var bcodecheck=branchcode.indexOf("1117") > -1;
	//var bcodecheck1=branchcode.indexOf("1119") > -1;
	//var bcodecheck2=branchcode.indexOf("1114") > -1;
	if(branchcode=='2222')
	{
		if(cancelledremarks=="Not Cancelled"){
			
			$.ajax({
				  type: "GET",
				  url: "./cancelReceipt/"+connectionno+"/"+receiptno,
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
	
	else{
		alert("This Option is not available for your branch!");
		return false;
	}
	
}
</script>					