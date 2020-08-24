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
	<div class="panel-body" style="overflow: scroll;">

		<form action="./ownerShipApproveStatus" method="POST">

			<input type="text" id="connectId" name="connectId" hidden="true">
			<input type="text" id="conStatus" name="conStatus" hidden="true">

			<fieldset class="content-group">
				<legend class="text-bold">
					Ownership Change Approval
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="submit" class="btn btn-success btn-ladda"
						data-style="expand-right" data-spinner-size="20"
						onclick="return billDetailsPopUp('CHECKBOXYES',1)">
						<span class="ladda-label">Approve</span>
					</button>
					<button type="submit" class="btn btn-danger btn-ladda"
						data-style="expand-right" data-spinner-size="20"
						onclick="return billDetailsPopUp('CHECKBOXYES',2)">
						<span class="ladda-label">Reject</span>
					</button>

				</legend>

				<div class="table-responsive">
					<table class="table datatable-basic table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="control-primary"
									id="selectall" onClick="selectAll(this)"></th>
								<th>Con&nbsp;No</th>
								<th style="min-width: 180px;">Old Customer Name</th>
								<th>Title</th>
								<th>New Customer Name</th>
								<th>Father Name</th>
								<th>Grand Father Name</th>
								<th>CitizenShip No</th>
								<th>Request By</th>
								<th>Request Date</th>
								<th>Effective Date</th>
								<th style="min-width: 250px;">Reason</th>
								<th>Public Notice Published</th>
								<th>Document</th>

							</tr>
						</thead>
						<tbody>

							<c:forEach var="app" items="${masterListApproval}">
								<tr>

									<td><div id="docketNumDiv">
											<input type="checkbox" class="control-primary"
												autocomplete="off" placeholder="" name="connectIdkey"
												id="connectIdkey" value="${app.connection_no}" />
										</div></td>
									<td>${app.connection_no}</td>
									<td>${app.old_name_eng}</td>
									<td>${app.new_consumer_title}</td>
									<td>${app.new_name_eng}</td>
									<td>${app.new_fname_eng}</td>
									<td>${app.new_gfname_eng}</td>
									<td>${app.new_citizenshipno}</td>
									<td>${app.request_by}</td>
									<td>${app.request_date}</td>
									<td>${app.effective_date}</td>
									<td>${app.reason}</td>
									<td>${app.public_notice_published}</td>
									<td><a href="" onclick="return viewDocument(${app.connection_no});">View</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</fieldset>



		</form>
	</div>

</div>


<div class="panel panel-flat">
					<div class="panel-body" style="overflow: scroll;">
							
							
							
							<fieldset class="content-group" > 
								<legend class="text-bold">List of Connections (Ownership Change)
								 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-danger btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="return getPendingList();"><span class="ladda-label" >Get Ownership Change List</span></button>
								
								</legend>
							
							<div class="table-responsive" >
								<table class="table datatable-basic table-bordered" id="consumerTable">
									<thead>
										<tr>
											<th>Sl&nbsp;No</th>
											<th>Con&nbsp;No</th>
											<th style="min-width: 180px;">Old Name</th>
											<th style="min-width: 180px;">New Name</th>
											<th>Citizenship No</th>
											<th>Requested By</th>
											<th>Requested Date</th>
											<th>Effected Date</th>
											<th>Approved By</th>
											<th>Approved Date</th>
											<th style="min-width: 250px;">Status</th>
											<th>Reason</th>
											
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
                         	<h3 id="loadingText">Loading..... Please wait. </h3> 
						 	<img alt="image" src="./resources/images/loading.gif" style="width:3%;height: 3%;">
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

	var billprimaryKeys = "";

	function selectAll(source) {

		var flagChecked = 0;
		checkboxes = document.getElementsByName('connectIdkey');
		for (var i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = source.checked;
			if (checkboxes[i].checked) {
				flagChecked = 1;
			}
		}

		if (flagChecked == 0) {
			$('#docketNumDiv span:first-child').removeClass("checked");
		} else {
			$('#docketNumDiv span:first-child').addClass("checked");
		}

	}

	function billDetailsPopUp(billprimaryKeys, status) {
		if (billprimaryKeys == 'CHECKBOXYES')//This is for Assign Complaint CheckBox
		{
			var checkboxes = document.getElementsByName('connectIdkey');
			var checks = "";
			for (var i = 0; i < checkboxes.length; i++) {

				if (checkboxes[i].checked) {
					checks = checks + checkboxes[i].value + ",";

				}
			}

			if (checks == "") {
				alert("Please Select the Connection Number ...");
				return false;
			}

			checks = checks.substring(0, checks.length - 1);

			billprimaryKeys = checks;

			$('#conStatus').val(status);
			$('#connectId').val(billprimaryKeys);

		}
	}
	
	
	function loadSearchFilter1(param,tableData,temp)
	{
	    $('#'+param).dataTable().fnClearTable();
	    $('#'+param).dataTable().fnDestroy();
	    $('#'+temp).html(tableData);
	    $('#'+param).dataTable();

	} 
	
	
	function getPendingList(){
		
		
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getOwnerShipChangeList",	
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
								  		if(data.old_name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.old_name_eng+"</td>"; }
								  		if(data.new_name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_name_eng+"</td>";}
								  		if(data.new_citizenshipno == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_citizenshipno+"</td>";}
								  		if(data.request_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.request_by+"</td>";}
								  		if( data.request_date == null ){ ConnectionData+="<td>-</td>"; }
										else{ ConnectionData+="<td>"+moment(data.request_date).format('DD-MM-YYYY')+"</td>"; }
								  		if(data.effective_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.effective_date+"</td>";}
								  		if(data.approve_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_by+"</td>";}
								  		if( data.approve_date == null ){ ConnectionData+="<td>-</td>"; }
										else{ ConnectionData+="<td>"+moment(data.approve_date).format('DD-MM-YYYY')+"</td>"; }
								  		if(data.approve_status == null ){ConnectionData+="<td></td>";}
								  		else
								  		{ 
								  			if(data.approve_status == '0'){ConnectionData+="<td>Inprogress</td>";}
								  			else if(data.approve_status == '1'){ConnectionData+="<td>Approved</td>";}
								  			else{ConnectionData+="<td>Rejected</td>";}
								  		}
								  		if( data.reason == null ){ ConnectionData+="<td>-</td>"; }
										else{ ConnectionData+="<td>"+data.reason+"</td>"; }
								  		
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId").html(ConnectionData);
				loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId');
				$("#loading").hide(); 
		
		
		
	}
		 })
	}
	
	function viewDocument(conNo){
		window.open("./ownershipChangeDocument/"+conNo);
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

.dataTables_filter {
    display: none;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}
</style>


