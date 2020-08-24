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

		<form action="./boardAdjTransApproveStatus" method="POST">

			<input type="text" id="connectId" name="connectId" hidden="true">
			<input type="text" id="conStatus" name="conStatus" hidden="true">

			<fieldset class="content-group">
				<legend class="text-bold">
					BOARD ADJUSTMENT TRANSACTION APPROVAL
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
								<th>Board Adj Amount</th>
								<th>Penalty Adj Amount</th>
								<th>Month Year</th>
								<th>Requested By</th>
								<th>Requested Date</th>
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
									<td>${app.board_adj_amt}</td>
									<td>${app.penalty_adj_amount}</td>
									<td>${app.to_mon_year}</td>
									<td>${app.submit_by}</td>
									<td>${app.submit_date}</td>
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
				Get Board Adjustment List
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get List</span>
				</button>

			</legend>

			<div class="table-responsive">
			<div class="col-md-12">
				<table class="table datatable-basic table-bordered" id="adjustTable">
					<thead>
						<tr>
							<th>Sl&nbsp;No</th>
							<th>Con&nbsp;No</th>
							<th style="min-width: 180px;">Name</th>
							<th>AreaNo</th>
							<th>Board Adj Amt</th>
							<th>Penalty Adj Amt</th>
							<th>MonthYear</th>
							<th>Submit By</th>
							<th>Submit Date</th>
							<th>Trans Approved By</th>
							<th>Trans Approved Date</th>
							<th>Approved By</th>
							<th>Approved Date</th>
							<th style="min-width: 180px;">Status</th>
							<th style="min-width: 200px;">Remarks</th>

						</tr>
					</thead>
					<tbody id="tableBodyId">


					</tbody>
				</table>
				</div>
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

		$('#boardAdjApproveTransModuleScreen').show();
		$('#boardAdjApproveTransModule').addClass('active');

		var activeMod = "${activeModulesNew}";
		var module = "Board Adjustment Trans Approve";
		var check = activeMod.indexOf(module) > -1;

		if (check) {
		} else {
			window.location.href = "./accessDenied";
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

			if (status == '1') {
				if (confirm("Are You Sure to Approve")) {

				} else {
					return false;
				}
			} else {
				if (confirm("Are You Sure to Reject")) {

				} else {
					return false;
				}
			}

		}
	}
	
	
	function getPendingList() {

		$("#loading").show();
		$
				.ajax({
					type : "GET",
					url : "./getBoardTransList",
					dataType : "json",
					cache : false,
					async : false,
					success : function(response) {
						if (response.length == 0) {
							$("#tableBodyId").empty();
							swal({
								title : "No Data Found.",
								text : "",
								confirmButtonColor : "#2196F3",
							});
							$("#loading").hide();
							return false;
						}

						var ConnectionData = "";

						var count;
						for (var i = 0; i < response.length; i++) {
							data = response[i];
							count = i + 1;
							ConnectionData += "<tr>" + "<td>" + count + "</td>";
							if(data.connection_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.connection_no+"</td>"; }
					  		if(data.name_eng == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.name_eng+"</td>"; }
					  		if(data.area_no == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.area_no+"</td>";}
					  		if(data.board_adj == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.board_adj+"</td>";}
					  		if(data.penalty_adj_amount == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.penalty_adj_amount+"</td>";}
					  		if(data.to_mon_year == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.to_mon_year+"</td>";}
					  		if(data.submit_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_by+"</td>";}
					  		if(data.submit_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.submit_date+"</td>";}
					  		if(data.trans_approve == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.trans_approve+"</td>";}
					  		if(data.trans_approve_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.trans_approve_date+"</td>";}
					  		if(data.approve_by == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_by+"</td>";}
					  		if(data.approve_date == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_date+"</td>";}
					  		if(data.approve_status == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.approve_status+"</td>";}
					  		if(data.remarks == null ){ConnectionData+="<td></td>";}else{ ConnectionData+="<td>"+data.remarks+"</td>";}
					  		
					  		ConnectionData+="</tr>";	
						}
						$("#tableBodyId").html(ConnectionData);
						loadSearchFilter1('adjustTable', ConnectionData,
								'tableBodyId');
						$("#loading").hide();

					}
				})
	}
	
	function loadSearchFilter1(param, tableData, temp) {
		$('#' + param).dataTable().fnClearTable();
		$('#' + param).dataTable().fnDestroy();
		$('#' + temp).html(tableData);
		$('#' + param).dataTable();

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


