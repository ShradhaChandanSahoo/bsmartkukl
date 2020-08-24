<script>
var lableDisplay="";
$(document).ready(function(){   
	$('#consumerModule').addClass('active');
	$('#umchild').addClass('active');
	$('#umConsumer').show();
	
	$("#lableDisplay").text("Connection No.");
	changeLabel();
		
}); 

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
#l1 {
   border-color: blue;
} 
/* .topic td
{ 
height: 14px 
}; */
hr {
    display: block;
    height: 1px;
    border: 0;
    margin: 1em 0;
    padding: 0;
    background-color: red;
}

</style>
<div class="panel panel-flat">
						

						<div class="panel-body" id="windowTop">
							<!-- <form class="form-horizontal" action="#">-->
							
							<fieldset class="content-group"> 
								<legend class="text-bold">Customer History</legend>
										
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>Enter <span id="lableDisplay"></span> </label>
					                                <input type="text" id="infoId" class="form-control" placeholder="Customer Info..." >
				                                </div>
											</div>
										</div>
									
									<div class="form-group">
									<label class="radio-inline">
										<input type="radio" id="cNumId" name="radio-inline-left" class="styled" checked="checked" onclick="return changeLabel();">
										Connection No.
									</label>
									<label class="radio-inline">
										<input type="radio" id="nameId" name="radio-inline-left" class="styled" onclick="return changeLabel();">
										Name
									</label>
									<label class="radio-inline">
										<input type="radio" id="wNumId" name="radio-inline-left" class="styled" onclick="return changeLabel();">
										Ward No.
									</label>
									<label class="radio-inline">
										<input type="radio" id="aNumId" name="radio-inline-left" class="styled" onclick="return changeLabel();">
										Area No.
									</label>
									<label class="radio-inline">
										<input type="radio" id="pNumId" name="radio-inline-left" class="styled" onclick="return changeLabel();">
										Phone No.
									</label>
									<label class="radio-inline">
										<input type="radio" id="oldConNumId" name="radio-inline-left" class="styled" onclick="return changeLabel();">
										Old Connection No.
									</label>
								</div>
								<div class="text-center">
									<button type="button" class="btn bg-teal" onclick="return checkForCategoty();">View<i class="icon-arrow-right14 position-right"></i></button>
								</div>
								</fieldset>	
						</div>
						</div>
						
						<div id="loading" hidden="true" style="text-align: center;">
                         	<h3 id="loadingText">Loading..... Please wait. </h3> 
						 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
						</div>
						
				<div class="panel panel-flat" hidden="true" id ="gridId1" >
					<div class="panel-body" style="overflow: scroll;">				
					<fieldset class="content-group"> 
							<div class="col-md-12">
								<div class="panel-body" style="margin-top: -38px;">
								<div class="table-responsive">
									<table class="table datatable-basic table-bordered" style="margin-top: 25px;" id="consumerTable">
										<thead>
											<tr style="background-color:#dbd7d7;">
												<th></th>
												<th>ConNo</th>
												<th>CustomerID</th>
												<th>Name</th>
												<th>Area No.</th>
												<th>Ward No.</th>
												<th>Address</th>
												<th>Old No.</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="tableBodyId">
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
				</div>	
				<div class="panel panel-flat" hidden="true" id="gridId11">
					<div class="panel-body">		<!-- <div id="tableBodyId"></div> -->
					<div id="tableId1"></div>
					</div></div>
					
					<div class="panel panel-flat" hidden="true" id ="LedgerGrid"  tabindex="1">
					<div class="panel-body">				
					 <div  class="table-responsive"> 
						<fieldset class="content-group"> 
						<legend class="text-bold">Ledger Details</legend>
							<div class="col-md-12">
								<div class="panel-body" style="margin-top: -38px;">
								<div class="table-responsive">
									<table class="table datatable-basic table-bordered" style="margin-top: 25px;" id="ledgerTable">
										<thead>
											<!-- <tr style="background-color:#dbd7d7;">
												<th>Sl.No</th>
												<th>Reading Date</th>
												<th>Bill No.</th>
												<th>FR</th>
												<th>IR</th>
												<th>Units(KL)</th>
												<th>Status</th>
												<th>Arrears</th>
												<th>Penality</th>
												<th>Rebate</th>
												<th>Water Charges</th>
												<th>SW Charges</th>
												<th>Service Charges</th>
												<th>Other</th>
												<th>Net Amount</th>
												<th>Due Date</th>
											</tr>  -->
											<tr style="background-color:#dbd7d7;">
												<th></th>
												<th>MonthYear(Nep)</th>
												<th>Bill No.</th>
												<th>Observation</th>
												<th>IR</th>
												<th>FR</th>
												<th>Units(KL)</th>
												
												<th>Water Charges</th>
												<th>SW Charges</th>
												<th>Meter Rent</th>
												<th>MISC</th>
												<th>Arrears</th>
												<th>Net Amount</th>
											
												
												<th>Penalty</th>
												<th>Rebate</th>
												<th>Paid Amount</th>
												
												<!-- <th>Balance</th> -->
												<th>Closing Balance</th>
												<!-- <th>Other</th>  -->
												
												<th>Reading Date</th>
												<th>Due Date</th> 
											</tr>
										</thead>
										<tbody id="ledgerTBodyId">
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</fieldset>
					</div> 
					</div></div>
					<div class="panel panel-flat" hidden="true" id ="PaymentGrid">
					<div class="panel-body">	
					<!-- <div hidden="true" id ="PaymentGrid"> -->
						<fieldset class="content-group"> 
						<legend class="text-bold">Payment Details</legend>
							<div class="col-md-12">
								<div class="panel-body" style="margin-top: -38px;">
								<div class="table-responsive">
									<table class="table datatable-basic table-bordered" style="margin-top: 25px;" id="PaymentTableId">
										<thead>
											<tr style="background-color:#dbd7d7;">
												<th>Receipt Date</th>
												<th>Receipt No.</th>
												<th>Amount</th>
												<th>Advance</th>
												<th>Adv_Reb</th>
												<th>Towards</th>
												<th>Pay Mode</th>
											</tr>
										</thead>
										<tbody id="PaymentTBodyId"> 
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
					</div>
					<div class="panel panel-flat"  hidden="true" id ="mDetailsGrid">
					<div class="panel-body">	
					<!-- <div hidden="true" id ="mDetailsGrid"> -->
						<fieldset class="content-group"> 
						<legend class="text-bold">Meter Details</legend>
							<div class="col-md-12">
								<div class="panel-body" style="margin-top: -38px;">
								<div class="table-responsive">
									<table class="table datatable-basic table-bordered" style="margin-top: 25px;" id="mDetailsTableId">
										<thead>
											<tr style="background-color:#dbd7d7;">
												<th>Meter No</th>
												<th>Meter Make</th>
												<th>IR</th>
												<th>Capacity</th>
												<th>Installation Date</th>
												<th>Connection Date</th>
											</tr>
										</thead>
										<tbody id="mDetailsTBodyId"> 
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
					</div>
					<div class="panel panel-flat" hidden="true" id ="mChangeGrid">
					<div class="panel-body">	
					<!-- <div hidden="true" id ="mChangeGrid"> -->
						<fieldset class="content-group"> 
						<legend class="text-bold">Meter Change Details</legend>
							<div class="col-md-12">
								<div class="panel-body" style="margin-top: -38px;">
								<div class="table-responsive">
									<table class="table datatable-basic table-bordered" style="margin-top: 25px;" id="mChangeTableId">
										<thead>
											<tr style="background-color:#dbd7d7;">
												<th>Ledg No.</th>
												<th>Folio</th>
												<th>FR</th>
												<th>IR</th>
												<th>Old Meter No.</th>
												<th>New Meter No.</th>
												<th>Reason</th>
											</tr>
										</thead>
										<tbody id="mChangeTBodyId"> 
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
						</div>	
								<div class="row" style="text-align: center;">
								<div class="form-group">
									<!-- <b><font  style="font-family:Arial, Helvetica, sans-serif;color: black;" size="2px;"><a href="#"><u>Master Details</u></a></font></b>
				                &nbsp;&nbsp;|&nbsp;&nbsp; -->
									<b><font  style="font-family:Arial, Helvetica, sans-serif;color: black;" size="3px;"><a href="#" onclick="return getLedgerDetails();"><u>Ledger Details</u></a></font></b>
								&nbsp;&nbsp;<span style="font-size: large;">|</span>&nbsp;&nbsp;
									<b><font  style="font-family:Arial, Helvetica, sans-serif;color: black;" size="3px;"><a href="#" onclick="return getPaymentDetails();"><u>Payment Details</u></a></font></b>
								&nbsp;&nbsp;<span style="font-size: large;">|</span>&nbsp;&nbsp;
									<b><font  style="font-family:Arial, Helvetica, sans-serif;color: black;" size="3px;"><a href="#" onclick="return getMeterDetails();"><u>Meter Details</u></a></font></b>
								&nbsp;&nbsp;<span style="font-size: large;">|</span>&nbsp;&nbsp;
									<b><font  style="font-family:Arial, Helvetica, sans-serif;color: black;" size="3px;"><a href="#" onclick="return getMeterChangeDetails();"><u>Meter Change Details</u></a></font></b>
								
								</div>
								</div>
								<div style="text-align: right;">
								<button style="text-align: right;" id="button"><i class="icon-rating3"></i></button>
					</div>	
					
					
					<!-- Basic modal -->
				<div id="modal_default" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h5 class="modal-title"><font color="red"><span id="connectionidspan"></span></font></h5>
							</div>

							<div class="modal-body">
								
								<table class="table datatable-basic table-bordered" id="popupPayment">
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
									<tbody id="viewPayHistotytbody">
										
									</tbody>
								</table>
								
								
							</div>

							<div class="modal-footer">
								
							</div>
						</div>
					</div>
				</div>
				
				
				
