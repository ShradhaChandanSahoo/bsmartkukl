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

function makeLowerCase() {
	var login=$('#user_login_name').val();
	$('#user_login_name').val(login.toLowerCase());
}

	</script>
<c:if test="${not empty msg}">
	<script>
		var msg = "${msg}";
		swal({
			title : msg,
			text : "",
			confirmButtonColor : "#2196F3",
		});
		
	
		
	</script>
</c:if>

<div class="panel panel-flat">
						<div class="panel-body">
							<form:form action="./createUserMaster" modelAttribute="userMaster" commandName="userMaster" method="POST"
							 id="userMasterId" role="form" class="form-horizontal form-validate-jquery">
							<fieldset class="content-group"> 
								<legend class="text-bold">User Creation Master</legend>
									<div class="row">
									<div class="col-md-4" hidden="true">
												<div class="form-group">
													<label><span class="text-danger">*</span></label>
					                               <form:input type="number" path="id" id="id" name="id" class="form-control" placeholder="" />
					                               <form:input type="text" path="status" id="status" name="status" class="form-control" placeholder="" value="" />
					                               
				                                </div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label>User Login Name</label>
					                                <form:input type="text" onkeyup="makeLowerCase()" onchange="return uniqueUser();" path="user_login_name" id="user_login_name" class="form-control" required="required" placeholder="User Login Name" />
				                                </div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label>Name</label>
					                                <form:input type="text" path="user_name" id="user_name" class="form-control" required="required" placeholder="User Name" />
				                                </div>
											</div>
											<div class="col-md-6" id="pass">
												<div class="form-group">
													<label>Password</label>
					                                <form:input type="password" name="password" path="password" id="password" class="form-control" required="required" placeholder="Password" />
				                                </div>
											</div>
											<div class="col-md-6" id="conf_pass">
												<div class="form-group">
													<label>Confirm Password</label>
					                                <input type="password" id="repeat_password" name="repeat_password" class="form-control" required="required" placeholder="Confirm Password" />
				                                </div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label>User Role</label>
					                                <form:select path="user_role_id" id="user_role_id" class="select form-control" required="required">
														
														<form:option value="" selected="selected" disabled="disabled">Select User Role</form:option>
														<c:forEach var="data" items="${userRolesData}">
															<form:option value="${data.userRoleId}">${data.userType}</form:option>
														</c:forEach>
													</form:select>
				                                </div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label>Designation</label>
													<form:select path="designation" id="designation" class="select form-control" required="required">
														
														<form:option value="" selected="selected" disabled="disabled">Select User Role</form:option>
														<c:forEach var="data" items="${userDesignations}">
															<form:option value="${data.id}">${data.designation}</form:option>
														</c:forEach>
													</form:select>
				                                </div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label>Mobile Number</label>
					                                <form:input type="text" path="mobileno" name="mobileno" id="mobileno" required="required" class="form-control" placeholder="Mobile Number"/>
				                                </div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label>Email</label>
					                                <form:input type="text" path="email_id" id="email_id" required="required" class="form-control" placeholder="Email" />
				                                </div>
											</div>
											
											<div class="col-md-12" id="addId" >
												<div class="form-group text-center" >
													<label>&nbsp;</label>
					                                <button type="submit" onclick="return callBeforeSubmit();" class="btn bg-teal btn-ladda" style="width: 200px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Add</span></button>
				                                </div>
											</div>
											<div class="col-md-12" hidden="true" id="editId">
												<div class="form-group text-center">
													<label>&nbsp;</label>
					                                <button type="button" hidden="true" onclick="modifyButton();" class="btn bg-teal btn-ladda" style="width: 200px;" data-style="expand-right" data-spinner-size="20">
													<span class="ladda-label">Modify</span></button>
				                                </div>
											</div> 

										</div>
									
									
								</fieldset>	
								</form:form>
							
						</div>	
						
								</div>
										<div class="panel panel-flat">	
								 
					<div class="panel-body" style="overflow: scroll;">
					<fieldset class="content-group" > 
									<legend class="text-bold">User Details</legend>
										<div class="col-md-12">
					<div class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr class="bg-blue">
									<th>User Login Name</th>
									<th>User Name</th>
									<th>User Role</th>
									<th>Designation</th>
									<th>Mobile Number</th>
									<th>Email</th>
									<th>Status</th>
									<th>Edit</th>
									<th></th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="data" items="${userMasterData}">
								<tr>
									<td>${data.user_login_name}</td>
									<td>${data.user_name}</td>
									<td>${data.userRolesEntity.userTypeEntity.user_type}</td>
									<td>${data.userDesignationEntity.designation}</td>
									<td>${data.mobileno}</td>
									<td>${data.email_id}</td>
									<td>${data.status}</td>
									<td><a href="#" onclick="editUserMaster('${data.id}');">[Edit]</a><%-- <a href="#" onclick="deleteUserMaster('${data.id}');">[Delete]</a> --%></td>
									
									<c:choose>
									
									<c:when test="${data.status=='Inactive'}">
									<td><button type="button" class="btn bg-teal btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="statusChange('Inactive','${data.id}');"><span class="ladda-label" >Activate</span></button></td>
									</c:when>
									<c:otherwise>
									<td><button type="button" class="btn bg-orange btn-ladda"  data-style="expand-right" data-spinner-size="20" onclick="statusChange('Active','${data.id}');"><span class="ladda-label" >DeActivate</span></button></td>
									</c:otherwise>
									</c:choose>
									
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
	var user_login_name=$("#user_login_name").val();
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserMasterForEdit/"+user_login_name+"/"+id,
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
		 callBeforeSubmit();
		$("#userMasterId").attr("action", "./updateUserMaster").submit();
	} 
	
}
function deleteUserMaster(id){
	$("#id").val(id);
	$("#userMasterId").attr("action", "./deleteUserData").submit();
}


