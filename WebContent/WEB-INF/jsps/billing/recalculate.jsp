<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<div class="panel panel-flat">
						
<div class="panel-body">
	<form action="#" method="POST" id="recalculateId">

	<fieldset class="content-group">

		<legend class="text-bold" style="margin: auto;text-align: center;font-size: 18px;">ReCalculate Arrears</legend>
		<br>
		
		<div class="row">
			
			<div class="col-md-3">
				<div class="form-group">
					<label>Connection No &nbsp;<font color="red">*</font></label> <input type="text"
						class="form-control" name="connection_no"  id="connection_no" placeholder="Connection No..." onchange="getConnectionDetails(this.value)" onkeyup="convertToUpperCase();">
				</div>
			</div>

			<div class="col-md-3">
				<div class="form-group">
					<label>Name</label> <input type="text" class="form-control" name="name_eng" id="name_eng" readonly="readonly"
						placeholder="Name">
				</div>
			</div>

			<div class="col-md-3">
				<div class="form-group">
					<label>Connection Category</label> <input type="text" class="form-control" name="con_category" id="con_category" readonly="readonly"
						placeholder="Connection Category...">
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label>Connection Type</label> <input type="text" class="form-control" name="con_type" id="con_type" readonly="readonly" 
						placeholder="Connection Type...">
				</div>
			</div>
			
			
		</div>
		
		
		<div class="row">
		
			
			
			
			<div class="col-md-3">
				<div class="form-group">
					<label>Area No</label> <input type="text" class="form-control" name="area_no"  id="area_no" readonly="readonly"
						placeholder="Area No...">
				</div>
			</div>
			
			
			<div class="col-md-3">
				<div class="form-group">
					<label>Pipe Size</label> <input type="text" class="form-control" name="pipe_size"  id="pipe_size" readonly="readonly"
						placeholder="Pipe Diameter...">
				</div>
			</div>
			
			
		</div>
		
	</fieldset>
	
		<div class="text-center">
			<button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="clearButton();"><span class="ladda-label" >Clear</span></button>
			<button type="submit" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return calculateButton();"><span class="ladda-label">ReCalculate</span></button>
			<button type="button" class="btn bg-teal btn-sm" data-toggle="modal" data-target="#modal_default1" id="onshow_callback" onclick='ledgerHistoryPopUp()'>View Ledger</button>
		</div>
		
	
</form>
</div>


<div id="modal_default1" class="modal fade">
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h5 class="modal-title">Bill Ledger History</h5>
		</div>

		<div class="modal-body" style="overflow: scroll;">
			
			<table class="table datatable-basic table-bordered" id="popupPayment1">
				<thead>
					<tr>
						<th></th>
						<th>MY</th>
						<th>CR</th>
						<th>LR</th>
						<th>U</th>
						<th>OB</th>
						<th>WC</th>
						<th>SC</th>
						<th>MR</th>
						<th>NT</th>
						<th style="color: red;">PE</th>
						<th style="color: green;">RB</th>
						<th>Badj</th>
						<th>Padj</th>
						<th>paid</th>
					    <th>CB</th>
						<th>RNo</th>
						<th>RDate</th>
						<th>BillNo</th>
						
					</tr>
				</thead>
				<tbody id="viewPayHistotytbody1">
					
				</tbody>
			</table>
			
			
		</div>

		<div class="modal-footer">
			
		</div>
	</div>
</div>
	</div>
				
				
</div>


<script>


window.onload = function(){   
	$('#arrearsRecalScreen').show();
	$('#arrearsRecal').addClass('active');
	
	var activeMod="${activeModulesNew}";
	var module="Arrears ReCalculation";
	var check=activeMod.indexOf(module) > -1;
	var userName = "${userName}";
	if(check){
		if(userName=='s_admin'){
		} else{
		window.location.href="./accessDenied";
		}
	}else{
		window.location.href="./accessDenied";
	}
	$("#connection_no").focus();
}



