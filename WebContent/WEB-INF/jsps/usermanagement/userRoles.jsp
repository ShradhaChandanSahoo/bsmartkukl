<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
 <script type="text/javascript" src="./resources/layout_3/assets/js/core/libraries/jquery_ui/interactions.min.js"></script>
	<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/selects/select2.min.js"></script>

	<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_select2.js"></script>
	<script type="text/javascript" src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript" src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>
	
	
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


	</script>
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
							 <form:form action="./createUserRoles" modelAttribute="userRolesEntity" commandName="userRolesEntity" method="POST"
							 id="userRolesEntity" role="form" class="form-horizontal form-validate-jquery">
							<fieldset class="content-group"> 
								<legend class="text-bold">User Modules</legend>
										
									<div class="col-md-4" hidden="true">
												<div class="form-group">
													<label><span class="text-danger">*</span></label>
					                               <form:input type="number" path="id" id="id" name="id" class="form-control" placeholder="" />
				                                </div>
											</div>
									<div class="row">
									
									
									
											<div class="col-md-4">
												<div class="form-group">
													<label>User Type</label>
					                                <form:select path="user_type_id" id="user_type_id" class="select form-control" required="required">
														
														<c:forEach var="data" items="${userTypeData}">
															<form:option value="${data.userTypeId}">${data.userType}</form:option>
														</c:forEach>
										</form:select>
				                                </div>
											</div>
											 <div class="col-md-4">
												<div class="form-group">
													<label>User Modules</label> 
													
									<form:select path="user_roles" id="user_roles" multiple="multiple" required="required" placeholder="Select User Roles" class="form-control">
			                                
			                                <c:forEach var="data" items="${moduleData}">
												<form:option value="${data}">${data}</form:option>
											</c:forEach>
			                                <%-- <form:option value="User Management">User Management</form:option>      
			                                <form:option value="Billing">Billing</form:option>
			                                <form:option value="Cash">Cash Collection</form:option>
			                                <form:option value="Metering">Metering</form:option> --%>
			                          </form:select>
				                                </div>
											</div> 
											
											<div class="col-md-4" id="addId" >
												<div class="form-group" style="padding-top: 27px;">
													<label>&nbsp;</label>
					                                <button type="submit" onclick="return uniqueUserRoleType();" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20">
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
									<legend class="text-bold">User Module Details</legend>
										<div class="col-md-12">
					<div class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr class="bg-blue">
									<th>User Type</th>
									<th>User Modules</th>
									<th>Edit/Delete</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="data" items="${userRoleData}">
								<tr>
									<td>${data.userTypeEntity.user_type}</td>
									<td>${data.user_roles}</td>
									<td><a href="#" onclick="editUserRoles('${data.id}','${data.userTypeEntity.id}','${data.user_roles}')" >[Edit]</a></td>
								</tr>
								</c:forEach>
								
							</tbody>
							
						</table>
						</div>
					</div>
								</fieldset>
					
					
					
					
					</div>
					
					
					
					</div>	
<script>
function modifyButton(){
	var id=$("#id").val();
	var usertypeid=$("#user_type_id").val();
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserRolesForEdit/"+usertypeid+"/"+id,
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
            title: "User Roles Type Already Defined",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	} else{
	
		$("#userRolesEntity").attr("action", "./editUserRoles").submit();
	} 
	
}


function editUserRoles(id,usertypeid,userroles){
	$("#user_type_id").val(usertypeid).trigger("change");
	$("#id").val(id);

	var dataarray=userroles.split(",");
	$("#user_roles").val(dataarray);
	 
	$("#addId").hide();
	$("#editId").show();
}

function uniqueUserRoleType(){
	var datavalue="";
	var userType=$("#user_type_id").val();
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserRoleType/"+userType,
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
            title: "User Role Type Already Defined",
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
.select2-selection--multiple .select2-selection__rendered {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    list-style: none;
    margin: 0;
    padding: 0 0px 0px 0px;
    width: 100%;
}
.form-horizontal .form-group {
    margin-left: -10px;
    margin-right: 0px;
}
.row {
    margin-left: 3px;
    margin-right: -10px;
}
</style>