
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
	<div class="panel-body">
		<form:form action="./createConTypeApprove" role="form" modelAttribute="conTypeChangeEntity" commandName="conTypeChangeEntity" method="POST"
			id="conTypeChangeEntity">

			<fieldset class="content-group">

				<legend class="text-bold"
					style="margin: auto; text-align: center; font-size: 18px;">Connection
					Type OR Connection Status Change Request</legend>
				<br>

				<div class="row">
					<div class="col-md-2">
						<div class="form-group">
							<label>Connection No &nbsp;<font color="red">*</font></label>
							<form:input type="text" class="form-control" name="connection_no"
								path="connection_no" id="connection_no"
								placeholder="Connection No..."
								onchange="getConnectionDetails(this.value)"
								onkeyup="convertToUpperCase();"></form:input>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Name</label>
							<form:input type="text" path="name_eng" class="form-control"
								name="name_eng" id="name_eng" readonly="true" placeholder="Name"></form:input>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Connection Category</label> <input type="text"
								class="form-control" name="con_category" id="con_category"
								readonly="readonly" placeholder="Connection Category...">
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group">
							<label>Area No</label>
							<form:input type="text" class="form-control" name="area_no"
								path="area_no" id="area_no" readonly="true"
								placeholder="Area No..."></form:input>
						</div>
					</div>
					<%-- <div class="col-md-2">
						<div class="form-group">
							<label>Area No</label>
							<form:input type="text" class="form-control" name="new_area_no"
								path="new_area_no" id="new_area_no" readonly="true"
								placeholder="Area No..."></form:input>
						</div>
					</div> --%>
					
					<div class="col-md-2">
						<div class="form-group">
							<label>Effected Date(Nep)&nbsp;<font color="red">*</font></label>
							<input type="text" id="efected_date_nep" name="efected_date_nep"
								class="form-control nepali-calendar" placeholder="Effected Date">
						</div>

					</div>
					
					
					<div class="col-md-12" id="newAreaDiv" hidden="true">
						<div class="col-md-2">
						<div class="form-group">
							<label>New Ward Number</label><select data-placeholder="Select" class="select" id="wardNo" name="wardNo" onchange="getAreaNo()">
							    <option value="" data-icon="icon-git-branch">Select</option>
								
								<c:forEach items="${wardNoList}" var="ward">
								<option value="${ward.wardNo}">${ward.wardNo}</option>
							   </c:forEach>
								
							</select>
						</div>
					  </div>
					  <div class="col-md-2">
						<div class="form-group">
							<label>New Reading Day</label><select data-placeholder="Select" class="select" id="reading_day" name="reading_day" onchange="getAreaNo()">
							    <option value="" data-icon="icon-git-branch">Select</option>
								
								<c:forEach items="${readingDayList}" var="rday">
								<%-- <c:if test="${rday.readingDay} < 10 ">
								<option value="${rday.readingDay}">0${rday.readingDay}</option></c:if> --%>
								
								<c:choose>
        <c:when test="${rday.readingDay < 10}">
            <option value="${rday.readingDay}">0${rday.readingDay}</option>
        </c:when>

        <c:otherwise>
            <option value="${rday.readingDay}">${rday.readingDay}</option>
        </c:otherwise>
    </c:choose>
							   </c:forEach>
								
							</select>
						</div>
					  </div>
					  <div class="col-md-2">
						<div class="form-group">
							<label>New Sequence No</label>
							<input type="text" class="form-control" name="seq_no"
								id="seq_no" placeholder="Sequence No..." onchange="getAreaNo()"></input>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label>New Area No</label>
							<form:input type="text" class="form-control" name="new_area_no"
								path="new_area_no" id="new_area_no" readonly="true"
								placeholder="Area No..."></form:input>
						</div>
					
					</div>
					</div>
					<div class="col-md-12">
					
						
						<%-- <div class="col-md-2">
							<div class="form-group">
								<label>Existing Type</label>
								<form:input type="text" class="form-control"
									name="exist_con_type" id="exist_con_type" readonly="true"
									path="exist_con_type" placeholder="Connection Type..."></form:input>
							</div>
						</div>

						<div class="form-group col-md-2">
							<label>New Connection Type <font color="red">*</font></label>
							<form:select class="selectbox input-group" path="new_con_type"
								name="new_con_type" id="new_con_type">
								<option value="" data-icon="icon-git-branch">Select</option>
								<option value="Metered" data-icon="icon-git-branch">Metered</option>
								<option value="Unmetered" data-icon="icon-git-commit">Unmetered</option>
							</form:select>
						</div>

						<div class="col-md-2">
							<div class="form-group">
								<label>Area No</label>
								<form:input type="text" class="form-control" name="area_no"
									path="area_no" id="area_no" readonly="true"
									placeholder="Area No..."></form:input>
							</div>
						</div>


						<div class="col-md-2">
							<div class="form-group">
								<label>Pipe Diameter</label>
								<form:input type="text" class="form-control" name="pipe_size"
									path="pipe_size" id="pipe_size" readonly="true"
									placeholder="Pipe Diameter..."></form:input>
							</div>
						</div>

						<div class="col-md-3">
							<div class="form-group">
								<label>Existing Connection Status</label>
								<form:input type="text" class="form-control"
									path="exist_con_status" name="exist_con_status"
									id="exist_con_status" readonly="true"
									placeholder="Connection Status..."></form:input>
							</div>
						</div>

						<div class="form-group col-md-3">
							<label>New Connection Status <font color="red">*</font></label>
							<form:select class="selectbox input-group" path="new_con_status"
								name="new_con_status" id="new_con_status">
								<option value="" data-icon="icon-git-branch">Select
									Connection Status</option>
								<option value="Normal" data-icon="icon-git-branch">Normal</option>
								<option value="Temporary" data-icon="icon-git-commit">Temporary</option>
								<option value="Defaulter" data-icon="icon-git-commit">Defaulter</option>
								<option value="Permanent" data-icon="icon-git-commit">Permanent</option>
							</form:select>
						</div> --%>


						<table class="table" id="tableForm">
							<tr>
								<td class="col-md-2"><input class="control-info styled"
									type="checkbox" id="connectionTypeCheck"
									onclick="disableOptions(this.id,'new_con_type')">&nbsp;&nbsp;Connection
									Type</td>
								<td class="col-md-3">
									<div class="form-group">
										<label>Existing Type</label>
										<form:input type="text" class="form-control"
											name="exist_con_type" id="exist_con_type" readonly="true"
											path="exist_con_type" placeholder="Connection Type..."></form:input>
									</div>
								</td>

								<td class="col-md-3">
									<div class="form-group">
										<label>New Connection Type <font color="red">*</font></label>
										<form:select data-placeholder="Select Connection Type"
											class="select" path="new_con_type" name="new_con_type"
											id="new_con_type" disabled="true">
											<option value="" data-icon="icon-git-branch">Select
												Connection Type</option>
											<option value="Metered" data-icon="icon-git-branch">Metered</option>
											<option value="Unmetered" data-icon="icon-git-commit">Unmetered</option>
										</form:select>
									</div>
								</td>
							</tr>
							<tr>
								<td class="col-md-2"><input class="control-info styled"
									type="checkbox" id="connectionStatusCheck"
									onclick="disableOptions(this.id,'new_con_status')">&nbsp;&nbsp;Connection
									Status</td>
								<td class="col-md-3">
									<div class="form-group">
										<label>Existing Connection Status</label>
										<form:input type="text" class="form-control"
											path="exist_con_status" name="exist_con_status"
											id="exist_con_status" readonly="true"
											placeholder="Connection Status..."></form:input>
									</div>
								</td>
								<td class="col-md-3">
									<div class="form-group">
										<label>New Connection Status <font color="red">*</font></label>
										<form:select data-placeholder="Select Connection Status"
											class="select" path="new_con_status" name="new_con_status"
											id="new_con_status" disabled="true">
											<option value="" data-icon="icon-git-branch">Select
												Connection Status</option>
											<option value="Normal" data-icon="icon-git-branch">Normal</option>
											<option value="Temporary" data-icon="icon-git-commit">Temporary</option>
											<option value="Defaulter" data-icon="icon-git-commit">Defaulter</option>
											<option value="Permanent" data-icon="icon-git-commit">Permanent</option>
										</form:select>
									</div>

								</td>
							</tr>
							<tr>
								<td class="col-md-2"><input class="control-info styled"
									type="checkbox" id="connectionSizeCheck"
									onclick="disableOptions(this.id,'new_pipe_size')">&nbsp;&nbsp;Pipe
									Size</td>
								<td class="col-md-3">
									<div class="form-group">
										<label>Existing Pipe Size</label>
										<form:input type="text" class="form-control" name="pipe_size"
											path="pipe_size" id="pipe_size" readonly="true"
											placeholder="Pipe Diameter..."></form:input>
									</div>
								</td>
								<td class="col-md-3">
									<div class="form-group">
										<label>New Pipe Size <font color="red">*</font></label> 
										<form:select data-placeholder="Select Pipe Size" class="select" disabled="true" name="new_pipe_size" id="new_pipe_size" path="new_pipe_size">
											<option value="" data-icon="icon-git-branch">Select Pipe Size</option>
											<option value="0.5" data-icon="icon-git-branch">0.5</option>
											<option value="0.75" data-icon="icon-git-commit">0.75</option>
											<option value="1" data-icon="icon-git-commit">1</option>
											<option value="1.5" data-icon="icon-git-commit">1.5</option>
											<option value="2" data-icon="icon-git-commit">2</option>
										</form:select>
									</div>
								</td>
							</tr>
						</table>
					</div>

					<div class="col-md-12">
						<div class="form-group">
							<label>Remarks&nbsp;<font color="red">*</font></label>
							<textarea placeholder="Enter your Remarks here in English"
								class="form-control" cols="1" rows="1" name="remarks"
								id="remarks" value="Bill Correction"></textarea>
						</div>
					</div>

				</div>

			</fieldset>

			<div class="text-center">
				<button type="button" class="btn bg-teal btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="clearButton();">
					<span class="ladda-label">Clear</span>
				</button>
				<button type="submit" class="btn bg-teal btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return checkValid();">
					<span class="ladda-label">Submit</span>
				</button>
			</div>

		</form:form>
	</div>
