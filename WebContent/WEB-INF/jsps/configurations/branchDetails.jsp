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

function groupDataByGroupId()
{
	$("#branchCode").val($("#gridOneId0").text());
	$("#branchName").val($("#gridOneConn0").text());
	$("#branchAddress").val($("#gridOneAddr0").text());
	//$("#gridId1").hide();
	//$("#gridId2").show(); 
	
	//document.getElementById('updateOption').style.display='block'; 
	document.getElementById('addOption').style.display = 'none';  
}

function getDetailsByConnNum()
{
	$("#gId").val($("#gid1").text());
	$("#conNum").val($("#gid1").text());
	$("#wNum").val($("#wNum1").text());
	$("#namee").val($("#wNum1").text());
	$("#addrEng").val($("#addr021").text());
	$("#addrNepali").val($("#addr021").text());
	document.getElementById('updateOption').style.display='block'; 
	document.getElementById('addOption').style.display = 'none';  
}

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
	border-color: #F01818;
}
</style>



<script type="text/javascript">

$(document).ready(function(){   
	$('#umconfig').show();
	$('#configModule').addClass('active');
	
	 $('#effectingDateN').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
  });
	
	/*  $("#id").val("0"); */
	/*  $("#branchCode1").val(""); */

	 $("#branchName1").val("");
	 $("#branchAddress1").val("");
	 
 /*   var msg = '${msg}';
	 if(msg!="")
	 {
		 swal({
             title: msg,
             text: "",
             confirmButtonColor: "#2196F3",
         }); 
	 }  */
});


function branchRecordById(id)
{
	$.ajax({
		type : "GET",
		url : "./getBranchDataForEditing/"+id,	
		dataType : "json",
		cache : false,
		async : false,
		success : function(response)
		{	
			for( var i=0;i<response.length;i++)
			{
				var dataEdit = response[i];
				$('#id').val(dataEdit.id);
				$('#branchCode1').val(dataEdit.branchCode);
				$('#branchName1').val(dataEdit.branchName);
				$('#bankId').val(dataEdit.bankDetailsEntity.id).trigger("change");
		      	$('#branchAddress1').val(dataEdit.branchAddress);
		    }
		}
	});
	document.getElementById('updateOption').style.display=''; 
	document.getElementById('deleteOption').style.display=''; 
	document.getElementById('addOption').style.display = 'none';  
}

function finalSubmit(param) {
	
	if(document.getElementById("branchCode1").value == "" || document.getElementById("branchCode1").value == null)
	{
	
		 swal({
             title: "Please Enter Branch Code",
             text: "",
             confirmButtonColor: "#2196F3",
         });
		return false;
	}
	
	if(document.getElementById("branchName1").value.trim() == "" || document.getElementById("branchName1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Branch Name",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	if(document.getElementById("bankId").value.trim() == "" || document.getElementById("bankId").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Bank Name",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	
	if(document.getElementById("branchAddress1").value.trim() == "" || document.getElementById("branchAddress1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Branch Address",
             text: "",
             confirmButtonColor: "#2196F3",
         });
	
		return false;
	}
	
	if(param =='0')
	{
		$("#branchDetailsId").attr("action", "./addBranchDetails").submit();
		
	}
	else if(param == '1')
	{
		$("#branchDetailsId").attr("action", "./updateBranchDetails").submit();
	}
	else
	{
		$("#branchDetailsId").attr("action", "./deleteBranchDetails").submit();
		/* swal({
            title: "Working on",
            text: "",
            confirmButtonColor: "#2196F3",
        });  */
	}

}

</script>


<div class="panel panel-flat">
	<div class="panel-body">
	
	<%--<c:if test="${not empty msg}">
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
		<!-- <form class="form-horizontal form-validate-jquery" action="#"> -->
		<form:form action="" modelAttribute="branchDetails"
			commandName="branchDetails" method="POST" id="branchDetailsId"
			role="form" class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold">Branch Details</legend>
				<div class="row">

					<div hidden="true" class="col-md-3">
						<div class="form-group">
							<label>ID</label>
							<form:input path="id" id="id" type="text" class="form-control"></form:input>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Branch Code</label>
							<input name="branchCode" id="branchCode1" type="text"
								class="form-control" placeholder="Branch Code...">
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Branch Name</label>
							<form:input path="branchName" id="branchName1" type="text"
								class="form-control" placeholder="Branch Name..."></form:input>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Bank Name</label>
							<form:select class="select" id="bankId" name="bankId"
								path="bankId" data-placeholder="Select Bank Name">
								<form:option value="" disabled="disabled">Select Bank</form:option>
								<c:forEach var="data" items="${bankData}">
									<form:option value="${data.id}">${data.bankName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<label>Branch Address</label>
							<form:input path="branchAddress" id="branchAddress1" type="text"
								class="form-control" placeholder="Branch Address..."></form:input>
						</div>
					</div>
				</div>


				<div class="text-center">
					<button type="submit" class="btn btn-primary"
						onclick="return finalSubmit(0)" id="addOption">
						Submit <i class="icon-arrow-right14 position-right"></i>
					</button>
					<button type="submit" class="btn btn-primary"
						onclick="return finalSubmit(1)" id="updateOption" style="display: none;">
						Modify <i class="icon-arrow-right14 position-right"></i>
					</button>
					<button type="submit" class="btn btn-primary"
						onclick="return finalSubmit(2)" id="deleteOption" style="display: none;">
						Delete <i class="icon-arrow-right14 position-right"></i>
					</button>
				</div>
			</fieldset>
		</form:form>
		<br />
		<br />

		<div id="gridId1">
			<fieldset class="content-group">
				<legend class="text-bold">Branch Details</legend>
				<div class="col-md-12">
					<div class="panel-body" style="margin-top: -38px;">
						<table class="table datatable-basic table-bordered">
							<thead>
								<tr style="background-color: #dbd7d7;">
									<th class="col-md-3">Branch Code</th>
									<th class="col-md-3">Branch Name</th>
									<th class="col-md-3">Bank Name</th>
									<th class="col-md-3">Branch Address</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="app" items="${branch}">
									<tr>
										<td>${app.branchCode}</td>
										<td><a href="#" id="${app.id}"
											onclick="return branchRecordById(this.id);">${app.branchName}</a></td>
										<td>${app.bankDetailsEntity.bankName}</td>
										<td>${app.branchAddress}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</fieldset>
		</div>

	</div>
</div>
