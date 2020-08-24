<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/core/libraries/jquery_ui/interactions.min.js"></script>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/plugins/forms/selects/select2.min.js"></script>

<script type="text/javascript"
	src="./resources/layout_3/assets/js/pages/form_select2.js"></script>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/pages/form_validation.js"></script>
<script type="text/javascript"
	src="./resources/layout_3/assets/js/plugins/forms/validation/validate.min.js"></script>


<div class="panel panel-flat">
	<div class="panel-body">
		<form:form action="./addtariffRateConversion" modelAttribute="tariffRateConversionEntity"
			commandName="tariffRateConversionEntity" method="POST" id="tariffRateConversionEntity" role="form"
			class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold">Tariff Rate Conversion</legend>
				
					<div class="col-md-4" hidden="true">
						<div class="form-group">
							<label><span class="text-danger">*</span></label>
							<form:input type="number" path="id" id="id" name="id"
								class="form-control" placeholder="" />
						</div>
					</div>
					<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label>Connection Type&nbsp;<font color="red">*</font></label>
							<form:select class="select" path="con_type" name="con_type" id="con_type">
								<form:option value="" data-icon="icon-git-branch">select connection Type</form:option>
								<form:option value="Metered" data-icon="icon-git-branch">Metered</form:option>
								<form:option value="UnMetered" data-icon="icon-git-branch">UnmMetered</form:option>
							</form:select>
						</div>
					</div>
					
					
					
					
					
					<div class=" col-md-4">
						<div class="form-group">
							<label>Tariff&nbsp;<font color="red">*</font></label>
							 <form:input type="text" path="tariff" id="tariff" name="tariff"
								class="form-control" placeholder="Enter The Tariff"/> 
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label>Diameter Tap Size (Inch)</label>
							<form:select path="tap_Size" id="tap_Size"
								class="select form-control" required="required">

								<form:option value="" selected="selected" disabled="disabled">Select Tap Size</form:option>
								<form:option value="0.5">0.5</form:option>
								<form:option value="0.75">0.75</form:option>
								<form:option value="1">1</form:option>
								<form:option value="1.5">1.5</form:option>
								<form:option value="2">2</form:option>
								<form:option value="3">3</form:option>
								<form:option value="4">4</form:option>
							</form:select>
						</div>
					</div>
					</div>
					<div class="row">
					

					<%-- <div class="col-md-3">
						<div class="form-group">
							<label>Rate Slab</label>
							<form:input type="text" path="rate_slab" name="rate_slab" id="rate_slab"
								required="required" class="form-control"
								placeholder="Mobile Number" />
						</div>
					</div> --%>
					
					<div class="col-md-4">
						<div class="form-group">
							<label>Rate Constant</label>
							<form:input type="text" path="rate_constant" name="rate_constant" id="rate_constant"
								required="required" class="form-control"
								 placeholder="Enter The Rate Constant" />
						</div>
					</div>
					<div class=" col-md-4">
					<div class="form-group">
						<label>Connection Category <font color="red">*</font></label>
						<form:select class="select" path="con_category"
							name="con_category" id="con_category">
							<option value="" data-icon="icon-git-branch">Select
								Connection Category</option>
							<option value="Domestic" data-icon="icon-git-branch">Domestic</option>
							<option value="Government" data-icon="icon-git-commit">Government</option>
							<option value="Industry and Company" data-icon="icon-git-commit">Industry and Company</option>
						</form:select>
					</div>
					
					</div>
					
					
					
					
					
					</div>

					<div class="col-md-12" id="addId">
						<div class="form-group text-center">
							<label>&nbsp;</label>
							<button type="submit" onclick="return callBeforeSubmit();"
								class="btn bg-teal btn-ladda" style="width: 200px;"
								data-style="expand-right" data-spinner-size="20">
								<span class="ladda-label">Add</span>
							</button>
						</div>
					</div>
					<div class="col-md-12" hidden="true" id="editId">
						<div class="form-group text-center">
							<label>&nbsp;</label>
							<button type="button" hidden="true" onclick="modifyButton();"
								class="btn bg-teal btn-ladda" style="width: 200px;"
								data-style="expand-right" data-spinner-size="20">
								<span class="ladda-label">Modify</span>
							</button>
						</div>
					</div>

				


			</fieldset>
		</form:form>

	</div>

