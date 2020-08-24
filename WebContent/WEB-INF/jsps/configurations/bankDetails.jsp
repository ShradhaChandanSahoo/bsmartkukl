<%@include file="/WEB-INF/decorators/taglibs.jsp"%>

<script type="text/javascript">

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
	
	
	 $('#effectingDateN').nepaliDatePicker({
		 dateFormat: "%D, %M %d, %y",
			npdMonth: true,
			npdYear: true
  });
	
	 $("#id").val("0");
	 $("#bankName1").val("");
	 $("#bankAbbreviation1").val("");
	 
	 var msg = '${mag}';
	 if(msg!="")
	 {
		 swal({
             title: msg,
             text: "",
             confirmButtonColor: "#2196F3",
         }); 
	 }
});

function finalSubmit(param) {
	
	if(document.getElementById("bankName1").value.trim() == "" || document.getElementById("bankName1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Bank Name",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	if(document.getElementById("bankAbbreviation1").value.trim() == "" || document.getElementById("bankAbbreviation1").value.trim() == null)
	{
		 swal({
             title:  "Please Enter Bank Abbreviation",
             text: "",
             confirmButtonColor: "#2196F3",
         });

		return false;
	}
	
	if(param =='0')
	{
		$("#bankDetailsId").attr("action", "./addBankDetails").submit();
	}
	else if(param == '1')
	{
		$("#bankDetailsId").attr("action", "./updateBankDetails").submit();
	}
	else
	{
		$("#bankDetailsId").attr("action", "./deleteBankDetails").submit();
	}
}



</script>


<div class="panel panel-flat">
					

						<div class="panel-body">
						<!-- <form class="form-horizontal form-validate-jquery" action="#"> -->
							<form:form action="" modelAttribute="bankDetails" commandName="bankDetails" method="POST"
							 id="bankDetailsId" role="form" class="form-horizontal form-validate-jquery">
								<fieldset class="content-group"> 
									<legend class="text-bold">Bank Details</legend>
										<div class="row">
											
											<div hidden="true" class="col-md-6">
												<div class="form-group">
													<label>ID</label>
					                                <form:input path="id" id="id" type="text" class="form-control" ></form:input>
					                             </div>
											</div>
											
											<div class="col-md-6">
												<div class="form-group">
													<label>Bank Name</label>
					                                <form:input path="bankName" id="bankName1" type="text" class="form-control" placeholder="Bank Name..." ></form:input>
					                             </div>
											</div>
											<div class="col-md-6">
												<div class="form-group" >
													<label>Bank Abbreviation</label>
					                                <form:input path ="bankAbbreviation" id="bankAbbreviation1" type="text" class="form-control" placeholder="Bank Abbreviation..." ></form:input>
												</div>
											</div>
											
										</div>
								
										<div class="text-center">
											<button type="submit" class="btn btn-primary" onclick="return finalSubmit(0)" id="addOption"> Submit <i class="icon-arrow-right14 position-right"></i></button>
											<button type="submit" class="btn btn-primary" onclick="return finalSubmit(1)" id="updateOption" style="display: none;"> Modify <i class="icon-arrow-right14 position-right"></i></button> 
											<button type="submit" class="btn btn-primary" onclick="return finalSubmit(2)" id="deleteOption" style="display: none;"> Delete <i class="icon-arrow-right14 position-right"></i></button> 
										</div>
								</fieldset>
							</form:form>
							<br/><br/>
								
								<div id ="gridId1">
									<fieldset class="content-group" > 
										<legend class="text-bold">Bank Details</legend>
										<div class="col-md-12">
											<div class="panel-body" style="margin-top: -38px;">
												<table class="table datatable-basic table-bordered">
													<thead>
														<tr style="background-color:#dbd7d7;" >
															<th class="col-md-6">Bank Name</th>
															<th class="col-md-6">Bank Abbreviation</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="app" items="${bank}">
															<tr>
																<td><a href="#" id="${app.id}" onclick="return bankRecordById(this.id);" >${app.bankName}</a></td>
																<td>${app.bankAbbreviation}</td>
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
				
					
					
<script>
$(document).ready(function(){   

	$('#openUl').show();
	$('#secUl').show();
	$('#genericLibrary').addClass('active');
	$('#dataTables1').addClass('active');
});

function groupDataByGroupId()
{
	$("#groupDataByGroupId").val($("#gridOneId0").text());
	$("#bankName").val($("#gridOneConn0").text());
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

function bankRecordById(id)
{
	$.ajax({
		type : "GET",
		url : "./getBankDataForEditing/"+id,	
		dataType : "json",
		cache : false,
		async : false,
		success : function(response)
		{	
			for( var i=0;i<response.length;i++)
			{
				var dataEdit = response[i];
				$('#id').val(dataEdit.id);
				$('#bankName1').val(dataEdit.bankName);
		      	$('#bankAbbreviation1').val(dataEdit.bankAbbreviation);
		    }
		}
	});
	document.getElementById('updateOption').style.display=''; 
	document.getElementById('deleteOption').style.display=''; 
	document.getElementById('addOption').style.display = 'none';  
}
</script>
<style>
.form-horizontal .form-group{
	margin-left: 0px !important;
	margin-right: 0px !important;
	
}
select{
width: 100%; border: 1px solid #DDD; border-radius: 3px; height: 36px;
}
legend {
	text-transform: none;
	font-size: 16px;
	border-color: #F01818;
}
</style>