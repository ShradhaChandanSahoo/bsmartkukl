<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
	
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
 			
<script>
		$(document).ready(function(){   
			$('#meteringscreens').show();
			$('#meterManagement').addClass('active');
			
			var activeMod="${activeModulesNew}";
			var module="Metering";
			var check=activeMod.indexOf(module) > -1;
			
			if(check){
			}else{
				window.location.href="./accessDenied";
			}
			
		$("#mobileno").val('');
		
		}); 
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
	border-color: skyblue;
}

.col-md-3 {
    width: 20%;
}

.datatable-header, .datatable-footer {
    padding: 8px 0px 0 0px;
}
</style>

<script type="text/javascript">

function checkMrDetails() 
{
	if($('#mrCode').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Reader Code</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	if($('#mrName').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Reader Name</span>');
      $("#alertDiv").fadeOut(5000);
      return false;
	}
	
	var datavalue="";
	var mrCode=$("#mrCode").val();
	$.ajax({
		  type: "GET",
		  url: "./uniqueMtrReader/"+mrCode,
		  dataType: "text",
		  cache       : false,
		  async       : false,
		  success: function(response)
		  {
			  if(response=="CanAdd"){
				 return true;
				}else{
					datavalue="AlreadyExist";
				}
		  }
		});
	if(datavalue=='AlreadyExist'){
		swal({
            title: "MR Code Already Exist",
            text: "",
            confirmButtonColor: "#2196F3",
        });
		return false;
	}else{
		return true;
	}
	
}

function editmrDetails(id,mrCode,mrName,address,mobileno,createdDate,createdBy,status)
{
	$("#id").val(id);
	
	$("#mrCode").val(mrCode);
	$("#mrName").val(mrName);
	$("#address").val(address);
	$("#mobileno").val(mobileno);
	$("#createdDate").val(createdDate);
	$("#createdBy").val(createdBy);
	$("#status").val(status);
	
	$("#addId").hide();
	$("#editId").show();
}

function modifyButton()
{
	var idVal=$("#id").val();
	var mrcodeVal=$("#mrCode").val();
	var mrnameVal=$("#mrName").val();
	var mraddressVal=$("#address").val();
	var mrmobilenoVal=$("#mobileno").val();
	
	if($('#mrCode').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Reader Code</span>');
      $("#alertDiv").fadeOut(3000);
      return false;
	}
	if($('#mrName').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Reader Name</span>');
      $("#alertDiv").fadeOut(3000);
      return false;
	}
	
	/* $.ajax({
			 	type : "GET",
				url : "./editMeterReaderDetails/"+idVal+"/"+mrcodeVal+"/"+mrnameVal+"/"+mraddressVal+"/"+mrmobilenoVal,
				dataType : "text",
				cache:false,
				async:false,
				success : function(response)
				{ 
					window.location.href="./mrDetails";   
				}
		 }); */
	$("#meterReaderEntity").attr("action", "./editMeterReaderDetails").submit();
}

function deleteButton()
{
	var idVal=$("#id").val();
		
	if($('#mrCode').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Reader Code</span>');
      $("#alertDiv").fadeOut(3000);
      return false;
	}
	if($('#mrName').val().trim()=='')
	{
	$('#alertDiv').show();
      $('#alertDiv').html('<div class="alert alert-danger alert-bordered"><button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button><span class="text" id="alert">Please enter Meter Reader Name</span>');
      $("#alertDiv").fadeOut(3000);
      return false;
	}
	
	 $.ajax({
			 	type : "GET",
				url : "./deleteMeterReaderDetails/"+idVal,
				dataType : "text",
				cache:false,
				async:false,
				success : function(response)
				{ 
					window.location.href="./mrDetails";   
				}
		 }); 
	
}

function resetButton()
{
	$('#meterReaderEntity')[0].reset();
	$('#mobileno').val('');
	$("#addId").show();
	$("#editId").hide();
}

</script>

					<div class="panel panel-flat">
						<div class="panel-body">
						<div class="row" hidden="true" id="alertDiv">
								    </div>
							<!-- <form class="form-horizontal form-validate-jquery" action="#">-->
							<form:form action="./addMeterReaderDetails" role="form" modelAttribute="meterReaderEntity" commandName="meterReaderEntity" method="POST" id="meterReaderEntity">
						<fieldset class="content-group"> 
									
								<legend class="text-bold" style="font-size: 18px; text-align: center">Meter Reader Details</legend>
										
										<div class="row">
											<div class="col-md-3">
												<div class="form-group">
													<label>Meter Reader Code&nbsp;<font color="red">*</font></label>
					                                <form:input path="mrCode" id="mrCode" name="mrCode" type="text" class="form-control" placeholder="Meter Reader Code" autocomplete="off" />
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Meter Reader Name&nbsp;<font color="red">*</font></label>
					                                <form:input path="mrName" id="mrName" name="mrName" type="text" class="form-control" placeholder="Meter Reader Name" />
				                                </div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
													<label>Address</label>
					                                <form:input path="address" id="address" name="address" type="text" class="form-control" placeholder="Address" />
				                                </div>
											</div>
											<div class="col-md-3">
												<div class="form-group">
													<label>Mobile No</label>
					                                <form:input path="mobileno" id="mobileno" name="mobileno" type="text" class="form-control" placeholder="Mobile No." />
				                                </div>
											</div>
											
											<div class="col-md-3" id="addId">
													<div class="form-group">
													<label>&nbsp;</label> 
													<div class="input-group">
															&nbsp;&nbsp;&nbsp;<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return checkMrDetails();"><span class="ladda-label">Submit</span></button>
													</div>
												</div>
											</div>
											
											<div class="col-md-3" id="editId" hidden="true">
													<div class="form-group">
													<label>&nbsp;</label> 
													<div class="input-group">
															&nbsp;<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="modifyButton();"><span class="ladda-label">Modify</span></button>
															<!-- &nbsp;<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="deleteButton();"><span class="ladda-label">Delete</span></button> -->
															&nbsp;<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="resetButton();"><span class="ladda-label">Reset</span></button>
													</div>
												</div>
											</div>
											
											<div hidden="true">
												<form:input path="id" id="id" name="id" type="text" class="form-control"/>
												<form:input path="createdDate" id="createdDate" name="createdDate" type="text" class="form-control"/>
												<form:input path="createdBy" id="createdBy" name="createdBy" type="text" class="form-control" />
												<form:input path="status" id="status" name="status" type="text" class="form-control" />
											</div>
											
									     </div>
									<br>
								<!-- <div class="text-center" id="addId">
									<button type="submit" class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="return checkMrDetails();"><span class="ladda-label">Submit</span></button>
								</div>
							
								<div class="text-center" id="editId" hidden="true">
									<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="modifyButton();"><span class="ladda-label">Modify</span></button>
									<button type="button"  class="btn bg-blue"  data-style="expand-right" data-spinner-size="20" onclick="deleteButton();"><span class="ladda-label">Delete</span></button>
								</div> -->
							
					<br>			
					<div id="mrDetails">
						<legend class="text-bold" >Meter Reader Details</legend>
								<div class="row">
									<div class="table-responsive">
										<div class="col-md-16">
										<div class="panel-body" style="margin-top: -3px; padding: 6px;" >
										<table class="table datatable-basic table-bordered" id="tableForm">
											<thead>
					
												<tr class="bg-blue" width="100%">
													<th width="20%">Meter Reader Code</th>
													<th width="20%">Meter Reader Name</th>
													<th width="20%">Address</th>
													<th width="20%">Mobile No</th>
													<th width="20%">Edit</th>
												</tr>
					
											</thead>
											<tbody>
												<c:forEach var="data" items="${mtrReaderList}">
												<tr>
													<td>${data.mrCode}</td>
													<td>${data.mrName}</td>
													<td>${data.address}</td>
													<td>${data.mobileno}</td>
										
													<td class="text-center"><a href="#" onclick="editmrDetails('${data.id}','${data.mrCode}','${data.mrName}','${data.address}','${data.mobileno}','${data.createdDate}','${data.createdBy}','${data.status}')">[Edit]</a></td> 
													
												</tr>
												</c:forEach>
											</tbody>
										</table>
					
									</div>
					
								</div></div>
								</div></div>
							</fieldset>	
						</form:form>								
				</div>			
			</div>	
					
					
					<!-- <div class="row">
						
						<div class="panel panel-flat">
						<div class="panel-heading">
						    <h4 class="panel-title">
						   <center>
						    <span class="label bg-info" STYLE="font-size: 11pt">Meter Reader Details</span></h4>
						    </center>
						    <div class="heading-elements">
								<ul class="icons-list">
			                		<li><a data-action="collapse"></a></li>
			                		<li><a data-action="reload"></a></li>
			                	</ul>
	                		</div>
						</div>

		                <div class="panel-body">
		                 <div class="table-responsive">
							<table class="table">
						
								<tr class="warning"><td>Meter Reader Code&nbsp;<font color="red">*</font></td><td><input type="text" class="form-control" placeholder="Enter MR Code"></td><td>Meter Reader Name&nbsp;<font color="red">*</font></td><td><input type="text" class="form-control" placeholder="Enter MR Name"></td><td>Address</td><td><input type="text" class="form-control" placeholder="Enter MR Address"></td></tr>
								
			                    </tbody>
			                </table>
						</div> 
						
		                <div class="text-left">
		                
								<br><center><button type="submit" class="btn bg-teal-400">Submit</i></button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="modify" class="btn bg-teal-400">Modify</i></button></center>
						</div>
		              </div>

						
					</div> -->

		