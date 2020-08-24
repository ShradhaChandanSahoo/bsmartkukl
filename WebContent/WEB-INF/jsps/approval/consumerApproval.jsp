<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript" src="resources/layout_3/assets/js/plugins/notifications/bootbox.min.js"></script>
<script>

function selectAll(source) {
	 
	   var flagChecked = 0;
		checkboxes = document.getElementsByName('consumerApprv');
		for(var i =0;i<checkboxes.length;i++)
			{
			checkboxes[i].checked = source.checked;
			if(checkboxes[i].checked)
			 {
				flagChecked = 1;
			}
			}
		
		 if(flagChecked==0)
		{
			$('#consumerApprv span:first-child').removeClass("checked");
		}
		else{
			$('#consumerApprv span:first-child').addClass("checked");
		} 
		
		
	}
	
function docketDetailsPopUp(docketNo) {

	bootbox.confirm("Are You sure Approve.", function(result) {
	if(result){
		
	if(docketNo=='CHECKBOXYES')//This is for Assign Complaint CheckBox
	{
var checkboxes = document.getElementsByName('consumerApprv');
var checks = "";
for(var i =0;i<checkboxes.length;i++)
{
	
	if(checkboxes[i].checked)
	 {
		checks = checks+checkboxes[i].value+",";
		
	}
	}
if(checks == "")
	{
	bootbox.alert("Please Select the consumer no to approve ...");
		return false;
	}
checks = checks.substring(0,checks.length-1);

docketNo=checks;

      $.ajax({
	  type: "GET",
	  url: "./approveConsumerDetails",
	  dataType: "text",
	  data:{
		  masterId:checks
	  },
	  async : false,
	  cache:false,
	  success: function(response){
		 //alert(response);
		                     swal({
				             title: response,
				             text: "",
				             confirmButtonColor: "#2196F3",
				               });
		  location.reload();
		  return false;
	  }
	});

	}
}   });
	

	
}

//------------------------------------
function meterDetailsPopUp(docketNo) {

	bootbox.confirm("Are You sure to Reject.", function(result) {
	if(result){
		
	if(docketNo=='CHECKBOXYES')//This is for Assign Complaint CheckBox
	{
var checkboxes = document.getElementsByName('consumerApprv');
var checks = "";
for(var i =0;i<checkboxes.length;i++)
{
	
	if(checkboxes[i].checked)
	 {
		checks = checks+checkboxes[i].value+",";
		
	}
	}
if(checks == "")
	{
	bootbox.alert("Please Select the consumer no to reject ...");
		return false;
	}
checks = checks.substring(0,checks.length-1);

docketNo=checks;


      $.ajax({
	  type: "GET",
	  url: "./rejectConsumerDetails",
	  dataType: "text",
	  data:{
		  masterId:checks
	  },
	  async : false,
	  cache:false,
	  success: function(response){
		alert(response);
		                   /* swal({
				             title: response,
				             text: "",
				             confirmButtonColor: "#2196F3",
				               }); */
		  location.reload();
		  return false;
	  }
	});

	}
}   });
	

	
}

</script>


				<div class="panel panel-flat">
					<div class="panel-body">
							<fieldset class="content-group" > 
							
							
							
							
							
							<legend class="text-bold">Customer Approval
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="submit" class="btn btn-success btn-ladda"  data-style="expand-right" data-spinner-size="20"  onclick="return docketDetailsPopUp('CHECKBOXYES')"><span class="ladda-label" >Approve</span></button>
							    <button type="submit" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return meterDetailsPopUp('CHECKBOXYES')"><span class="ladda-label" >Reject</span></button> 
								
								</legend>
							
							
							
							
							<!-- <legend class="text-bold">Customer Approval</legend>
							<button type="submit" onclick="return docketDetailsPopUp('CHECKBOXYES');" id="modifyConsumerId" class="btn btn-primary" ><i class="glyphicon glyphicon-user"></i> &nbsp;&nbsp;Approve<i class="icon-arrow-right14 position-right"></i></button> --><br>
							<div class="table-responsive">
								<table class="table datatable-basic table-bordered">
									<thead>
										<tr>
										<th> <input type="checkbox" id="selectall"  onClick="selectAll(this)" /></th>
											<th>Connection No</th>
											<th style="min-width: 200px;">Name in Eng</th>
											<th>Name in Nep</th>
											<th>Connection category</th>
											<th>Connection Type</th>
											<th>Remarks</th>
										</tr>
									</thead>
					
									<tbody>
										<c:forEach var="app" items="${masterListApproval}">
											<tr>
											<td><div id="consumerApprv"> <input type="checkbox"    autocomplete="off" placeholder="" name="consumerApprv" id="${app.id}" value="${app.id}" /> </div></td>
												<td>${app.connection_no}</td>
												<td>${app.name_eng}</td>
												<td>${app.name_nep}</td>
												<td>${app.con_category}</td>
												<td>${app.con_type}</td>
												<td>${app.remarks}</td>
												
											</tr>
									</c:forEach>
										
										
									</tbody>
									
									
								</table>
							</div>
						</fieldset>
						
			</div>
			</div>

<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">



		<fieldset class="content-group">
			<legend class="text-bold">
				Customer Approval List(Approved & Rejected)
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get Customer Approval List</span>
				</button>

			</legend>

			<div class="table-responsive">
				<table class="table datatable-basic table-bordered"
					id="consumerTable">
					<thead>
						<tr>
							<th>Sl&nbsp;No</th>
							<th>Connection No</th>
							<th style="min-width: 200px;">Name in Eng</th>
							<th>Name in Nep</th>
							<th>Connection category</th>
							<th>Connection Type</th>
							<th>Status</th>
							<th>Remarks</th>

						</tr>
					</thead>
					<tbody id="tableBodyId">


					</tbody>
				</table>
			</div>
		</fieldset>



	</div>

</div>


<div id="loading" hidden="true" style="text-align: center;">
	<h3 id="loadingText">Loading..... Please wait.</h3>
	<img alt="image" src="./resources/images/loading.gif"
		style="width: 3%; height: 3%;">
</div>






<script>

	$(document).ready(function() {
		
		$('#masterApprovalScreen').show();
		$('#masterApproval').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Master Approval";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		}
		
	});
	
	
	function getPendingList(){
		
		
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getConsumerApproveList",	
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
										"<td>"+count+"</td>";
								  		if(data.connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connection_no+"</td>"; }
								  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
								  		if(data.name_nep == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_nep+"</td>";}
								  		if(data.con_category == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.con_category+"</td>";}
								  		/* if(data.billno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.billno+"</td>";} */
								  		if(data.con_type == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.con_type+"</td>";}
								  		if(data.approve_status == null ){ConnectionData+="<td></td>";}
								  		else
								  		{ 
								  			if(data.approve_status == '1'){ConnectionData+="<td>Approved</td>";}
								  			else{ConnectionData+="<td>Rejected</td>";}
								  		}
								  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
								  		
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId").html(ConnectionData);
				loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
				$("#loading").hide(); 
		
		
		
	}
		 })
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
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}

.datatable-header, .datatable-footer {
    padding: 0px 0px 0 0px;
}

.dataTables_filter {
    /* display: none; */
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}

</style>