<script>

function checkForCategoty()
{
	$("#gridId11").hide();
	$("#LedgerGrid").hide();    	
	$("#PaymentGrid").hide();	
	$("#mDetailsGrid").hide();	
	$("#mChangeGrid").hide();
	if($("#infoId").val()=="")
	{
		swal({
            title: "Please enter ConnectionNo / Name / Ward No. / Area No. / Phone No. / Old Connection No.",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}
	
	if(cNumId.checked)
	{
		var conNum = $("#infoId").val();
			
			$("#loading").show();
			 $.ajax({
					type : "GET",
					url : "./getMasterDataByConnectionNum/"+conNum,	
					dataType : "json",
					cache : false,
					async : false,
					success : function(response)
					{	
						if(response.length==0)
						{
							$("#tableBodyId").empty();
							swal({
					             title: "No Data Found.",
					             text: "",
					             confirmButtonColor: "#2196F3",
					         }); 
							$("#loading").hide(); 
							return false;
						}	
						
						
										var ConnectionData="";
									    var count;
										for( var i=0;i<response.length;i++)
										{
						 					data = response[i];
						 					
						 					count=i+1;
						 					ConnectionData+="<tr>"+
											"<td>"+count+"</td>"+
											"<td>"+ 
									  		"<a href='#' onclick='return getDetailedMasterData(this.id,\""+data.connection_no+"\");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
									  		"</td>"; 
						 					if(data.customer_id == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.customer_id+"</td>"; }
									  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
									  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
									  		if(data.ward_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ward_no+"</td>";}
									  		if(data.address_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.address_eng+"</td>";}
									  		if(data.oldconnectionno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldconnectionno+"</td>";}
									  		
									  		ConnectionData+="<td><a href='#' onclick='return ledgerHistoryPopUp(this.id,\""+data.connection_no+"\",\""+data.customer_id+"\",\""+data.name_eng+"\",\""+data.area_no+"\",\""+data.pipe_size+"\");' data-toggle='modal' data-target='#modal_default' id='editData"+data.id+"'><i title='Click To View Details'></i>Ledger</a></td>";	

									  		ConnectionData+="</tr>";	
										} 
										ConnectionData+="";
										
						
					$("#tableBodyId").html(ConnectionData);
		 			$("#gridId1").show();
		 			$('html, body').animate({ scrollTop: $('#gridId1').offset().top }, 'slow');
		 			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
		 			$("#loading").hide();
		 			
			}});
	}
	else if(nameId.checked)
	{
		var name = $("#infoId").val();
		/* var alphaOnly = /^[a-zA-Z ]*$/;
		var test1 = alphaOnly.test(name);
 		if(test1 == false)
		{
 			swal({
	             title: "No Special Characters/Numbers Are Allowed for Name.",
	             text: "",
	             confirmButtonColor: "#2196F3",
	         }); 
			return false;
		} */	
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getMasterDataByName/"+name,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									
								    var count;
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					
					 					count=i+1;
					 					ConnectionData+="<tr>"+
										"<td>"+count+"</td>"+
										"<td>"+
								  		"<a href='#' onclick='return getDetailedMasterData(this.id,\""+data.connection_no+"\")' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
								  		"</td>"; 
					 					if(data.customer_id == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.customer_id+"</td>"; }
								  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
								  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
								  		if(data.ward_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ward_no+"</td>";}
								  		if(data.address_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.address_eng+"</td>";}
								  		if(data.oldconnectionno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldconnectionno+"</td>";}
								  		ConnectionData+="<td><a href='#' onclick='return ledgerHistoryPopUp(this.id,\""+data.connection_no+"\",\""+data.customer_id+"\",\""+data.name_eng+"\",\""+data.area_no+"\",\""+data.pipe_size+"\");' data-toggle='modal' data-target='#modal_default' id='editData"+data.id+"'><i title='Click To View Details'></i>Ledger</a></td>";
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId").html(ConnectionData);
	 			/* $("#tableBodyId").show(); */
	 			$("#gridId1").show();
	 			$('html, body').animate({ scrollTop: $('#gridId1').offset().top }, 'slow');
	 			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
	 			$("#loading").hide(); 
	 			
		}});
	}
	else if(aNumId.checked)
	{
		var areaNo = $("#infoId").val();
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getMasterDataByAreaNo/"+areaNo,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									
								    var count;
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					
					 					count=i+1;
					 					ConnectionData+="<tr>"+
										"<td>"+count+"</td>"+
										"<td>"+
								  		"<a href='#' onclick='return getDetailedMasterData(this.id,\""+data.connection_no+"\");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
								  		"</td>"; 
					 					if(data.customer_id == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.customer_id+"</td>"; }
								  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
								  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
								  		if(data.ward_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ward_no+"</td>";}
								  		if(data.address_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.address_eng+"</td>";}
								  		if(data.oldconnectionno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldconnectionno+"</td>";}
								  		ConnectionData+="<td><a href='#' onclick='return ledgerHistoryPopUp(this.id,\""+data.connection_no+"\",\""+data.customer_id+"\",\""+data.name_eng+"\",\""+data.area_no+"\",\""+data.pipe_size+"\");' data-toggle='modal' data-target='#modal_default' id='editData"+data.id+"'><i title='Click To View Details'></i>Ledger</a></td>";
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId").html(ConnectionData);
	 			/* $("#tableBodyId").show(); */
	 			$("#gridId1").show();
	 			$('html, body').animate({ scrollTop: $('#gridId1').offset().top }, 'slow');
	 			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
	 			$("#loading").hide(); 
	 			
		}});
	}
	else if(pNumId.checked)
	{
		var phoneNum = $("#infoId").val();
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getMasterDataByPhoneNum/"+phoneNum,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									
								    var count;
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					
					 					count=i+1;
					 					ConnectionData+="<tr>"+
										"<td>"+count+"</td>"+
										"<td>"+
								  		"<a href='#' onclick='return getDetailedMasterData(this.id,\""+data.connection_no+"\");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
								  		"</td>";
					 					if(data.customer_id == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.customer_id+"</td>"; }
								  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
								  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
								  		if(data.ward_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ward_no+"</td>";}
								  		if(data.address_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.address_eng+"</td>";}
								  		if(data.oldconnectionno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldconnectionno+"</td>";}
								  		ConnectionData+="<td><a href='#' onclick='return ledgerHistoryPopUp(this.id,\""+data.connection_no+"\",\""+data.customer_id+"\",\""+data.name_eng+"\",\""+data.area_no+"\",\""+data.pipe_size+"\");' data-toggle='modal' data-target='#modal_default' id='editData"+data.id+"'><i title='Click To View Details'></i>Ledger</a></td>";
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId").html(ConnectionData);
	 			/* $("#tableBodyId").show(); */
	 			$("#gridId1").show();
	 			$('html, body').animate({ scrollTop: $('#gridId1').offset().top }, 'slow');
	 			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
	 			$("#loading").hide(); 
	 			
		}});
	}
	else if(oldConNumId.checked)
	{
		var oldConNum = $("#infoId").val();
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getMasterDataByOldConnectionNum/"+oldConNum,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									
								    var count;
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					
					 					count=i+1;
					 					ConnectionData+="<tr>"+
										"<td>"+count+"</td>"+
										"<td>"+
								  		"<a href='#' onclick='return getDetailedMasterData(this.id,\""+data.connection_no+"\");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
								  		"</td>"; 
					 					if(data.customer_id == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.customer_id+"</td>"; }
								  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
								  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
								  		if(data.ward_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ward_no+"</td>";}
								  		if(data.address_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.address_eng+"</td>";}
								  		if(data.oldconnectionno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldconnectionno+"</td>";}
								  		ConnectionData+="<td><a href='#' onclick='return ledgerHistoryPopUp(this.id,\""+data.connection_no+"\",\""+data.customer_id+"\",\""+data.name_eng+"\",\""+data.area_no+"\",\""+data.pipe_size+"\");' data-toggle='modal' data-target='#modal_default' id='editData"+data.id+"'><i title='Click To View Details'></i>Ledger</a></td>";
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId").html(ConnectionData);
	 			/* $("#tableBodyId").show(); */
	 			$("#gridId1").show();
	 			$('html, body').animate({ scrollTop: $('#gridId1').offset().top }, 'slow');
	 			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
	 			$("#loading").hide(); 
	 			
		}});
	}
	else
	{
		var wardNum = $("#infoId").val();
		 $("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getMasterDataByWardNum/"+wardNum,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									
								    var count;
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					
					 					count=i+1;
					 					ConnectionData+="<tr>"+
										"<td>"+count+"</td>"+
										"<td>"+
								  		"<a href='#' onclick='return getDetailedMasterData(this.id,\""+data.connection_no+"\");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
								  		"</td>"; 
					 					if(data.customer_id == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.customer_id+"</td>"; }
								  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
								  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
								  		if(data.ward_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ward_no+"</td>";}
								  		if(data.address_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.address_eng+"</td>";}
								  		if(data.oldconnectionno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldconnectionno+"</td>";}
								  		ConnectionData+="<td><a href='#' onclick='return ledgerHistoryPopUp(this.id,\""+data.connection_no+"\",\""+data.customer_id+"\",\""+data.name_eng+"\",\""+data.area_no+"\",\""+data.pipe_size+"\");' data-toggle='modal' data-target='#modal_default' id='editData"+data.id+"'><i title='Click To View Details'></i>Ledger</a></td>";
								  		ConnectionData+="</tr>";	
									} 
									
				$("#tableBodyId").html(ConnectionData);
	 			$("#gridId1").show();
	 			$('html, body').animate({ scrollTop: $('#gridId1').offset().top }, 'slow');
	 			loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
	 		    $("#loading").hide(); 
	 			
		}});
	}
}

	var connId="";
 function getDetailedMasterData(id1,id2)
 {
	 connId = id2;
	$("#loading").show(); 
	$("#LedgerGrid").hide();    	
	$("#PaymentGrid").hide();	
	$("#mDetailsGrid").hide();	
	$("#mChangeGrid").hide();	
	$.ajax({
					type : "GET",
					url : "./getConsumerMasterDetails1/"+id2,	
					dataType : "json",
					cache : false,
					async : false,
					success : function(response)
					{	
						if(response.length==0)
						{
							$("#detailedMasterTBody").empty();
							swal({
					             title: "No Data Found.",
					             text: "",
					             confirmButtonColor: "#2196F3",
					         }); 
							$("#loading").hide(); 
							return false;
						}	
						
						var ConnectionData="<div>"+
						"<fieldset class='content-group'>"+ 
						"<legend id='l1'></legend>"+
							"<legend id='l1' class='text-bold' style='font-size: 19px; text-align: center; margin-top:-20px;'>Customer History for the Connection No.:&nbsp;"+id2+"</legend>"+
							"<legend class='text-bold'>Customer Details</legend>"+
							"<div class='col-md-12'>"+
								"<div class='panel-body' style='margin-top: -38px;'>"+
									"<table class='table datatable-basic table-bordered' style='margin-top: 25px;' id='detailedMaster'>"+
										"<thead>"+
											 
										"</thead>"+
										"<tbody id='detailedMasterTBody'>";
										
									   for( var i=0;i<response.length;i++)
										{
						 					data = response[i];
						 					
						 					ConnectionData+="<tr>"+
						 					"<td><b>Name</b></td>";
						 					if( data.name_eng == null){ ConnectionData+="<td>-</td>"; }
						 					else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
						 					ConnectionData+="<td><b>Plot No.</b></td>";
											if( data.plot_no == null){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+data.plot_no+"</td>"; }
											ConnectionData+="</tr>"+
											
											"<tr>"+
						 					"<td><b>Address</b></td>";
						 					if( data.address_eng == null){ ConnectionData+="<td>-</td>"; }
						 					else{ ConnectionData+="<td>"+data.address_eng+"</td>"; }
						 					ConnectionData+="<td><b>Ward No.</b></td>";
						 					if( data.ward_no==null ){ ConnectionData+="<td>-</td>"; }
						 					else{ ConnectionData+="<td>"+data.ward_no+"</td>"; }
						 					ConnectionData+="</tr>"+
											
											"<tr>"+
						 					"<td><b>Area No.</b></td>";
											if( data.area_no == null ){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+data.area_no+"</td>"; }
						 					ConnectionData+="<td><b>Plot Type</b></td>";
											if( data.con_category == null ){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+data.con_category+"</td>"; }
											ConnectionData+="</tr>"+
											
											"<tr>"+
						 					"<td><b>Tariff</b></td>";
											if( data.pipe_size == null ){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+data.pipe_size+"</td>"; }
						 					ConnectionData+="<td><b>Pipe Size</b></td>";
											if( data.pipe_size == null ){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+data.pipe_size+"</td>"; }
											ConnectionData+="</tr>"+
											
											"<tr>"+
						 					"<td><b>Connection Type</b></td>";
											if( data.con_type == null ){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+data.con_type+"</td>"; }
											ConnectionData+="<td><b>Date of Service</b></td>";
											if( data.connc_date_eng == null ){ ConnectionData+="<td>-</td>"; }
											else{ ConnectionData+="<td>"+moment(data.connc_date_eng).format('DD-MM-YYYY')+"</td>"; }
											ConnectionData+="</tr>";
											
											//ConnectionData+="";	
										} 
										
										ConnectionData+="</tbody>"+
										"</table>"+
									"</div>"+
								"</div>"+
						"</fieldset>"+
					"</div>";
						
					$("#tableId1").html(ConnectionData);
		 			$("#gridId11").show();
		 			$('html, body').animate({ scrollTop: $('#gridId11').offset().top }, 'slow');
		 			loadSearchFilter1('detailedMaster',ConnectionData,'detailedMasterTBody');
		 	}
	});
	$("#loading").hide(); 
} 

 function getLedgerDetails()
 {
	 $("#loading").show();
	 $("#PaymentGrid").hide();
	 $("#mDetailsGrid").hide();
	 $("#mChangeGrid").hide();
		 $.ajax({
				type : "GET",
				url : "./getLedgerDataByConnNum/"+connId,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#ledgerTBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									
								    var count;
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					//alert(data.observationEntity.observationName);
					 					count=i+1;
					 					ConnectionData+="<tr>";
										/* "<td>"+
								  		"<a href='#' onclick='return getDetailedMasterData(this.id,"+data.connection_no+");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
								  		"</td>";  */
								  	
							
								  		ConnectionData+="<td >"+count+"</td>"; 
								  		if(data.monthyearnep == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.monthyearnep+"</td>"; }
								  		if(data.billno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.billno+"</td>";}
								  		if(data.mc_status == null)
								  		{
								  			ConnectionData+="<td>Reading</td>";
								  		}
								  		else
								  		{
								  			ConnectionData+="<td>"+data.observationEntity.observationName+"</td>";
								  		}
								  		
								  		if(data.previous_reading == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.previous_reading+"</td>";}
								  		if(data.present_reading == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.present_reading+"</td>";}
								  		if(data.consumption == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.consumption+"</td>";}
								  		
								  		
								  		
								  		if(data.water_charges == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.water_charges+"</td>";}
								  		if(data.sw_charges == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.sw_charges+"</td>";}
								  		if(data.mtr_rent == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.mtr_rent+"</td>";}
								  		
								  		
								  		
								  		/* if(data.observationEntity.observationName == null ){ ConnectionData+="<td>Reading</td>"; }else{ ConnectionData+="<td>"+data.observationEntity.observationName+"</td>";} */
								  		
								  		if(data.other == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.other+"</td>";}
								  		
								  		
								  		if(data.arrears == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.arrears+"</td>";}
								  		if(data.net_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.net_amount+"</td>";}
								  		
								  		/* if(data.status == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.status+"</td>";} */
								  		if(data.penalty == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.penalty+"</td>";}
								  		if(data.rebate == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.rebate+"</td>";}
								  		
								  		if(data.last_paid_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.last_paid_amount+"</td>";}
								  		if(data.close_balance == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.close_balance+"</td>";}
								  		/* if(data.service_charge == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.service_charge+"</td>";} */
								  		/* if(data.other == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.other+"</td>";} */
								  		
								  		if(data.rdng_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+moment(data.rdng_date).format('DD-MM-YYYY')+"</td>"; }
								  		if(data.due_date == null ){ConnectionData+="<td></td>";}
								  		else{
								  			ConnectionData+="<td>"+moment(data.due_date).format('DD-MM-YYYY')+"</td>";}
								  		ConnectionData+="</tr>";	
									} 
				$("#ledgerTBodyId").html(ConnectionData);
	 			$("#LedgerGrid").show();
	 			$('html, body').animate({ scrollTop: $('#LedgerGrid').offset().top }, 'slow');
	 			$("#loading").hide(); 
	 			loadSearchFilter1('ledgerTable',ConnectionData,'ledgerTBodyId');
	 			/* displaySearchAndFilter(); */
		}});
		 
	 
 }
 
 function getPaymentDetails()
 {
	 $("#loading").show();
	 $("#LedgerGrid").hide();
	 $("#mDetailsGrid").hide();
	 $("#mChangeGrid").hide();
	 $.ajax({
			type : "GET",
			url : "./getPaymentDataByConnNum/"+connId,	
			dataType : "json",
			cache : false,
			async : false,
			success : function(response)
			{	
				if(response.length==0)
				{
					$("#PaymentTBodyId").empty();
					swal({
			             title: "No Data Found.",
			             text: "",
			             confirmButtonColor: "#2196F3",
			         }); 
					$("#loading").hide(); 
					return false;
				}	
				
				var ConnectionData="";
								for( var i=0;i<response.length;i++)
								{
				 					data = response[i];
				 					ConnectionData+="<tr>";
									/* "<td>"+
							  		"<a href='#' onclick='return getDetailedMasterData(this.id,"+data.connection_no+");' id='editData"+data.id+"'><i title='Click To View Details'></i>"+data.connection_no+"</a>"+
							  		"</td>";  */
							  		if(data.rdate == null ){ConnectionData+="<td></td>";}else
							  		{
							  			ConnectionData+="<td>"+moment(data.rdate).format("DD-MM-YYYY")+"</td>"; 
							  		} 
							  		
							  		if(data.receiptNo == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.receiptNo+"</td>";}
							  		if(data.amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.amount+"</td>";}
							  		if(data.advance == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.advance+"</td>";}
							  		if(data.advance_rebate == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.advance_rebate+"</td>";}
							  		if(data.towards == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.towards+"</td>";}
							  		if(data.payMode == null ){ConnectionData+="<td></td>";}
							  		else
							  		{ 
							  			if(data.payMode == '1'){ConnectionData+="<td>CASH</td>";}
							  			else{ConnectionData+="<td>CHEQUE</td>";}
							  			/* if(data.payMode == '2'){ConnectionData+="<td>CHEQUE</td>";}
							  			if(data.payMode == '3'){ConnectionData+="<td>DD</td>";}
							  			if(data.payMode == '5'){ConnectionData+="<td>ONLINE PAYMENT</td>";} */
							  		}
							  		/* if(data.status == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.status+"</td>";} */
							  		ConnectionData+="</tr>";	
								} 
			$("#PaymentTBodyId").html(ConnectionData);
 			$("#PaymentGrid").show();
 			$('html, body').animate({ scrollTop: $('#PaymentGrid').offset().top }, 'slow');
 			loadSearchFilter1('PaymentTableId',ConnectionData,'PaymentTBodyId');
 		}
	 });
	 $("#loading").hide(); 
 }
 
 function getMeterDetails()
 {
	$("#loading").show();
	$("#LedgerGrid").hide();
	$("#mChangeGrid").hide();
	$("#PaymentGrid").hide();
		 $.ajax({
				type : "GET",
				url : "./getMeterDataByConnNum/"+connId,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#mDetailsTBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					ConnectionData+="<tr>";
										if(data.meter_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.meter_no+"</td>";}
								  		if(data.meter_make == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.meter_make+"</td>";}
								  		if(data.ir == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ir+"</td>";}
								  		if(data.meter_capacity == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.meter_capacity+"</td>";}
								  		if(data.ins_date_eng == null ){ConnectionData+="<td></td>";}
								  		else{
								  			ConnectionData+="<td>"+moment(data.ins_date_eng).format('DD-MM-YYYY')+"</td>";
								  		} 
								  		if(data.metcon_date_eng == null ){ConnectionData+="<td></td>";}
								  		else{
								  			ConnectionData+="<td>"+moment(data.metcon_date_eng).format('DD-MM-YYYY')+"</td>";
								  		} 
								  		ConnectionData+="</tr>";	
									} 
				$("#mDetailsTBodyId").html(ConnectionData);
	 			$("#mDetailsGrid").show();
	 			loadSearchFilter1('mDetailsTableId',ConnectionData,'mDetailsTBodyId');
	 			$('html, body').animate({ scrollTop: $('#mDetailsGrid').offset().top }, 'slow');
	 			$("#loading").hide(); 
	 			/* displaySearchAndFilter(); */
		}});
	
 }
 
 function getMeterChangeDetails()
 {
	$("#loading").show();
	$("#LedgerGrid").hide();
	$("#PaymentGrid").hide();
	$("#mDetailsGrid").hide();
		 $.ajax({
				type : "GET",
				url : "./getMeterChangeDetailsByConnNum/"+connId,	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#mChangeTBodyId").empty();
						swal({
				             title: "No Data Found.",
				             text: "",
				             confirmButtonColor: "#2196F3",
				         }); 
						$("#loading").hide(); 
						return false;
					}	
					
					var ConnectionData="";
									for( var i=0;i<response.length;i++)
									{
					 					data = response[i];
					 					ConnectionData+="<tr>";
										if(data.ledgno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ledgno+"</td>"; } 
								  		if(data.folio == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.folio+"</td>";}
								  		if(data.fr == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.fr+"</td>";}
								  		if(data.ir == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.ir+"</td>";}
								  		if(data.oldmeterno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.oldmeterno+"</td>";}
								  		if(data.newmeterno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.newmeterno+"</td>";} 
								  		if(data.reason == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.reason+"</td>";} 
								  		ConnectionData+="</tr>";	
									} 
				$("#mChangeTBodyId").html(ConnectionData);
	 			$("#mChangeGrid").show();
	 			loadSearchFilter1('mChangeTableId',ConnectionData,'mChangeTBodyId');
	 			$('html, body').animate({ scrollTop: $('#mChangeGrid').offset().top }, 'slow');
	 			$("#loading").hide(); 
	 			
		}});
 }
 
 
 function changeLabel()
 {
	if(cNumId.checked)
	{
		$("#lableDisplay").text("Connection No.");
	}
	else if(nameId.checked)
	{
		$("#lableDisplay").text("Name");
	}
	else if(aNumId.checked)
	{
		$("#lableDisplay").text("Area No.");
	}
	else if(pNumId.checked)
	{
		$("#lableDisplay").text("Phone No.");
	}
	else if(oldConNumId.checked)
	{
		$("#lableDisplay").text("Old Connection No.");
	}
	else
	{
		$("#lableDisplay").text("Ward No.");
	}
 }
 
 function loadSearchFilter1(param,tableData,temp)
 {
     $('#'+param).dataTable().fnClearTable();
     $('#'+param).dataTable().fnDestroy();
     $('#'+temp).html(tableData);
     $('#'+param).dataTable();

 } 
 
 
 function ledgerHistoryPopUp(ID1,connectionNo,customerid,name,areano,pipesize){
		$("#viewPayHistotytbody").empty();
		
		
		if(connectionNo==null || connectionNo==""){
			
		}else{
		
			$('#connectionidspan').html("Connection No : "+connectionNo+"    &nbsp;&nbsp;&nbsp;   Name : "+name+"   &nbsp;&nbsp;&nbsp; Area No : "+areano+" &nbsp;&nbsp;&nbsp;Customer ID : "+customerid+" &nbsp;&nbsp;  Pipe Size(inch):"+pipesize);
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
					$('#viewPayHistotytbody').append(tableData);
					loadSearchFilter1('popupPayment',tableData,'viewPayHistotytbody');
			}
		

		});
	}
		
	}
 
 
/*  $("#button").on("click", function() {
	    $("body").scrollTop(0);
	}); */
 
 $("#button").click( function() {
	   $('html, body').animate({ scrollTop: $("#windowTop").offset().top }, 'slow');
});
</script>
<style>
.dataTables_length{
margin-left: -52px;
}
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