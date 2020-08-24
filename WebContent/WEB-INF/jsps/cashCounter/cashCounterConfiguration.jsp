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

<script>
	$(document).ready(function() {
		
		$('#configurationScreen').show();
		$('#configuration').addClass('active');
		
		var activeMod="${activeModulesNew}";
		var module="Configuration";
		var check=activeMod.indexOf(module) > -1;
		
		if(check){
		}else{
			window.location.href="./accessDenied";
		}
	});
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
		<fieldset>
			<legend class="text-bold"
				style="margin: auto; text-align: center; font-size: 18px;">Configuration</legend>
			<br>
		</fieldset>
		<form:form action="./createCashMasterConfig"
			modelAttribute="cashMasterConfig" commandName="cashMasterConfig"
			method="POST" id="cashMasterConfig" role="form"
			class="form-horizontal form-validate-jquery">


			<div class="row">

				<div class="col-md-12">
					<div class="col-md-4" hidden="true">
						<div class="form-group">
							<label><span class="text-danger">*</span></label>
							<form:input type="number" path="id" id="id" name="id"
								class="form-control" placeholder="" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">Counter No:</label>
						<div class="col-lg-6">
							<form:select class="select" id="counter_no" name="counter_no"
								onchange="setCounterName(this.value)" path="counter_no"
								required="required" data-placeholder="Select Counter No">
								<form:option value="" disabled="disabled">Select counter No</form:option>
								<c:forEach var="data" items="${counter}">
									<form:option value="${data.counterNumber}">${data.counterNumber}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-3 control-label">Counter Name:</label>
						<div class="col-lg-6">
							<form:input type="text" id="counter_name" name="counter_name"
								path="counter_name" class="form-control"
								placeholder="Counter Name" readonly="true"></form:input>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-3 control-label">Users:</label>
						<div class="col-lg-6">
							<form:select class="select" id="user_id" name="user_id"
								path="user_id" data-placeholder="Select Users">
								<form:option value="" disabled="disabled">Select Users</form:option>
								<c:forEach var="data" items="${userMasterData}">
									<form:option value="${data.id}">${data.user_name}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>


					<div class="text-center" id="addId">
						<button type="submit" class="btn btn-primary"
							onclick="return callBeforeSubmit();">
							Submit <i class="icon-arrow-right14 position-right"></i>
						</button>
					</div>
					<div class="text-center" hidden="true" id="editId">
						<button type="button" onclick="modifyButton();"
							class="btn btn-primary">
							Modify <i class="icon-arrow-right14 position-right"></i>
						</button>
					</div>

				</div>
			</div>



		</form:form>

	</div>