function uniqueUser(){
	var datavalue="";
	var user_login_name=$("#user_login_name").val().trim();
	$("#user_login_name").val(user_login_name);
	$.ajax({
		  type: "GET",
		  url: "./uniqueUserName/"+user_login_name,
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
            title: "User Login Name Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		$("#user_login_name").val("");
		return false;
	}else{
		return true;
	}
}	


function statusChange(status,id){
	
	
	$.ajax({
		  type: "GET",
		  url: "./changeUserStatus/"+id+"/"+status,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response){
			 
			  alert(response);
			  window.location.reload();
			 
		  }
		});
	
	
}

function editUserMaster(id){
	$("#user_login_name").prop('readonly', true);
	document.getElementById('password').readOnly = true;
	document.getElementById('repeat_password').readOnly = true;
	$('#pass').hide();
	$('#conf_pass').hide();
	$.ajax({
		  type: "GET",
		  url: "./editUserMaster/"+id,
		  dataType: "JSON",
		  cache       : false,
		  async       : false,
		  success: function(response){
			  $("#id").val(response.id);
			  $("#status").val(response.status);
			  $("#createdBy").val(response.createdBy);
			  $("#createdDate").val(moment(response.createdDate).format('YYYY-MM-DD hh:mm:ss'));
			  $("#user_name").val(response.user_name);
			  $("#user_login_name").val(response.user_login_name);
			  $("#password").val(response.password);
			  $("#repeat_password").val(response.password);
			  $("#user_role_id").val(response.user_role_id).trigger("change");
			  $("#designation").val(response.designation).trigger("change");
			  $("#mobileno").val(response.mobileno);
			  $("#email_id ").val(response.email_id);
			  $("#addId").hide();
			  $("#editId").show();
			  
			  
			 
		  }
		});
}
function callBeforeSubmit(){
var validator = $(".form-validate-jquery").validate({
    ignore: 'input[type=hidden], .select2-search__field', // ignore hidden fields
    errorClass: 'validation-error-label',
    successClass: 'validation-valid-label',
    highlight: function(element, errorClass) {
        $(element).removeClass(errorClass);
    },
    unhighlight: function(element, errorClass) {
        $(element).removeClass(errorClass);
    },

    // Different components require proper error label placement
    errorPlacement: function(error, element) {

        // Styled checkboxes, radios, bootstrap switch
        if (element.parents('div').hasClass("checker") || element.parents('div').hasClass("choice") || element.parent().hasClass('bootstrap-switch-container') ) {
            if(element.parents('label').hasClass('checkbox-inline') || element.parents('label').hasClass('radio-inline')) {
                error.appendTo( element.parent().parent().parent().parent() );
            }
             else {
                error.appendTo( element.parent().parent().parent().parent().parent() );
            }
        }

        // Unstyled checkboxes, radios
        else if (element.parents('div').hasClass('checkbox') || element.parents('div').hasClass('radio')) {
            error.appendTo( element.parent().parent().parent() );
        }

        // Input with icons and Select2
        else if (element.parents('div').hasClass('has-feedback') || element.hasClass('select2-hidden-accessible')) {
            error.appendTo( element.parent() );
        }

        // Inline checkboxes, radios
        else if (element.parents('label').hasClass('checkbox-inline') || element.parents('label').hasClass('radio-inline')) {
            error.appendTo( element.parent().parent() );
        }

        // Input group, styled file input
        else if (element.parent().hasClass('uploader') || element.parents().hasClass('input-group')) {
            error.appendTo( element.parent().parent() );
        }

        else {
            error.insertAfter(element);
        }
    },
    validClass: "validation-valid-label",
    success: function(label) {
        label.addClass("validation-valid-label").text("Success.");
    },
    rules: {
        password: {
        	required: true,
            minlength: 5,
            //pwcheck: true
        },
        repeat_password: {
            equalTo: "#password"
        },
        email_id: {
            email: true
        },
        mobileno: {
        	minlength: 10,
            maxlength: 10,
            digits: true
        },
        minimum_number: {
            min: 10
        },
        maximum_number: {
            max: 10
        },
        number_range: {
            range: [10, 20]
        },
        url: {
            url: true
        },
        date: {
            date: true
        },
        date_iso: {
            dateISO: true
        },
        numbers: {
            number: true
        },
        digits: {
            digits: true
        },
        creditcard: {
            creditcard: true
        },
        basic_checkbox: {
            minlength: 2
        },
        styled_checkbox: {
            minlength: 2
        },
        switchery_group: {
            minlength: 2
        },
        switch_group: {
            minlength: 2
        }
    },
    messages: {
        custom: {
            required: "This is a custom error message",
        },
        mobileno : "Please Enter 10 Digit Mobile Number",
        agree: "Please accept our policy",
        /* password: {
            pwcheck: "Should Contain atleast 1-digit, 1-Upper, 1-Lower Case Letter.",
        } */
    }
});

$.validator.addMethod("pwcheck", function(value) {
	   return /^[A-Za-z0-9\d=!\-@._*]*$/.test(value) // consists of only these
	       && /[a-z]/.test(value) // has a lowercase letter
	       && /[A-Z]/.test(value) // has a Upper Case
	       //&& /[!=\-!_!]/.test(value) //has a special charecter
	       && /\d/.test(value); // has a digit
	});
}
			

$(document).bind("contextmenu",function(e) {
	 e.preventDefault();
	});
</script>	
<style>
.form-group {
    margin-bottom: 10px;
    position: relative;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #0DB7A5;
}
.form-horizontal .form-group {
    margin-left: 20px;
    margin-right: 40px;
}

/* width */
::-webkit-scrollbar {
    width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
    background: #f1f1f1; 
}
 
/* Handle */
::-webkit-scrollbar-thumb {
    background: #888; 
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
    background: #555; 
}

</style>