function loadSearchFilter1(param,tableData,temp)
{
    $('#'+param).dataTable().fnClearTable();
    $('#'+param).dataTable().fnDestroy();
    $('#'+temp).html(tableData);
    $('#'+param).dataTable();

} 



function ledgerHistoryPopUp(){
	$("#viewPayHistotytbody").empty();
	
	var connectionNo=$("#connection_no").val();
	
	if(connectionNo==null || connectionNo==""){
		
	}else{
	
	var tableData = "";
	$.ajax
	({			
		type : "GET",
		url : "./billing/viewBillLedgertHistory/"+connectionNo,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	  
			
			
			for ( var s = 0, len = response.length; s < len; ++s) {
		              	var obj = response[s];
		              	
		              	tableData += "<tr>"
								    +"<td>"+(s+1)+"</td>" 
								    +"<td>"+obj.monthyearnep+"</td>"
									+"<td>"+obj.cr+"</td>"
									+"<td>"+obj.lr+"</td>"
									+"<td>"+obj.units+"</td>"
									+"<td>"+obj.lastarrears+"</td>"
									+"<td>"+obj.water_charges+"</td>"
									+"<td>"+obj.sw_charges+"</td>"
									+"<td>"+obj.mtr_rent+"</td>"
									+"<td>"+obj.net_amount+"</td>"
									+"<td>"+obj.penalty+"</td>"
									+"<td>"+obj.rebate+"</td>"
									+"<td>"+obj.bill_adj+"</td>"
									+"<td>"+obj.penalty_adj+"</td>"
									+"<td>"+obj.receipt_amount+"</td>"
									+"<td>"+obj.close_balnce+"</td>"
									+"<td>"+obj.receipt_no+"</td>"
									+"<td>"+obj.receipt_date+"</td>"
									+"<td>"+obj.billno+"</td>"

								+"</tr>";
				                
				     }
				$('#viewPayHistotytbody1').append(tableData);
				loadSearchFilter1('popupPayment1',tableData,'viewPayHistotytbody1');
		}
	

	});
}
	
}


function clearButton(){
	
	window.location.href="./recalculate";

}

function getConnectionDetails(connectionno){

$.ajax({
	type : "GET",
	url : "./getConsumerMasterDetails1/"+connectionno,	
	dataType : "json",
	cache : false,
	async : false,
	success : function(response)
	{	
					if(response.length==0)
					{
						alert("Entered Connection Number is not available");
						$("#connection_no").val("");
					}	
					else{
		
						
					   for( var i=0;i<response.length;i++)
						{
		 					data = response[i];
		 					$('#name_eng').val(data.name_eng);
		 					$('#con_category').val(data.con_category);
		 					$('#area_no').val(data.area_no);
		 					$('#pipe_size').val(data.pipe_size);
		 					$('#con_type').val(data.con_satatus);
						} 
						
					}
			
		
}
});

}

	function calculateButton() {
		var userName = "${userName}";
		if (userName == 's_admin') {

			var connectionNo = $("#connection_no").val();

			if (connectionNo == null || connectionNo == "") {
				alert("Please Enter Connection Number");
				return false;
			} else {

				if (confirm("Are You Sure You Want to ReCalculate Arrears")) {
					$.ajax({
						type : "GET",
						url : "./recalculateArrears/" + connectionNo,
						dataType : "text",
						cache : false,
						async : false,
						success : function(response) {
							alert(response);
						}
					});

				} else {
					return false;
				}
			}
		} else {
			alert("You are not authorised to do this operation!!");
			return false;
		}
	}

	function convertToUpperCase() {
		$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
	}
</script>


<style>
.modal.fade .modal-dialog {
width: 1260px;
}
.modal-body {
    position: relative;
    padding: 0px;
}
.datatable-header, .datatable-footer {
    padding: 5px 20px 0 20px;
}

body {
    font-family: "Roboto", Helvetica Neue, Helvetica, Arial, sans-serif;
    font-size: 12px;
    line-height: 1.5384616;
    color: #333333;
}
</style>