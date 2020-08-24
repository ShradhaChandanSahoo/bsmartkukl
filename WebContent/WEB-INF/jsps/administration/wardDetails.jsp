<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script>
		$(document).ready(function(){   
			$('#administrationModule').addClass('active');
			$('#administrationScreens').show();
			
			var activeMod="${activeModulesNew}";
			var module="Administration";
			var check=activeMod.indexOf(module) > -1;
			
			if(check){
			}else{
				window.location.href="./accessDenied";
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

.datatable-header,.datatable-footer {
	padding: 8px 0px 0 0px;
}
</style>


<script type="text/javascript">


	
	 $("#wardName1").val("");
	 
	


function wardRecordById(id)
{
	document.getElementById('updateOption').style.display='block';
	document.getElementById('addOption').style.display='none';
	$.ajax({
		type : "GET",
		url : "./getWardDataForEditing/"+id,	
		dataType : "json",
		cache : false,
		async : false,
		success : function(response)
		{	
			$("#id").val(id);
		    document.getElementById('wardNo1').value=response.wardNo;
		    document.getElementById('wardName1').value=response.wardName;
		    document.getElementById('muncipal_Id').value=response.muncipal_Id;
		}
	});
}



function finalSubmit(param) {
	
	if(document.getElementById("wardNo1").value == "" || document.getElementById("wardNo1").value == null)
	{
	
		 swal({
             title: "Please Enter Ward Number",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		
		 return false;
	}
	
	if(document.getElementById("wardName1").value.trim() == "" || document.getElementById("wardName1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Ward Name",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	if(param =='0')
	{
		
		$("#wardDetailsId").attr("action", "./addWardDetails").submit(); 
		
	}
   else if(param == '1')
	{
		$("#wardDetailsId").attr("action", "./updateWardDetails").submit();
	} 
	 else
	{
		return false;
	} 

}
</script>

<div class="panel panel-flat">
	<div class="panel-body">

		<%--  <c:if test="${not empty msg}">
				<div class="alert alert-success	 alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">${msg}</span>
				</div>
				<c:remove var="msg" scope="session" />
			</c:if>   --%>
			<c:if test = "${not empty msg}"> 			        
		        <script>		        
		            var msg = "${msg}";
		            swal({
		                title: msg,
		                text: "",
		                confirmButtonColor: "#2196F3",
		            });
		            
		        </script>
		        <c:remove var="msg" scope="session" />
 </c:if>


		<!-- <form class="form-horizontal" action="#">-->
		<form:form action="" modelAttribute="wardDetails"
			commandName="wardDetails" method="POST" id="wardDetailsId"
			role="form" class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold"
					style="margin: auto; text-align: left; font-size: 18px;">Ward
					Details</legend>
				<br>


				<div hidden="true" class="col-md-3">
					<div class="form-group">
						<label>ID</label>
						<form:input path="id" id="id" type="text" class="form-control"></form:input>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Ward No&nbsp;<font color="red">*</font></label>
							<form:input path="wardNo" id="wardNo1" type="text"
								class="form-control" placeholder="Ward Number..."></form:input>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Ward Name&nbsp;<font color="red">*</font></label>
							<form:input path="wardName" id="wardName1" type="text"
								class="form-control" placeholder="Ward Name..."></form:input>
						</div>
					</div>
					
				
												<div class="col-md-4">
													<div class="form-group">
														<label>Municipality Name.</label>
														<form:select class="selectbox input-group" id="muncipal_Id" name="muncipal_Id"
															path="muncipal_Id" data-placeholder="Select Municipality Name...">
															<form:option value="" disabled="disabled">Select Municipality Name</form:option>
															<c:forEach var="data" items="${municipal}">
																<form:option value="${data.id}">${data.muncipalityName}</form:option>
															</c:forEach>
														</form:select>
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
						onclick="return finalSubmit(1)" id="updateOption" style="display: none;">
						Modify <i class="icon-arrow-right14 position-right"></i>
					</button>

				</div>


			</fieldset>
		</form:form>
		<br></br>

		<div id="gridId1">
			<fieldset class="content-group">
				<legend class="text-bold">Ward Details</legend>
				<div class="row">
					<div class="table-responsive">
						<div class="col-md-16">
							<div class="panel-body" style="margin-top: -3px; padding: 6px;">
								<table class="table datatable-basic table-bordered"
									id="tableForm">
									<thead style="width: 100px">

										<tr class="bg-blue">
											<th style="width: 50px">Ward No</th>
											<th style="width: 50px">Ward Name</th>
											<th style="width: 50px">Municipality Name</th>
											<th style="width: 50px">Municipality Description</th>
											<th style="width: 50px">Edit</th>
										</tr>

									</thead>
									<tbody>
										<c:forEach var="app" items="${ward}">
											<tr>
												<td>${app.wardNo}</td>
												<td>${app.wardName}</td>
												
											    <td>${app.muncipalityDetailsEntity.muncipalityName}</td> 
												<td> ${app.muncipalityDetailsEntity.muncipalityDesc}</td>
											 
												<td><a href="#" onclick="return wardRecordById(${app.id});"
													id="editData ${app.id}">Edit</a></td>
											</tr>
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
