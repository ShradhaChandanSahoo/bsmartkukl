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

function checkNumeric() 
{
	var counterNumber1=$('#counterNumber1').val();
	if( $.isNumeric(counterNumber1)==false)
		{
			$('#counterNumber1').val(counterNumber1.substring(0, counterNumber1.length - 1));
		}
}

 function counterRecordById(id)
{
	$('#addOption').hide();
	$('#updateOption').show();
	$.ajax({
		type : "GET",
		url : "./getCounterDataForEditing/"+id,	
		dataType : "json",
		cache : false,
		async : false,
		success : function(response)
		{	
			$("#id").val(id);
		    document.getElementById('counterNumber1').value=response.counterNumber;
		    document.getElementById('counterName1').value=response.counterName;
		    document.getElementById('counterAddress1').value=response.counterAddress;
		}
	});

}
 


function finalSubmit(param) {
	
	if(document.getElementById("counterNumber1").value == "" || document.getElementById("counterNumber1").value == null)
	{
	
		 swal({
             title: "Please Enter Counter Number",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		
		 return false;
	}
	
	if(document.getElementById("counterName1").value.trim() == "" || document.getElementById("counterName1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Counter Name",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	if(document.getElementById("counterAddress1").value.trim() == "" || document.getElementById("counterAddress1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Counter Address",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	

	if(param =='0')
	{
		
		$("#counterDetailsId").attr("action", "./addCounterDetails").submit(); 
		
	}
 else if(param == '1')
	{
		$("#counterDetailsId").attr("action", "./updateCounterDetails").submit();
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
			<div class="alert alert-success	 alert-bordered">
				<button type="button" class="close" data-dismiss="alert">
					<span>&times;</span><span class="sr-only">Close</span>
				</button>
				<span class="text-semibold" id="alert">${msg}</span>
			</div>
			<c:remove var="msg" scope="session" />
		</c:if> --%>
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


		
		<form:form action="" modelAttribute="counterDetails"
			commandName="counterDetails" method="POST" id="counterDetailsId"
			role="form" class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold"
					style="margin: auto; text-align: left; font-size: 18px;">Counter
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
							<label>Counter Number&nbsp;<font color="red">*</font></label>
							<form:input path="counterNumber" id="counterNumber1" type="text"
								class="form-control" placeholder="Counter Number..." onkeyup="checkNumeric();"></form:input>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Counter Name&nbsp;<font color="red">*</font></label>
							<form:input path="counterName" id="counterName1" type="text"
								class="form-control" placeholder="Counter Name..."></form:input>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<label>Counter Address&nbsp;<font color="red">*</font></label>
							<form:input path="counterAddress" id="counterAddress1" type="text"
								class="form-control" placeholder="Counter Address..."></form:input>
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
				<legend class="text-bold">Counter Details</legend>
				<div class="row">
					<div class="table-responsive">
						<div class="col-md-16">
							<div class="panel-body" style="margin-top: -3px; padding: 6px;">
								<table class="table datatable-basic table-bordered"
									id="tableForm">
									<thead style="width: 100px">

										<tr class="bg-blue">
											<th style="width: 50px">Counter No</th>
											<th style="width: 50px">Counter Name</th>
											<th style="width: 50px">Counter Address</th>
											<th style="width: 50px">Edit</th>
										</tr>

									</thead>
									<tbody>
										<c:forEach var="app" items="${counter}">
											<tr>
												<td>${app.counterNumber}</td>
												<td>${app.counterName}</td>
												<td>${app.counterAddress}</td>
												<td><a href="#" onclick="counterRecordById(${app.id});"
													id="editData${app.id}">Edit</a></td>
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