</div>
<div class="panel panel-flat">

	<div class="panel-body">
		<fieldset class="content-group">
			<legend class="text-bold">User Details</legend>
			<div class="col-md-12">
				<div class="panel-body" style="margin-top: -38px;">
					<table class="table datatable-basic table-bordered">
						<thead>
							<tr class="bg-blue">
								<th>Counter No</th>
								<th>Counter Name</th>
								<th>User Name</th>
								<th>Edit</th>

							</tr>
						</thead>
						<tbody>
							<c:forEach var="data" items="${cashMasterConfigData}">
								<tr>
									<td>${data.counter_no}</td>
									<td>${data.counter_name}</td>
									<td>${data.userMaster.user_name}</td>
									<td><a href="#"
										onclick="editUserMaster('${data.id}','${data.counter_no}','${data.counter_name}','${data.userMaster.id}');">[Edit]</a><%-- <a
										href="#" onclick="deletecashconfig('${data.id}');">[Delete]</a> --%></td>
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
	function editUserMaster(id, counter_no, counter_name, userid) {

		$("#id").val(id);
		$("#counter_no").val(counter_no).trigger("change");
		$("#counter_name").val(counter_name);
		$("#user_id").val(userid).trigger("change");
		$("#addId").hide();
		$("#editId").show();

	}
	function callBeforeSubmit() {
		var validator = $(".form-validate-jquery")
				.validate(
						{
							ignore : 'input[type=hidden], .select2-search__field', // ignore hidden fields
							errorClass : 'validation-error-label',
							successClass : 'validation-valid-label',
							highlight : function(element, errorClass) {
								$(element).removeClass(errorClass);
							},
							unhighlight : function(element, errorClass) {
								$(element).removeClass(errorClass);
							},

							// Different components require proper error label placement
							errorPlacement : function(error, element) {

								// Styled checkboxes, radios, bootstrap switch
								if (element.parents('div').hasClass("checker")
										|| element.parents('div').hasClass(
												"choice")
										|| element.parent().hasClass(
												'bootstrap-switch-container')) {
									if (element.parents('label').hasClass(
											'checkbox-inline')
											|| element.parents('label')
													.hasClass('radio-inline')) {
										error.appendTo(element.parent()
												.parent().parent().parent());
									} else {
										error.appendTo(element.parent()
												.parent().parent().parent()
												.parent());
									}
								}

								// Unstyled checkboxes, radios
								else if (element.parents('div').hasClass(
										'checkbox')
										|| element.parents('div').hasClass(
												'radio')) {
									error.appendTo(element.parent().parent()
											.parent());
								}

								// Input with icons and Select2
								else if (element.parents('div').hasClass(
										'has-feedback')
										|| element
												.hasClass('select2-hidden-accessible')) {
									error.appendTo(element.parent());
								}

								// Inline checkboxes, radios
								else if (element.parents('label').hasClass(
										'checkbox-inline')
										|| element.parents('label').hasClass(
												'radio-inline')) {
									error.appendTo(element.parent().parent());
								}

								// Input group, styled file input
								else if (element.parent().hasClass('uploader')
										|| element.parents().hasClass(
												'input-group')) {
									error.appendTo(element.parent().parent());
								}

								else {
									error.insertAfter(element);
								}
							},
							validClass : "validation-valid-label",
							success : function(label) {
								label.addClass("validation-valid-label").text(
										"Success.")
							},
							rules : {
								password : {
									minlength : 5
								},
								repeat_password : {
									equalTo : "#password"
								},
								email_id : {
									email : true
								},
								mobileno : {
									minlength : 10,
									maxlength : 10,
									digits : true
								},
								minimum_number : {
									min : 10
								},
								maximum_number : {
									max : 10
								},
								number_range : {
									range : [ 10, 20 ]
								},
								url : {
									url : true
								},
								date : {
									date : true
								},
								date_iso : {
									dateISO : true
								},
								numbers : {
									number : true
								},
								digits : {
									digits : true
								},
								creditcard : {
									creditcard : true
								},
								basic_checkbox : {
									minlength : 2
								},
								styled_checkbox : {
									minlength : 2
								},
								switchery_group : {
									minlength : 2
								},
								switch_group : {
									minlength : 2
								}
							},
							messages : {
								custom : {
									required : "This is a custom error message",
								},
								mobileno : "Please Enter 10 Digit Mobile Number",
								agree : "Please accept our policy"
							}
						});
	}

	function setCounterName(counter_no) {

		$.ajax({
			type : "GET",
			url : "./getCounterDetails/" + $('#counter_no').val(),
			dataType : "JSON",
			async : false,
			cache : false,
			success : function(response) {

				$('#counter_name').val(response.counterName);

			}
		})
	}
	
     function modifyButton() {
		
		var counter_no=$('#counter_no').val();
		
		if(counter_no==null || counter_no==""){
			alert("Please Select Counter No");
			return false;
		}
		
		if(confirm("Are You Sure to Modify ")){
			
			$("#cashMasterConfig").attr("action", "./updateCashMasterConfig").submit();	
		}else{
			return false;
		}
		
	}
     
	function deletecashconfig(id) {
		$("#id").val(id);
		$("#cashMasterConfig").attr("action", "./deleteCashMasterConfig")
				.submit();
	}
</script>


<style>
select {
	width: 100%;
	border: 1px solid #DDD;
	border-radius: 3px;
	height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
</style>