</div>
<div class="panel panel-flat">

	<div class="panel-body">
		<fieldset class="content-group">
			<legend class="text-bold">Tariff Details</legend>
			<div class="col-md-12">
				<div class="panel-body" style="margin-top: -38px;">
					<table class="table datatable-basic table-bordered">
						<thead>
							<tr class="bg-blue">
								<th>Connection Type</th>
								<th>Connection Category</th>
								<th>Tariff</th>
								<th>Tap Size</th>
								<!-- <th>Slab Rate</th> -->
								<th>Rate Constant</th>
								<th>Edit/Delete</th>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="data" items="${tariffDetails}">
								<tr>
									<td>${data.con_type}</td>
					                <td>${data.con_category}</td>
									<td>${data.tariff}</td>
									<td>${data.tap_Size} </td>
									<td>${data.rate_constant}</td>
									<td> <a href="#" onclick="editUserMaster('${data.id}');">[Edit]</a><a href="#" onclick="deleteUserMaster('${data.id}');">[Delete]</a></td>
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
	
	/* callBeforeSubmit(); */
	$("#tariffRateConversionEntity").attr("action", "./updateTariffDetails").submit();
}
function mobileNoChk(){
	var id=$("#id").val();
	var number=$("#number").val();
	var datavalue="";
	$.ajax({
		  type: "GET",
		  url: "./editUniqueStore/"+number+"/"+id,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response){
			 
			  if(response=="CanAdd"){
				  datavalue="cann add";
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "Mobile Number Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		$("#number").val("");
		
	}
	
}

function deleteUserMaster(id){
	$("#id").val(id);
	$("#storeEntity").attr("action", "./deleteStore/"+id).submit();
}


function uniqueUser(){
	var datavalue="";
	var user_login_name=$("#user_login_name").val();
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

function editUserMaster(id){
	
	$.ajax({
		  type: "GET",
		  url: "./editTariffDetails/"+id,
		  dataType: "JSON",
		  cache       : false,
		  async       : false,
		  success: function(response){
			  $("#id").val(response.id);
			  $("#con_type").val(response.con_type).trigger("change");
			  $("#con_category").val(response.con_category).trigger("change");
			  $("#tariff").val(response.tariff);
			  $("#tap_Size").val(response.tap_Size).trigger("change");
			  $("#rate_constant").val(response.rate_constant);
			  $("#addId").hide();
			  $("#editId").show();
		  }
		});
}
function callBeforeSubmit(){
	 			rate_constant	
	if(document.getElementById("con_type").value == "" || document.getElementById("con_type").value == null)
	{
	
		 swal({
             title: "Please Select The Connection Type",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		
		 return false;
	}
	 if(document.getElementById("con_category").value == "" || document.getElementById("con_category").value == null)
		{
		
			 swal({
	             title: "Please Select The Connection Category",
	             text: "",
	             confirmButtonColor: "#2196F3",
	         });
			
			 return false;
		}
	 if(document.getElementById("tariff").value == "" || document.getElementById("tariff").value == null)
		{
		
			 swal({
	             title: "Please Enter the Tariff",
	             text: "",
	             confirmButtonColor: "#2196F3",
	         });
			
			 return false;
		}
	 f(document.getElementById("tap_Size").value == "" || document.getElementById("tap_Size").value == null)
		{
		
			 swal({
	             title: "Please Enter the Tariff",
	             text: "",
	             confirmButtonColor: "#2196F3",
	         });
			
			 return false;
		}
	
	
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
        label.addClass("validation-valid-label").text("Success.")
    },
    rules: {
        name: {
            required: true
        },
        branch: {
            required: true
        },
        number: {
        	minlength: 10,
            maxlength: 10,
            digits: true
        },
        address: {
        	required: true
        }
    },
    messages: {
        custom: {
            required: "This is a custom error message",
        },
        number : "Please Enter 10 Digit Mobile Number",
    }
});
}
				
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
</style>
