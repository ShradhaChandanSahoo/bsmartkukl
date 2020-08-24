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


 function MunicipalRecordById(id)
{
	document.getElementById('updateOption').style.display='block';
	document.getElementById('addOption').style.display='none';
	$.ajax({
		type : "GET",
		url : "./getMunicipalDataForEditing/"+id,	
		dataType : "json",
		cache : false,
		async : false,
		success : function(response)
		{	
			$("#id").val(id);
		    document.getElementById('muncipalityName').value=response.muncipalityName;
		    document.getElementById('muncipalityDesc').value=response.muncipalityDesc;
		}
	});
}



function finalSubmit(param) {
	
	
	
	if(document.getElementById("muncipalityName").value.trim() == "" || document.getElementById("muncipalityName").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Municipality Name",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	if(param =='0')
	{
		
		$("#muncipalityDetailsId").attr("action", "./addMuncipalityDetails").submit(); 
		
	}
   else if(param == '1')
	{
		$("#muncipalityDetailsId").attr("action", "./updateMunicipalDetails").submit();
	} 
	 else
	{
		return false;
	} 

}
</script>

<div class="panel panel-flat">
	<div class="panel-body">

		 <%-- <c:if test="${not empty msg}">
				<div class="alert alert-success	 alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text-semibold" id="alert">${msg}</span>
				</div>
				<c:remove var="msg" scope="session" />
			</c:if>  --%>
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
		<form:form action="" modelAttribute="muncipalityDetails"
			commandName="muncipalityDetails" method="POST" id="muncipalityDetailsId"
			role="form" class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold"
					style="margin: auto; text-align: left; font-size: 18px;">Municipality
					Details</legend>
				<br>


				<div hidden="true" class="col-md-3">
					<div class="form-group">
						<label>ID</label>
						<form:input path="id" id="id" type="text" class="form-control"></form:input>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label>Municipality Name&nbsp;<font color="red">*</font></label>
							<form:input path="muncipalityName" id="muncipalityName" type="text"
								class="form-control" placeholder="Municipality Name..."></form:input>
						</div>
					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label>Municipality Description&nbsp;</label>
							<form:textarea path="muncipalityDesc" id="muncipalityDesc" type="text"
								class="form-control" placeholder="Municipality Description..."></form:textarea>
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
				<legend class="text-bold">Municipality Details</legend>
				<div class="row">
					<div class="table-responsive">
						<div class="col-md-16">
							<div class="panel-body" style="margin-top: -3px; padding: 6px;">
								<table class="table datatable-basic table-bordered"
									id="tableForm">
									<thead style="width: 100px">

										<tr class="bg-blue">
											<th style="width: 50px">Municipality Name</th>
											<th style="width: 50px">Municipality Description</th>
											<th style="width: 50px">Edit</th>
										</tr>

									</thead>
									<tbody>
										<c:forEach var="app" items="${muncipal}">
											<tr>
												<td>${app.muncipalityName}</td>
												<td>${app.muncipalityDesc}</td>
												<td><a href="#" onclick="return MunicipalRecordById(${app.id});"
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
