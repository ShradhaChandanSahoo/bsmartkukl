<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script>
	$(document).ready(function() {

		$('#billingObservationScreen').show();
		$('#billingObservation').addClass('active');

		var activeMod = "${activeModulesNew}";
		var module = "Billing Observations";
		var check = activeMod.indexOf(module) > -1;

		if (check) {
		} else {
			window.location.href = "./accessDenied";
		}

	});
</script>

<style>
.form-horizontal .form-group {
	margin-left: 0px !important;
	margin-right: 0px !important;
}

select {
	width: 100%;
	border: 1px solid #DDD;
	border-radius: 3px;
	height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
}

.datatable-header, .datatable-footer {
	padding: 8px 0px 0 0px;
}
</style>


<script type="text/javascript">
	function observationRecordById(id) {
		document.getElementById('updateOption').style.display = 'block';
		document.getElementById('addOption').style.display = 'none';
		$
				.ajax({
					type : "GET",
					url : "./getObserevationDataForEditing/" + id,
					dataType : "json",
					cache : false,
					async : false,
					success : function(response) {
						$("#id").val(id);
						/*  alert(response[0].observationName); */
						document.getElementById('observationName1').value = response[0].observationName;
					}
				});
		$('#' + id).attr("data-toggle", "modal");
		$('#' + id).attr("data-target", "#stack1");

	}

	function finalSubmit(param) {
		
		var desig="${userbdesignation}";
		
		if (document.getElementById("observationName1").value.trim() == ""
				|| document.getElementById("observationName1").value.trim() == null) {
			swal({
				title : "Please Enter Observation Name",
				text : "",
				confirmButtonColor : "#2196F3",
			});

			return false;
		}
		
		if(desig=="Developer"){
			if (param == '0') {

				$("#observationDetailsId")
						.attr("action", "./addObservationDetails").submit();

			} else if (param == '1') {
				$("#observationDetailsId").attr("action",
						"./updateObservationDetails").submit();
			}
		}else {
			swal({
		    	  title: "Sorry!!<br> You are not <span style='color:red;font-weight: bold;'>Authorised</span> to Add!!",
		    	  text: "",
		    	  type: 'warning',
		    	  html:true,
		    	});
			return false;
		}

	}
</script>

<div class="panel panel-flat">
	<div class="panel-body">

		<c:if test="${not empty msg}">
			<div class="alert alert-success	 alert-bordered">
				<button type="button" class="close" data-dismiss="alert">
					<span>&times;</span><span class="sr-only">Close</span>
				</button>
				<span class="text-semibold" id="alert">${msg}</span>
			</div>
			<c:remove var="msg" scope="session" />
		</c:if>


		<!-- <form class="form-horizontal" action="#">-->
		<form:form action="" modelAttribute="observationDetails"
			commandName="observationDetails" method="POST"
			id="observationDetailsId" role="form"
			class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold"
					style="margin: auto; text-align: center; font-size: 18px;">Observation
					Details</legend>
				<br>


				<div hidden="true" class="col-md-3">
					<div class="form-group">
						<label>ID</label>
						<form:input path="id" id="id" type="text" class="form-control"></form:input>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label>Observation Name&nbsp;<font color="red">*</font></label>
							<form:input path="observationName" id="observationName1"
								type="text" class="form-control"
								placeholder="Observation Name..."></form:input>
						</div>
					</div>

				</div>
				<br>

				<div class="text-center">
					<button type="submit" class="btn btn-primary"
						onclick="return finalSubmit(0)" id="addOption">
						Submit <i class="icon-arrow-right14 position-right"></i>
					</button>
					<button type="submit" class="btn btn-primary"
						onclick="return finalSubmit(1)" id="updateOption"
						style="display: none;">
						Modify <i class="icon-arrow-right14 position-right"></i>
					</button>

				</div>


			</fieldset>
		</form:form>
		<br></br>

		<div id="gridId1">
			<fieldset class="content-group">
				<legend class="text-bold">Observation Details</legend>
				<div class="row">
					<div class="table-responsive">
						<div class="col-md-16">
							<div class="panel-body" style="margin-top: -3px; padding: 6px;">
								<table class="table datatable-basic table-bordered"
									id="tableForm">
									<thead style="width: 100px">

										<tr class="bg-blue">
											<th style="width: 50px">Sl. No.</th>
											<th style="width: 100px">Observation Name</th>
											<th style="width: 40px">Status</th>
											<!-- <th style="width: 40px"></th> -->
											<!-- <th style="width: 50px">Edit</th> -->
										</tr>

									</thead>
									<tbody>
										<c:set var="count" value="1" scope="page" />
										<c:forEach var="app" items="${observe}">
											<tr>
												<td>${count}</td>
												<td>${app.observationName}</td>
												<td>${app.status}</td>
												<%-- <td><a href="#" onclick="observationRecordById(${app.id});"
											id="editData ${app.id}">Edit</a></td> --%>
												<c:set var="count" value="${count+1}" scope="page" />
												
												<%-- <c:choose>
													<c:when test="${app.status=='Inactive'}">
														<td><button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="statusChange('Active','${app.id}');"><span class="ladda-label" >Activate</span></button></td>
													</c:when>
													<c:otherwise>
														<td><button type="button" class="btn bg-orange btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="statusChange('Inactive','${app.id}');"><span class="ladda-label" >DeActivate</span></button></td>
													</c:otherwise>
												</c:choose> --%>
												
										</c:forEach>
									</tbody>
								</table>

							</div>

						</div>
					</div>

				</div>

			</fieldset>
		</div>
	</div>
</div>




<script>

function statusChange(status,id){
	
	
	$.ajax({
		  type: "GET",
		  url: "./changeObservationStatus/"+id+"/"+status,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response){
			 
			  alert(response);
			  window.location.reload(); 
			 
		  }
		});
	
	
}




</script>