</div>




<div class="panel panel-flat">
	<div class="panel-body" style="overflow: scroll;">



		<fieldset class="content-group">
			<legend class="text-bold">
				Connection Type OR Connection Status Change Request
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-danger btn-ladda"
					data-style="expand-right" data-spinner-size="20"
					onclick="return getPendingList();">
					<span class="ladda-label">Get Pending List</span>
				</button>

			</legend>

			<div class="table-responsive">
				<table class="table datatable-basic table-bordered"
					id="consumerTable">
					<thead>
						<tr>
							<th>Sl&nbsp;No</th>
							<th>Con&nbsp;No</th>
							<th style="min-width: 180px;">Name</th>
							<th>AreaNo</th>
							<th>Pipe(inch)</th>
							<th>Exist Con Type</th>
							<th>New Con Type</th>
							<th>Exist Con Status</th>
							<th>New Con Status</th>
							<th>Requested By</th>
							<th style="min-width: 250px;">Remarks</th>

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
		$('#ummdmodify1').show();
		$('#masterDataModification1').addClass('active');

		var activeMod = "${activeModulesNew}";
		var module = "Master Modification";
		var check = activeMod.indexOf(module) > -1;
		
		
		if (check) {
		} else {
			window.location.href = "./accessDenied";
		}

		$('#efected_date_nep').nepaliDatePicker({
			dateFormat : "%D, %M %d, %y",
			npdMonth : true,
			npdYear : true,

		});
	});

	function convertToUpperCase() {
		$("#connection_no").val($("#connection_no").val().toUpperCase().trim());
	}

	function getPendingList() {

		$("#loading").show();
		$
				.ajax({
					type : "GET",
					url : "./getPendingConnectionsToApprove",
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
							if (data.connection_no == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.connection_no
										+ "</td>";
							}
							if (data.name_eng == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.name_eng
										+ "</td>";
							}
							if (data.area_no == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.area_no
										+ "</td>";
							}
							if (data.pipe_size == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.pipe_size
										+ "</td>";
							}

							if (data.exist_con_type == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.exist_con_type
										+ "</td>";
							}
							if (data.new_con_type == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.new_con_type
										+ "</td>";
							}

							if (data.exist_con_status == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>"
										+ data.exist_con_status + "</td>";
							}
							if (data.new_con_status == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.new_con_status
										+ "</td>";
							}
							if (data.submit_by == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.submit_by
										+ "</td>";
							}
							if (data.remarks == null) {
								ConnectionData += "<td></td>";
							} else {
								ConnectionData += "<td>" + data.remarks
										+ "</td>";
							}

							ConnectionData += "</tr>";
						}
						$("#tableBodyId").html(ConnectionData);
						loadSearchFilter1('consumerTable', ConnectionData,
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

	function clearButton() {

		window.location.href = "./conTypeChangeReq";
	}
	function checkValid() {

		var connectionNo = $("#connection_no").val();
		var new_con_type = $("#new_con_type").val();
		var new_con_status = $("#new_con_status").val();
		var efected_date_nep = $('#efected_date_nep').val();
		var new_pipe_size= $('#new_pipe_size').val();
		var remarks = $('#remarks').val();
		var new_area=$('#new_area_no').val();
		
		if(new_area==""||new_area==null){
			$('#new_area_no').val($('#area_no').val());
			alert($('#new_area_no').val());
		}
		
		if($('#connectionTypeCheck').prop('checked') == false){
			new_con_type=$("#exist_con_type").val();
			$("#new_con_type").val(new_con_type).trigger("change");
		}
		
		if($('#connectionStatusCheck').prop('checked') == false){
			new_con_status=$("#exist_con_status").val();
			$("#new_con_status").val(new_con_status).trigger("change");
		}
		
		if($('#connectionSizeCheck').prop('checked') == false){
			new_pipe_size=$("#pipe_size").val();
			$("#new_pipe_size").val(new_pipe_size).trigger("change");
		}
		
		if (connectionNo == "" || connectionNo == null) {
			alert("Please Enter Connection No");
			return false;
		} else if (new_con_type == "" || new_con_type == null) {
			alert("Please Select  New Connection Type");
			return false;
		} else if (new_con_status == "" || new_con_status == null) {
			alert("Please Select New Connection Status");
			return false;
		} else if (efected_date_nep == "" || efected_date_nep == null) {
			alert("Please Select Effected Date Nepali");
			return false;
		} else if (new_pipe_size == "" || new_pipe_size == null) {
			alert("Please Select Pipe Size");
			return false;
		} else if (remarks == "" || remarks == null) {
			alert("Please enter remarks");
			return false;
		}  else {
			
			if (confirm("Are you sure to Send Application for Approval?")) {
				$('#new_con_type').prop('disabled', false);
				$('#new_con_status').prop('disabled', false);
				$('#new_pipe_size').prop('disabled', false);
				//$("#new_con_type").val(new_con_type).trigger("change");
				//$("#new_con_status").val(new_con_status).trigger("change");
				//$('#new_pipe_size').val(new_pipe_size).trigger("change");
				return true;
			} else {
				return false;
			}

		}
	}

	function getConnectionDetails(connectionno) {

		$.ajax({
			type : "GET",
			url : "./getConsumerMasterDetails1/" + connectionno,
			dataType : "json",
			cache : false,
			async : false,
			success : function(response) {
				if (response.length == 0) {
					alert("Entered Connection Number is not available");
					$("#connection_no").val("");
				} else {

					for (var i = 0; i < response.length; i++) {
						data = response[i];
						$('#name_eng').val(data.name_eng);
						$('#con_category').val(data.con_category);
						$('#exist_con_type').val(data.con_type);
						$('#area_no').val(data.area_no);
						$('#new_area_no').val(data.area_no);
						$('#pipe_size').val(data.pipe_size);
						$('#exist_con_status').val(data.con_satatus);
                      
						var conT=data.connection_no; 
						
						if(data.branch==conT.substring(0,4) && conT.length== 10){
							
							if(data.con_satatus == 'Processing in New Connection' || data.con_satatus == 'Permanent'){
								alert("You can Modify the details after New Connection Approval");
								window.location.href="./conTypeChangeReq";
							}
						}
					}

				}

			}
		});

	}

	function disableOptions(checkId, inputId) {
		if ($('#' + checkId).prop('checked') == true) {
			//$('#'+ inputId).prop('readonly', false);
			$("#" + inputId).prop("disabled", false);
			
			/* if($("#new_con_type").prop("disabled")){
				alert('haaa');
			} else {
				alert('naaa');
			} */
			
			/* if($('#connectionTypeCheck').prop('checked')){
				$('#new_area_no').prop('readonly',false);
			} else{
				$('#new_area_no').prop('readonly',true);
			} */
			
		} else {
			var x = document.getElementById(inputId + '').tagName;
			if (x == 'SELECT') {
				$("#" + inputId).val('0');
			} else {
				$("#" + inputId).val('');
			}
			$('#' + inputId).prop('disabled', true);
			
		}
		if($('#connectionTypeCheck').prop('checked')){
			//alert();
			//$('#new_area_no').prop('readonly',false);
			$("#newAreaDiv").show();
			
		} else{
			//$('#new_area_no').prop('readonly',true);
			$("#newAreaDiv").hide();
			$('#new_area_no').val("");
		}
	}
	
	function enableArea(){
		
		//$("#newAreaDiv").show();
		
	}
	
	
	function getAreaNo() {
		var ward=$('#wardNo').val();
		var reading=$('#reading_day').val();
		var seq=$('#seq_no').val();
		if(reading < 10)
			{
			
			var area=ward+"-0"+reading+"-"+seq;
			$('#new_area_no').val(area);
			
			}
		else{
		var area=ward+"-"+reading+"-"+seq;
		$('#new_area_no').val(area);
		}
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

.modal.fade .modal-dialog {
width: 1200px;
}

</style>


