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

		<form action="./wrongPostUpdateStatus" method="POST">

			<input type="text" id="connectId" name="connectId" hidden="true">
			<input type="text" id="conStatus" name="conStatus" hidden="true">

			<fieldset class="content-group">
				<legend class="text-bold">
					Wrong Posting Approval
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
								<th><input type="checkbox" class="control-primary" id="selectall" onClick="selectAll(this)"></th>
								<th>From ConNo</th>
								<th style="min-width: 180px;">Name</th>
								<th>AreaNo</th>
								<th>Pipe(inch)</th>
								<th>REC NO</th>
								<th>REC Date</th>
								<th>MonthYear</th>
								<th>Adjustment No</th>
								<th>Adjust Amount</th>
								<th>Adjust Date</th>
								<th>To ConNo</th>
								<th>Requested By</th>
								<th>Requested Date</th>
								<th style="min-width: 250px;">Remarks</th>

							</tr>
						</thead>
						<tbody>

							<c:forEach var="app" items="${wrongPostList}">
								<tr>
									
									<td><div id="docketNumDiv">
									<input type="checkbox" class="control-primary" autocomplete="off" placeholder="" name="connectIdkey" id="connectIdkey" value="${app.connection_no}" />
									</div>
									</td>
									<td>${app.connection_no}</td>
									<td>${app.name_eng}</td>
									<td>${app.area_no}</td>
									<td>${app.pipe_size}</td>
									<td>${app.receiptNo}</td>
									<td>${app.engRdate}</td>
									<td>${app.monthyearnep}</td>
									<td>${app.adjustment_no}</td>
									<td>${app.adjustment_amount}</td>
									<td>${app.adjustment_date}</td>
									<td>${app.to_connection_no}</td>
									<td>${app.prepared_by}</td>
									<td>${app.added_date}</td>
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
				Wrong Posting List(Approved & Rejected)
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get Wrong Posting Approval List</span>
				</button>

			</legend>

			<div class="table-responsive">
				<table class="table datatable-basic table-bordered"
					id="consumerTable1">
					<thead>
						<tr>
							<th></th>
							<th>From ConNo</th>
							<th style="min-width: 180px;">Name</th>
							<th>AreaNo</th>
							<th>Pipe(inch)</th>
							<th>REC NO</th>
							<th>REC Date</th>
							<th>Adjustment No</th>
							<th>Adjust Amount</th>
							<th>Adjust Date</th>
							<th>To ConNo</th>
							<th>Requested By</th>
							<th>Requested Date</th>
							<th style="min-width: 250px;">Remarks</th>
							<th>Status</th>

						</tr>
					</thead>
					<tbody id="tableBodyId2">


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
				url : "./wrongPostApproveList",	
				dataType : "json",
				cache : false,
				async : false,
				success : function(response)
				{	
					if(response.length==0)
					{
						$("#tableBodyId2").empty();
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
								  		if(data.pipe_size == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.pipe_size+"</td>";}
								  		if(data.receiptNo == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.receiptNo+"</td>";}
								  		if(data.engRdate == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.engRdate+"</td>";}
								  		
								  		if(data.adjustment_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adjustment_no+"</td>";}
								  		if(data.adjustment_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adjustment_amount+"</td>";}
								  		if(data.adjustment_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.adjustment_date+"</td>";}
								  		
								  		if(data.to_connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.to_connection_no+"</td>";}
								  		if(data.prepared_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.prepared_by+"</td>";}
								  		if(data.added_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.added_date+"</td>";}
								  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
								  		
								  		if(data.status == null ){ConnectionData+="<td></td>";}
								  		else
								  		{ 
								  			if(data.status == '1'){ConnectionData+="<td>Approved</td>";}
								  			else if(data.status == '0'){ConnectionData+="<td>Pending</td>";}
								  			else{ConnectionData+="<td>Rejected</td>";}
								  		}
								  		
								  		ConnectionData+="</tr>";	
									} 
				$("#tableBodyId2").html(ConnectionData);
				loadSearchFilter1('consumerTable1',ConnectionData,'tableBodyId2');
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


