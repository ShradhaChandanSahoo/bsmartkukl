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

		<form action="./conTypeApproveStatus" method="POST">

			<input type="text" id="connectId" name="connectId" hidden="true">
			<input type="text" id="conStatus" name="conStatus" hidden="true">

			<fieldset class="content-group">
				<legend class="text-bold">
					Connection Type OR Connection Status Approval
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
								<th style="min-width: 180px;">Name</th>
								<th>AreaNo</th>
								<th>New AreaNo</th>
								<th>Exist Con Type</th>
								<th>New Con Type</th>
								<th>Exist Con Status</th>
								<th>New Con Status</th>
								<th>Exist Pipe</th>
								<th>New Pipe</th>
								<th>Requested By</th>
								<th style="min-width: 250px;">Remarks</th>

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
									<td>${app.name_eng}</td>
									<td>${app.area_no}</td>
									<td>${app.new_area_no}</td>
									<td>${app.exist_con_type}</td>
									<td>${app.new_con_type}</td>
									<td>${app.exist_con_status}</td>
									<td>${app.new_con_status}</td>
									<td>${app.pipe_size}</td>
									<td>${app.new_pipe_size}</td>
									<td>${app.submit_by}</td>
									<td>${app.remarks}</td>
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



		<fieldset class="content-group">
			<legend class="text-bold">
				Connection Type Approval List(Approved & Rejected)
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get Connection Type Approval List</span>
				</button>

			</legend>

			<div class="table-responsive">
				<table class="table datatable-basic table-bordered"
					id="consumerTable">
					<thead>
						<tr>
							<th></th>
							<th>Con&nbsp;No</th>
							<th  style="min-width: 180px;">Name</th>
							<th>AreaNo</th>
							<th>Exist Type</th>
							<th>New Type</th>
							<th>Exist Status</th>
							<th>New Status</th>
							<th>Exist Pipe</th>
							<th>New Pipe</th>
							<th>Requested By</th>
							<th>Status</th>
							<th>Remarks</th>

						</tr>
					</thead>
					<tbody id="tableBodyId1">


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
	
	
	function getPendingList(){
		
		
		$("#loading").show();
		 $.ajax({
				type : "GET",
				url : "./getConnectionTypeApproveList",	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId1").empty();
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
								  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
								  		if(data.exist_con_type == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.exist_con_type+"</td>";}
								  		if(data.new_con_type == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_con_type+"</td>";}
								  		if(data.exist_con_status == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.exist_con_status+"</td>";}
								  		if(data.new_con_status == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_con_status+"</td>";}
								  		if(data.pipe_size == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.pipe_size+"</td>";}
								  		if(data.new_pipe_size == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.new_pipe_size+"</td>";}
								  		if(data.submit_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_by+"</td>";}
								  		if(data.approve_status == null ){ConnectionData+="<td></td>";}
								  		else
								  		{ 
								  			if(data.approve_status == '1'){ConnectionData+="<td>Approved</td>";}
								  			else{ConnectionData+="<td>Rejected</td>";}
								  		}
								  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
								  		
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId1").html(ConnectionData);
				loadSearchFilter1('consumerTable',ConnectionData,'tableBodyId1');
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
    display: block;
    float: left;
    margin: 0 0 20px 20px;
    position: relative;
}
.dataTables_length > label {
    margin-bottom: 0;
    display: none;
}
</style>


