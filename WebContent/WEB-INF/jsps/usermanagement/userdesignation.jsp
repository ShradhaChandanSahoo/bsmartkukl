<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>


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
							 <form:form action="./createUserDesignation" modelAttribute="userDesignationEntity" commandName="userDesignationEntity" method="POST"
							id="userDesignationEntity" role="form" class="form-horizontal form-validate-jquery">
							<fieldset class="content-group"> 
								<legend class="text-bold">User Designation</legend>
										
											<div class="col-md-4" hidden="true">
												<div class="form-group">
													<label><span class="text-danger">*</span></label>
					                               <form:input type="number" path="id" id="id" name="id" class="form-control" placeholder="" />
				                                </div>
											</div>
									<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>User Designation <span class="text-danger">*</span></label>
					                                <form:input type="text" path="designation" id="designation" required="required" name="designation" class="form-control" placeholder="User Designation..." />
				                                </div>
											</div>
											<div class="col-md-4" id="addId" >
												<div class="form-group" style="padding-top: 27px;">
													<label>&nbsp;</label>
					                                <button type="submit" onclick="return uniqueUserDesignation();" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Add</span></button>
				                                </div>
											</div>
											<div class="col-md-4" hidden="true" id="editId">
												<div class="form-group" style="padding-top: 27px;">
													<label>&nbsp;</label>
					                                <button type="button" hidden="true" onclick="modifyButton();" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Modify</span></button>
				                                </div>
											</div>

										</div>
									
									
								</fieldset>	
								</form:form>
							
						</div>	
						
								</div>
										<div class="panel panel-flat">	
								 
					<div class="panel-body">
					<fieldset class="content-group" > 
									<legend class="text-bold">User Designation Details</legend>
										<div class="col-md-12">
					<div class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr class="bg-blue">
									<th>Designation</th>
									<th>Edit/Delete</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="data" items="${userList}">
								<tr>
									<td>${data.designation}</td>
						
									<td class="text-center"><a href="#" onclick="editUserDesignation('${data.id}','${data.designation}')">[Edit]</a></td>
									
								</tr>
								</c:forEach>
								
							</tbody>
						</table>
						</div>
					</div>
								</fieldset>
					
					
					
					
					</div>
					
					
					
					</div>	
<script type="text/javascript">

window.onload = function() {
	
	$('#usermanagementId').addClass('active');
	$('#umchild').show();
	
	var activeMod="${activeModulesNew}";
	var module="User Management";
	
	var check=activeMod.indexOf(module) > -1;
	
	if(check){
	}else{
		window.location.href="./accessDenied";
	}
}


function editUserDesignation(id,usertype){
	$("#id").val(id);
	$("#designation").val(usertype);
	$("#addId").hide();
	$("#editId").show();
}
function modifyButton(){
	var id=$("#id").val();
	var designation=$("#designation").val();
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserDesignationForEdit/"+designation+"/"+id,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response){
			 
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "User Designation Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}else{
	
		$("#userDesignationEntity").attr("action", "./editUserDesignation").submit();
	}
	
}

function deleteUserType(id){
	alert(id);
}

function uniqueUserDesignation(){
	var datavalue="";
	var designation=$("#designation").val();
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserDesignation/"+designation,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response){
			 
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "User Designation Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}else{
		return true;
	}
	
}			
</script>
<style>

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #0DB7A5;
}
.row {
    margin-left: 3px;
    margin-right: -10px;
}
</style>