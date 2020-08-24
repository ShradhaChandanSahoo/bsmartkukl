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
							 <form:form action="./createUserType" modelAttribute="userTypeEntity" commandName="userTypeEntity" method="POST"
							id="userTypeEntity" role="form" class="form-horizontal form-validate-jquery">
							<fieldset class="content-group"> 
								<legend class="text-bold">User Type</legend>
										
											<div class="col-md-4" hidden="true">
												<div class="form-group">
													<label><span class="text-danger">*</span></label>
					                               <form:input type="number" path="id" id="id" name="id" class="form-control" placeholder="" />
				                                </div>
											</div>
									<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label>User Type <span class="text-danger">*</span></label>
					                                <form:input type="text" path="user_type" id="user_type" required="required" name="user_type" class="form-control" placeholder="User Type..." />
				                                </div>
											</div>
											<div class="col-md-4" id="addId" >
												<div class="form-group" style="padding-top: 27px;">
													<label>&nbsp;</label>
					                                <button type="submit" onclick="return uniqueUserType();" class="btn bg-teal btn-ladda" data-style="expand-right" data-spinner-size="20">
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
									<legend class="text-bold">User Type Details</legend>
										<div class="col-md-12">
					<div class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr class="bg-blue">
									<th>User Type</th>
									<th>Edit/Delete</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="data" items="${userList}">
								<tr>
									<td>${data.user_type}</td>
						
									<td class="text-center"><a href="#" onclick="editUserType('${data.id}','${data.user_type}')">[Edit]</a></td>
									
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



function editUserType(id,usertype){
	$("#id").val(id);
	$("#user_type").val(usertype);
	$("#addId").hide();
	$("#editId").show();
}
function modifyButton(){
	var id=$("#id").val();
	var usertype=$("#user_type").val();
	var datavalue="";
	$.ajax({
		  type: "POST",
		  url: "./uniqueUserTypeForEdit/"+usertype+"/"+id,
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
            title: "User Type Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}else{
	
		$("#userTypeEntity").attr("action", "./editUserType").submit();
	}
	
}

function deleteUserType(id){
	alert(id);
}

function uniqueUserType(){
	var datavalue="";
	var userType=$("#user_type").val();
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserType/"+userType,
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
            title: "User Type Already Exist",
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

</style>